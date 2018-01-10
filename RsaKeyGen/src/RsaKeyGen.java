import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Random;
public class RsaKeyGen {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		Random r = new Random();
		int i;
		// byte array operations
		BigInteger p = BigInteger.probablePrime(256, r);
		BigInteger q = BigInteger.probablePrime(256, r);
		BigInteger n = p.multiply(q);
		BigInteger e;
		//phi(n) = (p-1)(q-1)
		BigInteger pMinusOne = p.subtract(BigInteger.valueOf(1));
		BigInteger qMinusOne = q.subtract(BigInteger.valueOf(1));
		BigInteger phiOfN = pMinusOne.multiply(qMinusOne);
		
		while (true){
			e = BigInteger.probablePrime(512, r);
			i = e.compareTo(phiOfN);
			if ((i >= 1) && (e.gcd(phiOfN).equals(BigInteger.valueOf(1)))) break;
		}
		
		BigInteger d = e.modInverse(phiOfN);
		
		PrintWriter writer = new PrintWriter("pubkey.rsa", "UTF-8");		//writes to pubkey
		writer.println(e);
		writer.println(n);
		writer.close();
		
		
		writer = new PrintWriter("privkey.rsa", "UTF-8");					//writes to privkey
		writer.println(d);
		writer.println(n);
		writer.close();
	}

}
