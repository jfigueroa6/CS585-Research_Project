package edu.csupomona.cs585.CS585_Research_Project;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;

import edu.csupomona.cs585.CS585_Research_Project.Encryption.Encryption;
import edu.csupomona.cs585.CS585_Research_Project.Encryption.RSAEncryption;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final int BITS_PER_BYTE = 8;
    public static void main( String[] args )
    {
    	Scanner input = new Scanner(System.in);
        System.out.print("RSA Performance Test\n\nInput RSA Key Size: ");
        int keySize = input.nextInt();
        System.out.print("Input rounds of testing: ");
        int numTests = input.nextInt();
        input.close();
        try {
			rsaTest(keySize, numTests);
		} catch (UnsupportedEncodingException e) {
			System.err.println("Incorrect encoding in generating plaintext method");
		}
    }
    
    private static void rsaTest(int keySize, int numTests) throws UnsupportedEncodingException {
    	System.out.println("\nGenerating keys\n(May take some time depending on key size.)");
    	try {
    		long keyGenStartTime = System.currentTimeMillis(); 
			Encryption rsa = new RSAEncryption(keySize);
			System.out.println("Key Generation Time: " + ((System.currentTimeMillis() - keyGenStartTime) / 1000.0 ) + "s\n");
			System.out.print("Key generation complete!\n\nText to encrypt: ");
			//Scanner input = new Scanner(System.in);
			//String plaintext = input.nextLine();
			//input.close();
			byte[] plaintext = generatePlaintext(keySize);
			System.out.println(new String(plaintext));
			
			long encryptionTotalTime = 0,
				 decryptionTotalTime = 0;
			for (int i = 1; i <= numTests; i++) {
				System.out.print("Round " + i + " ");
				encryptionTotalTime = rsa.encrypt(plaintext);
				System.out.print(". . . ");
				decryptionTotalTime = rsa.decrypt();
				System.out.println("Complete");
			}
			
			System.out.println("Encryption Avg. Time: " + (encryptionTotalTime / numTests) + "ms");
			System.out.println("Decryption Avg. Time: " + (decryptionTotalTime / numTests) + "ms");
			
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error: No Such Algorithm Excpetion");
		}
    }
    
    private static byte[] generatePlaintext(int keySize) throws UnsupportedEncodingException {  	
    	int stringLength = 3 *(keySize / BITS_PER_BYTE) / 4;	//Convert bits to bytes
    	stringLength -= stringLength % 2;	//Align string to char size
    	
    	byte[] tempString = new byte[stringLength];
    	Random rand = new Random(System.currentTimeMillis());
    	rand.nextBytes(tempString);
    	/*for (int i = 0; i < tempString.length; i++) {
    		tempString[i] = rand.next);
    	}*/
    	
    	return tempString;
    }
}
