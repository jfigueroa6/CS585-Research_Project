package edu.csupomona.cs585.CS585_Research_Project.Encryption;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import junit.framework.Assert;

public class RSAEncryptionTest {
	@Test
	public void testKeyGeneration() {
		try {
			RSAEncryption rsa = new RSAEncryption(2048);
			Assert.assertNotNull(rsa.getKeyPair());
		} catch (NoSuchAlgorithmException e) {
			Assert.fail();
		}
		
	}
	
	@Test
	public void testEncryption() {
		try {
			RSAEncryption rsa = new RSAEncryption(2048);
			long time = rsa.encrypt("Test Text".getBytes());
			Assert.assertEquals(true, time != -1);
			System.err.println(time);
			System.err.println(rsa.getCiphertext());
		} catch (NoSuchAlgorithmException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testDecryption() {
		try {
			RSAEncryption rsa = new RSAEncryption(2048);
			rsa.encrypt("Testing text".getBytes());
			long time = rsa.decrypt();
			Assert.assertEquals(true, time != -1);
			Assert.assertEquals(rsa.getOriginalText(), rsa.getDecryptedText());
			System.err.println(time);
			System.err.println(rsa.getDecryptedText());
		} catch (NoSuchAlgorithmException e) {
			Assert.fail();
		}
	}
}
