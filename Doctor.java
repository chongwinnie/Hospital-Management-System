import java.util.Scanner;

public class Doctor {
    private String id;
    private String name;
    private String specialist;
    private String workTime;
    private String qualification;
    private int room;

    private static Scanner sc = new Scanner(System.in);

    // Constructors
    public Doctor() {}

    public Doctor(String id, String name, String specialist, String workTime, String qualification, int room) {
        this.id = id;
        this.name = name;
        this.specialist = specialist;
        this.workTime = workTime;
        this.qualification = qualification;
        this.room = room;
    }

    // Getters + Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

 // Add new doctor (validation like Patient.newPatient)
    public void newDoctor() {
        System.out.println("\n------------------------------");
        System.out.println("        ADD NEW DOCTOR        ");
        System.out.println("------------------------------\n");

        // Doctor ID
        do {
            System.out.print("Enter Doctor ID: ");
            id = sc.nextLine().trim();
            if (id.isEmpty()) {
            	System.out.println("*** [!] ID Cannot Be Empty: Please Try Again. ***\n");
            }
        } while (id.isEmpty());

        // Doctor Name
        do {
            System.out.print("\nEnter Doctor Name: ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
            	System.out.println("*** [!] Name Cannot Be Empty: Please Try Again. ***\n");
            }
        } while (name.isEmpty());

        // Specialist
        do {
            System.out.print("\nEnter Specialist Field: ");
            specialist = sc.nextLine().trim();
            if (specialist.isEmpty()) {
            	System.out.println("*** [!] Specialist Cannot Be Empty: Please Try Again. ***\n");
            }
        } while (specialist.isEmpty());

        // Work Time
        do {
            System.out.print("\nEnter Work Time (e.g., 9-5): ");
            workTime = sc.nextLine().trim();
            if (workTime.isEmpty()) {
            	System.out.println("*** [!] Work Time Cannot Be Empty: Please Try Again. ***\n");
            }
        } while (workTime.isEmpty());

        // Qualification
        do {
            System.out.print("\nEnter Qualification: ");
            qualification = sc.nextLine().trim();
            if (qualification.isEmpty()) {
                System.out.println("*** [!] Qualification Cannot Be Empty: Please Try Again. ***\n");
            }
        } while (qualification.isEmpty());

        // Room number (must be positive int)
        while (true) {
            System.out.print("\nEnter Room No: ");
            try {
                room = Integer.parseInt(sc.nextLine().trim());
                if (room <= 0) {
                    System.out.println("*** [!] Room Number Must Be Positive: Please Try Again. ***\n");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("*** [!] Invalid Input: Please Try Again. ***\n");
            }
        }

        System.out.println("\n------------------------------------------------------");
        System.out.println("                   DOCTOR REGISTERED");
        System.out.println("------------------------------------------------------\n");
    }


    public void showDoctorInfo() {
        System.out.printf("%-10s %-21s %-20s %-15s %-16s %-10d%n",
            id, name, specialist, workTime, qualification, room);
    }

    @Override
    public String toString() {
        return showDoctorInfoString();
    }

    public String showDoctorInfoString() {
        return String.format("%s   %s   %s   %s   %s   %d",
                id, name, specialist, workTime, qualification, room);
    }


 /* ============ HELPER METHODS ============== */
 // Find index by ID in the first {@code count} elements; returns -1 if not found.
 public static int indexOfById(Doctor[] arr, int count, String targetId) {
     if (arr == null || targetId == null) return -1;
     for (int i = 0; i < count; i++) {
         if (targetId.equalsIgnoreCase(arr[i].getId())) return i;
     }
     return -1;
 }

 /* ========== Search / Filter ================= */
 // Prints the doctor with exact ID (if any) and returns number of matches (0 or 1).
 public static int searchById(Doctor[] arr, int count, String id) {
     System.out.println("\n------------------------------------------------------");
     System.out.println("                     SEARCH BY ID");
     System.out.println("------------------------------------------------------\n");

     int idx = indexOfById(arr, count, id);
     if (idx >= 0) {
    	 System.out.printf("%-8s %-15s %-20s %-12s %-18s %-12s%n",
         	    "ID", "Name", "Specialist", "Timing", "Qualification", "Room");
         	for (int i = 0; i < 86; i++) 
         		System.out.print("-");
         	System.out.println();
         arr[idx].showDoctorInfo();
         System.out.println("\nMatch Count: 1");
         return 1;
     }

     System.out.println("*** [!] NO MATCH. ***");
     System.out.println("\n Match Count: 0");
     return 0;
 }

 // Case-insensitive substring match on name. Prints matches and returns the count.
 public static int searchByNameContains(Doctor[] arr, int count, String query) {
     System.out.println("\n------------------------------------------------------");
     System.out.println("                  SEARCH BY NAME CONTAINS");
     System.out.println("------------------------------------------------------\n");

     if (query == null) query = "";
     String q = query.toLowerCase();
     int hits = 0;

     for (int i = 0; i < count; i++) {
         if (arr[i].getName().toLowerCase().contains(q)) {
        	 System.out.printf("%-8s %-15s %-20s %-12s %-18s %-12s%n",
              	    "ID", "Name", "Specialist", "Timing", "Qualification", "Room");
              	for (int j = 0; j < 86; j++) 
              		System.out.print("-");
              	System.out.println();
             arr[i].showDoctorInfo();
             for (int j = 0; j < 86; j++) System.out.print("-");
             System.out.println();
             hits++;
         }
     }

     System.out.println("\nMatch Count: " + hits);
     return hits;
 }

 // Filter by room range [minRoom, maxRoom]. Prints matches and returns the count.
 public static int searchByRoomRange(Doctor[] arr, int count, int minRoom, int maxRoom) {
     System.out.println("\n------------------------------------------------------");
     System.out.println("                     SEARCH BY ROOM RANGE");
     System.out.println("------------------------------------------------------\n");

     if (maxRoom < minRoom) { int t = minRoom; minRoom = maxRoom; maxRoom = t; }

     int hits = 0;
     System.out.printf("%-8s %-15s %-20s %-12s %-18s %-12s%n",
      	    "ID", "Name", "Specialist", "Timing", "Qualification", "Room");
      	for (int i = 0; i < 86; i++) 
      		System.out.print("-");
      	System.out.println();
     for (int i = 0; i < count; i++) {
         int r = arr[i].getRoom();
         if (r >= minRoom && r <= maxRoom) {
             arr[i].showDoctorInfo();
             for (int j = 0; j < 86; j++) System.out.print("-");
             System.out.println();
             hits++;
         }
     }

     System.out.println("\nMatch Count: " + hits);
     return hits;
 }

 /* ============== Sorting ================= */
 // Sort the first {@code count} doctors by name (A->Z, case-insensitive).
 public static void sortByName(Doctor[] arr, int count) {
     java.util.Arrays.sort(arr, 0, count,
             java.util.Comparator.comparing(Doctor::getName, String.CASE_INSENSITIVE_ORDER));
     System.out.println("\n------------------------------------------------------");
     System.out.println("                 SORTED BY NAME (A -> Z)");
     System.out.println("------------------------------------------------------\n");
 }

 // Sort the first {@code count} doctors by room (ascending).
 public static void sortByRoom(Doctor[] arr, int count) {
     java.util.Arrays.sort(arr, 0, count,
             java.util.Comparator.comparingInt(Doctor::getRoom));
     System.out.println("\n------------------------------------------------------");
     System.out.println("                SORTED BY ROOM NUMBER (ASC)");
     System.out.println("------------------------------------------------------\n");
 }

 /* ============= Update / Delete ============== */
 /* Interactive update for a doctor with the given ID.
    Prompts each field; press Enter to keep current value.
  */
 public static boolean updateById(Doctor[] arr, int count, String id) {
     int idx = indexOfById(arr, count, id);
     if (idx < 0) { 
         System.out.println("\n*** [!] Doctor Not Found. ***");
         return false; 
     }

     Doctor d = arr[idx];
     System.out.println("\n------------------------------------------------------");
     System.out.println("                     UPDATE DOCTOR");
     System.out.println("------------------------------------------------------\n");
     System.out.println("Leave blank to keep current value.\n");

     System.out.print("Name (" + d.getName() + "): ");
     String s = sc.nextLine().trim();
     if (!s.isEmpty()) d.setName(s);

     System.out.print("\nSpecialist (" + d.getSpecialist() + "): ");
     s = sc.nextLine().trim();
     if (!s.isEmpty()) d.setSpecialist(s);

     System.out.print("\nWork time (" + d.getWorkTime() + "): ");
     s = sc.nextLine().trim();
     if (!s.isEmpty()) d.setWorkTime(s);

     System.out.print("\nQualification (" + d.getQualification() + "): ");
     s = sc.nextLine().trim();
     if (!s.isEmpty()) d.setQualification(s);

     System.out.print("\nRoom (" + d.getRoom() + "): ");
     s = sc.nextLine().trim();
     if (!s.isEmpty()) {
         try {
             int r = Integer.parseInt(s);
             if (r > 0) d.setRoom(r);
             else System.out.println("\n*** [!] Room Number Must Be Positive: Previous Value Kept. ***");
         } catch (NumberFormatException e) {
        	 System.out.println("\n*** [!] Invalid Number: Previous Value Kept. ***");
         }
     }

     System.out.println("\n------------------------------------------------------");
     System.out.println("                   DETAILS UPDATED");
     System.out.println("------------------------------------------------------\n");
     return true;
 }

 /*
   Delete a doctor by ID from the first {@code count} elements (shifts left).
   Returns the new count after deletion.
 */
 public static int deleteById(Doctor[] arr, int count, String id) {
     int idx = indexOfById(arr, count, id);
     if (idx < 0) {
    	 System.out.println("\n*** [!] Doctor Not Found. ***");
         return count;
     }

     for (int i = idx; i < count - 1; i++) {
         arr[i] = arr[i + 1];
     }
     arr[count - 1] = null;

     System.out.println("\n------------------------------------------------------");
     System.out.println("                   DOCTOR REMOVED");
     System.out.println("------------------------------------------------------\n");
     return count - 1;
 }
}