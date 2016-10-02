// John Lester & Stephanie Gan
// CST 338-30_FA16
// Optical Barcode Readers (M4)

public class Assignment4 {
	public static void main(String[] args)
		{
	      String[] sImageIn =
	      {
	         "                                               ",
	         "                                               ",
	         "                                               ",
	         "     * * * * * * * * * * * * * * * * * * * * * ",
	         "     *                                       * ",
	         "     ****** **** ****** ******* ** *** *****   ",
	         "     *     *    ****************************** ",
	         "     * **    * *        **  *    * * *   *     ",
	         "     *   *    *  *****    *   * *   *  **  *** ",
	         "     *  **     * *** **   **  *    **  ***  *  ",
	         "     ***  * **   **  *   ****    *  *  ** * ** ",
	         "     *****  ***  *  * *   ** ** **  *   * *    ",
	         "     ***************************************** ",  
	         "                                               ",
	         "                                               ",
	         "                                               "

	      };      
	            
	         
	      
	      String[] sImageIn_2 =
	      {
	            "                                          ",
	            "                                          ",
	            "* * * * * * * * * * * * * * * * * * *     ",
	            "*                                    *    ",
	            "**** *** **   ***** ****   *********      ",
	            "* ************ ************ **********    ",
	            "** *      *    *  * * *         * *       ",
	            "***   *  *           * **    *      **    ",
	            "* ** * *  *   * * * **  *   ***   ***     ",
	            "* *           **    *****  *   **   **    ",
	            "****  *  * *  * **  ** *   ** *  * *      ",
	            "**************************************    ",
	            "                                          ",
	            "                                          ",
	            "                                          ",
	            "                                          "

	      };
	     
	      BarcodeImage bc = new BarcodeImage(sImageIn);
	      DataMatrix dm = new DataMatrix(bc);
	     
	      // First secret message
	      dm.translateImageToText();
	      dm.displayTextToConsole();
	      dm.displayImageToConsole();
	      
	      // second secret message
	      bc = new BarcodeImage(sImageIn_2);
	      dm.scan(bc);
	      dm.translateImageToText();
	      dm.displayTextToConsole();
	      dm.displayImageToConsole();
	        
	      // third message
	      dm.readText("What a great resume builder this is!");
	      dm.generateImageFromText();
	      dm.displayTextToConsole();
	      dm.displayImageToConsole();
	      
	      // create your own message
	      dm.readText("Dinosaurs are great. The stegosaurus is the best.");
	      dm.generateImageFromText();
	      dm.displayTextToConsole();
	      dm.displayImageToConsole();
	   }   
}

// Defines the I/O and basic methods of any barcode class which might 
// implement it.
interface BarcodeIO
{
   public boolean scan(BarcodeImage bc);
   public boolean readText(String text);
   public boolean generateImageFromText();
   public boolean translateImageToText();
   public void displayTextToConsole();
   public void displayImageToConsole();
}

// One of the main member-objects of the class DataMatrix, it describes the 
// 2D dot-matrix pattern, or "image".
class BarcodeImage implements Cloneable {
   public static final int MAX_HEIGHT = 30;
   public static final int MAX_WIDTH = 65;
   private boolean imageData[][];

   // Default constructor
   public BarcodeImage() {
      imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
      for(int y=0; y<MAX_HEIGHT; y++) {
         for(int x=0; x<MAX_WIDTH; x++) {
            imageData[y][x]=false;
         }
      }
   }

   // Takes a 1D array of Strings and converts it to the internal 2D array 
   // of booleans. 
   public BarcodeImage(String[] str_data) {
      this();
      if(checkSize(str_data)) {
	      for(int i = 0; i < str_data.length; i++) {
	         for(int j = 0; j < str_data[0].length(); j++) {
	            if(str_data[i].charAt(j) == '*') {
	               setPixel(MAX_HEIGHT-(str_data.length-i), j, true);
	            } else {
	               setPixel(MAX_HEIGHT-(str_data.length-i), j, false);
	            }
	         }
	      }
      }
   }

   // Accessor
   public boolean getPixel(int row, int col) {
      return imageData[row][col];
   }

   // Mutator
   public boolean setPixel(int row, int col, boolean value) {
      if(row >= MAX_HEIGHT || col >= MAX_WIDTH) {
         return false;
      } else {
         imageData[row][col] = value;
         return true;
      }
   }

   // checking the incoming data for every conceivable size or null error
   private boolean checkSize(String[] data) {
      if(data == null) {
         return false;
      }
      if(data.length > MAX_HEIGHT) {
         return false;
      }
      return true;
   }

