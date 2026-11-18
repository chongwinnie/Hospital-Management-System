import java.util.Scanner;

public class InputHandling {
    public static String readNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.print("\n[!] Empty Value - Please Try Again: ");
        }
    }
    public static double readDouble(Scanner sc, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                double v = Double.parseDouble(s);
                if (v < min || v > max) {
                    System.out.println("[!] Value must be between " + min + " and " + max + ".");
                } else return v;
            } catch (NumberFormatException e) {
            	System.out.print("\n[!] Invalid Input - Please Try Again: ");
            }
        }
    }
    public static boolean confirm(Scanner sc, String prompt) {
        System.out.print(prompt + " [Y / N]: ");
        String a = sc.nextLine().trim().toLowerCase();
        return a.startsWith("y");
    }
}
