package com.drive.common.beans;

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

	public static StatusCode validationErrors(String reason) {
		return new SimpleStatusCode(VALIDATION_KO.name(), VALIDATION_KO.getReason() + " : " + reason);
	}

	public static StatusCode bc301Errors(String reason) {
		return new SimpleStatusCode(VALIDATION_KO.name(), "Non respect du XSD : " + reason);
	}

	public static StatusCode deliveryDatePassed() {
		return makeDeliveryDateTimeError("Date antérieure à la réception de la commande");
	}

	public static StatusCode deliveryDateTooSoon() {
		return makeDeliveryDateTimeError("Date postérieure à la réception de la commande, mais trop proche");
	}

	public static StatusCode deliveryDateTooLate() {
		return makeDeliveryDateTimeError("Date postérieure à la réception de la commande, mais trop lointaine");
	}

	public static StatusCode noArticles() {
		return new SimpleStatusCode(VALIDATION_KO.name(), "Liste des articles vide");
	}

	public static StatusCode noCreationDateTime() {
		return makeCreationDateTimeError("Date absente");
	}

	public static StatusCode creationDateTimeInFuture() {
		return makeCreationDateTimeError("Date postérieure à la réception");
	}

	private static StatusCode makeDeliveryDateTimeError(String reason) {
		return new SimpleStatusCode(VALIDATION_KO.name(), "Date de livraison incorrecte : " + reason);
	}

	private static StatusCode makeCreationDateTimeError(String reason) {
		return new SimpleStatusCode(VALIDATION_KO.name(), "Date de création invalide : " + reason);
	}

	public static StatusCode paymentMethodNotFound() {
		return new SimpleStatusCode(VALIDATION_KO.name(), "Moyen de paiement inconnu ou n'est pas géré");
	}
}
