package com.cxtx.model;

/**
 * Created by jinchuyang on 16/10/24.
 */
public enum LeverStatus implements KeyValue<Integer, String>{
    Excellent(0, "极好"),// 0-3个月
    Good(1, "好"),//3-6个月
    General(2, "一般");

    private final int key;
    private final String value;

    LeverStatus(int key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static String getValue(int key) {
        for (LeverStatus status : LeverStatus.values()) {
            if (status.key == key) {
                return status.getValue();
            }
        }
        return "";
    }
}
