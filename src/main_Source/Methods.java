package main_Source;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Methods {
	int num_simpairs = 1000000;
    public static int max_its;
    public static double res;
    int[] firstxcords = new int[num_simpairs];
    int[] firstycords = new int[num_simpairs];
    int secxcord;
    int secycord; 	
    int diftemp;
    int tempx;
    int tempy;
    int tempang;
    private static float[] array;
    private static int length;
    public int radius;
    float outbrain[][] = new float[1024][1024];
    static float brain[][] = new float [1024][1024];
    ByteBuffer buffer = ByteBuffer.allocate(brain.length*Integer.BYTES);
    float angles[] = new float[num_simpairs];
    float difs[] = new float[num_simpairs];
    protected File defaultSaveFile = new File((System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop\\BrainOutput.txt"));
        public static ArrayList<String> RUN() throws IOException  {
     	   ArrayList<String> outbrain = new ArrayList<String>();
     	    float brain[][] =  importRawFile("C:\\Users\\jcase\\Downloads\\vector.raw");
     	    int it = 0;
     	    
     	    for (int i = 0; i<1024; i++){
     	    	for (int j = 0; j<5; j++){
     	    		System.out.print(brain[j][i]  + "	");
     	    	}
     	    	System.out.println();
     	    }
     	    ByteBuffer.allocate(brain.length*Integer.BYTES);
     	    while(it<max_its){
     	    	//creeating cordinate pairs
     	    	ArrayList<ArrayList<Float[]>> bigcords = new ArrayList<ArrayList<Float[]>> ();    
         	    float[] difs = new float[1048576];
         	    for (int i=0; i<1024; i++){
         		   for (int j =0; j<1024; j++){
         			   int[] seccordArray = findRandomPosition(i, j);
         			   Float[] fcs = new Float[2];
         			   fcs[0] = (float) i;
         			   fcs[1] = (float) j;
         			   Float[] scs = new Float[2];
         			   scs[0] = (float) seccordArray[0];
         			   scs[1] = (float) seccordArray[1];
         			   ArrayList<Float[]> cords = new ArrayList<Float[]>();
         			   cords.add(fcs);
         			   cords.add(scs);
         			   bigcords.add(cords);
         			   
         			   
         			   float seccord =  brain[seccordArray[0]][seccordArray[1]];
         			   float dif = findDifference(brain[i][j], seccord);
         			   difs[i+j] = dif;
         			   
         		   }
         	   }
         	    float average = findAverageArray(difs);
         	    float brains[][] = switchPoints(brain, average, bigcords);
         	    for (int i = 0; i < brains.length; i++){
         	    	
         	    	
         	    	
         	    	outbrain.add(brains[i].toString());
         	    	
         	    }
     	    }
     	    return outbrain;
     	    }
        
	public static int swap (int value)
    {
      int b1 = (value >>  0) & 0xff;
      int b2 = (value >>  8) & 0xff;
      int b3 = (value >> 16) & 0xff;
      int b4 = (value >> 24) & 0xff;

      return b1 << 24 | b2 << 16 | b3 << 8 | b4 << 0;
    }
   
	public static float floatSwap (float value)
    {
      int intValue = Float.floatToIntBits (value);
      intValue = swap (intValue);
      return Float.intBitsToFloat (intValue);
    }
  
	public static float[][] importRawFile(String route){
		String fileName = route;
        System.out.println(
                "starting '" + 
  
       fileName + "'");                
         
        try {
            // Use this for reading the data.
            
 
            FileInputStream inputStream = new FileInputStream(fileName);
        //    BufferedInputStream bin = new BufferedInputStream(inputStream);
            DataInputStream din = new DataInputStream(inputStream);
 
            //((nRead = inputStream.read()) != -1) {
                // Convert to String so we can display it.
                // Of course you wouldn't want to do this with
                // a 'real' binary file.
               for (int j = 0; j<1024; j++){
                   for (int i = 0; i < 1024; i++) {
                        float c = din.readFloat();
                        float b=floatSwap(c);
                        brain[j][i]= b;
                        
                        
                         
                   }
                   System.out.println(fileName  + "  row: " + j + " ");
               } 
               //}  
            // Always close files.
            inputStream.close();        
        }
               
 
catch(FileNotFoundException ex) {
          System.out.println(
               "Unable to open file '" + 
 
      fileName + "'");                
        }
        catch(IOException ex) {
           System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
          // Or we could just do this: 
             ex.printStackTrace();
        }
           
               
                 
		return brain;
		
	}

	public void assignArray(float[] array){
		this.array = array;
	}
	
 	public static void sort(float[] inputArr) {
     
    if (inputArr == null || inputArr.length == 0) {
        
    }
    array = inputArr;
    length = inputArr.length;
    quickSort(0, length - 1);
}

	private static void quickSort(int lowerIndex, int higherIndex) {
     
    int i = lowerIndex;
    int j = higherIndex;
    // calculate pivot number, I am taking pivot as middle index number
    float pivot = array[lowerIndex+(higherIndex-lowerIndex)/2];
    // Divide into two arrays
    while (i <= j) {
        
        while (array[i] < pivot) {
            i++;
        }
        while (array[j] > pivot) {
            j--;
        }
        if (i <= j) {
            exchangeNumbers(i, j);
            //move index to next position on both sides
            i++;
            j--;
        }
    }
    // call quickSort() method recursively
    if (lowerIndex < j)
        quickSort(lowerIndex, j);
    if (i < higherIndex)
        quickSort(i, higherIndex);
}
	/**
	 * Switches the two 
	 * @param i
	 * @param j
	 * 
	 */
	public static void exchangeNumbers(int i, int j) {

    float temp = array[i];
    array[i] = array[j];
    array[j] = temp;
}
	/**
	 * 
	 * @param SPoint the value of the first point
	 * @param EPoint the value of the second point
	 * @return Absolute Value of the difference of the points
	 */
	public static float findDifference(float SPoint, float EPoint){
		return Math.abs(SPoint-EPoint);
	}
	/**
	 * @param CurrentPositionRow the y value of the cordinate
	 * @param CurrentPositionCol the x value of the cordinate
	 * @return Returns a random Cordinate + pixels away from the cord you inputed. Is an array, [0] is the row, and [1] is the column
	 */
	public static int[] findRandomPosition(int CurrentPositionRow, int CurrentPositionCol){
		if (CurrentPositionRow >5 && CurrentPositionRow <1018 && CurrentPositionCol >5 && CurrentPositionCol <1019 ){
			int [] finco = new int[2];
			finco[0] = CurrentPositionRow;
			finco[1] = CurrentPositionCol;
			return finco;
		}
		else{
		int Fincords[] = new int[2];
		int randPosition = (int) (Math.random()*100 + 1);
		if (randPosition == 1){
			Fincords[0] = CurrentPositionRow-5;
			Fincords[1] = CurrentPositionCol;
		}
		if (randPosition == 2){
			Fincords[0] = CurrentPositionRow-5;
			Fincords[1] = CurrentPositionCol +1;
		}
		if (randPosition == 3){
			Fincords[0] = CurrentPositionRow-4;
			Fincords[1] = CurrentPositionCol +2;
		}
		if (randPosition == 4){
			Fincords[0] = CurrentPositionRow-3;
			Fincords[1] = CurrentPositionCol +3;
		}
		if (randPosition == 5){
			Fincords[0] = CurrentPositionRow-2;
			Fincords[1] = CurrentPositionCol +4;
		}
		if (randPosition == 6){
			Fincords[0] = CurrentPositionRow-1;
			Fincords[1] = CurrentPositionCol +5;
		}
		if (randPosition == 7){
			Fincords[0] = CurrentPositionRow;
			Fincords[1] = CurrentPositionCol +5;
		}
		if (randPosition == 8){
			Fincords[0] = CurrentPositionRow+1;
			Fincords[1] = CurrentPositionCol +5;
		}
		if (randPosition == 9){
			Fincords[0] = CurrentPositionRow+2;
			Fincords[1] = CurrentPositionCol+4;
		}
		if (randPosition == 10){
			Fincords[0] = CurrentPositionRow+3;
			Fincords[1] = CurrentPositionCol+3;
		}
		if (randPosition == 11){
			Fincords[0] = CurrentPositionRow+4;
			Fincords[1] = CurrentPositionCol+2;
		}
		if (randPosition == 12){
			Fincords[0] = CurrentPositionRow+5;
			Fincords[1] = CurrentPositionCol+1;
		}
		if (randPosition == 13){
			Fincords[0] = CurrentPositionRow+5;
			Fincords[1] = CurrentPositionCol;
		}
		if (randPosition == 14){
			Fincords[0] = CurrentPositionRow+5;
			Fincords[1] = CurrentPositionCol-1;
		}
		if (randPosition == 15){
			Fincords[0] = CurrentPositionRow+4;
			Fincords[1] = CurrentPositionCol-2;
		}
		if (randPosition == 16){
			Fincords[0] = CurrentPositionRow+3;
			Fincords[1] = CurrentPositionCol-3;
		}
		if (randPosition == 17){
			Fincords[0] = CurrentPositionRow+2;
			Fincords[1] = CurrentPositionCol-4;
		}
		if (randPosition == 18){
			Fincords[0] = CurrentPositionRow+1;
			Fincords[1] = CurrentPositionCol-5;
		}
		if (randPosition == 19){
			Fincords[0] = CurrentPositionRow;
			Fincords[1] = CurrentPositionCol-5;
		}
		if (randPosition == 20){
			Fincords[0] = CurrentPositionRow-1;
			Fincords[1] = CurrentPositionCol-5;
		}
		if (randPosition == 21){
			Fincords[0] = CurrentPositionRow-2;
			Fincords[1] = CurrentPositionCol-4;
		}
		if (randPosition == 22){
			Fincords[0] = CurrentPositionRow-3;
			Fincords[1] = CurrentPositionCol-3;
		}
		if (randPosition == 23){
			Fincords[0] = CurrentPositionRow-4;
			Fincords[1] = CurrentPositionCol-2;
		}
		if (randPosition == 24){
			Fincords[0] = CurrentPositionRow-5;
			Fincords[1] = CurrentPositionCol-1;
		}
				return Fincords;
		}
	}
	
	public byte[] convertFloatToByte(float[][] brains){
		byte[] fin = new byte[1024*1024];
		int counter = 0;
		for (int i = 0; i<brains.length; i++){
			for(int j =0; j<brains.length; j++){
				fin[counter] = (byte) brains[i][j];
				counter++;
			}
			
		}
		return fin;
	}
	public void printRaw(byte [] brain, String address){
		try {
			DataOutputStream os = new DataOutputStream(new FileOutputStream(address));
			os.write(brain);
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 * @param array is the float array of diferences
	 * @return the average difference between the cordinates
	 */
	public static float findAverageArray(float[] array){
		float i = 0;
		float j = 0;
		for (float x : array){
			i = i+x;
			j++;
		}
		float b = i/j;
		return b;
		
	}
	
	public static float[][] switchPoints(float[][] brains, float average, ArrayList<ArrayList<Float[]>> bigcords){
		for (int i = 0; i<1024; i++){
			for (int j = 0; j<1024; j++){
				ArrayList<Float[]> cords = bigcords.get(i+j);
				cords.get(0);
				Float[] scs = cords.get(1);
				float scs1 = scs[0];
				int scssc1 = (int) scs1;
				int scssc2 = (int) scs1;
				float dif = findDifference(brains[i][j],brains[scssc1][scssc2] );
				
				if (dif < average){
					brain[scssc1][scssc2] = 0;
				}
				else if (dif> average){
					
				}
			}
		}
		return brains;
		
	}

	
}

