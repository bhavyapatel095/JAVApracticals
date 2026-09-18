import java.util.*;

abstract class Employee {

    public String employee_name;
    public int employee_id;

    public Employee(String name, int id) {
        employee_name = name;
        employee_id = id;
    }

    public abstract float monthlySalary();
}

class FullTime extends Employee {

    public float salary;

    public FullTime(String name, int id) {
        super(name, id);
        salary = 100000;
    }

    public float monthlySalary() {
        return salary;
    }
}

class PartTime extends Employee {

    public int hour;
    public float rate;

    public PartTime(String name, int id, int h, float r) {
        super(name, id);
        hour = h;
        rate = r;
    }

    public float monthlySalary() {
        return rate * hour;
    }
}

class Intern extends Employee {

    public float salary;

    public Intern(String name, int id) {
        super(name, id);
        salary = 10000;
    }

    public float monthlySalary() {
        return salary;
    }
}

public class Main {

    public static void main(String[] args) {

        Employee[] employees = {
            new FullTime("Ansh", 101),
            new PartTime("Raj", 102, 80, 300),
            new Intern("Jay", 103),
            new FullTime("Karan", 104),
            new Intern("Dev", 105)
        };

        float total_salary = 0;

        for (Employee employee : employees) {
            float salary = employee.monthlySalary();

            System.out.println("Employee ID: " + employee.employee_id +", Name:" + employee.employee_name +", Salary: " + salary);

            if (employee instanceof Intern) {
                System.out.println("Employee is an Intern.");
            }

            total_salary = total_salary +salary;
        }

        System.out.println("Total Monthly Payroll:" + total_salary);
    }
}

