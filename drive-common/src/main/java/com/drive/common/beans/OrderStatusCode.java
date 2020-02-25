package com.drive.common.beans;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatusCode implements StatusCode {
	KO("Ko", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-KO-10000")),
	CAPTURED("Capturée", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-CAPTURE-D%02d-00000")),
	CAPTURE_KO("Echec de capture", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-CAPTURE-D%02d-10000")),
	CATEGORIZED("Catégorisée", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-CATEGORIZED-D%02d-00000")),
	CATEGORIZATION_KO(
					"Echec de catégorisation",
					StatusEventCode.promise("OMS-STATUS-LOGISTICAL-CATEGORIZED-D%02d-10000")
	),
	AFFECTED("Affectée", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-AFFECTED-D%02d-00000")),
	AFFECTED_KO("Echec d'affectation", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-AFFECTED-D%02d-10000")),
	CHANGED("Modifiée", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-CHANGED-D%02d-00000")),
	CHANGED_KO("Echec de modification", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-CHANGED-D%02d-10000")),
	VALIDATED("Validée", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-VALIDATED-D%02d-00000")),
	VALIDATED_KO("Echec de validation", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-VALIDATED-D%02d-10000")),
	CANCELLED("Annulée", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-CANCELLED-D%02d-00000")),
	CANCELLATION_KO("Annulation KO", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-CANCELLED-D%02d-10000")),
	AUTHORIZATION_CANCELED("Autorisé et annulé", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-CANCELLED-D%02d-00000")),
	OPTIMIZED("Optimisée", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-OPTIMIZED-00000")),
	OPTIMIZATION_KO("Echec d'optimisation", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-OPTIMIZED-10000")),
	PRIORITIZED("Priorisée", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-PRIORITIZED-00000")),
	PRIORITIZATION_KO("Echec de priorisation", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-PRIORITIZED-10000")),
	ROUTED("Routée", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-ROUTED-00000")),
	ROUTING_KO("Echec de routing", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-ROUTED-10000")),
	PREPARED("Préparée", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-PREPARED-00000")),
	PREPARED_KO("Echec de préparation", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-PREPARED-10000")),
	IN_PREPARATION("En cours de préparation", StatusEventCode.none()),
	IN_PREPARATION_KO("Echec de la préparation en cours", StatusEventCode.none()),
	LOADED("Chargée", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-LOADED-00000")),
	DISPATCHED_KO("Echec d'expédition", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-LOADED-10000")),
	TO_ANNOUNCE("A annoncer", StatusEventCode.delivery("OMS-STATUS-LOGISTICAL-TO_ANNOUNCE-00000")),
	ANNOUNCED("Annoncée", StatusEventCode.delivery("OMS-STATUS-LOGISTICAL-ANNOUNCED-00000")),
	ANNOUNCED_KO("Echec d'annonce au TMS", StatusEventCode.delivery("OMS-STATUS-LOGISTICAL-ANNOUNCED-10000")),
	DELIVERED("Livrée", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-DELIVERED-00000")),
	DELIVERED_KO("Echec de livraison", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-DELIVERED-10000")),
	ASSIGNED("Assignée", StatusEventCode.delivery("OMS-STATUS-LOGISTICAL-ASSIGNED-00000")),
	ASSIGNED_KO("Echec d'assignation", StatusEventCode.delivery("OMS-STATUS-LOGISTICAL-ASSIGNED-10000")),
	EXPEDIABLE("Disponible pour expédition", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-EXPEDIABLE-00000")),
	EXPEDIABLE_KO("Disponibilité pour expédition KO", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-EXPEDIABLE-10000")),
	UPDATED_KO("Echec de la mise jour de la commande", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-UPDATED-10000")),
	TREATED("Traitée", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-TREATED-00000")),
	INVOICED("Facturée", StatusEventCode.promise("OMS-STATUS-LOGISTICAL-INVOICED-00000")),

	/** TMS. */
	PENDING("En attente", StatusEventCode.delivery("OMS-STATUS-LOGISTICAL-PENDING-00000")),
	RECEIVED("Reçue", StatusEventCode.delivery("OMS-STATUS-LOGISTICAL-RECEIVED-00000")),
	PARTIAL_RECEIVED("Partiellement reçue", StatusEventCode.delivery("OMS-STATUS-LOGISTICAL-PARTIAL_RECEIVED-00000")),
	ARRIVED("Arrivée", StatusEventCode.delivery("OMS-STATUS-LOGISTICAL-ARRIVED-00000")),
	MISSING("Manquante", StatusEventCode.delivery("OMS-STATUS-LOGISTICAL-MISSING-00000")),
	PARTIAL_ARRIVED("Partiellement arrivée", StatusEventCode.delivery("OMS-STATUS-LOGISTICAL-PARTIAL_ARRIVED-00000")),
	DEPARTED("Expediée", StatusEventCode.delivery("OMS-STATUS-LOGISTICAL-DEPARTED-00000")),
	PARTIAL_DELIVERED("Partiellement livrée", StatusEventCode.delivery("OMS-STATUS-LOGISTICAL-PARTIAL_DELIVERED-00000")),
	NOT_DELIVERED("Non livrée", StatusEventCode.delivery("OMS-STATUS-LOGISTICAL-NOT_DELIVERED-00000")),
	DISCARDED("Annulée", StatusEventCode.delivery("OMS-STATUS-LOGISTICAL-DISCARDED-00000")),
	RETURNED("Retournée", StatusEventCode.delivery("OMS-STATUS-LOGISTICAL-RETURNED-00000")),

	/** MarketPlace */
	WAITING_CAPTURE("Paiement en cours", StatusEventCode.promise("OMS-STATUS-WAITING_CAPTURE-00000")),
	INCIDENT_OPEN("Incident ouvert", StatusEventCode.promise("OMS-STATUS-INCIDENT_CAPTURE-00000")),
	INCIDENT_CLOSE("Incident fermé", StatusEventCode.promise("OMS-STATUS-INCIDENT_CAPTURE-00000"));

	@Getter
	private final String reason;

	@Getter
	private final StatusEventCode eventCode;

	@Override
	public String getCode() {
		return this.name();
	}

}
