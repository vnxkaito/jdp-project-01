package com.ga.java.project01;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

public class PasswordHandler{
    private static final String keyValue = "hfdasouifhapfuh#@$@12312";
    private static final String IVValue = "9382y58rheapwoif";

    protected static String encrypt(String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            SecretKey key = new SecretKeySpec(keyValue.getBytes(), "AES");
            IvParameterSpec iv = new IvParameterSpec(IVValue.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        }

    protected static String decrypt(String encryptedPassword){
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            SecretKey key = new SecretKeySpec(keyValue.getBytes(), "AES");
            IvParameterSpec iv = new IvParameterSpec(IVValue.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);

            return new String(cipher.doFinal(decodedBytes));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    }

