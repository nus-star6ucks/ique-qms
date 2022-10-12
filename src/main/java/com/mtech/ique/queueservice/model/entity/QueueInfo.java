package com.mtech.ique.queueservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//import javax.persistence.Entity;
//import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class QueueInfo {

    private long queueId;

    private int waitingSize;

    private long estimateWaitingTime;

    private String seatTypeName;
}
