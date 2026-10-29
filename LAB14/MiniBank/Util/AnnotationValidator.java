package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import model.annotation.MaxLength;
import model.annotation.Positive;

public class AnnotationValidator {
    private AnnotationValidator() {
    }

    public static String[] validate(Object object) {
        if (object == null) {
            return new String[] {"Object cannot be null."};
        }

        ArrayList<String> errors = new ArrayList<>();

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                Object value = field.get(object);

                if (field.isAnnotationPresent(Positive.class)
                        && value instanceof Number number
                        && number.doubleValue() <= 0) {
                    errors.add(field.getName() + " must be > 0");
                }

                if (field.isAnnotationPresent(MaxLength.class)
                        && value instanceof String text) {
                    int max = field.getAnnotation(MaxLength.class).value();
                    if (text.length() > max) {
                        errors.add(field.getName()
                                + " must be at most " + max + " characters");
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Could not validate " + field.getName());
            }
        }

        return errors.toArray(new String[0]);
    }
}
