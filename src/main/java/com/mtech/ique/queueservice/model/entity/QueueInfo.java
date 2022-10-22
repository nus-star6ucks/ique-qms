package com.mtech.ique.queueservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class QueueInfo {

  private long queueId;

  private long waitingSize;

  private long estimateWaitingTime;

  private SeatType seatType;
}
