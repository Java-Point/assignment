package com.geekzila.terminalserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class TerminalNotAvailable {

	 public void TerminalServerException(String message) {
		    super(message);
		  }
}
