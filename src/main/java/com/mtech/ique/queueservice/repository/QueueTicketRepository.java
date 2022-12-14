package com.mtech.ique.queueservice.repository;

import com.mtech.ique.queueservice.model.entity.QueueTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueueTicketRepository extends JpaRepository<QueueTicket, Long> {
  List<QueueTicket> findAllByCustomerId(long id);

  List<QueueTicket> findAllByStoreId(long id);

  List<QueueTicket> findAllByQueueId(long queueId);
}
