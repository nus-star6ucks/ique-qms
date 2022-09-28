//package com.mtech.ique.queueservice.service.impl;
//
//import com.mtech.ique.queueservice.model.entity.QueueInfo;
//import com.mtech.ique.queueservice.model.entity.QueueList;
//import com.mtech.ique.queueservice.model.entity.QueueTicket;
//import com.mtech.ique.queueservice.model.entity.User;
//import com.mtech.ique.queueservice.repository.QueueInfoRepository;
//import com.mtech.ique.queueservice.repository.QueueTicketRepository;
//import com.mtech.ique.queueservice.service.QueueManagementService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@Service
//public class QueueManagementImpl implements QueueManagementService {
////    @Autowired private QueueInfoRepository queueInfoRepository;
//    @Autowired private QueueTicketRepository queueTicketRepository;
//    private ArrayList<QueueList> queueList = new ArrayList<>();
//    private HashMap<Long, LinkedList<QueueTicket>> queueTicketsMap = new HashMap<Long, LinkedList<QueueTicket>>();
//
//    @Override
//    public void createTicket(Long queueId){
//
//        for (QueueList queue: queueList) {
//            if (queue.getQueueId() == queueId){
//
//            }
//        }
//        UUID uuid = UUID.randomUUID();
//        long ticket = uuid.toString().replace("-", "").hashCode();
////        long queueId = queueTicket.getQueueId();
////        if (!queueTicketsMap.containsKey(queueId)){
////            LinkedList<QueueTicket> linkedList = new LinkedList<>();
////            linkedList.add(queueTicket);
////            queueTicketsMap.put(queueId, linkedList);
////        }
////        else
////            queueTicketsMap.get(queueId).add(queueTicket);
//    }
//
//    private QueueTicket initiateTicket(Long queueId){
//        //初始化ticketId
//        UUID uuid = UUID.randomUUID();
//        long ticketId = uuid.toString().replace("-", "").hashCode();
//        QueueTicket queueTicket = new QueueTicket();
//        queueTicket.setTicketId(ticketId);
//        //初始化时间
//        Date date = new Date();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss");
//        String startTime = dateFormat.format(date);
//        queueTicket.setStartTime(startTime);
//        //初始化searType
//        return queueTicket;
//    }
//
//    @Override
//    public List<QueueTicket> getTickets() {
//        List<QueueTicket> ticketList = null;
//        for (LinkedList linkedList: queueTicketsMap.values()) {
//            ticketList.addAll(linkedList);
//        }
//        return ticketList;
//    }
//
//    @Override
//    public QueueInfo getQueueInfo(Long ticketId, Long queueId) {
//        Iterator<Long> iterator = queueTicketsMap.keySet().iterator();
//        while(iterator.hasNext()){
//            Long key = iterator.next();
//            LinkedList<QueueTicket> linkedTicket = queueTicketsMap.get(key);
//            for (QueueTicket queueTicket: linkedTicket) {
//                if (queueTicket.getTicketId() == ticketId){
//                    return queueInfoRepository.findById(queueTicket.getQueueId()).get();
//                }
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public QueueTicket checkIn(Long ticketId, Long queueId) {
//        Iterator<Long> iterator = queueTicketsMap.keySet().iterator();
//        while(iterator.hasNext()){
//            Long key = iterator.next();
//            LinkedList<QueueTicket> linkedTicket = queueTicketsMap.get(key);
//            for (QueueTicket queueTicket: linkedTicket) {
//                if (queueTicket.getTicketId() == ticketId){
//                    return queueTicketsMap.get(queueId).poll();
//                }
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public void notify(User user) {
//
//    }
//
//    @Override
//    public void createQueue(long queueID){
//        LinkedList<QueueTicket> linkedList = new LinkedList<>();
//        queueTicketsMap.put(queueID, linkedList);
//    }
//
//}
