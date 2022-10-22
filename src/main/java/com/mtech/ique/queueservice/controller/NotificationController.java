package com.mtech.ique.queueservice.controller;

import com.mtech.ique.queueservice.model.DirectNotification;
import com.mtech.ique.queueservice.service.FCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RequestMapping("/queues")
public class NotificationController {
  @Autowired FCMService fcmService;

  @PostMapping("/notification")
  ResponseEntity<String> sendTargetedNotification(@RequestBody DirectNotification notification) {
    fcmService.sedNotificationToTarget(notification);
    return new ResponseEntity<>("success", HttpStatus.OK);
  }

  @PostMapping("/registerToken")
  ResponseEntity<String> registerToken(@RequestParam Long userId, @RequestParam String token) {
    fcmService.registerToken(userId, token);
    return new ResponseEntity<>("success", HttpStatus.OK);
  }
}
