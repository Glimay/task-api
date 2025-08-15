package com.app.task_api.service;

import com.app.task_api.Utils.KeyUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Service
public class SecurityService {

    private static  PrivateKey privateKey;
    private static  PublicKey publicKey;

    @PostConstruct
    public void generateKeys() throws Exception {
        try (InputStream privateKeyStream = getClass().getClassLoader().getResourceAsStream("keys/private_key.pem");
             InputStream publicKeyStream = getClass().getClassLoader().getResourceAsStream("keys/public_key.pem")) {

            if (privateKeyStream == null || publicKeyStream == null) {
                throw new IllegalStateException("No se encontraron las claves en el classpath");
            }

            this.privateKey = KeyUtils.loadPrivateKey(privateKeyStream);
            this.publicKey = KeyUtils.loadPublicKey(publicKeyStream);
        }
    }

    // Encriptar con RSA/OAEP
    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Desencriptar con RSA/OAEP
    public static String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}
