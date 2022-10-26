package com.mtech.ique.queueservice.service.impl;

import com.mtech.ique.queueservice.model.entity.QueueInfo;
import com.mtech.ique.queueservice.model.entity.QueueTicket;
import com.mtech.ique.queueservice.repository.QueueTicketRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class QueueManagementImplTest {
  @Mock QueueTicketRepository queueTicketRepository;

  @InjectMocks QueueManagementImpl queueManagement;

  @BeforeEach
  void setUp() {}

  @AfterEach
  void tearDown() {}

  @Test
  void getQueueTicketDetail() {
    QueueTicket queueTicket = new QueueTicket(1L, 1L, 1L, 1L, 1L, 1L, 1L, null, "1");

    Mockito.when(queueTicketRepository.findById(1L)).thenReturn(java.util.Optional.of(queueTicket));

    HashMap<String, Object> queueTicketDetail = queueManagement.getQueueTicketDetail(1L);

    assertEquals(1L, queueTicketDetail.get("id"));

    Mockito.verify(queueTicketRepository, times(1)).findById(1L);
  }

  @Test
  void checkIn() {
  }

  @Test
  void call() throws ExecutionException, InterruptedException {

  }

  @Test
  void createQueues() {
    List<HashMap<String, Object>> seatTypeList = new ArrayList<>();
    HashMap<String, Object> seatType = new HashMap<>();
    seatType.put("id", 111L);
    seatType.put("name", "big");
    seatTypeList.add(seatType);

    List<HashMap<String, Object>> seatTypeListResult = queueManagement.createQueues(seatTypeList);

    assertEquals(1, queueManagement.getQueueList().size());
    assertEquals(111L, queueManagement.getQueueList().get(0).getSeatType().getSeatTypeId());
    assertEquals("big", queueManagement.getQueueList().get(0).getSeatType().getName());

    assertEquals(111l, seatTypeListResult.get(0).get("seatTypeId"));
  }

  @Test
  void deleteQueues() {
    List<HashMap<String, Object>> seatTypeList = new ArrayList<>();
    HashMap<String, Object> seatType = new HashMap<>();
    seatType.put("id", 111L);
    seatType.put("name", "big");
    seatTypeList.add(seatType);

    List<HashMap<String, Object>> seatTypeListResult = queueManagement.createQueues(seatTypeList);

    List<Long> queueIdList = new ArrayList<>();
    long queueId = Long.valueOf(String.valueOf(seatTypeListResult.get(0).get("queueId")));
    queueIdList.add(queueId);

    queueManagement.deleteQueues(queueIdList);

    assertEquals(0, queueManagement.getQueueList().size());
  }

  @Test
  void getQueueInfoDetail() {
    List<HashMap<String, Object>> seatTypeList = new ArrayList<>();
    HashMap<String, Object> seatType = new HashMap<>();
    seatType.put("id", 111L);
    seatType.put("name", "big");
    seatTypeList.add(seatType);

    List<HashMap<String, Object>> seatTypeListResult = queueManagement.createQueues(seatTypeList);
    long queueId = Long.valueOf(String.valueOf(seatTypeListResult.get(0).get("queueId")));
    QueueInfo queueInfo = queueManagement.getQueueInfoDetail(queueId);

    assertEquals(queueId, queueInfo.getQueueId());
  }

  @Test
  void getQueueTicketsByUser() {
    List<QueueTicket> queueTicketList = Collections.singletonList(new QueueTicket(1L, 2L, 3L, 1L, 1L, 1L, 1L, null, "1"));

    Mockito.when(queueTicketRepository.findAllByCustomerId(3L)).thenReturn(queueTicketList);

    queueManagement.getQueueTicketsByUser(3L);

    Mockito.verify(queueTicketRepository, times(1)).findAllByCustomerId(3L);
  }

  @Test
  void getQueueTicketsByStore() {
    List<QueueTicket> queueTicketList = Collections.singletonList(new QueueTicket(1L, 2L, 3L, 1L, 1L, 1L, 1L, null, "1"));

    Mockito.when(queueTicketRepository.findAllByStoreId(2L)).thenReturn(queueTicketList);

    queueManagement.getQueueTicketsByStore(2L);

    Mockito.verify(queueTicketRepository, times(1)).findAllByStoreId(2L);
  }

  @Test
  void skipCustomer() {}

  @Test
  void getQueueTicketsByUserAndStore() {
    List<QueueTicket> queueTicketList = Collections.singletonList(new QueueTicket(1L, 2L, 3L, 1L, 1L, 1L, 1L, null, "1"));

    Mockito.when(queueTicketRepository.findAllByCustomerId(3L)).thenReturn(queueTicketList);

    queueManagement.getQueueTicketsByUser(3L);

    Mockito.verify(queueTicketRepository, times(1)).findAllByCustomerId(3L);
  }
}
