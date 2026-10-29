package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import model.annotation.MaxLength;
import model.annotation.Positive;

public class AnnotationValidator {
    private AnnotationValidator() {
    }

    public static String[] validate(Object obj) {
        if (obj == null) {
            return new String[] {"Object cannot be null."};
        }

        List<String> errors = new ArrayList<>();

        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                Object value = field.get(obj);

                if (field.isAnnotationPresent(Positive.class)
                        && value instanceof Number number
                        && number.doubleValue() <= 0) {
                    Positive rule = field.getAnnotation(Positive.class);
                    errors.add(field.getName() + " " + rule.message());
                }

                if (field.isAnnotationPresent(MaxLength.class)
                        && value instanceof String text) {
                    MaxLength rule = field.getAnnotation(MaxLength.class);

                    if (text.length() > rule.value()) {
                        errors.add(field.getName() + " must be at most "
                                + rule.value() + " characters");
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Could not validate field: " + field.getName());
            }
        }

        return errors.toArray(new String[0]);
    }
}