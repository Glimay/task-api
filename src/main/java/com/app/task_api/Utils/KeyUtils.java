package com.app.task_api.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.stream.Collectors;

public class KeyUtils {
    public static PrivateKey loadPrivateKey(InputStream keyStream) throws Exception {
        String keyPem = new BufferedReader(new InputStreamReader(keyStream))
                .lines()
                .filter(line -> !line.startsWith("-----BEGIN") && !line.startsWith("-----END"))
                .collect(Collectors.joining());

        byte[] encoded = Base64.getDecoder().decode(keyPem);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    public static PublicKey loadPublicKey(InputStream keyStream) throws Exception {
        String keyPem = new BufferedReader(new InputStreamReader(keyStream))
                .lines()
                .filter(line -> !line.startsWith("-----BEGIN") && !line.startsWith("-----END"))
                .collect(Collectors.joining());

        byte[] encoded = Base64.getDecoder().decode(keyPem);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(keySpec);
    }
}
