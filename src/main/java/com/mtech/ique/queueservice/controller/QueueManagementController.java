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
    public ResponseEntity createQueueTickets(@RequestParam Long queueId, Long customerId){
        HashMap<String, Object> hashmap = queueManagementService.createTicket(queueId, customerId);
        if (hashmap.isEmpty())
            return new ResponseEntity<>("Fail to find queue", HttpStatus.CONFLICT);
        return new ResponseEntity<>(hashmap, HttpStatus.CREATED);
    }

    @PostMapping("/start")
    public ResponseEntity createQueues(@RequestBody List<HashMap<String, Object>> seatTypeList){
        return new ResponseEntity<>(queueManagementService.createQueues(seatTypeList), HttpStatus.CREATED);
    }

    @PostMapping("/stop")
    public ResponseEntity<Object> deleteQueues(@RequestBody List<Long> queueIdList){
        queueManagementService.deleteQueues(queueIdList);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tickets")
    public ResponseEntity getQueueTickets(@RequestParam("userId") Long userId, @RequestParam("storeId") Long storeId){
        if (userId == null && storeId != null){
            return new ResponseEntity<>(queueManagementService.getQueueTicketsByUser(userId), HttpStatus.OK);
        }
        if (userId != null && storeId == null){
            return new ResponseEntity<>(queueManagementService.getQueueTicketsByStore(storeId), HttpStatus.OK);
        }
        if (userId != null && storeId != null){
            return new ResponseEntity<>(queueManagementService.getQueueTicketsByUserAndStore(userId, storeId), HttpStatus.OK);
        }
        return new ResponseEntity<>("error", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<Object> getQueueTicketDetail(@PathVariable Long ticketId){
        HashMap<String, Object> hashMap = queueManagementService.getQueueTicketDetail(ticketId);
        if (hashMap.isEmpty()){
            return new ResponseEntity<>("Record not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    @GetMapping("/{queueId}")
    public ResponseEntity<Object> getQueueInfoDetail(@PathVariable Long queueId){
        QueueInfo queueInfo = queueManagementService.getQueueInfoDetail(queueId);
        if (queueInfo.getQueueId() == 0){
            return new ResponseEntity<>("Recode not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(queueInfo, HttpStatus.OK);
    }

    @PostMapping("/checkin")
    public ResponseEntity<Object> checkinForCustomer(@RequestParam Long queueId){
        if (queueManagementService.checkIn(queueId))
            return new ResponseEntity<>("Success", HttpStatus.OK);
        return new ResponseEntity<>("error", HttpStatus.UNAUTHORIZED);
    }
}
