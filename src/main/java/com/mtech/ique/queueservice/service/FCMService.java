package com.mtech.ique.queueservice.service;

import com.mtech.ique.queueservice.model.DirectNotification;

public interface FCMService {
  void sedNotificationToTarget(DirectNotification directNotification);
}
