package com.mtech.ique.queueservice.service.impl;

import com.google.firebase.messaging.*;
import com.mtech.ique.queueservice.model.DirectNotification;
import com.mtech.ique.queueservice.service.FCMService;
import org.springframework.stereotype.Service;

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
}
