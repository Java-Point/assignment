package com.geekzila.terminalserver.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geekzila.terminalserver.service.TerminalServerService;

@RestController
@RequestMapping(value = "v1/terminal/server")
public class TerminalServerController {

	private TerminalServerService terminalServerService;

	public TerminalServerController(TerminalServerService terminalServerService) {
		super();
		this.terminalServerService = terminalServerService;
	}
	
	 @GetMapping("availableTerminalId")
	  public ResponseEntity<TerminalResponse> getAvailableTerminalId() {
	    final String terminalId = terminalServerService.getAvailableTerminalId();
	    return ResponseEntity.ok(new TerminalResponse(terminalId));
	  }

	  @PutMapping
	  public ResponseEntity<String> processTerminalRequest(@RequestBody @Valid TerminalPayload payload) throws Exception {
	    terminalService.processRequest(payload.getTerminalId(), payload.getSequenceNo(), payload.getTimestamp());
	    return ResponseEntity.ok().build();
	  }
}
