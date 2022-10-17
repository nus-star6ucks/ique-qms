package com.mtech.ique.queueservice.service.impl;

import com.mtech.ique.queueservice.model.entity.*;
import com.mtech.ique.queueservice.model.enums.TicketStatus;
import com.mtech.ique.queueservice.repository.QueueTicketRepository;
import com.mtech.ique.queueservice.service.QueueManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class QueueManagementImpl implements QueueManagementService {
    @Autowired private QueueTicketRepository queueTicketRepository;
    private ArrayList<QueueList> queueList = new ArrayList<>();
    private HashMap<Long, LinkedList<QueueTicket>> queueTicketsMap = new HashMap<Long, LinkedList<QueueTicket>>();

    @Override
    public HashMap<String, Object> createTicket(Long queueId){
        HashMap<String, Object> hashMap = new HashMap<>();
        System.out.println(queueList.size());
        for (QueueList queue: queueList) {
            if (queue.getQueueId() == queueId){
                QueueTicket queueTicket = initiateTicket();
                queueTicket.setQueueNumber(queue.getQueueNumber() + 1);
                queueTicket.setSeatType(queue.getSeatType());
                //insert queueTicket into sql
//                queueTicketRepository.save(queueTicket);
                //update queue information
                queue.getQueueTickets().add(queueTicket);
                queue.setWaitingSize(queue.getWaitingSize() + 1);
                queue.setEstimateWaitingTime(queue.getWaitingSize() * 5);
                queue.setQueueNumber(queue.getQueueNumber() + 1);
                //format return
                hashMap.put("ticketId", queueTicket.getTicketId());
                hashMap.put("queueNumber", queueTicket.getQueueNumber());
                hashMap.put("seatTypeName", queueTicket.getSeatType().getName());
                hashMap.put("waitingSize", queue.getWaitingSize());
                hashMap.put("estimateWaitingTime", queue.getEstimateWaitingTime());
                System.out.print(queueTicket.getQueueNumber());
                return hashMap;
            }
        }
        return hashMap;
    }

    private QueueTicket initiateTicket(){
        //初始化ticketId
        UUID uuid = UUID.randomUUID();
        long ticketId = Math.abs(uuid.toString().replace("-", "").hashCode()) % 10000;
        QueueTicket queueTicket = new QueueTicket();
        queueTicket.setTicketId(ticketId);
        //初始化时间
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss");
        String startTime = dateFormat.format(date);
        queueTicket.setStartTime(startTime);
        //初始化searType
        return queueTicket;
    }

    @Override
    public List<QueueTicket> getTickets() {
        List<QueueTicket> ticketList = null;
        for (LinkedList linkedList: queueTicketsMap.values()) {
            ticketList.addAll(linkedList);
        }
        return ticketList;
    }

    @Override
    public HashMap<String, Object> getQueueTicketDetail(Long ticketId) {
        HashMap<String, Object> queueInfo = new HashMap<>();
        for (QueueList queue : queueList) {
            for (QueueTicket queueTicket : queue.getQueueTickets()) {
                if (queueTicket.getTicketId() == ticketId){
                    queueInfo.put("id", ticketId);
                    queueInfo.put("storeId", queueTicket.getStoreId());
                    queueInfo.put("customerId", queueTicket.getCustomerId());
                    queueInfo.put("queueNumber", queueTicket.getQueueNumber());
                    queueInfo.put("startTime", queueTicket.getStartTime());
                    queueInfo.put("endTime", queueTicket.getEndTime());
                    queueInfo.put("status", queueTicket.getStatus().name());
                    QueueInfo tempQueueInfo = new QueueInfo(queue.getQueueId(), queue.getWaitingSize(), queue.getEstimateWaitingTime(), queueTicket.getSeatType().getName());
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
            for (QueueTicket queueTicket : queue.getQueueTickets()) {
                if(queueTicket.getTicketId() == ticketId){
                    queue.getQueueTickets().poll();
                    queue.setWaitingSize(queue.getWaitingSize() - 1);
                    queue.setEstimateWaitingTime(queue.getWaitingSize() * 5);
                    queueTicket.setStatus(TicketStatus.SEATED);
                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss");
                    String endTime = dateFormat.format(date);
                    queueTicket.setEndTime(endTime);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void notify(User user) {

    }

    @Override
    public List<HashMap<String, Object>> createQueues(List<HashMap<String, Object>> seatTypeList) {

        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (HashMap<String, Object> hashMapMap: seatTypeList) {
            QueueList queue = new QueueList();
            long queueId = Math.abs(UUID.randomUUID().toString().replace("-", "").hashCode()) % 10000;
            queue.setQueueId(queueId);
            SeatType seatType = new SeatType();
            long seatTypeId = Long.valueOf(String.valueOf(hashMapMap.get("id")));
            seatType.setId(seatTypeId);
            seatType.setName(String.valueOf(hashMapMap.get("name")));
            queue.setSeatType(seatType);
            queueList.add(queue);
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("seatTypeId", seatTypeId);
            hashMap.put("queueId", queueId);
            hashMapList.add(hashMap);
        }
        return hashMapList;
    }

    @Override
    public void deleteQueues(List<Long> queueIdList) {
        for (long queueId : queueIdList) {
            for (QueueList queue : queueList) {
                if (queue.getQueueId() == queueId) {
                    queueList.remove(queue);
                    break;
                }
            }
        }
    }

    @Override
    public QueueInfo getQueueInfoDetail(Long queueId) {
        QueueInfo queueInfo = new QueueInfo();
        queueInfo.setQueueId(0);
        for (QueueList queue : queueList) {
            if (queue.getQueueId() == queueId){
                queueInfo.setQueueId(queueId);
                queueInfo.setSeatTypeName(queue.getSeatType().getName());
                queueInfo.setEstimateWaitingTime(queue.getEstimateWaitingTime());
                queueInfo.setWaitingSize(queue.getWaitingSize());
                return queueInfo;
            }
        }
        return queueInfo;
    }

    @Override
    public List<QueueTicket> getQueueTicketsByUser(Long userId) {
        return queueTicketRepository.findAllByCustomerId(userId);
    }

    @Override
    public List<QueueTicket> getQueueTicketsByStore(Long storeId) {
        return queueTicketRepository.findAllByStoreId(storeId);
    }

    @Override
    public List<QueueTicket> getQueueTicketsByUserAndStore(Long userId, Long storeId) {
        List<QueueTicket> queueTicketsByUser = queueTicketRepository.findAllByCustomerId(userId);
        List<QueueTicket> queueTickets = new ArrayList<>();
        for (QueueTicket queueTicket : queueTicketsByUser) {
            if (queueTicket.getStoreId() == storeId){
                queueTickets.add(queueTicket);
            }
        }
        return queueTickets;
    }

}
