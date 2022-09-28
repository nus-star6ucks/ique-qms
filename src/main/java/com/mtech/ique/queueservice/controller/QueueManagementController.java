package com.mtech.ique.queueservice.controller;

import com.mtech.ique.queueservice.service.QueueManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/queue")
public class QueueManagementController {

    @Autowired private QueueManagementService queueManagementService;

}
