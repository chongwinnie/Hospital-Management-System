import java.util.Scanner;

public class Lab {
    private String lab;
    private int cost;

    private static Scanner sc = new Scanner(System.in);

    // Constructors
    public Lab() {}

    public Lab(String lab, int cost) {
        this.lab = lab;
        this.cost = cost;
    }

    // Getters + Setters
    public String getLab() { return lab; }
    public void setLab(String lab) { this.lab = lab; }

    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }

    // Add new lab (validation like Doctor.newDoctor)
    public void newLab() {
        System.out.println("\n----------------------------------------------");
        System.out.println("                   ADD NEW LAB               ");
        System.out.println("----------------------------------------------\n");

        // Lab Name
        do {
            System.out.print("Enter Lab Name: ");
            lab = sc.nextLine().trim();
            if (lab.isEmpty()) {
                System.out.println("*** [!] Lab Name Cannot Be Empty: Please Try Again. ***\n");
            }
        } while (lab.isEmpty());

        // Cost (must be positive int)
        while (true) {
            System.out.print("\nEnter Lab Cost [RM]: ");
            try {
                cost = Integer.parseInt(sc.nextLine().trim());
                if (cost <= 0) {
                    System.out.println("*** [!] Cost Must Be Positive: Please Try Again. ***\n");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("*** [!] Invalid Input: Please Enter a Number. ***\n");
            }
        }

        System.out.println("\n------------------------------------------------------");
        System.out.println("                     LAB REGISTERED");
        System.out.println("------------------------------------------------------\n");
    }

    // Display lab info in tabular form like Doctor.showDoctorInfo
    public void labList() {
        System.out.printf("%-26s %-10d%n", lab, cost);
    }

    @Override
    public String toString() {
        return showLabInfoString();
    }

    public String showLabInfoString() {
        return String.format("%s   %d", lab, cost);
    }

    // Find index by lab name in array
    public static int indexOfByName(Lab[] arr, int count, String targetLab) {
        if (arr == null || targetLab == null) return -1;
        for (int i = 0; i < count; i++) {
            if (targetLab.equalsIgnoreCase(arr[i].getLab())) return i;
        }
        return -1;
    }

    // Search lab by name
    public static int searchByName(Lab[] arr, int count, String name) {
        System.out.println("\n----------------------------------------------");
        System.out.println("                   SEARCH BY NAME            ");
        System.out.println("----------------------------------------------\n");

        int hits = 0;
        System.out.printf("%-20s %-10s%n", "Lab Name", "Cost");
        for (int j = 0; j < 40; j++) System.out.print("-");
        System.out.println();
        for (int i = 0; i < count; i++) {
            if (arr[i].getLab().equalsIgnoreCase(name)) {
                arr[i].labList();
                hits++;
            }
        }

        if (hits == 0) System.out.println("*** [!] NO MATCH. ***");
        System.out.println("\nMatch Count: " + hits);
        return hits;
    }

    // Update lab info by name
    public static boolean updateByName(Lab[] arr, int count, String name) {
        int idx = indexOfByName(arr, count, name);
        if (idx < 0) {
            System.out.println("\n*** [!] Lab Not Found. ***");
            return false;
        }

        Lab l = arr[idx];
        System.out.println("\n----------------------------------------------");
        System.out.println("                  UPDATE LAB                 ");
        System.out.println("----------------------------------------------\n");
        System.out.println("Leave blank to keep current value.\n");

        System.out.print("Lab Name (" + l.getLab() + "): ");
        String s = sc.nextLine().trim();
        if (!s.isEmpty()) l.setLab(s);

        System.out.print("\nCost (" + l.getCost() + "): ");
        s = sc.nextLine().trim();
        if (!s.isEmpty()) {
            try {
                int c = Integer.parseInt(s);
                if (c > 0) l.setCost(c);
                else System.out.println("\n*** [!] Cost Must Be Positive: Previous Value Kept. ***");
            } catch (NumberFormatException e) {
                System.out.println("\n*** [!] Invalid Number: Previous Value Kept. ***");
            }
        }

        System.out.println("\n------------------------------------------------------");
        System.out.println("                    DETAILS UPDATED");
        System.out.println("------------------------------------------------------\n");
        return true;
    }

    // Delete lab by name
    public static int deleteByName(Lab[] arr, int count, String name) {
        int idx = indexOfByName(arr, count, name);
        if (idx < 0) {
            System.out.println("\n*** [!] Lab Not Found. ***");
            return count;
        }

        for (int i = idx; i < count - 1; i++) arr[i] = arr[i + 1];
        arr[count - 1] = null;

        System.out.println("\n------------------------------------------------------");
        System.out.println("                    LAB REMOVED");
        System.out.println("------------------------------------------------------\n");
        return count - 1;
    }
}