package com.drive.common.beans.statut;

import com.drive.common.beans.statut.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderCaptureUpdateMotiveStatus implements StatusCode {
	/**
	 * Capture was successful
	 */
	CAPTURE_OK("Capture successful"),
	/**
	 * Capture failed because of one or more validation errors (eg: missing field, field in wrong format etc.)
	 */
	VALIDATION_KO("Validation error(s)");

	@Getter
	private final String reason;

	@Override
	public String getCode() {
		return this.name();
	}

}