   public void displayToConsole() {
      int row, col;
      System.out.println();
      for(row = 0; row < MAX_HEIGHT; row++) {
         for(col = 0; col < MAX_WIDTH; col++) {
            if(getPixel(row, col) == true) {
               System.out.print("*");
            } else {
               System.out.print(" ");
            }
         }
         System.out.println();
      }
   }

   // overrides the method of that name in Cloneable interface
   public Object clone() throws CloneNotSupportedException {
      BarcodeImage newBC = (BarcodeImage)super.clone();
      for(int i=0; i<MAX_HEIGHT; i++) {
         for(int j=0; j<MAX_WIDTH; j++) {
            newBC.imageData[i][j] = this.getPixel(i, j);
         }
      }
      return newBC;
   }
}

// Does the 2D array format and a left and bottom BLACK "spine" as well as an 
// alternating right and top BLACK-WHITE pattern.
class DataMatrix implements BarcodeIO {
   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';
   private BarcodeImage image;
   private String text;
   private int actualWidth;
   private int actualHeight;

   // Default constructor.
   public DataMatrix() {
      actualWidth = 0;
      actualHeight = 0;
      text = "";
      image = new BarcodeImage();
   }

   // Sets the image but leaves the text at its default value.
   public DataMatrix(BarcodeImage image) {
      this();
      scan(image);
   }

   // Sets the text but leaves the image at its default value.
   public DataMatrix(String text) {
      this();
      readText(text);
   }

   // Accepts a text string to be encoded in an image.
   public boolean readText(String text) {
	   if (text == null) {
		   return false;
	   }
	   
	   else {
		   this.text = text;
		   
		   return true;
	   }
   }

   // Accepts some image, represented as a BarcodeImage object
   // and stores a copy of this image.
   public boolean scan(BarcodeImage image) {
      try {
         this.image = (BarcodeImage) image.clone();
      } 
      catch(CloneNotSupportedException e) {
      }
      
      cleanImage();
      
      actualHeight = computeSignalHeight();
      actualWidth = computeSignalWidth();
      
      return true;
   }

   private int computeSignalWidth() {
      int width = 0;
      for(int i=0; i<image.MAX_WIDTH; i++) {
         if(image.getPixel(image.MAX_HEIGHT-1, i) == true) {
            width++;
         }
      }
      
      return width;
   }

   private int computeSignalHeight() {
      int height = 0;
      for(int i=0; i<BarcodeImage.MAX_HEIGHT; i++) {
         if(image.getPixel(i, 0) == true) {
            height++;
         } 
      }
      
      return height;
   }

   // Looks at the internal text stored in the implementing class and 
   // produces a companion BarcodeImage
   public boolean generateImageFromText() {
	   clearImage();
	   char[] textArray = text.toCharArray();
	   
	   for (int i = 1; i < textArray.length + 1; i++) {
		   char[] binaryColumn = Integer.toBinaryString((int)textArray[i-1]).toCharArray();
		   
		   for (int j = BarcodeImage.MAX_HEIGHT-2; j > BarcodeImage.MAX_HEIGHT-binaryColumn.length-2; j--) {
			   if (binaryColumn[binaryColumn.length-(BarcodeImage.MAX_HEIGHT-1-j)] == '1') {
				   //set pixels to true
				   image.setPixel(j, i, true);
			   }
			   
			   else {
				   //set pixels to false
				   image.setPixel(j, i, false);
			   }
		   }
	   }
	   
	   //setting the left edge
	   for (int i = BarcodeImage.MAX_HEIGHT - 1; i >= BarcodeImage.MAX_HEIGHT - 9; i--) {
		   image.setPixel(i, 0, true);
	   }
	   
	   for (int i = 0; i <= textArray.length; i++) {
		   image.setPixel(BarcodeImage.MAX_HEIGHT-1, i, true);
		   
		   if (i%2==0) {
			   image.setPixel(BarcodeImage.MAX_HEIGHT-10, i, true);
		   }
	   }
	   
	   actualWidth = computeSignalWidth();
	   actualHeight = computeSignalHeight();
	   
      return true;
   }

   // Looks at the internal image stored in the implementing class,
   // and produces a companion text string
   public boolean translateImageToText() 	{
	   cleanImage();
	   	
	   text = "";
      for(int i = 1 ; i < actualWidth - 1; i++) {
         text += (readCharFromCol(i));
      }
      
      return false;
   }

   // prints out the text string to the console
   public void displayTextToConsole() {
      System.out.println(text);
   }

