package com.mtech.ique.queueservice.service.impl;

import com.mtech.ique.queueservice.model.entity.QueueInfo;
import com.mtech.ique.queueservice.model.entity.QueueTicket;
import com.mtech.ique.queueservice.model.entity.User;
import com.mtech.ique.queueservice.repository.QueueInfoRepository;
import com.mtech.ique.queueservice.repository.QueueRepository;
import com.mtech.ique.queueservice.repository.QueueTicketRepository;
import com.mtech.ique.queueservice.service.QueueManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueueManagementImpl implements QueueManagementService {
    @Autowired private QueueInfoRepository queueInfoRepository;
    @Autowired private QueueRepository queueRepository;
    @Autowired private QueueTicketRepository queueTicketRepository;
    private List<QueueTicket> tickets;

    @Override
    public void createTicket(QueueTicket queueTicket){

    }

    @Override
    public List<QueueTicket> getTickets() {
        return null;
    }

    @Override
    public QueueInfo getQueueInfo(Long ticketId) {
        return null;
    }

    @Override
    public QueueTicket checkIn(Long ticketId) {
        return null;
    }

    @Override
    public void notify(User user) {

    }

}
