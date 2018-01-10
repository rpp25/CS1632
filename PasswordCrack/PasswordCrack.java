//author rpp25
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;

public class PasswordCrack {
	public static DLBTrie goodPass = new DLBTrie();
	public static void main(String[] args) throws IOException {
		//find();
		if (args[0].equals("-check")){
			
			/*user will enter passwords. The program will search through a dlb Trie containing
			valid passwords. If the password the user entered is valid, the program returns 
			the time it took to get find that match in millseconds. If not, the program gives
			you 10 suggestions from the good passwords that the user can use
			*/
			System.out.println("Setting up...");
			BufferedReader reader = new BufferedReader(new FileReader(
					"all_passwords.txt"));
			String string;
			while ((string = reader.readLine()) != null) {
				goodPass.addWord(string.substring(0, 5));
			}
			reader.close();
			//findPrefix("cl34m");
			Scanner in = new Scanner(System.in);
			String input;
			boolean foo = false;
			while (true) {
				System.out.println("Enter a password (or enter \"quit\" to quit): ");
				input = in.nextLine();
				if (input.equals("quit"))
					System.exit(0);
				if (input.length() != 5) {
					while (input.length() != 5) {
						System.out.println("re-enter your password: ");
						input = in.nextLine();
					}
				}
				long timeStart = System.nanoTime();
				long time;
				final float milli;
				
				if (checkMethod(input)){
					time = System.nanoTime() - timeStart;
					milli = time/1000000;
					System.out.println(input+" found in " +time+" ms");
				}
				else {
					System.out.println("Bad password. Suggestions:");
					prefix(input);
				}
			}

		}

		else if (args[0].equals("-find")){
			/*find() generates a dlb trie full of bad passwords and constructs a file containing
			all the valid passwords based on the trie*/
			find();
		}
	}
		
	public static void find() throws IOException{
		DLBTrie dlb = new DLBTrie();
		
		BufferedReader read = new BufferedReader(new FileReader(
				"dictionary.txt")); 
		String string;

		while ((string = read.readLine()) != null) {
			if (string.length() <= 5 && string.length() > 0) {
				dlb.addWord(string);
				numerateWords(string, dlb);
				//System.out.println(s);
			}
		}

		read.close();
		createGoodPass(dlb);

		if(dlb.search("c141m")){
			System.out.println("done");
		}
		else{
			System.out.println("welp");
		}
	}
		
	public static boolean checkMethod(String test) throws IOException{
		long timeStart = System.nanoTime();
		long time;
		final long ms;
		final float milli;
		if(check(test)){
			//time = System.nanoTime() - timeStart;
			//milli = time / 1000000.0f;
			//System.out.println(test + " found in " + milli + " ms");
			return true;
		}
		else{
			return false;
		}
	}

	public static boolean check(String test){
		if (goodPass.search(test)){
			return true;
		} else return false;
	}

	public static void numerateWords(String string, DLBTrie dlb) throws IOException {
		/*checks which passwords have characters need to be switched in with numbers
		and changes them with switchLetters()*/
		String temp;
		for (int i = 0; i < string.length(); i++) {

			if (string.charAt(i) == 'i' || string.charAt(i) == 'a' || string.charAt(i) == 'l'
					|| string.charAt(i) == 'e' || string.charAt(i) == 's'
					|| string.charAt(i) == 'o' || string.charAt(i) == 't') {
				temp = switchLetters(string, i, dlb);
				//System.out.println(temp);
				numerateWords(temp, dlb);

			}
		}
	}

