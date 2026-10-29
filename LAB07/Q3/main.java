import java.lang.annotation.*;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column {
    String name();
}

class Student {

    @Column(name = "id")
    String id;

    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;
}

public class main {

    public static void main(String[] args) throws Exception {

        String[] headers = {"id", "name", "email"};
        String[] data = {"101", "Bhavya", "bhavya@gmail.com"};

        Student student = new Student();

        Field[] fields = student.getClass().getDeclaredFields();

        for (Field field : fields) {

            if (field.isAnnotationPresent(Column.class)) {

                Column column = field.getAnnotation(Column.class);

                for (int i = 0; i < headers.length; i++) {

                    if (headers[i].equals(column.name())) {

                        field.setAccessible(true);
                        field.set(student, data[i]);

                        break;
                    }
                }
            }
        }

        System.out.println("ID: " + student.id);
        System.out.println("Name: " + student.name);
        System.out.println("Email: " + student.email);
    }
}

