import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Scanner;

public class RsaSign {
	public static void main(String[] args){
		if (args[0].equals("s")){
			sign(args[1]);
		}else if (args[0].equals("v")){
			verify(args[1]);
		} else {
			System.out.println("Invalid command line arguments.");
		}
	}
	
	public static void sign(String filename){
		try {
			Path path = Paths.get(filename);
			byte[] data = Files.readAllBytes(path);

			// create class instance to create SHA-256 hash
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			// process the file
			md.update(data);
			
			// generate a hash of the file
			byte[] digest = md.digest();

			BigInteger b = new BigInteger(1, digest);

			File file = new File("privkey.rsa");
			Scanner s = new Scanner(file);
			
			//retrieves private key
			BigInteger d = new BigInteger(s.nextLine());			
			BigInteger n = new BigInteger(s.nextLine());
			s.close();
			//create the hash
			b = b.modPow(d, n);
			
			String hashCodeHex = b.toString();
			FileWriter fw = new FileWriter(filename + ".sig");
			//writes hash to new signed file
			fw.write(hashCodeHex);					
			fw.close();
	}
		catch(Exception e) {
			System.out.println("Invalid file input");
		}
	}
	
	public static void verify(String filename){
		try{
			//retrieves information from pubkey.rsa
			File f = new File("pubkey.rsa");
			Scanner sc = new Scanner(f);
			
			BigInteger e= new BigInteger(sc.nextLine());
			BigInteger n= new BigInteger(sc.nextLine());
			sc.close();
			String sign = "";
			
			//find the signed file
			File file = new File(filename + ".sig");
			Scanner line = new Scanner(file);
			while (line.hasNext()){
				sign = line.nextLine();
			}
			line.close();
			
			BigInteger pubkey =  new BigInteger(sign);
			BigInteger hash = pubkey.modPow(e, n);
			Path path = Paths.get(filename);
			byte[] data = Files.readAllBytes(path);

			// create class instance to create SHA-256 hash
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			// process the file
			md.update(data);
			// generate a has of the file
			byte[] digest = md.digest();

			BigInteger b = new BigInteger(1, digest);
			
			System.out.println(b);
			System.out.println("AND");
			System.out.println(hash);
			//Checks if hashes match
	        if (b.compareTo(hash) == 0){									
	        	System.out.println("This signature is valid.");
	        }else{
	        	System.out.println("This signature is not valid.");
	        }
		}
		catch(Exception e) {
			System.out.println("Invalid file input");
		}
	}

}
