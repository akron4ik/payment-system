package ru.philit.ufs.model.entity.oper;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public enum LimitStatusType {
    LIMIT_ERROR("Limit error", "Лимит превышен"),
    LIMIT_PASSED("Limit passed", "Лимит не превышен");

    private static final ImmutableMap<String, LimitStatusType> CODES_MAP;

    static {
        Map<String, LimitStatusType> mapCodes = new HashMap<>();
        for (LimitStatusType item : values()) {
            mapCodes.put(item.code(), item);
        }
        CODES_MAP = ImmutableMap.copyOf(mapCodes);
    }

    public static LimitStatusType getByCode(String code) {
        return CODES_MAP.get(code);
    }

    private final String code;
    private final String value;

    LimitStatusType(String code, String value) {
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
