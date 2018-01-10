import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/*************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *  WARNING: STARTING WITH ORACLE JAVA 6, UPDATE 7 the SUBSTRING
 *  METHOD TAKES TIME AND SPACE LINEAR IN THE SIZE OF THE EXTRACTED
 *  SUBSTRING (INSTEAD OF CONSTANT SPACE AND TIME AS IN EARLIER
 *  IMPLEMENTATIONS).
 *
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *
 *************************************************************************/

public class MyLZW {
    private static final int R = 256;        // number of input chars
    private static int W = 9;         // codeword width
    private static int L = 512;       // number of codewords = 2^W
    public static int maxCodewords = 65536; //maximum number of codewords, 2^16
    public static int inputSize = 0;
    public static int outputSize = 0;
    public static double compRatio = 0;
    public static double oldComp = 0;
    public static double newComp = 0;
    public static boolean isMonitoring = false;
    public static char compMode = ' ';

    public static boolean resetMode=false;
    public static boolean monitorMode=false;
    public static boolean doNothingMode=false;

    public static void compress() {
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();
        int i;
        for (i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R + 1;  // R is codeword for EOF


        switch(compMode) {
	        case 'n':
	          BinaryStdOut.write('n');
	          break;
	        case 'r':
	          BinaryStdOut.write('r');
	          break;
	        case 'm':
	          BinaryStdOut.write('m');
	          break;
        }

        while (input.length() > 0) {
        	 if (code == L && code < maxCodewords){ //checks if symbol table is under 2^16 codewords
             	W++;	 //increments codeword width
             	L = L*2; //increases maximum number of codewords by a factor of 2
             }

        	 //RESET MODE
        	 //resets the symbol table back to starting with 8 bit values when the codebook fills with 16 bit codes
             if (resetMode && code == maxCodewords)	{
             	 st = new TST<Integer>();
                  for (int j = 0; j < 256; j++)
                      st.put("" + (char) j, j);

                  W = 9;
                  L = 512;
                  code = R + 1;
             }

             //MONITOR MODE
             if (monitorMode){
            	 if (outputSize != 0){
                	 compRatio = (double) inputSize/(double) outputSize;
                 }
            	 if (code == maxCodewords ||isMonitoring){
            		 if (oldComp/compRatio > 1.1){

                         st = new TST<Integer>(); // reset TST
                         for (i = 0; i < R; i++)
                             st.put("" + (char) i, i);

                         W = 9;
                         L = 512;
                         code = R + 1; // R is codeword for EOF
                         i = i + 1;
                         isMonitoring = false;
                         inputSize = 0;
                         outputSize = 0;
            		 }else{
            			 oldComp = compRatio;
            			 isMonitoring = true;
            		 }

            	 }
            	 System.err.println("File compressed at a ratio of " + compRatio);
             }
            String s = st.longestPrefixOf(input);  // Search codebook the longest prefix of the current input string
            inputSize = inputSize + (8 * s.length());		//keeps track of the size of the input file for monitor mode
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            outputSize = outputSize + W;									//keeps track of the size of the output file for monitor mode
            int t = s.length();
            if (t < input.length() && code < L)    // Add s to symbol table
                st.put(input.substring(0, t + 1), code++);
            input = input.substring(t);            // Scan past s in input
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }


    public static void expand() {
    	char mode  = BinaryStdIn.readChar();
  		switch(mode) {
  		    case 'n':
  		    doNothingMode = true;
  		    break;
  		case 'r':
  			resetMode = true;
  			break;
  		case 'm':
  			monitorMode = true;
  			break;
		  }

        String[] st = new String[maxCodewords];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            outputSize = outputSize + W;
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) st[i++] = val + s.charAt(0);
            val = s;
            inputSize = inputSize + (8*val.length());
            if (i == L-1 && i < maxCodewords-1){
            	if (W != 16){
            		W++; //increment codeword width
            		L = L*2; //increase maximum codewords by a factor of 2
            	}

            }
            //RESET MODE
            if (resetMode&& i==maxCodewords-1)	{
        		System.err.println("here");
        		W=9;
                L=512;
            	st = new String[maxCodewords];
                for (i = 0; i < R; i++)
                    st[i] = "" + (char) i;
                st[i++] = "";
                i--;

            }

            //MONITOR MODE
            if (monitorMode){
				 if (outputSize != 0){
				   	 compRatio = (double) inputSize/(double) outputSize;
				    }
				 if (i == maxCodewords - 1 ||isMonitoring){
					 if (oldComp/compRatio > 1.1){
				            st = new String[maxCodewords]; //resets the TST
				        for (i = 0; i < R; i++)
				            st[i]="" + (char) i;
				        st[i++] = "";
				        W = 9;  //initial codeword width
				        L = 512;
				        isMonitoring = false;
				        inputSize = 0;
				        outputSize = 0;
					 }else{
						 oldComp = compRatio;
						 isMonitoring = true;
					 }
					 System.err.println("File expanded at a ratio of " + compRatio);
				 }
            }
        }
        BinaryStdOut.close();
    }


    public static void main(String[] args) {
        switch (args[0]) {
            case "-":
                String modeChoice = args[1].toLowerCase();
                switch (modeChoice) {
                    case "r": // Reset mode
                    	resetMode = true;
                    	compMode = 'r';
                        break;
                    case "m": // Monitor mode
                    	monitorMode = true;
                    	compMode = 'm';
                        break;
                    case "n": // Do nothing mode
                    	doNothingMode = true;
                    	compMode = 'n';
                        break;
                }
                compress();
                break;
            case "+":
                expand();
                break;
            default:
                throw new IllegalArgumentException("Illegal command line argument");
        }
    }

}
