package com.drive.common.beans;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SimpleStatusCode implements StatusCode {

	private static final long serialVersionUID = 6347411483275186595L;

	private final String code;

	private final String reason;

}
