package com.ppb.secure.component;

import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.ppb.secure.component.GenerateRandomKey.byteArrayToHexString;

@Component
public class GenerateEncryptionPassword {

    public String generateEncryptedPassword(String key, String password)  {
        String encryptedString = null;
        try{
            byte[] bytekey = GenerateRandomKey.hexStringToByteArray(key);
            SecretKeySpec sks = new SecretKeySpec(bytekey, GenerateRandomKey.AES);
            Cipher cipher = Cipher.getInstance(GenerateRandomKey.AES);
            cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
            byte[] encrypted = cipher.doFinal(password.getBytes());
            encryptedString = byteArrayToHexString(encrypted);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return encryptedString;
    }

}
