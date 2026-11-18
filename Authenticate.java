import java.util.Scanner;

public class Authenticate {
	
    public static String login(Scanner sc) {
        System.out.println("----------------------------");
        System.out.println("|          LOGIN           |");
        System.out.println("----------------------------");

        String u, p;

        // User Validation
        do {
            System.out.print("\nUsername: ");
            u = sc.nextLine().trim();
            if (u.isEmpty()) {
                System.out.println("\n*** [!] USERNAME CANNOT BE EMPTY ***");
            }
        } while (u.isEmpty());

        // Password Validation
        do {
            System.out.print("\nPassword: ");
            p = sc.nextLine().trim();
            if (p.isEmpty()) {
                System.out.println("\n*** [!] PASSWORD CANNOT BE EMPTY ***");
            }
        } while (p.isEmpty());

        // Credential Check
        if ("admin".equalsIgnoreCase(u) && "admin123".equals(p)) {
            System.out.println("\n------------------------------------------------------");
            System.out.println("                You're Logged In [ADMIN]");
            System.out.println("------------------------------------------------------");
            return "admin";
        } else if ("staff".equalsIgnoreCase(u) && "staff123".equals(p)) {
        	System.out.println("\n------------------------------------------------------");
            System.out.println("                You're Logged In [STAFF]");
            System.out.println("------------------------------------------------------");
            return "staff";
        } else {
            System.out.println("\n*** [!] UNKNOWN CREDENTIALS: DEFAULTING TO STAFF ***");
            return "staff";
        }
    }
}
