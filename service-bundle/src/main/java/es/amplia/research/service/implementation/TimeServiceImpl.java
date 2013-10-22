package es.amplia.research.service.implementation;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.amplia.research.service.definition.TimeService;

public class TimeServiceImpl implements TimeService{

	private static Logger logger = LoggerFactory.getLogger(TimeServiceImpl.class);
	
	public String getCurrentTime() {
		logger.info("current time requested!!!");
		return new Date().toString();
	}

}
