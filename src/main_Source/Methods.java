package main_Source;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Methods {
	int num_simpairs = 1000000;
	public File importFile;
    public static int max_its = 1000;
    public static double res;
    int[] firstxcords = new int[num_simpairs];
    int[] firstycords = new int[num_simpairs];
    int secxcord;
    int secycord; 	
    int diftemp;
    int tempx;
    int tempy;
    int tempang;
    public int radius = 2;
    static float brain[][] = new float [1024][1024];
    ByteBuffer buffer = ByteBuffer.allocate(brain.length*Integer.BYTES);
    float angles[] = new float[num_simpairs];
    float difs[] = new float[num_simpairs];
    protected File defaultSaveFile = new File((System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop\\BrainOutput.txt"));
        public static void main(String [] args)  {
        	float[][] outbrain = null;
     	    float brain[][] =  importRawFile("C:\\Users\\JACHICKEN\\Downloads\\cerebrum-PET.raw");
     	    int it = 0;
     	    
     	    for (int i = 0; i<1024; i++){
     	    	for (int j = 0; j<5; j++){
     	    		System.out.print(brain[j][i]  + "	");
     	    	}
     	    	System.out.println();
     	    }
     	    ByteBuffer.allocate(brain.length*Integer.BYTES);
     	    while(it<max_its){
     	    	it++;
     	    	System.out.println("Runnning iteration " + it);
     	    	//creeating cordinate pairs
     	    	ArrayList<ArrayList<Float[]>> bigcords = new ArrayList<ArrayList<Float[]>> ();    
         	    float[] difs = new float[1048576];
         	    for (int i=0; i<1024; i++){
         		   for (int j =0; j<1024; j++){
         			   int[] seccordArray = findRandomPosition(i, j);
         			   Float[] firstCordsSet = new Float[2];
         			   firstCordsSet[0] = (float) i;
         			   firstCordsSet[1] = (float) j;
         			   Float[] secondCordsSet = new Float[2];
         			   secondCordsSet[0] = (float) seccordArray[0];
         			   secondCordsSet[1] = (float) seccordArray[1];
         			   ArrayList<Float[]> cords = new ArrayList<Float[]>();
         			   cords.add(firstCordsSet);
         			   cords.add(secondCordsSet);
         			   bigcords.add(cords);
         			   
         			   
         			   float seccord =  brain[seccordArray[0]][seccordArray[1]];
         			   float dif = findDifference(brain[i][j], seccord);
         			   difs[i*1024+j] = dif;
         			   
         		   }
         	   }
         	    float average = findAverageArray(difs);
         	    outbrain = switchPoints(brain, average, bigcords);
         	    
     	    }
    
     	    File outfile = new File("C:\\Users\\JACHICKEN\\Desktop\\outputbrain.txt");
     	    try{
     	    	if (!outfile.exists()){
     	    		outfile.createNewFile();
     	    	}
     	    	StringBuilder b = new StringBuilder();
    			for (int i = 0; i<1024; i++) {
    				for (int j = 0; j<1024; j++){
    					b.append("" +outbrain[i][j] + ", ");
    				}
    				b.append("\n");
    			}
    			String outputString = b.toString();
    			Files.write(outfile.toPath(), outputString.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
     	    	}
     	    	catch (FileNotFoundException e1) {
     				e1.printStackTrace();
     			} catch (IOException e) {
     				e.printStackTrace();
     			}
     	    
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
                 System.out.print(".");
                 
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
		if (Fincords[0]<0){
			Fincords[0] = Math.abs(Fincords[0]);
		}
		if (Fincords[1]<0){
			Fincords[1] = Math.abs(Fincords[1]);
		}
		if (Fincords[0] >=1024){
			Fincords[0] = CurrentPositionRow;
		}
		if (Fincords[1] >=1024){
			Fincords[1] = CurrentPositionCol;
		}
				return Fincords;
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
			
			e.printStackTrace();
		} catch (IOException e) {
			
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
				Float[] secondCordsSet = cords.get(1);
				float secondCordsSet1 = secondCordsSet[0];
				int secondCordsSetsc1 = (int) secondCordsSet1;
				int secondCordsSetsc2 = (int) secondCordsSet1;
				float dif = findDifference(brains[i][j],brains[secondCordsSetsc1][secondCordsSetsc2] );
				
				if (dif < average){
					brain[secondCordsSetsc1][secondCordsSetsc2] = 0;
				}
				else if (dif> average){
					
				}
			}
		}
		return brains;
		
	}

	
}

