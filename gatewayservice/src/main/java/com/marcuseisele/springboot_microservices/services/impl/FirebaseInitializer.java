/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.datn.server.services.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author UserName
 */
@Service
public class FirebaseInitializer {

    @PostConstruct
    private void init() throws IOException {
        InputStream serviceAccount = this.getClass().getClassLoader().getResourceAsStream("./demojavaspring-931ff8c70d7c.json");

        assert serviceAccount != null;
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://demojavaspring-default-rtdb.firebaseio.com")
                .setStorageBucket("demojavaspring.appspot.com")
                .build();
        
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

    }


}
