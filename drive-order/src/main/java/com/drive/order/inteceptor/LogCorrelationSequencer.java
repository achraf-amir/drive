package com.drive.order.inteceptor;

import java.util.UUID;

public class LogCorrelationSequencer {
	private static final char SEQUENCE_SEPARATOR = ' ';
	private StringBuilder builder;
	private int sequence;
	private int offset;

	public LogCorrelationSequencer(String parentOrPreviouscorrelationId, boolean isParentCorrelationId) {
		this.builder = new StringBuilder(parentOrPreviouscorrelationId.length() + 5);
		this.builder.append(parentOrPreviouscorrelationId);
		if (isParentCorrelationId) {
			this.builder.append(' ');
			this.offset = this.builder.length();
		} else {
			this.offset = parentOrPreviouscorrelationId.lastIndexOf(32) + 1;
			this.sequence = Integer.parseInt(parentOrPreviouscorrelationId.substring(this.offset));
		}

	}

	public LogCorrelationSequencer() {
		this(UUID.randomUUID().toString(), true);
	}

	public synchronized String getId() {
		return this.builder.length() > this.offset ? this.builder.toString() : this.incAndGetId();
	}

	public synchronized String incAndGetId() {
		this.builder.setLength(this.offset);
		this.builder.append(++this.sequence);
		return this.builder.toString();
	}

	public String toString() {
		return this.getId();
	}
}
