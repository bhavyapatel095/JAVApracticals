public class Driver{
    public static void main(String[] args){
        Fraction fraction1 = new Fraction(1, 2);
        Fraction fraction2 = new Fraction(2, 4);
        Fraction fraction3 = new Fraction(3, 6);
        System.out.println("Fraction 1 : " + fraction1);
        System.out.println("Fraction 2 : " + fraction2);
        System.out.println("Fraction 3 : " + fraction3);
        System.out.println();
        System.out.println("fraction1 equals fraction2 : " + fraction1.equals(fraction2));
        System.out.println("fraction2 equals fraction3 : " + fraction2.equals(fraction3));
        System.out.println("fraction1 equals fraction3 : " + fraction1.equals(fraction3));
    }
}