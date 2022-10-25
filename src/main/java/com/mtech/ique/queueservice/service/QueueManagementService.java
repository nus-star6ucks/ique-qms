package com.mtech.ique.queueservice.service;

import com.mtech.ique.queueservice.model.entity.QueueInfo;
import com.mtech.ique.queueservice.model.entity.QueueTicket;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface QueueManagementService {

  HashMap<String, Object> createTicket(Long queueId, Long customerId, Long storeId);

  HashMap<String, Object> getQueueTicketDetail(Long ticketId);

  String checkIn(Long ticketId);

  String call(Long ticketId) throws ExecutionException, InterruptedException;

  List<HashMap<String, Object>> createQueues(List<HashMap<String, Object>> seatTypeList);

  void deleteQueues(List<Long> queueIdList);

  QueueInfo getQueueInfoDetail(Long queueId);

  List<QueueTicket> getQueueTicketsByUser(Long userId);

  List<QueueTicket> getQueueTicketsByStore(Long storeId);

  String skipCustomer(Long ticketId);

  List<QueueTicket> getQueueTicketsByUserAndStore(Long userId, Long storeId);
}
