package com.mtech.ique.queueservice.controller;

import com.mtech.ique.queueservice.model.entity.QueueInfo;
//import com.mtech.ique.queueservice.model.entity.QueueTicket;
//import com.mtech.ique.queueservice.service.QueueManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/queues")
public class QueueManagementController {

    @GetMapping("/{queueId}")
    public ResponseEntity<QueueInfo> getQueueInfo(@PathVariable Long queueId){
        QueueInfo queueInfo = new QueueInfo();
        queueInfo.setQueueId(queueId);
        queueInfo.setEstimateWaitingTime(2);
        queueInfo.setWaitingSize(3);
        queueInfo.setSeatTypeName("hh");
        return new ResponseEntity<>(queueInfo, HttpStatus.OK);
    }
}
