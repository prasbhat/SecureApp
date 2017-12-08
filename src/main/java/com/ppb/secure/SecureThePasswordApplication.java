package com.ppb.secure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecureThePasswordApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureThePasswordApplication.class, args);
        //Use this to generate the random key
//        GenerateRandomKey.generateRandomKeys();

        //Generate the encrypted password
//        GenerateEncryptionPassword.generateEncryptedPassword();

        //Generate the de-encrypted password
//        GenerateDecryptionPassword.generatedecryptionPassword();
    }
}
