package com.zoe.wiki.service;

import com.zoe.wiki.websocket.WebSocketServer;
import javax.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class WsService {
  @Resource
  public WebSocketServer webSocketServer;

  @Async
  public void sendInfo(String message) {
    webSocketServer.sendInfo(message);
  }

}
