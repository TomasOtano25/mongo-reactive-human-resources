package com.one.mongoreactivehumanresources.documents.enums;

public enum Level {
    GRADO,
    POST_GRADO,
    MAESTRIA,
    DOCTORADO,
    TECNICO,
    GESTION;

    public String levelName() {
        return "LEVEL_" + this.toString();
    }
}
