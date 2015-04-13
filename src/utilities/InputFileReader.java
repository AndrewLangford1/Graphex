package utilities;



/**
 * 
 * Class to read input files
 * 
 * 
 */

import java.io.BufferedReader;
import java.io.FileReader;

public class InputFileReader {
	
	
	/**
	 * Reads a file
	 * @param fileName the name of the file to be read
	 */
	public static String readFile(String fileName){
		
		try{
			
			//read the file
			FileReader  fileReader = new FileReader(fileName);
			
			//set up the buffered reader for this file reader
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			StringBuilder builder = new StringBuilder();
			
			////build the string from the file
			String line = bufferedReader.readLine();

			while(line != null){
				builder.append(line);
				builder.append(System.lineSeparator());
				line = bufferedReader.readLine();
			}
			
			bufferedReader.close();
			//return the file if all goes well
			return builder.toString();
		}
		
		catch(Exception e){
			System.out.println("Error reading file");
			e.printStackTrace();
		}
		
		
		//return null otherwise.
		return null;
	}
}
