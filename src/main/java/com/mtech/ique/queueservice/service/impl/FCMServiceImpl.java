package com.mtech.ique.queueservice.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.*;
import com.mtech.ique.queueservice.model.DirectNotification;
import com.mtech.ique.queueservice.model.entity.UserToken;
import com.mtech.ique.queueservice.service.FCMService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FCMServiceImpl implements FCMService {
  @Override
  public void sedNotificationToTarget(DirectNotification notification) {
    Message message =
        Message.builder()
            .setWebpushConfig(
                WebpushConfig.builder()
                    .setNotification(
                        WebpushNotification.builder()
                            .setTitle(notification.getTitle())
                            .setBody(notification.getMessage())
                            .setIcon("https://assets.mapquestapi.com/icon/v2/circle@2x.png")
                            .build())
                    .build())
            .setToken(notification.getTarget())
            .build();
    try {
      String response = FirebaseMessaging.getInstance().send(message);
      System.out.println("response = " + response);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String getTokenByUserId(Long userId) {
    Firestore dbFirestore = FirestoreClient.getFirestore();
    DocumentReference documentReference =
        dbFirestore.collection("userTokens").document(userId.toString());
    ApiFuture<DocumentSnapshot> future = documentReference.get();

    DocumentSnapshot document = null;
    try {
      document = future.get();
    } catch (Exception e) {
      System.out.println("ERROR: " + e.getMessage());
    }

    UserToken userToken = null;
    if (document.exists()) {
      userToken = document.toObject(UserToken.class);
      return userToken.getToken();
    } else {
      return null;
    }
  }

  @Override
  public void registerToken(Long userId, String token) {
    Firestore dbFirestore = FirestoreClient.getFirestore();

    UserToken userToken = UserToken.builder().token(token).userId(userId).build();

    ApiFuture<WriteResult> collectionApiFuture =
        dbFirestore.collection("userTokens").document(userId.toString()).set(userToken);
  }
}
