package com.mtech.ique.queueservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class QueueTicket {
    @Id
    private long ticketId;

    private long storeId;
    private long customerId;
    private long queueNumber;
    private long queueId;
    private long startTime;
    private long endTime;
    private enum status{

    }
}
