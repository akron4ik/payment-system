package ru.philit.ufs.model.entity.oper;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import ru.philit.ufs.model.entity.common.ExternalEntity;

import java.util.HashMap;
import java.util.Map;


public enum CashOrderTypeCode {

    KO_1("KO1", "КО1"),
    KO_2("KO2", "КО2");

    private static final ImmutableMap<String, CashOrderTypeCode> CODES_MAP;

    static {
        Map<String, CashOrderTypeCode> mapCodes = new HashMap<>();
        for (CashOrderTypeCode item : values()) {
            mapCodes.put(item.code(), item);
        }
        CODES_MAP = ImmutableMap.copyOf(mapCodes);
    }

    public static CashOrderTypeCode getByCode(String code) {
        return CODES_MAP.get(code);
    }

    private final String code;
    private final String value;

    CashOrderTypeCode(String code, String value) {
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
