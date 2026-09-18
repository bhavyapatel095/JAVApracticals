abstract class Shape{
    public abstract float area();
}
class Circle extends Shape{
        public float radius;
        public Circle(int r){
            radius=r;
        }
        public float area(){
            return 3.14f*radius*radius;
        }
}
class Rectangle extends Shape{
    public float lenght;
    public float breadth;

    public Rectangle(float l, float b){
            lenght = l;
            breadth = b;
        }
        public float area(){
            return lenght * breadth;
        }
    
}
class Triangle extends Shape{
    public float base;
    public float height;

    public Triangle(float b, float h){
            base= b;
            height = h;
        }
        public float area(){
            return 0.5f*base*height;
        }
}
public class main {
    public static void main(String[] args) {
        Shape[] shapes = {new Circle(5),new Rectangle(4, 6),new Triangle(3, 8),new Circle(3)};

        double total_area = 0;
        double largest_area = 0;

        for (Shape shape : shapes) {

            double current_area = shape.area();

            System.out.printf("Area: %.2f%n", current_area);
            total_area += current_area;

            if (current_area > largest_area) {
                largest_area = current_area;
            }
        }

        System.out.printf("%nTotal Area: %.2f%n", total_area);
        System.out.printf("Largest Area: %.2f%n", largest_area);
    
    }
}


