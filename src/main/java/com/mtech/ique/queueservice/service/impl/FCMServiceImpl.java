package com.mtech.ique.queueservice.service.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.*;
import com.mtech.ique.queueservice.model.DirectNotification;
import com.mtech.ique.queueservice.model.entity.UserToken;
import com.mtech.ique.queueservice.service.FCMService;
import com.mtech.ique.queueservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FCMServiceImpl implements FCMService {

  @Autowired NotificationService notificationService;

  @Override
  public void sedNotificationToTarget(DirectNotification notification) {

    notificationService.sendPush(notification.getTarget());

    //    WebpushNotification push =
    //        WebpushNotification.builder()
    //            .setTitle(notification.getTitle())
    //            .setBody(notification.getMessage())
    //            .setIcon("https://ique.vercel.app/favicon-32x32.png")
    //            .build();
    //
    //    WebpushConfig webpushConfig = WebpushConfig.builder().setNotification(push).build();
    //
    //    Message message =
    //        Message.builder()
    //            .setWebpushConfig(webpushConfig)
    //            .setToken(notification.getTarget())
    //            .build();
    //
    //    try {
    //      String response = FirebaseMessaging.getInstance().send(message);
    //      System.out.println("response = " + response);
    //    } catch (FirebaseMessagingException e) {
    //      throw new RuntimeException(e);
    //    }
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
    dbFirestore.collection("userTokens").document(userId.toString()).set(userToken);
  }
}
