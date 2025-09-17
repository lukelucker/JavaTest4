package pl.kurs.task1.exceptions;

public class InvalidStringContainerPatternException extends RuntimeException {

    public InvalidStringContainerPatternException(String badPattern) {
        super("Invalid pattern: " + badPattern);
    }
}
