package graphex;

import utilities.InputFileReader;




public class Grep {
	
	public static void main(String[] args){
		
		try{
			readRegex(args[0]);
			readFile(args[1]);
		}
		
		catch(Exception e){
			System.out.println("Error in main grep function");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @param regexFileName the file we are reading the regexfrom
	 */
	private static void readRegex(String regexFileName){
		String regexFileAsString = InputFileReader.readFile(regexFileName);
		System.out.println(regexFileAsString);
		//pass this on to NFA construction
	}
	
	
	/**
	 * 
	 * @param fileName the file we are testing the regex on
	 */
	private static void readFile(String fileName){
		String fileAsString = InputFileReader.readFile(fileName);
		System.out.println(fileAsString);
		
		// learn the alphabet
		
		//perform grep functionality
	}
	


}
