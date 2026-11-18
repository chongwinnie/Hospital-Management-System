import java.util.Scanner;

public abstract class Staff {
    protected String id;
    protected String name;
    protected String sex;
    protected int salary;

    protected static Scanner sc = new Scanner(System.in);

    // Constructors
    public Staff() {}

    public Staff(String id, String name, String sex, int salary) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.salary = salary;
    }

    // Getters + Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public int getSalary() { return salary; }
    public void setSalary(int salary) { this.salary = salary; }

    // Abstract method for subclasses to define their role
    public abstract String getRole();

    // Add new staff with validation (shared)
    public void newStaff() {
        System.out.println("\n---------------------------------");
        System.out.println("        ADD NEW " + getRole().toUpperCase());
        System.out.println("---------------------------------\n");

        // ID
        do {
            System.out.print(" Enter Staff ID: ");
            id = sc.nextLine().trim();
            if (id.isEmpty()) {
                System.out.println("*** [!] ID Cannot Be Empty: Please Try Again. ***\n");
            }
        } while (id.isEmpty());

        // Name
        do {
            System.out.print("\n Enter Staff Name: ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("*** [!] Name Cannot Be Empty: Please Try Again. ***\n");
            }
        } while (name.isEmpty());

        // Sex
        while (true) {
            System.out.print("\n Enter Sex [M / F]: ");
            sex = sc.nextLine().trim().toUpperCase();
            if (sex.equals("M") || sex.equals("F")) break;
            System.out.println("*** [!] Invalid Input: Please enter 'M' or 'F'. ***\n");
        }

        // Salary
        while (true) {
            System.out.print("\n Enter Salary [RM]: ");
            try {
                salary = Integer.parseInt(sc.nextLine().trim());
                if (salary <= 0) {
                    System.out.println("*** [!] Salary Must Be Positive: Please Try Again. ***\n");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("*** [!] Invalid Input: Please enter a number. ***\n");
            }
        }

        System.out.println("\n------------------------------------------------------");
        System.out.println("                  " + getRole().toUpperCase() + " REGISTERED");
        System.out.println("------------------------------------------------------\n");
    }

    // Display staff info
    public void showStaffInfo() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return String.format("%-8s %-15s %-21s %-10s %-10d",
                id, name, getRole(), sex, salary);
    }

    /* -------- HELPER METHODS ----------- */
    protected static int indexOfById(Staff[] arr, int count, String targetId) {
        if (arr == null || targetId == null) return -1;
        for (int i = 0; i < count; i++) {
            if (targetId.equalsIgnoreCase(arr[i].getId())) return i;
        }
        return -1;
    }

    public static boolean updateById(Staff[] arr, int count, String id) {
        int idx = indexOfById(arr, count, id);
        if (idx < 0) { 
            System.out.println("\n*** [!] Staff Not Found. ***");
            return false; 
        }

        Staff s = arr[idx];
        System.out.println("\n------------------------------------------------------");
        System.out.println("                  UPDATE " + s.getRole().toUpperCase());
        System.out.println("------------------------------------------------------\n");
        System.out.println("Leave blank to keep current value.\n");

        // Name
        System.out.print("Name (" + s.getName() + "): ");
        String in = sc.nextLine().trim();
        if (!in.isEmpty()) s.setName(in);

        // Sex
        System.out.print("\nSex (" + s.getSex() + ") [M/F]: ");
        in = sc.nextLine().trim();
        if (!in.isEmpty()) {
            String sx = in.toUpperCase();
            if (sx.equals("M") || sx.equals("F")) s.setSex(sx);
            else System.out.println("*** [!] Invalid sex. Previous value kept. ***");
        }

        // Salary
        System.out.print("\nSalary (" + s.getSalary() + "): ");
        in = sc.nextLine().trim();
        if (!in.isEmpty()) {
            try {
                int sal = Integer.parseInt(in);
                if (sal > 0) s.setSalary(sal);
                else System.out.println("*** [!] Salary Must Be Positive. Previous value kept. ***");
            } catch (NumberFormatException e) {
                System.out.println("*** [!] Invalid Number. Previous value kept. ***");
            }
        }

        System.out.println("\n------------------------------------------------------");
        System.out.println("                  DETAILS UPDATED");
        System.out.println("------------------------------------------------------\n");
        return true;
    }

    public static int deleteById(Staff[] arr, int count, String id) {
        int idx = indexOfById(arr, count, id);
        if (idx < 0) {
            System.out.println("\n*** [!] Staff Not Found. ***");
            return count;
        }

        for (int i = idx; i < count - 1; i++) {
            arr[i] = arr[i + 1];
        }
        arr[count - 1] = null;

        System.out.println("\n------------------------------------------------------");
        System.out.println("                  STAFF REMOVED");
        System.out.println("------------------------------------------------------\n");
        return count - 1;
    }
}