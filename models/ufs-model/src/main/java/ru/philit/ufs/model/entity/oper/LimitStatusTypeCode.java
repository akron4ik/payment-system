package ru.philit.ufs.model.entity.oper;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public enum LimitStatusTypeCode {
    LIMIT_ERROR("Limit error", "Лимит превышен"),
    LIMIT_PASSED("Limit passed", "Лимит не превышен");

    private static final ImmutableMap<String, LimitStatusTypeCode> CODES_MAP;

    static {
        Map<String, LimitStatusTypeCode> mapCodes = new HashMap<>();
        for (LimitStatusTypeCode item : values()) {
            mapCodes.put(item.code(), item);
        }
        CODES_MAP = ImmutableMap.copyOf(mapCodes);
    }

    public static LimitStatusTypeCode getByCode(String code) {
        return CODES_MAP.get(code);
    }

    private final String code;
    private final String value;

    LimitStatusTypeCode(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("code", code()).add("value", value()).toString();
    }

    public String code() {
        return code;
    }

    public String value() {
        return value;
    }
}
