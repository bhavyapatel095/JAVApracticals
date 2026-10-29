import java.io.*;

class Student implements Serializable 
{
    int id;
    String name;
    transient String password;

    Student(int id, String name, String password) 
    {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}

public class SerializationDemo 
{
    public static void main(String[] args) 
    {
        try 
        {
            Student[] students = 
            {
                new Student(1, "Bhavya", "abc123"),
                new Student(2, "Rahul", "xyz456")
            };

            ObjectOutputStream out =
                new ObjectOutputStream(new FileOutputStream("student.dat"));

            out.writeObject(students);
            out.close();

            ObjectInputStream in =
                new ObjectInputStream(new FileInputStream("student.dat"));

            Student[] loadedStudents = (Student[]) in.readObject();
            in.close();

            for (Student s : loadedStudents) 
            {
                System.out.println("ID: " + s.id);
                System.out.println("Name: " + s.name);
                System.out.println("Password: " + s.password);
                System.out.println();
            }

        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }
}
