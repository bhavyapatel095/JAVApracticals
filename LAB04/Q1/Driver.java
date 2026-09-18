public class Driver {
public static void main(String[] args) {
String[] passwords = { "abc",
"abcdefghi", "Abcdefgh", "Abcd1234", "Abcd1234!"
};
for (String pw : passwords) {
System.out.println("Password: " + pw);
System.out.println("Length >= 8: "
+ PasswordChecker.hasMinimumLength(pw));
System.out.println("Uppercase letter: "
+ PasswordChecker.hasUppercase(pw));
System.out.println("Digit: "
+ PasswordChecker.hasDigit(pw));
System.out.println("Special character: "
+ PasswordChecker.hasSpecialCharacter(pw));
System.out.println("Strength: "
+ PasswordChecker.strength(pw));
System.out.println("	");
}
}
}
