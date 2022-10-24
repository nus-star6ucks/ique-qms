package com.mtech.ique.queueservice.service.impl;

import com.mtech.ique.queueservice.model.DirectNotification;
import com.mtech.ique.queueservice.model.entity.*;
import com.mtech.ique.queueservice.model.enums.TicketStatus;
import com.mtech.ique.queueservice.repository.QueueTicketRepository;
import com.mtech.ique.queueservice.service.NotificationService;
import com.mtech.ique.queueservice.service.QueueManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class QueueManagementImpl implements QueueManagementService {

  @Autowired private QueueTicketRepository queueTicketRepository;

  @Autowired NotificationService notificationService;

  private ArrayList<QueueList> queueList = new ArrayList<>();

  @Override
  public HashMap<String, Object> createTicket(Long queueId, Long customerId, Long storeId) {

    HashMap<String, Object> hashMap = new HashMap<>();
    System.out.println(queueList.size());
    for (QueueList queue : queueList) {
      if (queue.getQueueId() == queueId) {

        // initiate Ticket
        QueueTicket queueTicket = new QueueTicket();
        queueTicket.setQueueNumber(queue.getQueueNumber() + 1);
        queueTicket.setSeatType(queue.getSeatType());
        queueTicket.setCustomerId(customerId);
        queueTicket.setStoreId(storeId);
        queueTicket.setQueueId(queueId);
        queueTicket.setStatus(TicketStatus.PENDING.toString());
        // insert queueTicket into sql
        queueTicketRepository.save(queueTicket);

        // update queue information
        queue.getQueueTickets().add(queueTicket);
        queue.setWaitingSize(queue.getWaitingSize() + 1);
        queue.setEstimateWaitingTime(queue.getWaitingSize() * 5);

        queue.setQueueNumber(queue.getQueueNumber() + 1);

        // format return
        hashMap.put("ticketId", queueTicket.getTicketId());
        hashMap.put("queueNumber", queueTicket.getQueueNumber());
        hashMap.put("seatTypeName", queueTicket.getSeatType().getName());
        hashMap.put("waitingSize", queue.getWaitingSize() - 1);
        hashMap.put("estimateWaitingTime", queue.getEstimateWaitingTime() - 5);

        System.out.print(queueTicket.getQueueNumber());
        return hashMap;
      }
    }
    return hashMap;
  }

  @Override
  public HashMap<String, Object> getQueueTicketDetail(Long ticketId) {
    HashMap<String, Object> queueInfo = new HashMap<>();
    for (QueueList queue : queueList) {
      for (QueueTicket queueTicket : queue.getQueueTickets()) {
        if (queueTicket.getTicketId().equals(ticketId)) {
          queueInfo.put("id", ticketId);
          queueInfo.put("storeId", queueTicket.getStoreId());
          queueInfo.put("customerId", queueTicket.getCustomerId());
          queueInfo.put("queueNumber", queueTicket.getQueueNumber());
          queueInfo.put("startTime", queueTicket.getStartTime());
          queueInfo.put("endTime", queueTicket.getEndTime());
          queueInfo.put("status", queueTicket.getStatus());
          QueueInfo tempQueueInfo =
              new QueueInfo(
                  queue.getQueueId(),
                  queue.getWaitingSize(),
                  queue.getEstimateWaitingTime(),
                  queueTicket.getSeatType());
          queueInfo.put("queueInfo", tempQueueInfo);
          return queueInfo;
        }
      }
    }

    return queueInfo;
  }

  @Override
  public boolean checkIn(Long ticketId) {

    for (QueueList queue : queueList) {
      LinkedList<QueueTicket> queueListList = queue.getQueueTickets();
      for (QueueTicket queueTicket : queue.getQueueTickets()) {
        if (queueTicket.getTicketId().equals(ticketId)) {
          queueListList.poll();
          queue.setWaitingSize(queue.getWaitingSize() - 1);
          queue.setEstimateWaitingTime(queue.getWaitingSize() * 5);
          queueTicket.setStatus(TicketStatus.SEATED.toString());
          queue.setQueueTickets(queueListList);
          // update database
          queueTicketRepository.save(queueTicket);
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void call(Long ticketId) throws ExecutionException, InterruptedException {
    // find customer by ticketId
    Optional<QueueTicket> queueTicketOp = queueTicketRepository.findById(ticketId);
    if (queueTicketOp.isPresent()) {
      QueueTicket queueTicket = queueTicketOp.get();
      Long customerId = queueTicket.getCustomerId();
      // notify customer
      DirectNotification notification = new DirectNotification();
      notification.setMessage("We are ready to serve!");
      notification.setTitle("iQue");
      notification.setTarget(notificationService.getTokenByUserId(customerId));
      notificationService.sendNotificationToTarget(notification);

      // find next 2 customers in queue
      Long queueId = queueTicket.getQueueId();
      Optional<QueueList> queueOp =
          queueList.stream().filter(queue -> queueId == queue.getQueueId()).findFirst();

      if (queueOp.isPresent()) {
        LinkedList<QueueTicket> queueTickets = queueOp.get().getQueueTickets();
        // only send notification if queue.size > 1
        if (queueTickets.size() > 1) {
          List<QueueTicket> topTwoTickets =
              queueTickets.stream()
                  .filter(t -> t.getTicketId() != ticketId)
                  .limit(3)
                  .collect(Collectors.toList());

          topTwoTickets.forEach(
              nextTicket -> {
                Long customerId1 = nextTicket.getCustomerId();
                // notify
                DirectNotification nextNotification = new DirectNotification();
                nextNotification.setMessage("You are next!");
                nextNotification.setTitle("title");
                try {
                  nextNotification.setTarget(notificationService.getTokenByUserId(customerId));
                } catch (Exception e) {
                  e.printStackTrace();
                }
                notificationService.sendNotificationToTarget(nextNotification);
              });
        }
      }
    }
  }

  @Override
  public List<HashMap<String, Object>> createQueues(List<HashMap<String, Object>> seatTypeList) {

    List<HashMap<String, Object>> hashMapList = new ArrayList<>();
    for (HashMap<String, Object> hashMapMap : seatTypeList) {
      // initiate queueList
      QueueList queue = new QueueList();
      long queueId = Math.abs(UUID.randomUUID().toString().replace("-", "").hashCode()) % 10000;
      queue.setQueueId(queueId);
      SeatType seatType = new SeatType();
      long seatTypeId = Long.valueOf(String.valueOf(hashMapMap.get("id")));
      seatType.setId(seatTypeId);
      seatType.setName(String.valueOf(hashMapMap.get("name")));
      queue.setSeatType(seatType);
      queueList.add(queue);
      // format return
      HashMap<String, Object> hashMap = new HashMap<>();
      hashMap.put("seatTypeId", seatTypeId);
      hashMap.put("queueId", queueId);
      hashMapList.add(hashMap);
    }
    return hashMapList;
  }

  @Override
  public void deleteQueues(List<Long> queueIdList) {
    QueueList queueListTemp = new QueueList();
    for (long queueId : queueIdList) {
      for (QueueList queue : queueList) {
        if (queue.getQueueId() == queueId) {
          queueListTemp = queue;
          break;
        }
      }
    }
    queueList.remove(queueListTemp);
  }

  @Override
  public QueueInfo getQueueInfoDetail(Long queueId) {
    QueueInfo queueInfo = new QueueInfo();
    queueInfo.setQueueId(0);
    for (QueueList queue : queueList) {
      if (queue.getQueueId() == queueId) {
        queueInfo.setQueueId(queueId);
        queueInfo.setSeatType(queue.getSeatType());
        queueInfo.setEstimateWaitingTime(queue.getEstimateWaitingTime());
        queueInfo.setWaitingSize(queue.getWaitingSize());
        return queueInfo;
      }
    }
    return queueInfo;
  }

  @Override
  public List<QueueTicket> getQueueTicketsByUser(Long userId) {
    List<QueueTicket> queueTickets = queueTicketRepository.findAllByCustomerId(userId);
    return getQueueTicketsOfToday(queueTickets);
  }

  private Long getTimestampOfTodayMiddleNight() {
    Calendar day = Calendar.getInstance();
    day.set(Calendar.MILLISECOND, 0);
    day.set(Calendar.SECOND, 0);
    day.set(Calendar.MINUTE, 0);
    day.set(Calendar.HOUR_OF_DAY, 0);
    return day.getTimeInMillis();
  }

  @Override
  public List<QueueTicket> getQueueTicketsByStore(Long storeId) {
    List<QueueTicket> queueTickets = queueTicketRepository.findAllByStoreId(storeId);
    return getQueueTicketsOfToday(queueTickets);
  }

  private List<QueueTicket> getQueueTicketsOfToday(List<QueueTicket> queueTickets) {
    return queueTickets.stream()
        .filter(t -> t.getStartTime() <= getTimestampOfTodayMiddleNight())
        .collect(Collectors.toList());
  }

  @Override
  public void skipCustomer(Long ticketId) {
    checkIn(ticketId);
    QueueTicket queueTicket = queueTicketRepository.findById(ticketId).get();
    queueTicket.setStatus(TicketStatus.SKIPPED.toString());
    queueTicketRepository.save(queueTicket);
  }

  @Override
  public List<QueueTicket> getQueueTicketsByUserAndStore(Long userId, Long storeId) {
    List<QueueTicket> queueTicketsByUser = queueTicketRepository.findAllByCustomerId(userId);
    List<QueueTicket> queueTickets = new ArrayList<>();
    for (QueueTicket queueTicket : queueTicketsByUser) {
      if (queueTicket.getStoreId().equals(storeId)
          && queueTicket.getStatus().equals(TicketStatus.PENDING.toString())) {
        queueTickets.add(queueTicket);
      }
    }
    return queueTickets;
  }
}
