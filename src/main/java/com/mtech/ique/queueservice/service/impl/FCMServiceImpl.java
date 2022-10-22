package com.mtech.ique.queueservice.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.mtech.ique.queueservice.model.DirectNotification;
import com.mtech.ique.queueservice.service.FCMService;

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
    FirebaseMessaging.getInstance().sendAsync(message);
  }
}
