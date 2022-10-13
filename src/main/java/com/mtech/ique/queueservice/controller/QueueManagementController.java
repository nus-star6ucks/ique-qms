package com.mtech.ique.queueservice.controller;

import com.mtech.ique.queueservice.model.entity.QueueInfo;
import com.mtech.ique.queueservice.model.entity.QueueTicket;
import com.mtech.ique.queueservice.service.QueueManagementService;
import com.mtech.ique.queueservice.model.entity.QueueTicket;
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
//@RequestMapping("/queues")
public class QueueManagementController {

    @Autowired private QueueManagementService queueManagementService;

    @PostMapping("/tickets")
    public ResponseEntity<Object> createQueueTickets(@RequestBody Long queueId){
        HashMap<String, Object> hashmap = queueManagementService.createTicket(queueId);
        if (hashmap.isEmpty())
            return new ResponseEntity<>("Fail to find queue", HttpStatus.CONFLICT);
        return new ResponseEntity<>(hashmap, HttpStatus.CREATED);
    }

    @PostMapping("/queues/start")
    public ResponseEntity<Object> createQueues(@RequestBody List<HashMap<String, Object>> seatTypeList){
        return new ResponseEntity<>(queueManagementService.createQueues(seatTypeList), HttpStatus.CREATED);
    }

    @PostMapping("/queues/stop")
    public ResponseEntity<Object> deleteQueues(@RequestBody List<Long> queueIdList){
        queueManagementService.deleteQueues(queueIdList);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/queues/tickets")
    public ResponseEntity<List<QueueTicket>> getQueueTickets(@RequestBody Long storeId, Long userId){
        return null;
    }

    @GetMapping("/queues/tickets/{ticketId}")
    public ResponseEntity<Object> getQueueTicketDetail(@PathVariable Long ticketId){
        HashMap<String, Object> hashMap = queueManagementService.getQueueInfo(ticketId);
        if (hashMap.isEmpty()){
            return new ResponseEntity<>("Record not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hashMap, HttpStatus.OK);
    }

    @GetMapping("/queues/{queueId}")
    public ResponseEntity<QueueInfo> getQueueInfo(@PathVariable Long queueId){
        QueueInfo queueInfo = new QueueInfo();
        queueInfo.setQueueId(queueId);
        queueInfo.setEstimateWaitingTime(2);
        queueInfo.setWaitingSize(3);
        queueInfo.setSeatTypeName("hh");
        return new ResponseEntity<>(queueInfo, HttpStatus.OK);
    }

    @PostMapping("/queues/checkin")
    public ResponseEntity<Object> checkinForCustomer(@RequestBody Long queueId){

        return null;
    }
}
