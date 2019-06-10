package com.geekzila.validationclient.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.geekzila.validationclient.service.ValidationClientService;
import com.thoughtworks.xstream.core.SequenceGenerator;

@Service
public class ValidationClientServiceImpl implements ValidationClientService{

	private static final String SERVER_TERMINAL_URL = "/v1/terminal/server";

	  private final SequenceGenerator sequenceGenerator;

	  private final RestTemplate restTemplate;

	  private final RetryTemplate retryTemplate;
	  
	  public ValidationServiceImpl(SequenceGenerator sequenceGenerator, RestTemplate restTemplate, RetryTemplate retryTemplate) {
		  this.sequenceGenerator = sequenceGenerator;
		    this.restTemplate = restTemplate;
		    this.retryTemplate = retryTemplate;
	  }

	@Override
	public String processRequest(Map<String, Object> payload) {
		final TerminalResponse terminalResponse = retryTemplate.execute(
	            (RetryCallback<TerminalResponse, RestClientException>) context ->
	                    restTemplate.getForObject(SERVER_TERMINAL_URL + "/availableTerminalId", TerminalResponse.class));

	    final int sequenceId = sequenceGenerator.getNext();
	    final long timestamp = System.currentTimeMillis();
	    final TerminalPayload terminalPayload = new TerminalPayload(terminalResponse.getTerminalId(), sequenceId, timestamp);

	    return retryTemplate.execute(
	            (RetryCallback<String, RestClientException>) context ->
	                    restTemplate.postForObject(SERVER_TERMINAL_URL, terminalPayload, String.class));
	}
	  

}
