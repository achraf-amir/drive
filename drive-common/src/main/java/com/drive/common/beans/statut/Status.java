package com.drive.common.beans.statut;


import com.drive.common.beans.type.StatusTypeCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

import static com.drive.common.beans.type.StatusTypeCode.LOGISTICAL;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Status implements Serializable {


		private static final long serialVersionUID = 8939142637501799414L;

		private StatusTypeCode statusTypeCode;

		private String statusCode;

		private String status;

		private LocalDateTime dateTime;

		private String updateMotiveCode;

		private String updateMotive;

		private String updatedBy;

		/**
		 * Helper for creating Status
		 *
		 * @param statusCode             the status
		 * @param statusDateTime         the update status date
		 * @param statusUpdateMotiveCode the reason for status update
		 */
		public static Status fromStatusCode(
						StatusTypeCode statusTypeCode,
						StatusCode statusCode, LocalDateTime statusDateTime,
						StatusCode statusUpdateMotiveCode, String updatedBy
		) {
			final StatusBuilder builder = Status.builder().status(statusCode.getReason()).statusCode(statusCode.getCode())
			                                    .dateTime(statusDateTime);

			if (statusUpdateMotiveCode != null) {
				builder.updateMotive(statusUpdateMotiveCode.getReason());
				builder.updateMotiveCode(statusUpdateMotiveCode.getCode());
			}
			if (updatedBy != null) {
				builder.updatedBy(updatedBy);
			}
			builder.statusTypeCode(statusTypeCode);
			return builder.build();
		}

		public static Status fromStatusCode(StatusTypeCode statusTypeCode, StatusCode statusCode) {
			return fromStatusCode(statusTypeCode, statusCode, LocalDateTime.now(), null, null);
		}

		public static Status fromStatusCode(StatusCode statusCode) {
			return fromStatusCode(LOGISTICAL, statusCode, LocalDateTime.now(), null, null);
		}

		public static Status fromStatusCode(
						StatusTypeCode statusTypeCode, StatusCode statusCode, LocalDateTime
						statusDateTime
		) {
			return fromStatusCode(statusTypeCode, statusCode, statusDateTime, null, null);
		}

		public static Status fromStatusCode(StatusCode statusCode, LocalDateTime statusDateTime) {
			return fromStatusCode(LOGISTICAL, statusCode, statusDateTime, null, null);
		}

		public static Status fromStatusCode(
						StatusTypeCode statusTypeCode,
						StatusCode statusCode, StatusCode statusUpdateMotiveCode
		) {
			return fromStatusCode(statusTypeCode, statusCode, LocalDateTime.now(), statusUpdateMotiveCode, null);
		}

		public static Status fromStatusCode(StatusCode statusCode, StatusCode statusUpdateMotiveCode) {
			return fromStatusCode(LOGISTICAL, statusCode, LocalDateTime.now(), statusUpdateMotiveCode, null);
		}

		public static Status fromStatusCode(
						StatusTypeCode statusTypeCode,
						StatusCode statusCode, StatusCode statusUpdateMotiveCode,
						String updatedBy
		) {
			return fromStatusCode(statusTypeCode, statusCode, LocalDateTime.now(), statusUpdateMotiveCode, updatedBy);
		}

		public static Status fromStatusCode(StatusCode statusCode, StatusCode statusUpdateMotiveCode, String updatedBy) {
			return fromStatusCode(LOGISTICAL, statusCode, LocalDateTime.now(), statusUpdateMotiveCode, updatedBy);
		}

		// Getter to retrieve updateMotiveCode from Status
		@JsonIgnore
		public StatusCode getUpdateMotiveStatusCode() {
			return new StatusCode() {
				private static final long serialVersionUID = 2680233189685002356L;

				@Override
				public String getCode() {
					return updateMotiveCode;
				}

				@Override
				public String getReason() {
					return updateMotive;
				}
			};
		}

}
