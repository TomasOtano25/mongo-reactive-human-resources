package com.one.mongoreactivehumanresources.documents.enums;

public enum  Risk {
    ALTO,
    MEDIO,
    BAJO;

    public String riskName() {
        return "RISK_" + this.toString();
    }
}
