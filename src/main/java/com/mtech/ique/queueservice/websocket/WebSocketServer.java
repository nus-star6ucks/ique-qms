package com.mtech.ique.queueservice.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {
//    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet
//            = new CopyOnWriteArraySet<WebSocketServer>();
//    private Session session;
}
