package com.boredream.designrescollection.entity;

public class ResponseSupport {

	protected static final int SC_TIMEOUT = -200;
	protected static final int SC_FAIL = -1;

	private String messageId;
	private String sessionId;
	protected int statusCode;
	protected String statusMessage;

	public ResponseSupport() {
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public boolean hasError() {
		return statusCode < 0;
	}

	public void fail(String msg) {
		statusCode = SC_FAIL;
		statusMessage = msg;
	}

	public void timeout() {
		statusCode = SC_TIMEOUT;
	}

}