   // prints out the image to the console
   public void displayImageToConsole() {
      int row, col;
      System.out.println();
      for(row = image.MAX_HEIGHT-actualHeight; row < image.MAX_HEIGHT; row++) {
         for(col = 0; col < actualWidth; col++) {
            if(image.getPixel(row, col) == true) {
               System.out.print("*");
            } else {
               System.out.print(" ");
            }
         }
         System.out.println();
      }
      System.out.println();
   }

   //Converts a specified column in BarcodeImage and returns the char value.
   private char readCharFromCol(int col) {
      String numVal = "";
      for(int i = BarcodeImage.MAX_HEIGHT-actualHeight+1; i<BarcodeImage.MAX_HEIGHT-1; i++) {
         if(image.getPixel(i, col)) {
            numVal += "1";
         }
         else {
        	 numVal += "0";
         }
      }
      return ((char)Integer.parseInt(numVal, 2));
   }

   private boolean writeCharToCol(int col, int code) {
      String binary = Integer.toBinaryString(code);
      image.setPixel(image.MAX_HEIGHT, col, true);

      if(col%2==0) {
         image.setPixel(image.MAX_HEIGHT-(binary.length()+1), col, true);
      }
      for(int i=0; i<binary.length(); i++) {
         if(binary.charAt(i) == '1') {
            image.setPixel((image.MAX_HEIGHT-1)-(i+1), col, true);
         } else {
            image.setPixel((image.MAX_HEIGHT-1)-(i+1), col, false);
         }
      }
      return true;
   }
   
   //moves the signal to the lower-left of the 2D array
   private void cleanImage() {
	   //making variables to store starting positions
	   int verticalStart = 0;
	   int horizontalStart = 0;
	   
	   //traversing image vertically
	   for (int i = BarcodeImage.MAX_HEIGHT-1; i >= 0; i--) {
		   //traversing image horizontally
		   for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++) {
			   if (image.getPixel(i, j) && verticalStart == 0 && horizontalStart == 0) {
				   //setting the starting positions to the first solid pixel
				   verticalStart = i;
				   horizontalStart = j;
			   }
		   }
	   }
	   
	   //making another loop to copy the image to the bottom left corner
	   //pixel by pixel
	   for (int i = 0; i < BarcodeImage.MAX_HEIGHT; i++) {
		   for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++) {
			   if(verticalStart - i >= 0 && horizontalStart + j < BarcodeImage.MAX_WIDTH) {
				   image.setPixel(BarcodeImage.MAX_HEIGHT-i-1, j, image.getPixel(verticalStart-i, horizontalStart+j));
			   }
		   }
	   }
   }
   
   //sets the image to white =  false
   private void clearImage() {
	   for (int i = BarcodeImage.MAX_HEIGHT-1; i >=0; i--) {
		   for (int j = 0; j < BarcodeImage.MAX_WIDTH; j++) {
			   image.setPixel(i,  j,  false);
		   }
	   }
   }
}

/*
CSUMB CSIT online program is top notch.

* * * * * * * * * * * * * * * * * * * * *
*                                       *
****** **** ****** ******* ** *** *****
*     *    ******************************
* **    * *        **  *    * * *   *
*   *    *  *****    *   * *   *  **  ***
*  **     * *** **   **  *    **  ***  *
***  * **   **  *   ****    *  *  ** * **
*****  ***  *  * *   ** ** **  *   * *
*****************************************

You did it!  Great work.  Celebrate.

* * * * * * * * * * * * * * * * * * *
*                                    *
**** *** **   ***** ****   *********
* ************ ************ **********
** *      *    *  * * *         * *
***   *  *           * **    *      **
* ** * *  *   * * * **  *   ***   ***
* *           **    *****  *   **   **
****  *  * *  * **  ** *   ** *  * *
**************************************

What a great resume builder this is!

* * * * * * * * * * * * * * * * * * *
*
***** * ***** ****** ******* **** **
* ***********************************
**  *    *  * * **    *    * *  *  *
* *               *    **     **  *
**  *   * * *  * ***  * ***  *
**      **    * *    *     *    *  *
** *  * * **   *****  **  *    ** ***
*************************************

Dinosaurs are great. The stegosaurus is the best.

* * * * * * * * * * * * * * * * * * * * * * * * *
*
********** *** *****  *** *********** ** *** ****
* ******************** ***************************
*    * ***  *   *  *  *   **   * ****  * *     **
* ***               *  *      *       *   *      *
** **  *     * * * ** * *  ****  * *     * *  * **
*  ***  **  *  **   *     *  ***  * *  *     * * *
* * **** * * * * **     * * ****** ** **   *  **
**************************************************
*/