package pl.kurs.task2.exceptions;

public class ConditionNotMetException extends RuntimeException {

    public ConditionNotMetException(String message) {
        super(message);
    }
}
