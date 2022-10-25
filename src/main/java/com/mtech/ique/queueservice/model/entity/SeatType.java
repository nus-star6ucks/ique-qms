package com.mtech.ique.queueservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class SeatType {
  @Column(name = "seatTypeId")
  private Long id;

  private String name;
}
