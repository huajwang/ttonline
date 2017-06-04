package com.troika.emall.websocket;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class SpittrMessageController {

  private SpittleFeedService feedService;

  @Autowired
  public SpittrMessageController(SpittleFeedService feedService) {
	this.feedService = feedService;
  }
  
  @MessageMapping("/spittle")
  @SendTo("/topic/customerservice")
  public Notification handleSpittle(SpittleForm form) {
	  System.out.println("principal = ");
	  //Spittle spittle = new Spittle(principal.getName(), form.getText(), new Date());
	  //feedService.broadcastSpittle(spittle);
	  return new Notification(form.getText());
  }
  
}
