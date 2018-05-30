import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
/**
 * A class that reads text data, parsing the text for the "#include" keyword and merging any filenames
 * listed with that keyword.
 * Merges the entire chain of "included" files into one file called "MergedFile.txt" in the directory
 * where the JavaTextMerger code is stored. 
 *
 * Contains unique methods for buffered reading and buffered writing of text data.
 *
 * @author Jerod Dunbar
 * @version 14 June 2017
 */
public class JavaTextMerger{
	public static void main(String[] args) throws IOException{
		String readIn;//A container for the filename passed in by the user.
		
		if (args.length > 0){
			readIn = args[0];
				//This allows the program to take filenames as command-line arguments.
		}
		else{
			//If no command-line arguments are given, prompt user for filename and initialize 
			//readIn to the given token.
			System.out.println("Please enter the name of the file to be read: ");
			System.out.println("(Include the file type suffix.)");
			Scanner scan = new Scanner(System.in);
			readIn = scan.next();
		}
		ArrayList <String> titles = new ArrayList<>();
		
		try{
			fileReader(readIn,titles);
		}
		catch (IOException e){
			System.out.println("IOException Thrown: file not found.");
			e.printStackTrace();
		}
	}
	/**
     * The method fileReader creates a BufferedReader for the fileIn argument and reads it, 
	 * line by line, with an emphasis on finding and handling #include statements. 
	 *
	 * Found #include statements are handled first by splitting the statement to separate the included 
	 * filename and checking that filename against the contents of the files argument. If the files
	 * ArrayList does not contain the filename, it is added. Then a recursive fileReader call is made.
	 * If the filename is contained in files, the include statement is skipped and no recursive method
	 * call is made.
	 *
	 * For every line of text that does not contain an #include statement, fileReader calls fileWriter.
     *
     * @param String fileIn - the name of the file to be read (the first filename is given by the user) 
     * @param ArrayList files - contains the filenames of files already being read in order to break recursion
     */
	static void fileReader (String fileIn, ArrayList files) throws IOException{
			String nextLine = "";
				//Container variable for each line read by the BufferedReader.
			String[] includeStatement;
			String include;
				//Stores the name of a file read in using the #include statement
			InputStreamReader read = new InputStreamReader(new FileInputStream(fileIn));
			BufferedReader bRead = new BufferedReader(read);
		if (files.contains(fileIn)){
			return;
				//In order to avoid infinite loops of file reads:
				//If the ArrayList argument passed into the method already contains the filename 
				//passed in, return.
		}
		else{
			files.add(fileIn);
				//If the ArrayList argument does not contain the filename, add it and
				//carry on reading the file contents.				
			try{	
				while ((nextLine = bRead.readLine()) != null){
					if (nextLine.startsWith("#include")){
						includeStatement = nextLine.split("\"");
						include = includeStatement[1];
						fileReader(include, files);
							//Here, the method parses the read file data for a statment 
							//beginning with #include. That statement is split and stored
							//temporarily in an array "includeStatement" as two strings.
							
							//The string in [1] of includeStatement, i.e. the name of the file
							//to be included, is saved as "include" and passed as an argument to 
							//a recursive call of fileReader.
					}
					else{
						fileWriter(nextLine);
							//For all read lines of text that do not contain #include statements,
							//call fileWriter.
					}
				}
			}
			catch (IOException e){
				System.out.println("IOException Thrown: File not found.");
				e.printStackTrace();
			}
			finally{
				bRead.close();
			}
		}
	}//end of method fileReader
	/**
     * The method fileWriter creates a BufferedWriter for given lines of text to a new file. The file 
	 * that is created is called "MergedFile.txt" and is created in the working directory for this 
	 * program. 
     *
     * @param String toWrite - the String representation of the line of text to be written 
     */
	static void fileWriter (String toWrite) throws IOException{
		FileWriter write = null;
		BufferedWriter bWrite = null;
			
		try{
			write = new FileWriter("MergedFile.txt", true);
			bWrite = new BufferedWriter(write);
			System.out.println(toWrite);
			bWrite.write(toWrite);
			bWrite.newLine();
		}
		catch (IOException e){
			System.out.println("IOException Thrown: ");
			e.printStackTrace();
		}
		finally{
			bWrite.close();
		}
	}//end of method fileWriter
}