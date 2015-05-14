package com.bhaptics.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bhaptics.demo.service.SerialCommunicationService;

@Controller
public class HomeController {
	final static Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	SerialCommunicationService serialCommunicationService;
	
	@RequestMapping(value = {"/", "/index"})
	ModelAndView  home() {
		ModelAndView mav = new ModelAndView("home/index");
		mav.addObject("ports", serialCommunicationService.getPorts());
		return mav;
	}
	
	@MessageMapping("/hello")
//    @SendTo("/topic/greetings")
    public void greeting(String param) throws Exception {
		String[] value = param.split(",");
		logger.debug("{}", param);
		serialCommunicationService.write(value[0], "0," + value[1] + "," + value[2] + ",");
    }
	
	@MessageMapping("/done")
//  @SendTo("/topic/greetings")
  public void done(String portName) throws Exception {
		logger.info("{}", portName);
		serialCommunicationService.write(portName, "-1,");
  }
}
