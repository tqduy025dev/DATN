package com.server.datn.server.common.utils;

import com.server.datn.server.common.factory.PasswordGeneratorFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public final class KeyGenarator {
    public static String getKey(){
        return UUID.randomUUID().toString();
    }

    public static String[] getDefaultPassword(PasswordEncoder encoder){
        PasswordGeneratorFactory passwordGeneratorFactory = new PasswordGeneratorFactory.PasswordGeneratorBuilder()
                .usePunctuation(false)
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
        String[] strings = new String[2];
        String pass = passwordGeneratorFactory.generate(8);
        strings[0] = encoder.encode(pass);
        strings[1] = pass;
        return strings;
    }
}
