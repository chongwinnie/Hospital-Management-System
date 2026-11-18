import java.util.Scanner;

public class Facility {
    private String facility;

    private static Scanner sc = new Scanner(System.in);

    // Constructors
    public Facility() {}

    public Facility(String facility) {
        this.facility = facility;
    }

    // Getter + Setter
    public String getFacility() { return facility; }
    public void setFacility(String facility) { this.facility = facility; }

    // Add new facility with validation
    public void newFacility() {
        System.out.println("\n------------------------------");
        System.out.println("       ADD NEW FACILITY       ");
        System.out.println("------------------------------\n");

        do {
            System.out.print("Enter Facility Name: ");
            facility = sc.nextLine().trim();
            if (facility.isEmpty()) {
                System.out.println("*** [!] Facility Cannot Be Empty: Please Try Again. ***\n");
            }
        } while (facility.isEmpty());

        System.out.println("\n------------------------------------------------------");
        System.out.println("                  FACILITY REGISTERED");
        System.out.println("------------------------------------------------------\n");
    }

    // Display facility info
    public void showFacility() {
        System.out.println(facility);
    }

    @Override
    public String toString() {
        return showFacilityString();
    }

    public String showFacilityString() {
        return String.format("%s", facility);
    }

    /* ------- HELPER METHODS ----------*/
    // Find index by facility name in the first {@code count} elements; returns -1 if not found.
    private static int indexOfByName(Facility[] arr, int count, String name) {
        if (arr == null || name == null) return -1;
        for (int i = 0; i < count; i++) {
            if (name.equalsIgnoreCase(arr[i].getFacility())) return i;
        }
        return -1;
    }

    /*
         Interactive update for a facility with the given name.
         Prompts for new name; press Enter to keep current value.
         @return true if updated, false if not found.
    */
    public static boolean updateByName(Facility[] arr, int count, String name) {
        int idx = indexOfByName(arr, count, name);
        if (idx < 0) { 
            System.out.println("\n*** [!] Facility Not Found. ***");
            return false; 
        }

        Facility f = arr[idx];
        System.out.println("\n------------------------------------------------------");
        System.out.println("                    UPDATE FACILITY");
        System.out.println("------------------------------------------------------\n");
        System.out.println("Leave blank to keep current value.\n");

        System.out.print("Facility Name (" + f.getFacility() + "): ");
        String s = sc.nextLine().trim();
        if (!s.isEmpty()) {
            f.setFacility(s);
        }

        System.out.println("\n------------------------------------------------------");
        System.out.println("                   DETAILS UPDATED");
        System.out.println("------------------------------------------------------\n");
        return true;
    }

    /*
         Delete a facility by name from the first {@code count} elements (shifts left).
         @return the new count after deletion.
    */
    public static int deleteByName(Facility[] arr, int count, String name) {
        int idx = indexOfByName(arr, count, name);
        if (idx < 0) {
            System.out.println("\n*** [!] Facility Not Found. ***");
            return count;
        }
        for (int i = idx; i < count - 1; i++) {
            arr[i] = arr[i + 1];
        }
        arr[count - 1] = null;
        System.out.println("\n------------------------------------------------------");
        System.out.println("                   FACILITY REMOVED");
        System.out.println("------------------------------------------------------\n");
        return count - 1;
    }
}
