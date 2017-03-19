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
    public static int max_its = 100;
    public static double res;
//    int[] firstxcords = new int[num_simpairs];
//    int[] firstycords = new int[num_simpairs];
//    int secxcord;
//    int secycord; 	
//    int diftemp;
//    int tempx;
//    int tempy;
//    int tempang;
    public static int radius = 2;
    static float brain[][] = new float [1024][1024];
    ByteBuffer buffer = ByteBuffer.allocate(brain.length*Integer.BYTES);
    float angles[] = new float[num_simpairs];
    float difs[] = new float[num_simpairs];
    protected File defaultSaveFile = new File((System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop\\BrainOutput.txt"));
        public static void main(String [] args)  {
        	float[][] smallestItBrain = null;
        	float[][] biggestItBrain = null;
        	float[][] outbrain =new float[1024][1024];
     	    float brain[][] =  importRawFile("C:\\Users\\Amesome Paul Gaming\\Downloads\\cerebrum-PET.raw");
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
     	    	//creating coordinate pairs
     	    	ArrayList<ArrayList<Float[]>> bigcords = new ArrayList<ArrayList<Float[]>> ();    
         	    ArrayList<Float> difs = new ArrayList<Float>();
         	    for (int i=0; i<1024; i++){
         		   for (int j =0; j<1024; j++){
         			   int[] seccordArray = findRandomPosition(i, j,radius);
//         			   System.out.println("Second Cord {" + seccordArray[0] + ", " + seccordArray[1] + "}");
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
         			   difs.add(dif);
         			   
         		   }
         	   }
         	    float average = findAverageArray(difs);
         	    System.out.println("Average Diference:" + average);
         	    int counter= 0;
         	    for(int i =0; i<1024;i++){{
         	    	for(int j = 0; j<1024; j++){
         	    		ArrayList<Float[]> pairs = bigcords.get(counter);
         	    		Float[] firstPair = pairs.get(0);
         	    		Float[] secPair = pairs.get(1);
         	    		float firstXCord = firstPair[0];
         	    		float firstYCord = firstPair[1];
         	    		float secXCord = secPair[0];
         	    		float secYCord = secPair[1];
         	    		switchPoints(brain,average, (int)firstXCord,(int) firstYCord,(int) secXCord, (int)secYCord);
         	    		counter++;
         	    	}
         	    }
         	    	
         	    }
         	    if(it == 1){
         	    	smallestItBrain = brain;
         	    }
         	   if(it == max_its){
        	    	biggestItBrain = brain;
        	    }
         	   File outfile = new File("C:\\Users\\Amesome Paul Gaming\\Desktop\\outbrains\\outputbrain" + it+".txt");
        	    try{
        	    	if (!outfile.exists()){
        	    		outfile.createNewFile();
        	    	}
        	    	StringBuilder b = new StringBuilder();
       			for (int i = 0; i<1024; i++) {
       				for (int j = 0; j<1024; j++){
       					b.append("" +brain[i][j] + ", ");
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
     	    for (int i = 0; i<1024; i++){
     	    	for (int j = 0; j<1024; j++){
     	    		outbrain[i][j] = smallestItBrain[i][j] * biggestItBrain[i][j];
     	    		
     	    	}
     	    }
     	   File outfile = new File("C:\\Users\\Amesome Paul Gaming\\Desktop\\outbrains\\outputbrainMulti.txt");
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
		return Math.abs(SPoint-EPoint)/(SPoint+EPoint);
	}
	/**
	 * @param CurrentPositionRow the y value of the coordinate
	 * @param CurrentPositionCol the x value of the coordinate
	 * @return Returns a random Coordinate + pixels away from the cord you inputed. Is an array, [0] is the row, and [1] is the column
	 */
	public static int[] findRandomPosition(int CurrentPositionRow, int CurrentPositionCol,int rad){
		int Fincords[] = new int[2];
		float randCol = (float) (Math.random() * rad*2 -rad);
		float randRow = (float) (Math.random() * rad*2-rad);
		int finX = (int) ((float) CurrentPositionRow + randRow);
		int finY = (int) ((float) CurrentPositionCol + randCol);
		if(finX >1023){
			finX = (int) (CurrentPositionRow-randRow);
		}
		if (finY >1023){
			finY = (int) (CurrentPositionCol -randCol);
		}
		Fincords[0] = Math.abs(finX);
		Fincords[1] = Math.abs(finY);
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
	/**
	 * 
	 * @param difs2 is the float array of differences
	 * @return the average difference between the coordinates
	 */
	public static float findAverageArray(ArrayList<Float> difs2){
		float i = 0;
		float j = 0;
		for (float x : difs2){
			i = i+x;
			j++;
		}
		float b = i/j;
		
		return b;
		
	}
	public static void switchIndividualPoints(){
		
	}
	public static void switchPoints(float[][] brains, float average, int firstXCord, int firstYCord, int secXCord, int secYCord){
	
				
				if (dif > average && brains[i][j] >44){
					int[] newPos = findRandomPosition(i,j,radius);
				//	float newVal  = (float) ( (((Math.random()*10 + 1)-5)/100)*brains1[i][j]);
					float geometricAv1 = (float) Math.sqrt(brain[i][j]);
					float geometricAv2 = (float) Math.sqrt(brains[(int) secondCordsSetXCord][(int) secondCordsSetYCord]);
					float newVal  = (float) (geometricAv1*geometricAv2);
				if (dif > average && brains[firstXCord][firstYCord] >44){
					int[] newPos = findRandomPosition(firstXCord,firstYCord,radius);
					int newXCord = newPos[0];
					int newYCord = newPos[1];
					switchPoints(brains, average, firstXCord,firstYCord,newXCord,newYCord);
//					float newVal  = (float) ( (((Math.random()*10 + 1)-5)/100)*brains1[i][j]);
//					float geometricAv1 = (float) Math.sqrt(brain[i][j]);
//					float geometricAv2 = (float) Math.sqrt(brains[(int) secondCordsSetXCord][(int) secondCordsSetYCord]);
//					float newVal  = (float) (geometricAv1*geometricAv2);
					// float newVal  = (float) (brains[i][j] + (((Math.random()*10 + 1)-5)/100)*brains[i][j]);
					brains1[(int) secondCordsSetXCord][(int) secondCordsSetYCord] = newVal;
				//	brains[(int) secondCordsSetXCord][(int) secondCordsSetYCord] =brains1[(int) secondCordsSetXCord][(int) secondCordsSetYCord]*brains[(int) secondCordsSetXCord][(int) secondCordsSetYCord]
				}
				else if (dif < average){
					float newval = (brains[firstXCord][firstYCord] + brains[secXCord][secYCord])/2;
					brains[firstXCord][firstYCord] = newval;
					brains[secXCord][secYCord] = newval;
				}
}}