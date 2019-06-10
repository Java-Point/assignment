package com.geekzila.terminalserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TerminalServerException extends RuntimeException{

	public void InvalidRequestException(String message) {
	    super(message);
	  }
}
