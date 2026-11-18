import java.util.Scanner;

public class Medicine {
    private String name;
    private String manufacturer;
    private String expiryDate;
    private int cost;
    private int count;  // Stock count

    private static Scanner sc = new Scanner(System.in);

    // Constructors
    public Medicine() {}

    public Medicine(String name, String manufacturer, String expiryDate, int cost, int count) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.expiryDate = expiryDate;
        this.cost = cost;
        this.count = count;
    }

    // Getters + Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    // Add new medicine
    public void newMedicine() {
        System.out.println("\n------------------------------");
        System.out.println("        ADD NEW MEDICINE      ");
        System.out.println("------------------------------\n");

        // Medicine name
        do {
            System.out.print("\n Enter Medicine Name: ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) System.out.println("*** [!] Name Cannot Be Empty. Please Try Again. ***\n");
        } while (name.isEmpty());

        // Manufacturer
        do {
            System.out.print("\n Enter Manufacturer: ");
            manufacturer = sc.nextLine().trim();
            if (manufacturer.isEmpty()) System.out.println("*** [!] Manufacturer Cannot Be Empty. Please Try Again. ***\n");
        } while (manufacturer.isEmpty());

        // Expiry date
        while (true) {
            System.out.print("\n Enter Expiry Date (YYYY-MM): ");
            expiryDate = sc.nextLine().trim();
            if (expiryDate.matches("\\d{4}-\\d{2}")) break;
            else System.out.println("*** [!] Invalid format. Please enter date in YYYY-MM format. ***\n");
        }

        // Cost
        while (true) {
            System.out.print("\n Enter Cost [RM]: ");
            try {
                cost = Integer.parseInt(sc.nextLine().trim());
                if (cost <= 0) System.out.println("*** [!] Cost Must Be Positive. Please Try Again. ***\n");
                else break;
            } catch (NumberFormatException e) {
                System.out.println("*** [!] Invalid Input. Please Enter a Number. ***\n");
            }
        }

        // Stock count
        while (true) {
            System.out.print("\n Enter Available Stock: ");
            try {
                count = Integer.parseInt(sc.nextLine().trim());
                if (count < 0) System.out.println("*** [!] Stock Cannot Be Negative. Please Try Again. ***\n");
                else break;
            } catch (NumberFormatException e) {
                System.out.println("*** [!] Invalid Input. Please Enter a Number. ***\n");
            }
        }

        System.out.println("\n------------------------------------------------------");
        System.out.println("                   MEDICINE REGISTERED");
        System.out.println("------------------------------------------------------\n");
    }

    // Display medicine info in tabular style
    public void findMedicine() {
        System.out.printf("%-20s %-18s %-20s %-14s %-5d%n",
                name, manufacturer, expiryDate, cost, count);
    }

    @Override
    public String toString() {
        return showMedicineInfoString();
    }

    public String showMedicineInfoString() {
        return String.format("%s   %s   %s   %d   %d",
                name, manufacturer, expiryDate, cost, count);
    }

    /* ----------- HELPER METHODS ----------- */
    private static int indexOfByName(Medicine[] arr, int count, String name) {
        if (arr == null || name == null) return -1;
        for (int i = 0; i < count; i++) {
            if (name.equalsIgnoreCase(arr[i].getName())) return i;
        }
        return -1;
    }

    private static boolean confirm(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt + " [ Y / N ]: ");
            String s = sc.nextLine().trim().toLowerCase();
            if (s.equals("y") || s.equals("yes")) return true;
            if (s.equals("n") || s.equals("no")) return false;
            System.out.println("*** [!] Please answer [ Y / N ] ***");
        }
    }

    // Search / Update / Delete Methods
    public static int searchByNameContains(Medicine[] arr, int count, String q) {
        System.out.println("\n------------------------------------------");
        System.out.println("              SEARCH BY NAME");
        System.out.println("------------------------------------------\n");

        if (q == null) q = "";
        String qq = q.toLowerCase();
        int hits = 0;

        System.out.printf("%-20s %-18s %-18s %-15s %-5s%n",
                "Name", "Supplier", "Expiry", "Price [RM]", "Stock");
        for (int i = 0; i < 84; i++) System.out.print("-");
        System.out.println();
        
        for (int i = 0; i < count; i++) {
            if (arr[i].getName().toLowerCase().contains(qq)) {
                arr[i].findMedicine();
                hits++;
                for (int j = 0; j < 84; j++) System.out.print("-");
                System.out.println();
            }
        }
        System.out.println("\nMatch Count: " + hits);
        return hits;
    }

    public static int searchByPriceRange(Medicine[] arr, int count, int min, int max) {
        System.out.println("\n------------------------------------------");
        System.out.println("           SEARCH BY PRICE RANGE");
        System.out.println("------------------------------------------\n");

        if (max < min) { int t = min; min = max; max = t; }

        int hits = 0;
        
        System.out.printf("%-20s %-18s %-18s %-15s %-5s%n",
                "Name", "Supplier", "Expiry", "Price [RM]", "Stock");
        for (int i = 0; i < 84; i++) System.out.print("-");
        System.out.println();
        
        for (int i = 0; i < count; i++) {
            int price = arr[i].getCost();
            if (price >= min && price <= max) {
                arr[i].findMedicine();
                for (int j = 0; j < 84; j++) System.out.print("-");
                System.out.println();
                hits++;
            }
        }
        System.out.println("\nMatch Count: " + hits);
        return hits;
    }

    public static int searchByStockThreshold(Medicine[] arr, int count, int threshold) {
        System.out.println("\n------------------------------------------");
        System.out.println("          SEARCH BY STOCK THRESHOLD");
        System.out.println("------------------------------------------\n");

        int hits = 0;
        
        System.out.printf("%-20s %-18s %-18s %-15s %-5s%n",
                "Name", "Supplier", "Expiry", "Price [RM]", "Stock");
        for (int i = 0; i < 84; i++) System.out.print("-");
        System.out.println();
        
        for (int i = 0; i < count; i++) {
            if (arr[i].getCount() <= threshold) {
                arr[i].findMedicine();
                for (int j = 0; j < 84; j++) System.out.print("-");
                System.out.println();
                hits++;
            }
        }
        System.out.println("\nMatch Count: " + hits);
        return hits;
    }

    public static void sortByName(Medicine[] arr, int count) {
        java.util.Arrays.sort(arr, 0, count,
                java.util.Comparator.comparing(Medicine::getName, String.CASE_INSENSITIVE_ORDER));
        System.out.println("\n------------------------------------------------------");
        System.out.println("               SORTED BY NAME (A -> Z)               ");
        System.out.println("------------------------------------------------------\n");
    }

    public static void sortByPrice(Medicine[] arr, int count) {
        java.util.Arrays.sort(arr, 0, count,
                java.util.Comparator.comparingInt(Medicine::getCost));
        System.out.println("\n------------------------------------------------------");
        System.out.println("                SORTED BY PRICE (ASC)           ");
        System.out.println("------------------------------------------------------\n");
    }

    public static void sortByStock(Medicine[] arr, int count) {
        java.util.Arrays.sort(arr, 0, count,
                java.util.Comparator.comparingInt(Medicine::getCount));
        System.out.println("\n------------------------------------------------------");
        System.out.println("                SORTED BY STOCK (ASC)          ");
        System.out.println("------------------------------------------------------\n");
    }

    public static void update(Scanner sc, Medicine[] arr, int count, String role, String name) {
        if (!"admin".equalsIgnoreCase(role)) { 
            System.out.println("*** [!] Only ADMIN Can Update. ***"); 
            return; 
        }
        int idx = indexOfByName(arr, count, name);
        if (idx < 0) { 
            System.out.println("*** [!] Medicine Not Found. ***"); 
            return; 
        }

        Medicine m = arr[idx];
        System.out.println("\n------------------------------------------");
        System.out.println("              UPDATE MEDICINE");
        System.out.println("------------------------------------------\n");
        System.out.println("Leave blank to keep current value.");

        System.out.print("\n Manufacturer (" + m.getManufacturer() + "): ");
        String s = sc.nextLine().trim();
        if (!s.isEmpty()) m.setManufacturer(s);

        System.out.print("\n Expiry (YYYY-MM) (" + m.getExpiryDate() + "): ");
        s = sc.nextLine().trim();
        if (!s.isEmpty() && s.matches("\\d{4}-\\d{2}")) m.setExpiryDate(s);

        System.out.print("\n Cost in RM (" + m.getCost() + "): ");
        s = sc.nextLine().trim();
        if (!s.isEmpty()) {
            try { int c = Integer.parseInt(s); if (c > 0) m.setCost(c); } catch (Exception e) {}
        }

        System.out.print("\n Stock (" + m.getCount() + "): ");
        s = sc.nextLine().trim();
        if (!s.isEmpty()) {
            try { int c = Integer.parseInt(s); if (c >= 0) m.setCount(c); } catch (Exception e) {}
        }

        System.out.println("\n------------------------------------------------------");
        System.out.println("                 DETAILS UPDATED");
        System.out.println("------------------------------------------------------\n");
    }

    public static int delete(Scanner sc, Medicine[] arr, int count, String role, String name) {
        if (!"admin".equalsIgnoreCase(role)) { 
            System.out.println("*** [!] Only ADMIN Can Delete. ***"); 
            return count; 
        }
        int idx = indexOfByName(arr, count, name);
        if (idx < 0) {
            System.out.println("*** [!] Medicine Not Found. ***");
            return count;
        }
        if (!confirm(sc, "\n [!] Delete " + name + "?")) return count;

        for (int i = idx; i < count - 1; i++) arr[i] = arr[i + 1];
        arr[count - 1] = null;

        System.out.println("\n------------------------------------------------------");
        System.out.println("                 MEDICINE REMOVED");
        System.out.println("------------------------------------------------------\n");
        return count - 1;
    }

    public static int countLowStock(Medicine[] arr, int count, int threshold) {
        int c = 0; 
        for (int i = 0; i < count; i++) if (arr[i].getCount() < threshold) c++;
        return c;
    }

    public static int countExpiringWithinMonths(Medicine[] arr, int count, int months) {
        int c = 0; 
        java.time.YearMonth now = java.time.YearMonth.now();
        java.time.YearMonth limit = now.plusMonths(months);
        for (int i = 0; i < count; i++) {
            try {
                java.time.YearMonth exp = java.time.YearMonth.parse(arr[i].getExpiryDate());
                if (!exp.isAfter(limit)) c++;
            } catch (Exception e) {}
        }
        return c;
    }
}
