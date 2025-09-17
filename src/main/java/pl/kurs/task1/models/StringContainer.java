package pl.kurs.task1.models;

import pl.kurs.task1.exceptions.InvalidStringContainerPatternException;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class StringContainer {

    private final Pattern pattern;
    private LocalDateTime dateTime;

    public StringContainer(String regex) {
        try {
            this.pattern = Pattern.compile(regex);
        } catch (Exception e) {
            throw new InvalidStringContainerPatternException(regex);
        }
    }
}
