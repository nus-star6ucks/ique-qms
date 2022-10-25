package com.mtech.ique.queueservice.controller;

import com.mtech.ique.queueservice.model.entity.QueueInfo;
import com.mtech.ique.queueservice.service.QueueManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/queues")
public class QueueManagementController {

  @Autowired private QueueManagementService queueManagementService;

  @PostMapping("/tickets")
  public ResponseEntity createQueueTickets(
      @RequestParam("queueId") Long queueId,
      @RequestParam("customerId") Long customerId,
      @RequestParam("storeId") Long storeId) {
    HashMap<String, Object> hashmap =
        queueManagementService.createTicket(queueId, customerId, storeId);
    if (hashmap.isEmpty()) return new ResponseEntity<>("Fail to find queue", HttpStatus.CONFLICT);
    return new ResponseEntity<>(hashmap, HttpStatus.CREATED);
  }

  @PostMapping("/start")
  public ResponseEntity createQueues(@RequestBody List<HashMap<String, Object>> seatTypeList) {
    return new ResponseEntity<>(
        queueManagementService.createQueues(seatTypeList), HttpStatus.CREATED);
  }

  @PostMapping("/stop")
  public ResponseEntity<Object> deleteQueues(@RequestBody List<Long> queueIdList) {
    queueManagementService.deleteQueues(queueIdList);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/tickets")
  public ResponseEntity getQueueTickets(
      @RequestParam(name = "userId", required = false) Long userId,
      @RequestParam(name = "storeId", required = false) Long storeId) {
    if (userId == null && storeId != null) {
      return new ResponseEntity<>(
          queueManagementService.getQueueTicketsByStore(storeId), HttpStatus.OK);
    }
    if (userId != null && storeId == null) {
      return new ResponseEntity<>(
          queueManagementService.getQueueTicketsByUser(userId), HttpStatus.OK);
    }
    if (userId != null && storeId != null) {
      return new ResponseEntity<>(
          queueManagementService.getQueueTicketsByUserAndStore(userId, storeId), HttpStatus.OK);
    }
    return new ResponseEntity<>("error", HttpStatus.UNAUTHORIZED);
  }

  @GetMapping("/tickets/{ticketId}")
  public ResponseEntity<Object> getQueueTicketDetail(@PathVariable Long ticketId) {
    HashMap<String, Object> hashMap = queueManagementService.getQueueTicketDetail(ticketId);
    if (hashMap.isEmpty()) {
      return new ResponseEntity<>("Record not found", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(hashMap, HttpStatus.OK);
  }

  @GetMapping("/{queueId}")
  public ResponseEntity<Object> getQueueInfoDetail(@PathVariable Long queueId) {
    QueueInfo queueInfo = queueManagementService.getQueueInfoDetail(queueId);
    return new ResponseEntity<>(queueInfo, HttpStatus.OK);
  }

  @PostMapping("/call")
  public ResponseEntity<Object> callForCustomer(@RequestParam Long ticketId) {
    try {
      String call = queueManagementService.call(ticketId);
      return new ResponseEntity<>(call, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println("e.getMessage() = " + e.getMessage());
      return new ResponseEntity<>("error", HttpStatus.OK);
    }
  }

  @PostMapping("/checkin")
  public ResponseEntity<Object> checkinForCustomer(@RequestParam Long ticketId) {
    String checkInResponse = queueManagementService.checkIn(ticketId);
    if (checkInResponse != null) return new ResponseEntity<>(checkInResponse, HttpStatus.OK);
    return new ResponseEntity<>("error", HttpStatus.UNAUTHORIZED);
  }

  @PostMapping("/skip")
  public ResponseEntity<Object> skipCustomer(@RequestParam Long ticketId) {
    String response = queueManagementService.skipCustomer(ticketId);
    if (response != null) {
      return new ResponseEntity(response, HttpStatus.OK);
    } else {
      return new ResponseEntity<>("error", HttpStatus.OK);
    }
  }
}
