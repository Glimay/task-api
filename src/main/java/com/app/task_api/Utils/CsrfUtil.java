package com.app.task_api.Utils;

import java.security.SecureRandom;
import java.util.Base64;

public class CsrfUtil {

    public static String generateCsrfToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

}
