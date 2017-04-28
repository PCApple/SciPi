package main_Source;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Methods {
	// the total amount of iterations for a single run
    public static int max_its = 100;
    // the radius of the search area in pixels
    public static int radius = 5;
    // the values of the image
    static float brain[][] = new float [1024][1024];
    // the input raw file
    public static String IMPORT_FILE = "C:\\Users\\JACHICKEN\\Desktop\\meme.txt";
    // the path for the output folder
    public static final String OUTPUT_FOLDER= "C:\\Users\\JACHICKEN\\Desktop\\outputtest\\output";
    /**
     * the main program, inputs a raw image in little endian and outputs it as a text doc at the OUTPUT_FOLDER path
     */
    
        public static void main()  {
        	//declaring the brain array as the read in raw file
     	    brain =  importTextFile(IMPORT_FILE);
     	    // Initializing the iteration count
     	    int it = 0;
     	    // printing out the input image
     	    for (int i = 0; i<1024; i++){
     	    	for (int j = 0; j<5; j++){
     	    		System.out.print(brain[j][i]  + "	");
     	    	}
     	    	System.out.println();
     	    }
     	    //starting the iteration sequence
     	    while(it<max_its){
     	    	// adding the iteration count up by 1
     	    	it++;
     	    	//printing the iteration
     	    	System.out.println("Runnning iteration " + it);
     	    	//creating coordinate pairs
     	    	ArrayList<ArrayList<Float[]>> bigcords = new ArrayList<ArrayList<Float[]>> ();    
         	    ArrayList<Float> difs = new ArrayList<Float>();
         	    //finding the coordinate pairs and differences
         	    for (int i=0; i<1024; i++){
         		   for (int j =0; j<1024; j++){
         			   // finding a new random position and placing it into seccordArray
         			   int[] seccordArray = findRandomPosition(i, j,radius);
         			   // setting the parent pair into firstCordSet
         			   Float[] firstCordsSet = new Float[2];
         			   firstCordsSet[0] = (float) i;
         			   firstCordsSet[1] = (float) j;
         			   // Setting the child pair to secondCordSet
         			   Float[] secondCordsSet = new Float[2];
         			   secondCordsSet[0] = (float) seccordArray[0];
         			   secondCordsSet[1] = (float) seccordArray[1];
         			   // grouping the two pairs in an arraylist
         			   ArrayList<Float[]> cords = new ArrayList<Float[]>();
         			   cords.add(firstCordsSet);
         			   cords.add(secondCordsSet);
         			   //adding grouped pairs into full arraylist, bigcords
         			   bigcords.add(cords);
         			   // getting the value of the child pair
         			   float seccord =  brain[seccordArray[0]][seccordArray[1]];
         			   //finding the difference between the pairs
         			   float dif = findDifference(brain[i][j], seccord);
         			   // adding the difference to the difs arraylist
         			   difs.add(dif);
         			   
         		   }
         	   }
         	    // finding the average of the differences
         	    float average = findAverageArray(difs);
         	    // printing the average
         	    System.out.println("Average Diference:" + average);
         	    // creating counter
         	    int counter= 0;
         	    for(int i =0; i<1024;i++){{
         	    	for(int j = 0; j<1024; j++){
         	    		// pulling pair from bigcords
         	    		ArrayList<Float[]> pairs = bigcords.get(counter);
         	    		// pulling parent pair from pairs
         	    		Float[] firstPair = pairs.get(0);
         	    		//pulling child pair from pairs
         	    		Float[] secPair = pairs.get(1);
         	    		// setting coordinates of parent pair 
         	    		float firstXCord = firstPair[0];
         	    		float firstYCord = firstPair[1];
         	    		// setting coordinates of child pair 
         	    		float secXCord = secPair[0];
         	    		float secYCord = secPair[1];
         	    		// applies filter to image
         	    		switchPoints(brain,average, (int)firstXCord,(int) firstYCord,(int) secXCord, (int)secYCord);
         	    		// adding counter
         	    		counter++;
         	    	}
         	    }
         	    	
         	    }
         	   // creates a file for the output
         	   File outfile = new File(OUTPUT_FOLDER + it+".txt");
        	    try{
        	    	//checking if the file exists already
        	    	if (!outfile.exists()){
        	    		// if the file does not exist, it creates a new one
        	    		outfile.createNewFile();
        	    	}
        	    	// creating a  new StringBuilder to create the output
        	    	StringBuilder builder = new StringBuilder();
       			for (int i = 0; i<1024; i++) {
       				for (int j = 0; j<1024; j++){
       					// adding each value, followed by a comma
       					builder.append("" +brain[i][j] + ", ");
       				}
       				// adding a carriage return after each row
       				builder.append("\n");
       			}
       			// creating a new String to hold the image
       			String outputString = builder.toString();
       			// writing the image to a text file
       			Files.write(outfile.toPath(), outputString.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        	    	}
        	    catch (FileNotFoundException e1) {
        	    	e1.printStackTrace();
        	    } catch (IOException e) {
        	    	e.printStackTrace();
        	    }
     	    }
     	    }
     /**
      * swapping the bytes from little to big endian   
      * @param value
      * @return swapped value
      */
	public static int swap (int value){
      int b1 = (value >>  0) & 0xff;
      int b2 = (value >>  8) & 0xff;
      int b3 = (value >> 16) & 0xff;
      int b4 = (value >> 24) & 0xff;
      return b1 << 24 | b2 << 16 | b3 << 8 | b4 << 0;
    }
	/**
	 * converting the bytes to floats
	 * @param value
	 * @return the float value
	 */
	public static float floatSwap (float value){
	
		int intValue = Float.floatToIntBits (value);
		intValue = swap (intValue);
		return Float.intBitsToFloat (intValue);
    }
	/**
	 * reads in the file and places it into a 1024 by 1024 float array
	 * @param route the raw file path
	 * @return the image in array form
	 */
	public static float[][] importTextFile(String route){
		String filename = route;
		float[][] output = new float[1024][1024];
		File file = new File(filename);
		try {
			List<String> lines = Files.readAllLines(file.toPath());
			for (int i =0; i<1024; i++){
				System.out.print(".");
				String line = lines.get(i);
				String[] values = line.split("\t");
				for (int j = 0; j<1024; j++){
					output[i][j] = Float.parseFloat(values[j]);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
		
	}
	public static float[][] importRawFile(String route){
		// setting the filename to the route
		String fileName = route;                       
        try {
        	// reading in the bytes and wrapping it in a dataIpnput Stream
        	FileInputStream inputStream = new FileInputStream(fileName);
            DataInputStream din = new DataInputStream(inputStream);
            for (int j = 0; j<1024; j++){
            	   for (int i = 0; i < 1024; i++) {
                	   // reading in each value and swapping the byte data
                	   float c = din.readFloat();
                	   float b=floatSwap(c);
                	   //setting the value to a position in the brain array
                	   brain[j][i]= b;
            	   	}
            	   // printing a dot
            	   System.out.print(".");
                 
            } 
            // closing the inputStream din
            inputStream.close();
            din.close();
        }
        catch(FileNotFoundException ex) {
        	System.out.println("Unable to open file '" +   fileName + "'");                
        }
        catch(IOException ex) {
        	System.out.println("Error reading file '"  + fileName + "'");                  
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
	 * @param rad the radius of the search area
	 * @return Returns a random Coordinate + pixels away from the cord you inputed. Is an array, [0] is the row, and [1] is the column
	 */
	public static int[] findRandomPosition(int CurrentPositionRow, int CurrentPositionCol,int rad){
		// creating a new array for the coordinate pair
		int Fincords[] = new int[2];
		// finding new random x and y coordinates offsets
		float randCol = (float) (Math.random() * rad*2 -rad);
		float randRow = (float) (Math.random() * rad*2-rad);
		// adding offsets to the coordinates
		int finX = (int) ((float) CurrentPositionRow + randRow);
		int finY = (int) ((float) CurrentPositionCol + randCol);
		// checks if the coordinate is past 1023
		if(finX >1023){
			// flips the offset value
			finX = (int) (CurrentPositionRow-randRow);
		}
		// checks if the coordinate is past 1023
		if (finY >1023){
			// flips the offset value
			finY = (int) (CurrentPositionCol -randCol);
		}
		// setting the x and y coordinates into the array
		Fincords[0] = Math.abs(finX);
		Fincords[1] = Math.abs(finY);
		return Fincords;
		
	}

	/**
	 * finds the average of the differances
	 * @param difs2 is the float array of differences
	 * @return the average difference between the coordinates
	 */
	public static float findAverageArray(ArrayList<Float> difs2){
		
		float totalSum = 0;
		float totalValueCount = 0;
		for (float value : difs2){
			if(value>0 && value <255){
				totalSum = totalSum+value;
				totalValueCount++;
			}
		}
		float average = totalSum/totalValueCount;
		
		return average;
		
	}
	public static void switchPoints(float[][] brains, float average, int firstXCord, int firstYCord, int secXCord, int secYCord){
				// finding the difference between the two coordinates
				float dif = findDifference(brains[firstXCord][firstYCord],brains[secXCord][secYCord] );
				// checking if the difference is above the average and not unnecessary noise
				if (dif > average && brains[firstXCord][firstYCord] >44){
					// creates a new random position to check
					int[] newPos = findRandomPosition(firstXCord,firstYCord,radius);
					int newXCord = newPos[0];
					int newYCord = newPos[1];
					// repeats the same process
					switchPoints(brains, average, firstXCord,firstYCord,newXCord,newYCord);
				}
				// checks if the difference is less than the average
				else if (dif < average){
					// creates a new value that is the average of the parent and the child pairs
					float newval = (brains[firstXCord][firstYCord] + brains[secXCord][secYCord])/2;
					// sets the child and parent pairs to the average
					brains[firstXCord][firstYCord] = newval;
					brains[secXCord][secYCord] = newval;
				}
}}