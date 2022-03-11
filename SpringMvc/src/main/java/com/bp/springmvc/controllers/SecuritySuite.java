package com.bp.springmvc.controllers;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SecuritySuite {
    
    private static final String ALGO = "AES";
    private static final byte[] keyValue = new byte[] {
      'T',
      'h',
      'e',
      'B',
      'e',
      's',
      't',
      'S',
      'e',
      'c',
      'r',
      'e',
      't',
      'K',
      'e',
      'y'
    };

    public String encrypt(String data, Key key) throws Exception {
      Cipher c = Cipher.getInstance(ALGO);
      c.init(Cipher.ENCRYPT_MODE, key);
      byte[] encVal = c.doFinal(data.getBytes());
      return Base64.getEncoder().encodeToString(encVal);
    }

    public String decrypt(String encryptedData, Key key) throws Exception {
      Cipher c = Cipher.getInstance(ALGO);
      c.init(Cipher.DECRYPT_MODE, key);
      byte[] decodedValue = Base64.getDecoder().decode(encryptedData);
      byte[] decValue = c.doFinal(decodedValue);
      return new String(decValue);
    }
    
    public Key generateKey(String password) throws Exception {
      return new SecretKeySpec(calculateMD5(password), ALGO);
    }
    
    public byte[] calculateMD5(String text) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(text.getBytes());
             return messageDigest;
        } catch (NoSuchAlgorithmException e) {
           throw new RuntimeException(e);
        }
    }
    
    
    
    
    
    public String calculateSHA512(String text) {
        try {
          //get an instance of SHA-512
          MessageDigest md = MessageDigest.getInstance("SHA-512");

          //calculate message digest of the input string - returns byte array
          byte[] messageDigest = md.digest(text.getBytes());

          // Convert byte array into signum representation
          BigInteger no = new BigInteger(1, messageDigest);

          // Convert message digest into hex value
          String hashtext = no.toString(16);

          // Add preceding 0s to make it 32 bit
          while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
          }

          // return the HashText
          return hashtext;
        }

        // If wrong message digest algorithm was specified
        catch (NoSuchAlgorithmException e) {
          throw new RuntimeException(e);
        }
    }
    
    public static final String PEPPER = "pepperoni";
    public static final String HMAC_SHA512 = "HmacSHA512";
    public String calculateHMAC(String text, String key) {
        Mac sha512Hmac;
        String result = "";
        try {
          final byte[] byteKey = key.getBytes(StandardCharsets.UTF_8);
          sha512Hmac = Mac.getInstance(HMAC_SHA512);
          SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
          sha512Hmac.init(keySpec);
          byte[] macData = sha512Hmac.doFinal(text.getBytes(StandardCharsets.UTF_8));
          result = Base64.getEncoder().encodeToString(macData);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
          e.printStackTrace();
        } finally {}
        return result;
      }
}
