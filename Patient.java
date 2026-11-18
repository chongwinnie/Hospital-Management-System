import java.util.Scanner;

public class Patient {
    private String id;
    private String name;
    private String disease;
    private String sex;
    private String admitStatus;
    private int age;

    private static Scanner sc = new Scanner(System.in);

    // Constructors
    public Patient() {}
    public Patient(String id, String name, String disease, String sex, String admitStatus, int age) {
        this.id = id;
        this.name = name;
        this.disease = disease;
        this.sex = sex;
        this.admitStatus = admitStatus;
        this.age = age;
    }

    // Getters + Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDisease() { return disease; }
    public void setDisease(String disease) { this.disease = disease; }
    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }
    public String getAdmitStatus() { return admitStatus; }
    public void setAdmitStatus(String admitStatus) { this.admitStatus = admitStatus; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    // Add new patient
    public void newPatient() {
        System.out.println("\n------------------------------");
        System.out.println("        ADD NEW PATIENT       ");
        System.out.println("------------------------------\n");

        // Patient ID
        do {
            System.out.print("Enter Patient ID: ");
            id = sc.nextLine().trim();
            if (id.isEmpty()) System.out.println("*** [!] ID Cannot Be Empty. Please Try Again. ***\n");
        } while (id.isEmpty());

        // Name
        do {
            System.out.print("Enter Patient Name: ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) System.out.println("*** [!] Name Cannot Be Empty. Please Try Again. ***\n");
        } while (name.isEmpty());

        // Disease
        do {
            System.out.print("Enter Disease: ");
            disease = sc.nextLine().trim();
            if (disease.isEmpty()) System.out.println("*** [!] Disease Cannot Be Empty. Please Try Again. ***\n");
        } while (disease.isEmpty());

        // Sex
        while (true) {
            System.out.print("Enter Sex (M/F): ");
            sex = sc.nextLine().trim().toUpperCase();
            if (sex.equals("M") || sex.equals("F")) break;
            System.out.println("*** [!] Invalid input. Please enter 'M' or 'F'. ***\n");
        }

        // Admit Status
        while (true) {
            System.out.print("Enter Admit Status (Admitted/Released): ");
            admitStatus = sc.nextLine().trim();
            if (admitStatus.equalsIgnoreCase("Admitted") || admitStatus.equalsIgnoreCase("Released")) {
                admitStatus = admitStatus.substring(0,1).toUpperCase() + admitStatus.substring(1).toLowerCase();
                break;
            }
            System.out.println("*** [!] Invalid status. Must be 'Admitted' or 'Released'. ***\n");
        }

        // Age
        while (true) {
            System.out.print("Enter Age: ");
            try {
                age = Integer.parseInt(sc.nextLine().trim());
                if (age <= 0) System.out.println("*** [!] Age Must Be Positive. Please Try Again. ***\n");
                else break;
            } catch (NumberFormatException e) {
                System.out.println("*** [!] Invalid input. Please enter a number for age. ***\n");
            }
        }

        System.out.println("\n------------------------------------------------------");
        System.out.println("                  PATIENT REGISTERED");
        System.out.println("------------------------------------------------------\n");
    }

    // Display patient info in table format
    public void showPatientInfo() {
        System.out.printf("%-8s %-15s %-20s %-8s %-16s %-8s%n",
                id, name, disease, sex, admitStatus, age);
    }

    @Override
    public String toString() {
        return showPatientInfoString();
    }

    public String showPatientInfoString() {
        return String.format("%s   %s   %s   %s   %s   %d",
                id, name, disease, sex, admitStatus, age);
    }

    /* ---------- HELPER METHODS ---------- */
    public static int indexOfById(Patient[] arr, int count, String targetId) {
        if (arr == null || targetId == null) return -1;
        for (int i = 0; i < count; i++)
            if (targetId.equalsIgnoreCase(arr[i].getId())) return i;
        return -1;
    }

    /* ---------- Search / Filter ---------- */
    public static int searchById(Patient[] arr, int count, String id) {
        System.out.println("\n------------------------------");
        System.out.println("          SEARCH BY ID        ");
        System.out.println("------------------------------\n");

        int idx = indexOfById(arr, count, id);
        if (idx >= 0) {
        	System.out.printf("%-8s %-15s %-20s %-8s %-16s %-8s%n",
                    "ID", "Name", "Disease", "Sex", "Status", "Age");
                for (int i = 0; i < 80; i++) System.out.print("-");
                System.out.println();
            arr[idx].showPatientInfo();
            System.out.println("\nMatch Count: 1");
            return 1;
        }

        System.out.println("*** [!] NO MATCH. ***");
        System.out.println("\nMatch Count: 0");
        return 0;
    }

    public static int searchByNameContains(Patient[] arr, int count, String query) {
        System.out.println("\n------------------------------");
        System.out.println("        SEARCH BY NAME         ");
        System.out.println("------------------------------\n");

        if (query == null) query = "";
        String q = query.toLowerCase();
        int hits = 0;

        for (int i = 0; i < count; i++) {
            if (arr[i].getName().toLowerCase().contains(q)) {
                if (hits == 0) {
                	System.out.printf("%-8s %-15s %-20s %-8s %-16s %-8s%n",
                            "ID", "Name", "Disease", "Sex", "Status", "Age");
                        for (int j = 0; j < 80; j++) System.out.print("-");
                        System.out.println();
                }
                arr[i].showPatientInfo();
                for (int j = 0; j < 80; j++) System.out.print("-");
                System.out.println();
                hits++;
            }
        }

        System.out.println("\nMatch Count: " + hits);
        return hits;
    }

    public static int searchByAgeRange(Patient[] arr, int count, int minAge, int maxAge) {
        System.out.println("\n------------------------------");
        System.out.println("        SEARCH BY AGE RANGE    ");
        System.out.println("------------------------------\n");

        if (maxAge < minAge) { int t = minAge; minAge = maxAge; maxAge = t; }
        int hits = 0;

        System.out.printf("%-8s %-15s %-20s %-8s %-16s %-8s%n",
                "ID", "Name", "Disease", "Sex", "Status", "Age");
            for (int i = 0; i < 80; i++) System.out.print("-");
            System.out.println();

        for (int i = 0; i < count; i++) {
            int a = arr[i].getAge();
            if (a >= minAge && a <= maxAge) {
                arr[i].showPatientInfo();
                for (int j = 0; j < 80; j++) System.out.print("-");
                System.out.println();
                hits++;
            }
        }

        System.out.println("\nMatch Count: " + hits);
        return hits;
    }

    /* ---------- Sorting ---------- */
    public static void sortByAge(Patient[] arr, int count) {
        java.util.Arrays.sort(arr, 0, count, java.util.Comparator.comparingInt(Patient::getAge));
        System.out.println("\n------------------------------------------------------");
        System.out.println("                  SORTED BY AGE (ASC)");
        System.out.println("------------------------------------------------------\n");
    }

    public static void sortByName(Patient[] arr, int count) {
        java.util.Arrays.sort(arr, 0, count,
                java.util.Comparator.comparing(Patient::getName, String.CASE_INSENSITIVE_ORDER));
        System.out.println("\n------------------------------------------------------");
        System.out.println("                  SORTED BY NAME (A -> Z)");
        System.out.println("------------------------------------------------------\n");
    }

    /* ---------- Update / Delete ---------- */
    public static boolean updateById(Patient[] arr, int count, String id) {
        int idx = indexOfById(arr, count, id);
        if (idx < 0) { 
            System.out.println("\n*** [!] Patient Not Found. ***"); 
            return false; 
        }

        Patient p = arr[idx];
        System.out.println("\n------------------------------");
        System.out.println("          UPDATE PATIENT       ");
        System.out.println("------------------------------\n");
        System.out.println("Leave blank to keep current value.\n");

        System.out.print("Name (" + p.getName() + "): ");
        String s = sc.nextLine().trim();
        if (!s.isEmpty()) p.setName(s);

        System.out.print("\nDisease (" + p.getDisease() + "): ");
        s = sc.nextLine().trim();
        if (!s.isEmpty()) p.setDisease(s);

        System.out.print("\nSex (" + p.getSex() + "): ");
        s = sc.nextLine().trim();
        if (!s.isEmpty()) {
            String sx = s.toUpperCase();
            if (sx.equals("M") || sx.equals("F")) p.setSex(sx);
            else System.out.println("*** [!] Invalid sex. Previous value kept. ***\n");
        }

        System.out.print("\nAdmit Status (" + p.getAdmitStatus() + ") [Admitted/Released]: ");
        s = sc.nextLine().trim();
        if (!s.isEmpty()) {
            if (s.equalsIgnoreCase("Admitted") || s.equalsIgnoreCase("Released")) {
                s = s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
                p.setAdmitStatus(s);
            } else {
                System.out.println("*** [!] Invalid status. Previous value kept. ***\n");
            }
        }

        System.out.print("\nAge (" + p.getAge() + "): ");
        s = sc.nextLine().trim();
        if (!s.isEmpty()) {
            try {
                int a = Integer.parseInt(s);
                if (a > 0) p.setAge(a);
                else System.out.println("*** [!] Age must be positive. Previous value kept. ***\n");
            } catch (NumberFormatException e) {
                System.out.println("*** [!] Invalid number. Previous value kept. ***\n");
            }
        }

        System.out.println("\n------------------------------------------------------");
        System.out.println("                 DETAILS UPDATED");
        System.out.println("------------------------------------------------------\n");
        return true;
    }

    public static int deleteById(Patient[] arr, int count, String id) {
        int idx = indexOfById(arr, count, id);
        if (idx < 0) {
            System.out.println("\n*** [!] Patient Not Found. ***");
            return count;
        }

        for (int i = idx; i < count - 1; i++) arr[i] = arr[i + 1];
        arr[count - 1] = null;

        System.out.println("\n------------------------------------------------------");
        System.out.println("                  PATIENT REMOVED");
        System.out.println("------------------------------------------------------\n");
        return count - 1;
    }
}
