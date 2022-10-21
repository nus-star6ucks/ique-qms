package com.mtech.ique.queueservice.service;

import com.mtech.ique.queueservice.model.entity.QueueInfo;
import com.mtech.ique.queueservice.model.entity.QueueTicket;
import com.mtech.ique.queueservice.model.entity.User;

import java.util.HashMap;
import java.util.List;

public interface QueueManagementService {

  HashMap<String, Object> createTicket(Long queueId, Long customerId, Long storeId);

  HashMap<String, Object> getQueueTicketDetail(Long ticketId);

  boolean checkIn(Long ticketId);

  void call(Long ticket);

  List<HashMap<String, Object>> createQueues(List<HashMap<String, Object>> seatTypeList);

  void deleteQueues(List<Long> queueIdList);

  QueueInfo getQueueInfoDetail(Long queueId);

  List<QueueTicket> getQueueTicketsByUser(Long userId);

  List<QueueTicket> getQueueTicketsByStore(Long storeId);

  void skipCustomer(Long ticketId);

  List<QueueTicket> getQueueTicketsByUserAndStore(Long userId, Long storeId);
}
