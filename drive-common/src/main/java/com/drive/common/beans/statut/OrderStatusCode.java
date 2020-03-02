package com.drive.common.beans.statut;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatusCode implements StatusCode {
	KO("Ko"),
	CAPTURED("Capturée"),
	CAPTURE_KO("Echec de capture"),
	CATEGORIZED("Catégorisée"),
	CATEGORIZATION_KO("Echec de catégorisation"),
	VALIDATED("Validée"),
	VALIDATED_KO("Echec de validation"),
	CANCELLED("Annulée"),
	PREPARED("Préparée"),
	PREPARED_KO("Echec de préparation"),
	IN_PREPARATION("En cours de préparation"),
	IN_PREPARATION_KO("Echec de la préparation en cours");

	@Getter
	private final String reason;

	@Override
	public String getCode() {
		return this.name();
	}

}
