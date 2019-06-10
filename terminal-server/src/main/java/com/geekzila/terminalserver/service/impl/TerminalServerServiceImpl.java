package com.geekzila.terminalserver.service.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.geekzila.terminalserver.config.TerminalServerConfig;
import com.geekzila.terminalserver.repository.TerminalServerRepository;
import com.geekzila.terminalserver.service.TerminalServerService;

@Service
public class TerminalServerServiceImpl implements TerminalServerService {

	private static final Logger log = LoggerFactory.getLogger(TerminalServerServiceImpl.class);

	private TerminalServerConfig terminalConfig;
	private TerminalServerRepository terminalRepository;

	@Value("${terminal.processing.time.seconds}")
	private long terminalProcessingTime;

	@Autowired
	public TerminalServerServiceImpl(TerminalServerConfig terminalConfig, TerminalServerRepository terminalRepository,
			long terminalProcessingTime) {
		super();
		this.terminalConfig = terminalConfig;
		this.terminalRepository = terminalRepository;
		this.terminalProcessingTime = terminalProcessingTime;
	}

	private static boolean between(int value, int minValueInclusive, int maxValueExclusive) {
		return (value >= minValueInclusive && value < maxValueExclusive);
	}

	@Override
	public String getAvailableTerminalId() {
		// TODO Auto-generated method stub
		return terminalRepository.getTerminalId();
	}

	@Override
	public void processRequest(String terminalId, int sequenceNo, long timestamp) throws Exception {
		log.info("Terminal Payload: terminalId={} sequence number={} timestamp={}", terminalId, sequenceNo, timestamp);
	    if (isNotValidRequest(sequenceNo, terminalId)) {
	      throw new InvalidRequestException(String.format("sequenceNo(%d) is either not in range or terminal(%s) not locked for use", sequenceNo, terminalId));
	    }
	    try {
	      TimeUnit.SECONDS.sleep(terminalProcessingTime);
	    } 
	    finally {
	      terminalRepository.makeTerminalAvailable(terminalId);
	    }
	}

	private boolean isNotValidRequest(int sequenceNo, String terminalId) {
		return !between(sequenceNo, terminalConfig.getStart(), terminalConfig.getEnd())	|| !terminalRepository.isTerminalValid(terminalId);
	}

// TODO Auto-generated method stub

}
