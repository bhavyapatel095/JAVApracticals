import java.util.Objects;
public class Fraction{
    private int num;
    private int den;
    public Fraction(int num,int den){
        int gcd = findGCD(num,den);
        this.num=num/gcd;
        this.den=den/gcd;
    }
    private int findGCD(int firstNumber,int secondNumber){
        while(secondNumber!=0) {
            int remainder=firstNumber%secondNumber;
            firstNumber=secondNumber;
            secondNumber=remainder;
        }
        return firstNumber;
    }
    @Override
    public String toString(){
        return num + "/" + den;
    }
    @Override
    public boolean equals(Object object){
        if (this==object){
            return true;
        }
        if (object==null || getClass()!=object.getClass()){
            return false;
        }
        Fraction fraction=(Fraction) object;
        return num == fraction.num && den == fraction.den;
    }
    @Override
    public int hashCode(){
        return Objects.hash(num, den);
    }
}