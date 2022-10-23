package com.mtech.ique.queueservice.service;

import com.mtech.ique.queueservice.model.DirectNotification;

import java.util.concurrent.ExecutionException;

public interface FCMService {
  void sedNotificationToTarget(DirectNotification directNotification);

  String getTokenByUserId(Long userId);

  void registerToken(Long userId, String token);
}