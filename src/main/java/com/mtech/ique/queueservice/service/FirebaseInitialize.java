package com.mtech.ique.queueservice.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseInitialize {

  Logger logger = LoggerFactory.getLogger(FirebaseInitialize.class);

  @PostConstruct
  public void initialize() {
    try {
      FileInputStream serviceAccount = new FileInputStream("/opt/firebase/firebase_key.json");
      FirebaseOptions options =
          FirebaseOptions.builder()
              .setCredentials(GoogleCredentials.fromStream(serviceAccount))
              .build();
      FirebaseApp.initializeApp(options);
      logger.info("Firebase application has been initialized");

    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }
}
