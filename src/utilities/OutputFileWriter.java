package utilities;

import java.io.*;
import java.util.ArrayList;


public class OutputFileWriter {
	
	
	public static void writeToFile(String fileName, ArrayList<String> lines){
		try {
			//get the file from the command line
			File file =  new File(fileName);
			
			
			//create the file writer for the file
			FileWriter fileWriter = new FileWriter(file);
			
			
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			for(String line : lines){
				bufferedWriter.newLine();
				bufferedWriter.write(line);
			}
			
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
