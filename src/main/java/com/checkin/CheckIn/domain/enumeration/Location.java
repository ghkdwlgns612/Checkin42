package com.checkin.CheckIn.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Location {
    SEOCHO("SEOCHO","서초"),
    GAEPO("GAEPO","개포");

    private final String key;
    private final String value;
}
