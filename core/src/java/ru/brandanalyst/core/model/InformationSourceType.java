package ru.brandanalyst.core.model;

/**
 * @author daddy-bear
 *         Date: 03.03.12
 */
public enum InformationSourceType{
    ALL(2, "все источники"),
    OFFICIAL(0, "публичные источники"),
    SOCIAL(1, "оффициальные источники");

    private final int code;
    private final String name;

    private InformationSourceType(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode(){
        return code;
    }

    public String getName(){
        return name;
    }
}
