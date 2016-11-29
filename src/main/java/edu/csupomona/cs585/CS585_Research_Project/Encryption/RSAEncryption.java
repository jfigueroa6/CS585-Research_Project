package edu.csupomona.cs585.CS585_Research_Project.Encryption;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.util.test.FixedSecureRandom;

public class RSAEncryption implements Encryption {
	//===============================================================
	//	Data Members
	//===============================================================
	private AsymmetricCipherKeyPair keyPair;
	private byte[] originalText,
				   decryptedText,
				   ciphertext;
	
	//===============================================================
	//	Constructor
	//===============================================================
	public RSAEncryption(int keySize) throws NoSuchAlgorithmException {
		generateKeyPair(keySize);
	}
	
	//===============================================================
	//	Methods
	//===============================================================
	private void generateKeyPair(int keySize) throws NoSuchAlgorithmException {
		AsymmetricCipherKeyPairGenerator generator = new RSAKeyPairGenerator();
		
		//Generate key pair generator parameters
		BigInteger publicExponent = new BigInteger("10001", 16);		//e = 65537, universally used
		SecureRandom random = FixedSecureRandom.getInstance("SHA1PRNG");	//Use SHA1 to generate random secure number
		int certainty = 80;			//Certainty that generated number is a prime = 80%
		
		generator.init(new RSAKeyGenerationParameters(publicExponent, random, keySize, certainty));
		keyPair = generator.generateKeyPair();
	}
	
	public long decrypt() {
		//Setup
		AsymmetricBlockCipher decryptor = new PKCS1Encoding(new RSAEngine());
		decryptor.init(false, keyPair.getPrivate());
		
		//Start Timer and encrypt
		long startTime = System.currentTimeMillis();
		try {
			decryptedText = decryptor.processBlock(ciphertext, 0, ciphertext.length);
		} catch (InvalidCipherTextException e) {
			System.err.println("Error decrypting");
			return -1;
		}
		long endTime = System.currentTimeMillis();
		
		return endTime - startTime;
	}

	public long encrypt(byte[] text) {
		//Setup
		AsymmetricBlockCipher encryptor = new PKCS1Encoding(new RSAEngine());
		encryptor.init(true, keyPair.getPublic());
		originalText = text;
		
		//Start Timer and encrypt
		long startTime = System.currentTimeMillis();
		try {
			ciphertext = encryptor.processBlock(text, 0, text.length);
		} catch (InvalidCipherTextException e) {
			System.err.println("Error encrypting");
			return -1;
		}
		long endTime = System.currentTimeMillis();
		
		return endTime - startTime;
	}

	public AsymmetricCipherKeyPair getKeyPair() {
		return keyPair;
	}
	
	public String getCiphertext() {
		return new String(ciphertext);
	}
	
	public String getDecryptedText() {
		return new String(decryptedText);
	}
	
	public String getOriginalText() {
		return new String(originalText);
	}
}
