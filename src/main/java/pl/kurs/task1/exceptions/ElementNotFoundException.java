package pl.kurs.task1.exceptions;

public class ElementNotFoundException extends RuntimeException {

    public ElementNotFoundException(String value) {
        super("Element not found: " + value);
    }
}
