package com.mtech.ique.queueservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@AllArgsConstructor
@Setter
@Getter
public class QueueList {
    private long queueId;
    private LinkedList<QueueTicket> queueTickets;
    private long estimateWaitingTime;
    private SeatType seatType;
    private long waitingSize;

    public QueueList() {
        queueId = 0;
        queueTickets = new LinkedList<QueueTicket>();
        estimateWaitingTime = 0;
        seatType = new SeatType();
        waitingSize = 0;
    }
}