	// checks the characters that need to be switched and performs the switch
	public static String switchLetters(String string, int i, DLBTrie dlb) throws IOException {
		String word = "";
		for (int j = 0; j < string.length(); j++) {
			if (j == i) {
				if (string.charAt(j) == 'i' || string.charAt(j) == 'l')
					word += '1';
				if (string.charAt(j) == 'a')
					word += '4';
				if (string.charAt(j) == 't')
					word += '7';
				if (string.charAt(j) == 'e')
					word += '3';
				if (string.charAt(j) == 's')
					word += '5';
				if (string.charAt(j) == 'o')
					word += '0';

			} else {
				word += string.charAt(j);
			}
		}

		if (dlb.search(word) == false) {
			//badPasswords.add(word);
			dlb.addWord(word);
		}
		return word;
	}
	/*iteratively generates 10 passwords that match the longest prefix of the input*/
	public static void createGoodPass(DLBTrie dlb) throws IOException {
		File statText = new File("all_passwords.txt");
		statText.createNewFile();
		FileOutputStream stream = new FileOutputStream(statText);
		OutputStreamWriter output = new OutputStreamWriter(stream);
		Writer w = new BufferedWriter(output);
		char[] chars = { 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z', '2', '3', '5', '6',
				'7', '8', '9', '0', '!', '@', '$', '%', '&', '*' };
		int counter = 0;
		for (int i = 0; i < chars.length; i++) {
			for (int j = 0; j < chars.length; j++) {
				for (int k = 0; k < chars.length; k++) {
					for (int l = 0; l < chars.length; l++) {
						for (int m = 0; m < chars.length; m++) {
							char[] pass = { chars[i], chars[j], chars[k],
									chars[l], chars[m] };
							long timeStart = System.nanoTime();
							long time;
							int number = 0;
							int symbol = 0;
							
							//uses int values of symbol hex values
							for (int a = 0; a < pass.length; a++) {
								if ((int) pass[a] > 47 && (int) pass[a] < 58) {
									number++;
								}
								if ((int) pass[a] == 33 || (int) pass[a] == 36 || (int) pass[a] == 37
										|| (int) pass[a] == 38 || (int) pass[a] == 64 ||
										(int) pass[a] == 42) {
									symbol++;
								}

							}
							if (number > 0 && number < 3 && symbol > 0 && symbol < 3) {

								String password = String.valueOf(pass);
								boolean check = false;
								
								//checks every length of prefix
								if (dlb.search(password.substring(0, 2)))
									check = true;
								if (dlb.search(password.substring(1, 3)))
									check = true;
								if (dlb.search(password.substring(2, 4)))
									check = true;
								if (dlb.search(password.substring(3, 5)))
									check = true;
								if (dlb.search(password.substring(0, 3)))
									check = true;
								if (dlb.search(password.substring(1, 4)))
									check = true;
								if (dlb.search(password.substring(2, 5)))
									check = true;
								if (dlb.search(password.substring(0, 4)))
									check = true;
								if (dlb.search(password.substring(1, 5)))
									check = true;

								if (!check) {
									time = System.nanoTime() - timeStart;
									final long ms;
									final float milli;
									milli = time / 1000000.0f;
									w.write(password + ", " + milli);
									w.write(System.getProperty("line.separator"));
									//System.out.println(milli + " " + password);
								}
							}
						}
					}
				}
			}
		}
		w.close();
	}
	
	/* uses substrings of the input to find compare against the passwords in the bad password
	trie and uses it to find a word in the good password file*/
	static void prefix(String word) throws IOException {
		int index = 1;
		DLBTrie dlb = new DLBTrie();
		BufferedReader read = new BufferedReader(new FileReader(
				"dictionary.txt"));
		String string;
		while ((string = read.readLine()) != null) {
			if (string.length() <= 5 && string.length() > 0) {
				dlb.addWord(string);
				numerateWords(string, dlb);
				//System.out.println(s);
			}
		}

		read.close();
		if (dlb.search(word.substring(0, 2)))
			index = 1;
		if (dlb.search(word.substring(1, 3)))
			index = 2;
		if (dlb.search(word.substring(2, 4)))
			index = 3;
		if (dlb.search(word.substring(3, 5)))
			index = 4;
		if (dlb.search(word.substring(0, 3)))
			index = 2;
		if (dlb.search(word.substring(1, 4)))
			index = 3;
		if (dlb.search(word.substring(2, 5)))
			index = 4;
		if (dlb.search(word.substring(0, 4)))
			index = 3;
		if (dlb.search(word.substring(1, 5)))
			index = 4;
		suggestions(word, index);
	}


	//search through the good passwords file and prints out the words that contain the prefix
	public static void suggestions(String word, int index) throws IOException {	
		BufferedReader read = new BufferedReader(new FileReader(
				"all_passwords.txt"));
		String temp;
		int i = 0;
		while ((temp = read.readLine()) != null) {
			if (temp.substring(0, index).equals(word.substring(0, index))) {
				i++;
				System.out.println(temp);
			}
			if (i == 10){
				break;
			}
		}
		read.close();
	}
}