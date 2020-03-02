package com.drive.order.utils;


import com.drive.common.beans.utils.AttributeValuePair;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AttributeValueUtils {


	/**
	 * Returns the value of the given pairs associated to the given typeCode.
	 *
	 * @param pairs    the list to find in
	 * @param typeCode the typeCode to find
	 *
	 * @return the found value or null
	 */
	static String findValueFromTypeCode(final List<AttributeValuePair> pairs, final String typeCode) {
		return findOptionalValueFromTypeCode(pairs, typeCode).orElse(null);
	}


	/**
	 * Returns an {@link Optional<String>} of the value of the given pairs associated to the given typeCode.
	 *
	 * @param pairs    the list to find in
	 * @param typeCode the typeCode to find
	 *
	 * @return an {@link Optional<String>} if the value is found, {@link Optional#empty()} otherwise
	 */
	static Optional<String> findOptionalValueFromTypeCode(final List<AttributeValuePair> pairs, final String typeCode) {
		return findOptionalPairByTypeCode(pairs, typeCode).map(AttributeValuePair::getValue);
	}

	/**
	 * Returns an {@link Optional<AttributeValuePair>} of the pair by given typeCode.
	 *
	 * @param pairs    the list to find in
	 * @param typeCode the typeCode to find
	 *
	 * @return an {@link Optional<String>} if the value is found, {@link Optional#empty()} otherwise
	 */
	static Optional<AttributeValuePair> findOptionalPairByTypeCode(
					final List<AttributeValuePair> pairs,
					final String typeCode
	) {
		if (CollectionUtils.isEmpty(pairs)) {
			return Optional.empty();
		}

		return pairs
						.stream()
						.filter(pair -> StringUtils.equals(pair.getTypeCode(), typeCode))
						.findFirst();
	}



}
