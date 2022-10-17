package com.mtech.ique.queueservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class QueueTicket {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long ticketId;

  private Long storeId;
  private Long customerId;
  private Long queueNumber;
  private Long queueId;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private Long startTime;

  @LastModifiedDate private Long endTime;

  private SeatType seatType;

  private String status;
}
