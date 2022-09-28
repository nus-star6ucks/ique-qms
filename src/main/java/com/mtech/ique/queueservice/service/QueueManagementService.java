package com.mtech.ique.queueservice.service;

import com.mtech.ique.queueservice.model.entity.QueueInfo;
import com.mtech.ique.queueservice.model.entity.QueueTicket;
import com.mtech.ique.queueservice.model.entity.User;

import java.util.List;

public interface QueueManagementService {

    void createTicket(QueueTicket queueTicket);

    List<QueueTicket> getTickets();

    QueueInfo getQueueInfo(Long ticketId);

    QueueTicket checkIn(Long ticketId);

    void notify(User user);
}
