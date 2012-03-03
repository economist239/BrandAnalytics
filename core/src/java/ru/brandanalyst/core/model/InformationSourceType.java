package ru.brandanalyst.core.model;

/**
 * @author daddy-bear
 *         Date: 03.03.12
 */
public enum InformationSourceType {
    OFFICIAL(1),
    SOCIAL(2);

    private final int code;

    private InformationSourceType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
