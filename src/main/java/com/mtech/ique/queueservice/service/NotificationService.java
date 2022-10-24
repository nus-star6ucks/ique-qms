package com.mtech.ique.queueservice.service;

import com.mtech.ique.queueservice.model.DirectNotification;

public interface NotificationService {
  void sendNotificationToTarget(DirectNotification directNotification);

  String getTokenByUserId(Long userId);

  void registerToken(Long userId, String token);
}
