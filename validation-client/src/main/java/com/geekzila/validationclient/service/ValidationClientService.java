package com.geekzila.validationclient.service;

import java.util.Map;

public interface ValidationClientService {

	String processRequest(Map<String, Object> payload);
}
