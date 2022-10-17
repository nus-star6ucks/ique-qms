package com.mtech.ique.queueservice.repository;

import com.mtech.ique.queueservice.model.entity.QueueTicket;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QueueTicketRepository extends CrudRepository<QueueTicket, Long> {
    List<QueueTicket> findAllByCustomerId(long id);
    List<QueueTicket> findAllByStoreId(long id);
}
