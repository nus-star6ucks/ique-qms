package com.mtech.ique.queueservice.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class SeatType {
  private Long seatTypeId;
  private String name;
}
