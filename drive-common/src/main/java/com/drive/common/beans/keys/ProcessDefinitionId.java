package com.drive.common.beans.keys;

public enum ProcessDefinitionId {

	MAIN_WORKFLOW("main-workflow");

	private final String name;

	ProcessDefinitionId(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
