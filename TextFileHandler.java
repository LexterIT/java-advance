

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TextFileHandler {
	
	private String pathToFile;
	private String fileName;
	private Table table;
	private Scanner sc = new Scanner(System.in);
	private String[] arguments;

	public TextFileHandler() {
		pathToFile = "../J/";
		fileName = "samplejavatexts.txt";
	}

	public TextFileHandler(String[] args) {
		this();
		arguments = args;
	}

	public String generateSetOfPairs() {
		String text = "";
		String stringTemp = "";
		String[] args = arguments;
		if(args != null) {
			try { 
				this.fileName = args[0];
			} catch(ArrayIndexOutOfBoundsException e) {
				this.fileName = "samplejavatexts.txt";
				System.out.println("No specified textfile found! Using program's own textfile!");
			}
		}
		try {
			File file = new File(pathToFile + fileName);
			BufferedReader br = new BufferedReader(new FileReader(file));	
			while((stringTemp = br.readLine()) != null) {
				text += stringTemp + "\n";
			}
		} catch(IOException e) {
			System.out.println("txt file cannot be found, Creating new txt file named "+fileName+" with random generated pairs");
			text = createOwnKV();
			createFile(text);
		}
		return text;
	}

	public String createOwnKV(){
		int rows, cols;
		String text="";
		try {
			System.out.println("File cannot be found, create your own table with random generated key-values");
			System.out.println("Enter number of Rows:");
			rows = sc.nextInt();
			System.out.println("Enter number of Columns:");
			cols = sc.nextInt();
			text = keyValueGenerator(rows, cols);
		} catch(InputMismatchException e) {
			System.out.println("Invalid input! Program closing!");
			System.exit(0);
		}
		return text;
	}

	public String keyValueGenerator(int rows, int cols) {
		char charVal;
		String text="";
		for(int i=0; i<rows; i++) {
			for(int a=0; a<cols; a++) {
				text += "( ";
				text += threeCharGenerator();
				text += ",";
				text += threeCharGenerator();
				text += " )";
			}
			text += "\n";
		}
		return text;
	}

	public String threeCharGenerator(){
		int numbersofAscii = 95;
		String threeChar="";
		char charVal;
		for(int i=0; i<3; i++) {
			int value = 32 + (int) (Math.random() * numbersofAscii);
			charVal = (char) value;
			threeChar += charVal;
		}
		return threeChar;
	}


	public void createFile(String text) {
		try {
			File newFile = new File(pathToFile+fileName);
			if(!newFile.exists()) {
				newFile.createNewFile();
			}

			FileWriter fw = new FileWriter(newFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(text);
			bw.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}


}