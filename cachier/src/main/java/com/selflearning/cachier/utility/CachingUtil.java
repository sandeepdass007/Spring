package com.selflearning.cachier.utility;

import java.time.Instant;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.selflearning.cachier.constant.CachingScheme;

final public class CachingUtil {

	public static String getIdentifier(CachingScheme cachingScheme, String customIdentifier) {
		switch(cachingScheme) {
		case GUID:
			return getGuid();
		case SESSION:
			return getGuid(); // to do
		case CUSTOM:
			return StringUtils.hasText(customIdentifier) ? customIdentifier : getGuid();
		}
		return getGuid();
	}
	
	public static String getGuid() {
		final Long currentTime = Date.from(Instant.now()).getTime();
		return String.format("_%s", currentTime);
	}
}
