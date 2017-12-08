package com.ppb.secure.component;

import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class GenerateDecryptionPassword {

    public String generatedecryptionPassword(String keyGen, String encrypted_Password) {
        String originalString = null;
        try {
            byte[] bytekey = GenerateRandomKey.hexStringToByteArray(keyGen);
            SecretKeySpec sks = new SecretKeySpec(bytekey, GenerateRandomKey.AES);
            Cipher cipher = Cipher.getInstance(GenerateRandomKey.AES);
            cipher.init(Cipher.DECRYPT_MODE, sks);
            byte[] decrypted = cipher.doFinal(GenerateRandomKey.hexStringToByteArray(encrypted_Password));
            originalString = new String(decrypted);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return originalString;
    }
}
