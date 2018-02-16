package de.randomerror.cocktails.backend.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity, Object value) {
        this(entity, value, "id");
    }

    public NotFoundException(String entity, Object value, String field) {
        super(entity + " with " + field + " " + value + " could not be found");
    }
}
