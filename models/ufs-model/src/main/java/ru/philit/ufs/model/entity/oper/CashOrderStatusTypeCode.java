package ru.philit.ufs.model.entity.oper;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import ru.philit.ufs.model.entity.common.ExternalEntity;

import java.util.HashMap;
import java.util.Map;

public enum CashOrderStatusTypeCode  {
    CREATED("Created", "Создан"),

    COMMITTED("Committed", "Поручен");

    private static final ImmutableMap<String, CashOrderStatusTypeCode> CODES_MAP;

    static {
        Map<String, CashOrderStatusTypeCode> mapCodes = new HashMap<>();
        for (CashOrderStatusTypeCode item : values()) {
            mapCodes.put(item.code(), item);
        }
        CODES_MAP = ImmutableMap.copyOf(mapCodes);
    }

    public static CashOrderStatusTypeCode getByCode(String code) {
        return CODES_MAP.get(code);
    }

    private final String code;
    private final String value;

    CashOrderStatusTypeCode(String code, String value) {
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
