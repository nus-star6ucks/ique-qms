//package com.mtech.ique.queueservice.model.entity;
//
//import com.mtech.ique.queueservice.model.enums.TicketStatus;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.Embedded;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Setter
//@Getter
//@Entity
//public class QueueTicket {
//    @Id
//    private long ticketId;
//
//    private long storeId;
//    private long customerId;
//    private long queueNumber;
//    private long queueId;
//    private String startTime;
//    private String endTime;
//
//    @Embedded
//    private SeatType seatType;
//
//    private TicketStatus status = TicketStatus.WAITTING;
//
//}
