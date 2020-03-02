package com.drive.common.beans.statut;

import java.io.Serializable;

/**
 * Simple interface that represents a status code and it's label Implement this
 * interface in order to be able to use
 * {@link Status#fromStatusCode(StatusCode, StatusCode)}
 */
public interface StatusCode extends Serializable {
	String getCode();

	String getReason();

}
