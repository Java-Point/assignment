package com.geekzila.terminalserver.service;

public interface TerminalServerService {

	String getAvailableTerminalId();

	void processRequest(String terminalId, int sequenceNo, long timestamp) throws Exception;

}
