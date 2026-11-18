import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class HospitalManagement {

    // Arrays with fixed capacities
    private static Doctor[] doctors = new Doctor[25];
    private static Patient[] patients = new Patient[100];
    private static Lab[] labs = new Lab[20];
    private static Facility[] facilities = new Facility[20];
    private static Medicine[] medicines = new Medicine[100];
    private static Staff[] staff = new Staff[100];

    // Counters to track how many are currently stored

    // ===== Duplicate-key checks (primary keys) =====
    private static boolean doctorIdExists(String id) {
        for (int i = 0; i < doctorCount; i++) {
            if (doctors[i] != null && doctors[i].getId().equalsIgnoreCase(id)) return true;
        }
        return false;
    }
    private static boolean patientIdExists(String id) {
        for (int i = 0; i < patientCount; i++) {
            if (patients[i] != null && patients[i].getId().equalsIgnoreCase(id)) return true;
        }
        return false;
    }
    private static boolean staffIdExists(String id) {
        for (int i = 0; i < staffCount; i++) {
            if (staff[i] != null && staff[i].getId().equalsIgnoreCase(id)) return true;
        }
        return false;
    }
    private static boolean medicineNameExists(String name) {
        for (int i = 0; i < medicineCount; i++) {
            if (medicines[i] != null && medicines[i].getName().equalsIgnoreCase(name)) return true;
        }
        return false;
    }
    private static boolean labNameExists(String name) {
        for (int i = 0; i < labCount; i++) {
            if (labs[i] != null && labs[i].getLab().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    private static int doctorCount = 0;
    private static int patientCount = 0;
    private static int labCount = 0;
    private static int facilityCount = 0;
    private static int medicineCount = 0;
    private static int staffCount = 0;

    private static Scanner sc = new Scanner(System.in);
    private static String ROLE = "staff"; // set on login

    public static void main(String[] args) {
        // Welcome + date/time
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("------------------------------------------------------");
        System.out.println("|      Welcome to the Hospital Management System     |");
        System.out.println("------------------------------------------------------");
        System.out.println("\nCurrent Date & Time: " + LocalDateTime.now().format(fmt));
        System.out.println();

        // Initialize sample data
        loadSampleData();

        // Login (roles: admin / staff)
        ROLE = Authenticate.login(sc);

        // Menu loop
        boolean running = true;
        while (running) {
            System.out.println("\n------------------------------------------------------");
            System.out.printf("               MAIN MENU  [%s]\n", ROLE.toUpperCase());
            System.out.println("------------------------------------------------------\n");
            System.out.println(" [1] DOCTORS\n");
            System.out.println(" [2] PATIENTS\n");
            System.out.println(" [3] MEDICINES\n");
            System.out.println(" [4] LABORATORIES\n");
            System.out.println(" [5] FACILITIES\n");
            System.out.println(" [6] STAFF\n");
            System.out.println(" [0] EXIT\n");
            System.out.println("------------------------------------------------------");
            System.out.print(" OPTION [ 0 - 6 ]: ");

            int choice = readInt();
            switch (choice) {
                case 1: doctorsMenu(); break;
                case 2: patientsMenu(); break;
                case 3: medicineMenu(); break;
                case 4: labsMenu(); break;
                case 5: facilitiesMenu(); break;
                case 6: staffMenu(); break;
                case 0:
                    if (InputHandling.confirm(sc, "\n*** Confirm Exit: Are you sure you want to close HMS? ***")) {
                        running = false;
                        System.out.println("\nThank you for using HMS. Wishing you good health - Have a great day!");
                    }
                    break;
                default:
                    System.out.println("*** Invalid Option: Select [ 0 - 6 ]. ***");
            }
        }

        sc.close();
    }


    /* ================= SAMPLE DATA ================= */
    private static void loadSampleData() {
        // reset counts in case loadSampleData() is called more than once
        doctorCount = patientCount = labCount = facilityCount = medicineCount = staffCount = 0;

        // ===== Doctors =====
        doctors[doctorCount++] = new Doctor("D271", "Chong Min Hing",     "Cardiology",   "9-5",  "MBBS", 101);
        doctors[doctorCount++] = new Doctor("D895", "Chong Win Nie",    "Neurology",    "10-6", "MD",   102);
        doctors[doctorCount++] = new Doctor("D001", "Ali",     "Cardiology",   "9-5",  "MBBS", 103);
        doctors[doctorCount++] = new Doctor("D002", "Siti",    "Neurology",    "10-6", "MD",   104);
        doctors[doctorCount++] = new Doctor("D003", "John",    "Orthopedics",  "11-7", "PhD",  105);
        doctors[doctorCount++] = new Doctor("D004", "Amina",   "Pediatrics",   "8-4",  "MBBS", 106);
        doctors[doctorCount++] = new Doctor("D005", "Ravi",    "Dermatology",  "12-8", "MD",   107);
        doctors[doctorCount++] = new Doctor("D006", "Mei",     "Oncology",     "9-3",  "FRCS", 108);
        doctors[doctorCount++] = new Doctor("D007", "Omar",    "ENT",          "10-7", "MBBS", 109);
        doctors[doctorCount++] = new Doctor("D008", "Grace",   "Gynecology",   "9-5",  "MD",   110);
        doctors[doctorCount++] = new Doctor("D009", "Chen",    "Radiology",    "8-2",  "PhD",  111);
        doctors[doctorCount++] = new Doctor("D010", "Samuel",  "Psychiatry",   "1-9",  "MMed", 112);

        // ===== Patients =====
        patients[patientCount++] = new Patient("P001", "Ahmad",    "Fever",     "M", "Admitted", 24);
        patients[patientCount++] = new Patient("P002", "Aisha",    "Flu",       "F", "Released", 30);
        patients[patientCount++] = new Patient("P003", "Michael",  "Asthma",    "M", "Admitted", 40);
        patients[patientCount++] = new Patient("P004", "Nurul",    "Diabetes",  "F", "Admitted", 55);
        patients[patientCount++] = new Patient("P005", "Hassan",   "Hypertension","M","Released",62);
        patients[patientCount++] = new Patient("P006", "Priya",    "Migraine",  "F", "Admitted", 19);
        patients[patientCount++] = new Patient("P007", "Daniel",   "Fracture",  "M", "Released", 33);
        patients[patientCount++] = new Patient("P008", "Zara",     "Allergy",   "F", "Admitted", 27);
        patients[patientCount++] = new Patient("P009", "Victor",   "COVID-19",  "M", "Released", 46);
        patients[patientCount++] = new Patient("P010", "Lily",     "Ulcer",     "F", "Admitted", 72);

        // ===== Labs =====
        labs[labCount++] = new Lab("Blood Test",           100);
        labs[labCount++] = new Lab("X-Ray",                200);
        labs[labCount++] = new Lab("MRI",                  500);
        labs[labCount++] = new Lab("Ultrasound",           250);
        labs[labCount++] = new Lab("CT Scan",              600);
        labs[labCount++] = new Lab("Urine Test",            60);
        labs[labCount++] = new Lab("ECG",                  180);
        labs[labCount++] = new Lab("Echocardiogram",       350);
        labs[labCount++] = new Lab("Liver Function Test",  140);
        labs[labCount++] = new Lab("COVID PCR",            220);

        // ===== Facilities =====
        facilities[facilityCount++] = new Facility("Emergency Ward");
        facilities[facilityCount++] = new Facility("ICU");
        facilities[facilityCount++] = new Facility("Pharmacy");
        facilities[facilityCount++] = new Facility("Maternity Ward");
        facilities[facilityCount++] = new Facility("Operation Theater");
        facilities[facilityCount++] = new Facility("General Ward");
        facilities[facilityCount++] = new Facility("Outpatient Department (OPD)");
        facilities[facilityCount++] = new Facility("Cafeteria");
        facilities[facilityCount++] = new Facility("Billing Counter");
        facilities[facilityCount++] = new Facility("Waiting Area");

        // ===== Medicines =====
        medicines[medicineCount++] = new Medicine("Paracetamol",   "PharmaA", "2026-01",  5, 100);
        medicines[medicineCount++] = new Medicine("Amoxicillin",   "PharmaB", "2025-12", 10,  50);
        medicines[medicineCount++] = new Medicine("Ventolin",      "PharmaC", "2027-06", 20,  30);
        medicines[medicineCount++] = new Medicine("Ibuprofen",     "PharmaD", "2026-05",  8, 200);
        medicines[medicineCount++] = new Medicine("Insulin",       "PharmaE", "2025-10",150,  15);  
        medicines[medicineCount++] = new Medicine("Omeprazole",    "PharmaF", "2026-12", 12,  80);
        medicines[medicineCount++] = new Medicine("Cough Syrup",   "PharmaG", "2025-08",  7,  12);  
        medicines[medicineCount++] = new Medicine("Aspirin",       "PharmaH", "2025-11",  3,   5); 
        medicines[medicineCount++] = new Medicine("Vitamin C",     "PharmaI", "2027-03", 10, 300);
        medicines[medicineCount++] = new Medicine("Azithromycin",  "PharmaJ", "2025-12", 18,  25);

        // ===== Staff =====
        staff[staffCount++] = new Nurses("N001", "Nora", "F", 3000);
        staff[staffCount++] = new Pharmacists("PH001", "James", "M", 4000);
        staff[staffCount++] = new Security("S001", "Farah", "F", 2000);
        staff[staffCount++] = new Nurses("N002", "Rahman", "M", 2800);
        staff[staffCount++] = new Pharmacists("PH002", "Emily", "F", 3200);
        staff[staffCount++] = new Security("S002", "Kumar", "M", 1800);
        staff[staffCount++] = new Nurses("N003", "Aqilah", "F", 3500);
        staff[staffCount++] = new Pharmacists("PH003", "Ben", "M", 3800);
        staff[staffCount++] = new Security("S003", "Sophia", "F", 2200);
        staff[staffCount++] = new Nurses("N004", "Hakim", "M", 3100);
    }

    /* ================= MENU HANDLERS ================= */

    private static void doctorsMenu() {
        while (true) {
            System.out.println("\n------------------------------------------------------");
            System.out.println("                    DOCTORS MENU");
            System.out.println("------------------------------------------------------\n");
            System.out.println(" [1] Register New Doctor\n");
            System.out.println(" [2] View All Doctors\n");
            System.out.println(" [3] Return to Main Menu\n");
            System.out.println(" [4] Update Doctor Details (by ID)\n");
            System.out.println(" [5] Remove Doctor Record (by ID)\n");
            System.out.println(" [6] Search / Filter Doctors\n");
            System.out.println(" [7] Sort Doctors by Name\n");
            System.out.println(" [8] Sort Doctors by Room Number\n");
            System.out.println("------------------------------------------------------");
            System.out.print(" OPTION [ 1 - 8 ]: ");
            int c = readInt();

            switch (c) {

                case 1:
                    if (doctorCount < doctors.length) {
                        Doctor d = new Doctor();
                        d.newDoctor();
                        if (doctorIdExists(d.getId())) {
                            System.out.println("\n*** [!] Doctor ID already exists. Record not added. ***");
                        } else {
                            doctors[doctorCount++] = d;
                        }
                    } else {
                    	System.out.println("\n*** Registration Failed: Maximum Capacity Reached. ***");
                    }
                    break;

                case 2:
                    if (doctorCount == 0) {
                    	System.out.println("\n*** Doctor Not Found. ***");
                    } else {
                        System.out.println("\n------------------------------------------------------");
                        System.out.println("                     LIST OF DOCTORS");
                        System.out.println("------------------------------------------------------\n");

                        System.out.printf("%-10s %-21s %-20s %-12s %-18s %-12s%n",
                        	    "ID", "Name", "Specialist", "Timing", "Qualification", "Room");
                        	for (int i = 0; i < 94; i++) System.out.print("-");
                        	System.out.println();

                        	for (int i = 0; i < doctorCount; i++) {
                        	    doctors[i].showDoctorInfo();
                        	    for (int j = 0; j < 94; j++) System.out.print("-");
                        	    System.out.println();
                        	}
                        	System.out.println("\nDoctors Count: " + doctorCount);
                    }
                    break;


                case 3:
                    return;

                case 4: { // Update
                    if (doctorCount == 0) {
                    	System.out.println("\n*** No Doctors Available. ***");
                        break;
                    }
                    String id = InputHandling.readNonEmpty(sc, "\nEnter Doctor ID to Update: ");
                    boolean ok = Doctor.updateById(doctors, doctorCount, id);
                    if (!ok) {
                        System.out.println("*** Update Unsuccessful: Please check the provided Doctor ID. ***");
                    }
                    break;
                }

                case 5: { // Delete
                    if (doctorCount == 0) {
                    	System.out.println("\n*** No Doctors Available. ***");
                        break;
                    }
                    String id = InputHandling.readNonEmpty(sc, "\nEnter Doctor ID to Delete: ");
                    int newCount = Doctor.deleteById(doctors, doctorCount, id);
                    if (newCount != doctorCount) {
                        doctorCount = newCount;
                    } else {
                    	System.out.println("\n*** Deletion Unsuccessful: Please check the provided Doctor ID. ***");
                    }
                    break;
                }

                case 6: { // Search / Filter
                    if (doctorCount == 0) {
                    	System.out.println("\n*** No Doctors Available. ***");
                        break;
                    }
                    System.out.println("\n--- Search / Filter Options ---\n");
                    System.out.println(" [1] Search by ID\n");
                    System.out.println(" [2] Search by Name\n");
                    System.out.println(" [3] Search by Room Range\n");
                    System.out.println("------------------------------------------------------");
                    System.out.print(" OPTION [ 1 - 3 ]: ");
                    int op = readInt();
                    switch (op) {
                        case 1: {
                            String id = InputHandling.readNonEmpty(sc, "\nEnter Doctor ID: ");
                            Doctor.searchById(doctors, doctorCount, id);
                            break;
                        }
                        case 2: {
                            String q = InputHandling.readNonEmpty(sc, "\nEnter Doctor's Name: ");
                            Doctor.searchByNameContains(doctors, doctorCount, q);
                            break;
                        }
                        case 3: {
                            System.out.print("\nEnter Minimum Room Number: ");
                            int min = readInt();
                            System.out.print("\nEnter Maximum Room Number: ");
                            int max = readInt();
                            Doctor.searchByRoomRange(doctors, doctorCount, min, max);
                            break;
                        }
                        default:
                        	System.out.println("\n*** Invalid Option: Select [ 1 - 3 ]. ***");
                    }
                    break;
                }

                case 7: { // Sort by Name
                    if (doctorCount == 0) {
                    	System.out.println("\n*** No Doctors Available. ***");
                        break;
                    }
                    Doctor.sortByName(doctors, doctorCount);
                    System.out.printf("%-8s %-15s %-20s %-12s %-18s %-12s%n",
                       	    "ID", "Name", "Specialist", "Timing", "Qualification", "Room");
                    for (int i = 0; i < 86; i++) System.out.print("-");
                	System.out.println();
                	
                    for (int i = 0; i < doctorCount; i++) {
                        doctors[i].showDoctorInfo();
                        for (int j = 0; j < 86; j++) System.out.print("-");
                	    System.out.println();
                    }
                    break;
                }

                case 8: { // Sort by Room
                    if (doctorCount == 0) {
                    	System.out.println("\n*** No Doctors Available. ***");
                        break;
                    }
                    Doctor.sortByRoom(doctors, doctorCount);
                    System.out.printf("%-8s %-15s %-20s %-12s %-18s %-12s%n",
                       	    "ID", "Name", "Specialist", "Timing", "Qualification", "Room");
                    for (int i = 0; i < 86; i++) System.out.print("-");
                	System.out.println();
                	
                    for (int i = 0; i < doctorCount; i++) {
                        doctors[i].showDoctorInfo();
                        for (int j = 0; j < 86; j++) System.out.print("-");
                	    System.out.println();
                    }
                    break;
                }

                default:
                	System.out.println("\n*** Invalid Option: Select [ 1 - 8 ]. ***");
            }
        }
    }

    private static void patientsMenu() {
        while (true) {
            System.out.println("\n------------------------------------------------------");
            System.out.println("                   PATIENTS MENU");
            System.out.println("------------------------------------------------------\n");
            System.out.println(" [1] Register New Patient\n");
            System.out.println(" [2] View All Patients\n");
            System.out.println(" [3] Return to Main Menu\n");
            System.out.println(" [4] Update Patient Details (by ID)\n");
            System.out.println(" [5] Remove Patient Record (by ID)\n");
            System.out.println(" [6] Search / Filter Patients\n");
            System.out.println(" [7] Sort Patients by Age\n");
            System.out.println(" [8] Sort Patients by Name\n");
            System.out.println("------------------------------------------------------");
            System.out.print(" OPTION [ 1 - 8 ]: ");
            int c = readInt();

            switch (c) {
                case 1:
                    if (patientCount < patients.length) {
                        Patient p = new Patient();
                        p.newPatient();
                        if (patientIdExists(p.getId())) {
                            System.out.println("\n*** [!] Patient ID already exists. Record not added. ***");
                        } else {
                            patients[patientCount++] = p;
                        }
                    } else {
                        System.out.println("\n*** Registration Failed: Maximum Capacity Reached. ***");
                    }
                    break;

                case 2:
                    if (patientCount == 0) {
                        System.out.println("\n*** No patient records available. ***");
                    } else {
                        System.out.println("\n------------------------------------------------------");
                        System.out.println("                    LIST OF PATIENTS");
                        System.out.println("------------------------------------------------------\n");
                        System.out.printf("%-8s %-15s %-20s %-8s %-16s %-8s%n",
                            "ID", "Name", "Disease", "Sex", "Status", "Age");
                        for (int i = 0; i < 80; i++) System.out.print("-");
                        System.out.println();

                        for (int i = 0; i < patientCount; i++) {
                            patients[i].showPatientInfo();
                            for (int j = 0; j < 80; j++) System.out.print("-");
                            System.out.println();
                        }
                        System.out.println("\nTotal Patients: " + patientCount);
                    }
                    break;

                case 3:
                    return;

                case 4: {
                    if (patientCount == 0) {
                        System.out.println("\n*** No patient records available. ***");
                        break;
                    }
                    String id = InputHandling.readNonEmpty(sc, "\nEnter Patient ID to update: ");
                    boolean ok = Patient.updateById(patients, patientCount, id);
                    if (!ok) System.out.println("\n*** Update Unsuccessful: Please check the provided Patient ID. ***");
                    break;
                }

                case 5: {
                    if (patientCount == 0) {
                        System.out.println("\n*** No patient records available. ***");
                        break;
                    }
                    String id = InputHandling.readNonEmpty(sc, "\nEnter Patient ID to delete: ");
                    int newCount = Patient.deleteById(patients, patientCount, id);
                    if (newCount != patientCount) patientCount = newCount;
                    else System.out.println("\n*** Deletion Unsuccessful: Please check the provided Patient ID. ***");
                    break;
                }

                case 6: {
                    if (patientCount == 0) {
                        System.out.println("\n*** No patient records available. ***");
                        break;
                    }
                    System.out.println("\n--- Search / Filter Options ---\n");
                    System.out.println(" [1] Search by ID\n");
                    System.out.println(" [2] Search by Name\n");
                    System.out.println(" [3] Search by Age Range\n");
                    System.out.println("------------------------------------------------------");
                    System.out.print(" OPTION [ 1 - 3 ]: ");
                    int op = readInt();
                    switch (op) {
                        case 1: {
                            String id = InputHandling.readNonEmpty(sc, "\nEnter ID: ");
                            Patient.searchById(patients, patientCount, id);
                            break;
                        }
                        case 2: {
                            String q = InputHandling.readNonEmpty(sc, "\nEnter Name: ");
                            Patient.searchByNameContains(patients, patientCount, q);
                            break;
                        }
                        case 3: {
                            System.out.print("\nMin age: ");
                            int min = readInt();
                            System.out.print("\nMax age: ");
                            int max = readInt();
                            Patient.searchByAgeRange(patients, patientCount, min, max);
                            break;
                        }
                        default:
                            System.out.println("\n*** Invalid Option: Select [ 1 - 3 ]. ***");
                    }
                    break;
                }

                case 7: {
                    if (patientCount == 0) {
                        System.out.println("\n*** No patient records available. ***");
                        break;
                    }
                    Patient.sortByAge(patients, patientCount);
                    System.out.printf("%-8s %-15s %-20s %-8s %-16s %-8s%n",
                            "ID", "Name", "Disease", "Sex", "Status", "Age");
                        for (int i = 0; i < 80; i++) System.out.print("-");
                        System.out.println();
                    for (int i = 0; i < patientCount; i++) {
                    	patients[i].showPatientInfo();
                    	for (int j = 0; j < 80; j++) System.out.print("-");
                    	System.out.println();
                    }
                    break;
                }

                case 8: {
                    if (patientCount == 0) {
                        System.out.println("\n*** No patient records available. ***");
                        break;
                    }
                    Patient.sortByName(patients, patientCount);
                    System.out.printf("%-8s %-15s %-20s %-8s %-16s %-8s%n",
                            "ID", "Name", "Disease", "Sex", "Status", "Age");
                        for (int i = 0; i < 80; i++) System.out.print("-");
                        System.out.println();
                    for (int i = 0; i < patientCount; i++) {
                    	patients[i].showPatientInfo();
                    	for (int j = 0; j < 80; j++) System.out.print("-");
                    	System.out.println();
                    }
                    break;
                }

                default:
                    System.out.println("\n*** Invalid Option: Select [ 1 - 8 ]. ***");
            }
        }
    }

    private static void medicineMenu() {
        while (true) {
            System.out.println("\n------------------------------------------------------");
            System.out.println("                   MEDICINES MENU");
            System.out.println("------------------------------------------------------\n");
            System.out.println(" [1] Register New Medicine\n");
            System.out.println(" [2] View All Medicines\n");
            System.out.println(" [3] Return to Main Menu\n");
            System.out.println(" [4] [ADMIN ONLY] Update Medicine Details (by Name)\n");
            System.out.println(" [5] [ADMIN ONLY] Remove Medicine Record (by Name)\n");
            System.out.println(" [6] Search / Filter Medicines\n");
            System.out.println(" [7] Sort Medicines by Name\n");
            System.out.println(" [8] Sort Medicines by Price\n");
            System.out.println(" [9] Sort Medicines by Stock\n");
            System.out.println(" [10] Alerts (Low Stock / Expiring Soon)\n");
            System.out.println("------------------------------------------------------");
            System.out.print(" OPTION [ 1 - 10 ]: ");
            int c = readInt();

            switch (c) {
                case 1:
                    if (medicineCount < medicines.length) {
                        Medicine m = new Medicine();
                        m.newMedicine();
                        if (medicineNameExists(m.getName())) {
                            System.out.println("\n*** [!] Medicine name already exists. Record not added. ***");
                        } else {
                            medicines[medicineCount++] = m;
                        }
                    } else {
                        System.out.println("\n*** Medicine array is full! Cannot add more. ***");
                    }
                    break;

                case 2:
                    if (medicineCount == 0) {
                        System.out.println("\n*** No medicine records are currently available. ***");
                    } else {
                        System.out.println("\n------------------------------------------------------");
                        System.out.println("                   LIST OF MEDICINES");
                        System.out.println("------------------------------------------------------\n");

                        System.out.printf("%-20s %-18s %-18s %-15s %-5s%n",
                                "Name", "Supplier", "Expiry", "Price [RM]", "Stock");
                        for (int i = 0; i < 84; i++) System.out.print("-");
                        System.out.println();

                        for (int i = 0; i < medicineCount; i++) {
                            medicines[i].findMedicine();
                            for (int j = 0; j < 84; j++) System.out.print("-");
                            System.out.println();
                        }

                        System.out.println("\nTotal number of medicines: " + medicineCount);
                    }
                    break;

                case 3:
                    return;

                case 4: // Update
                    if (medicineCount == 0) { 
                        System.out.println("\n*** No medicine records to update. ***"); 
                        break; 
                    }
                    if (!"admin".equalsIgnoreCase(ROLE)) {
                        System.out.println("\n*** Only ADMIN can update medicines. ***");
                        break;
                    }
                    String nameToUpdate = InputHandling.readNonEmpty(sc, "\n Enter Medicine Name to Update: ");
                    Medicine.update(sc, medicines, medicineCount, ROLE, nameToUpdate);
                    break;

                case 5: // Delete
                    if (medicineCount == 0) { 
                        System.out.println("\n*** No medicine records to delete. ***"); 
                        break; 
                    }
                    if (!"admin".equalsIgnoreCase(ROLE)) {
                        System.out.println("\n*** Only ADMIN can delete medicines. ***");
                        break;
                    }
                    String nameToDelete = InputHandling.readNonEmpty(sc, "\n Enter Medicine Name to Delete: ");
                    int newCount = Medicine.delete(sc, medicines, medicineCount, ROLE, nameToDelete);
                    if (newCount != medicineCount) {
                        medicineCount = newCount;
                    } else {
                        System.out.println("\n*** Deletion failed. Check medicine name. ***");
                    }
                    break;

                case 6: // Search / Filter
                    if (medicineCount == 0) { 
                        System.out.println("\n*** No medicine records to search. ***"); 
                        break; 
                    }
                    System.out.println("\n--- Search / Filter Options ---\n");
                    System.out.println(" [1] Search by Name\n");
                    System.out.println(" [2] Search by Price Range\n");
                    System.out.println(" [3] Search by Stock <= Threshold\n");
                    System.out.println("------------------------------------------------------");
                    System.out.print(" OPTION [ 1 - 3 ]: ");
                    int op = readInt();
                    switch (op) {
                        case 1:
                            String keyword = InputHandling.readNonEmpty(sc, "\n Keyword: ");
                            Medicine.searchByNameContains(medicines, medicineCount, keyword);
                            break;
                        case 2:
                            System.out.print("\nMin Price [RM]: ");
                            int pmin = readInt();
                            System.out.print("\nMax Price [RM]: ");
                            int pmax = readInt();
                            Medicine.searchByPriceRange(medicines, medicineCount, pmin, pmax);
                            break;
                        case 3:
                            System.out.print("\n Threshold: ");
                            int th = readInt();
                            Medicine.searchByStockThreshold(medicines, medicineCount, th);
                            break;
                        default:
                            System.out.println("\n*** Invalid option: Select [ 1 - 3 ] ***");
                    }
                    break;

                case 7: // Sort by Name
                    if (medicineCount == 0) { 
                        System.out.println("\n*** No Medicine Records Available. ***"); 
                        break; 
                    }
                    Medicine.sortByName(medicines, medicineCount);
                    System.out.printf("%-20s %-18s %-18s %-15s %-5s%n",
                            "Name", "Supplier", "Expiry", "Price [RM]", "Stock");
                    for (int i = 0; i < 84; i++) System.out.print("-");
                    System.out.println();
                    for (int i = 0; i < medicineCount; i++) {
                    	medicines[i].findMedicine();
                    	for (int j = 0; j < 84; j++) System.out.print("-");
                        System.out.println();
                    }
                    break;

                case 8: // Sort by Price
                    if (medicineCount == 0) { 
                    	System.out.println("\n*** No Medicine Records Available. ***"); 
                        break; 
                    }
                    Medicine.sortByPrice(medicines, medicineCount);
                    System.out.printf("%-20s %-18s %-18s %-15s %-5s%n",
                            "Name", "Supplier", "Expiry", "Price [RM]", "Stock");
                    for (int i = 0; i < 84; i++) System.out.print("-");
                    System.out.println();
                    for (int i = 0; i < medicineCount; i++) {
                    	medicines[i].findMedicine();
                    	for (int j = 0; j < 84; j++) System.out.print("-");
                        System.out.println();
                    }
                    break;

                case 9: // Sort by Stock
                    if (medicineCount == 0) { 
                    	System.out.println("\n*** No Medicine Records Available. ***"); 
                        break; 
                    }
                    Medicine.sortByStock(medicines, medicineCount);
                    System.out.printf("%-20s %-18s %-18s %-15s %-5s%n",
                            "Name", "Supplier", "Expiry", "Price [RM]", "Stock");
                    for (int i = 0; i < 84; i++) System.out.print("-");
                    System.out.println();
                    for (int i = 0; i < medicineCount; i++) {
                    	medicines[i].findMedicine();
                    	for (int j = 0; j < 84; j++) System.out.print("-");
                        System.out.println();
                    }
                    break;

                case 10: // Alerts
                    if (medicineCount == 0) { 
                    	System.out.println("\n*** No Medicine Records Available. ***");
                        break; 
                    }
                    System.out.print("\n Set a minimum stock level to trigger a low-stock alert: ");
                    int threshold = readInt();
                    int low = Medicine.countLowStock(medicines, medicineCount, threshold);
                    System.out.print("\n Enter the number of months ahead to check for upcoming expiries: ");
                    int months = readInt();
                    int expSoon = Medicine.countExpiringWithinMonths(medicines, medicineCount, months);
                    System.out.println("\n------------------------------------------------------");
                    System.out.println("                        ALERTS");
                    System.out.println("------------------------------------------------------");
                    System.out.println(" \nMedicines below stock threshold : " + low);
                    System.out.println(" \nMedicines expiring within " + months + " month(s): " + expSoon);
                    break;

                default:
                	System.out.println("\n*** Invalid option: Select [ 1 - 10 ] ***");
            }
        }
    }

    private static void labsMenu() {
        while (true) {
            System.out.println("\n------------------------------------------------------");
            System.out.println("                    LABORATORIES MENU");
            System.out.println("------------------------------------------------------\n");
            System.out.println(" [1] Register New Laboratory\n");
            System.out.println(" [2] View All Laboratories\n");
            System.out.println(" [3] Return to Main Menu\n");
            System.out.println(" [4] Update Laboratory Details (by Name)\n");
            System.out.println(" [5] Remove Laboratory Record (by Name)\n");
            System.out.println("------------------------------------------------------");
            System.out.print(" OPTION [ 1 - 5 ]: ");
            int c = readInt();

            switch (c) {
                case 1:
                    if (labCount < labs.length) {
                        Lab l = new Lab();
                        l.newLab();
                        if (labNameExists(l.getLab())) {
                            System.out.println("\n*** [!] Laboratory name already exists. Record not added. ***");
                        } else {
                            labs[labCount++] = l;
                        }
                    } else {
                        System.out.println("\n*** Registration Failed: Maximum Capacity Reached. ***");
                    }
                    break;

                case 2:
                    if (labCount == 0) {
                        System.out.println("\n*** No laboratory records available. ***");
                    } else {
                        System.out.println("\n------------------------------------------------------");
                        System.out.println("                 LIST OF LABORATORIES");
                        System.out.println("------------------------------------------------------\n");
                        System.out.printf("%-24s %-10s%n", "Lab Name", "Cost [RM]");
                        for (int i = 0; i < 34; i++) System.out.print("-");
                        System.out.println();

                        for (int i = 0; i < labCount; i++) {
                            labs[i].labList();
                            for (int j = 0; j < 34; j++) System.out.print("-");
                            System.out.println();
                        }
                        
                        System.out.println("\nTotal Laboratories: " + labCount);
                    }
                    break;

                case 3:
                    return;

                case 4: {
                    if (labCount == 0) {
                        System.out.println("\n*** No Lab Records Available ***");
                        break;
                    }
                    String name = InputHandling.readNonEmpty(sc, "\n Enter Laboratory to Update: ");
                    boolean ok = Lab.updateByName(labs, labCount, name);
                    if (!ok) System.out.println("\n*** Update Unsuccessful: Please Check the Provided Lab Name. ***");
                    break;
                }

                case 5: {
                    if (labCount == 0) {
                    	System.out.println("\n*** No Lab Records Available ***");
                        break;
                    }
                    String name = InputHandling.readNonEmpty(sc, "\n Enter Laboratory to Delete: ");
                    int newCount = Lab.deleteByName(labs, labCount, name);
                    if (newCount != labCount) labCount = newCount;
                    else System.out.println("\n*** Deletion Unsuccessful: Please Check the Provided Lab Name. ***");
                    break;
                }

                default:
                    System.out.println("\n*** Invalid Option: Select [ 1 - 5 ]. ***");
            }
        }
    }

    private static void facilitiesMenu() {
        while (true) {
            System.out.println("\n------------------------------------------------------");
            System.out.println("                    FACILITIES MENU");
            System.out.println("------------------------------------------------------\n");
            System.out.println(" [1] Register New Facility\n");
            System.out.println(" [2] View All Facilities\n");
            System.out.println(" [3] Return to Main Menu\n");
            System.out.println(" [4] Update Facility Details (by Name)\n");
            System.out.println(" [5] Remove Facility Record (by Name)\n");
            System.out.println("------------------------------------------------------");
            System.out.print(" OPTION [ 1 - 5 ]: ");
            int c = readInt();

            switch (c) {
                case 1:
                    if (facilityCount < facilities.length) {
                        Facility f = new Facility();
                        f.newFacility();
                        facilities[facilityCount++] = f;
                    } else {
                        System.out.println("\n*** Registration Failed: Maximum Capacity Reached. ***");
                    }
                    break;

                case 2:
                    if (facilityCount == 0) {
                        System.out.println("\n*** No facility records available. ***");
                    } else {
                        System.out.println("\n------------------------------------------------------");
                        System.out.println("                   LIST OF FACILITIES");
                        System.out.println("------------------------------------------------------\n");
                        System.out.printf("%-30s%n", "Facility Name");
                        for (int i = 0; i < 30; i++) System.out.print("-");
                        System.out.println();

                        for (int i = 0; i < facilityCount; i++) {
                            facilities[i].showFacility();
                            for (int j = 0; j < 30; j++) System.out.print("-");
                            System.out.println();
                        }
                        System.out.println("\nTotal Facilities: " + facilityCount);
                    }
                    break;

                case 3:
                    return;

                case 4: {
                    if (facilityCount == 0) {
                        System.out.println("\n*** No Facility Records Available ***");
                        break;
                    }
                    String name = InputHandling.readNonEmpty(sc, "\nEnter Facility name to update: ");
                    boolean ok = Facility.updateByName(facilities, facilityCount, name);
                    if (!ok) System.out.println("\n*** Update Unsuccessful: Please Check the Provided Facility Name. ***");
                    break;
                }

                case 5: {
                    if (facilityCount == 0) {
                        System.out.println("\n*** No Facility Records Available ***");
                        break;
                    }
                    String name = InputHandling.readNonEmpty(sc, "\nEnter Facility name to delete: ");
                    int newCount = Facility.deleteByName(facilities, facilityCount, name);
                    if (newCount != facilityCount) facilityCount = newCount;
                    else System.out.println("\n*** Deletion Unsuccessful: Please Check the Provided Facility Name. ***");
                    break;
                }

                default:
                    System.out.println("\n*** Invalid Option: Select [ 1 - 5 ]. ***");
            }
        }
    }

    private static void staffMenu() {
        while (true) {
            System.out.println("\n------------------------------------------------------");
            System.out.println("                     STAFF MENU");
            System.out.println("------------------------------------------------------\n");
            System.out.println(" [1] Register New Staff\n");
            System.out.println(" [2] View All Staff\n");
            System.out.println(" [3] Return to Main Menu\n");
            System.out.println(" [4] Update Staff Details (by ID)\n");
            System.out.println(" [5] Remove Staff Record (by ID)\n");
            System.out.println("------------------------------------------------------");
            System.out.print(" OPTION [ 1 - 5 ]: ");
            int c = readInt();

            switch (c) {
	            case 1:
	                if (staffCount < staff.length) {
	                    System.out.println("\n------------------------------------------------------");
	                    System.out.println("                   CHOOSE YOUR ROLE");
	                    System.out.println("------------------------------------------------------\n");
	                    System.out.println(" [1] Nurses\n");
	                    System.out.println(" [2] Pharmacists\n");
	                    System.out.println(" [3] Security\n");
	                    System.out.println("------------------------------------------------------");
	                    System.out.print(" OPTION [ 1 - 3 ]: ");
	                    int roleChoice = readInt();
	
	                    Staff s = null;
	                    switch (roleChoice) {
	                        case 1: s = new Nurses(); break;
	                        case 2: s = new Pharmacists(); break;
	                        case 3: s = new Security(); break;
	                        default:
	                            System.out.println("\n*** Invalid Role. Registration Failed. ***");
	                            break;
	                    }
	
	                    if (s != null) {
	                        s.newStaff();
	                        if (staffIdExists(s.getId())) {
	                            System.out.println("\n*** [!] Staff ID already exists. Record not added. ***");
	                        } else {
	                            staff[staffCount++] = s;
	                        }
	                    }
	
	                } else {
	                    System.out.println("\n*** Registration Failed: Maximum Capacity Reached. ***");
	                }
	                break;


                case 2:
                    if (staffCount == 0) {
                        System.out.println("\n*** No staff records available. ***");
                    } else {
                        System.out.println("\n------------------------------------------------------");
                        System.out.println("                     LIST OF STAFF");
                        System.out.println("------------------------------------------------------\n");
                        System.out.printf("%-8s %-15s %-20s %-8s %-10s%n",
                        	    "ID", "Name", "Designation", "Sex", "Salary [RM]");
                        for (int i = 0; i < 70; i++) System.out.print("-");
                        System.out.println();

                        for (int i = 0; i < staffCount; i++) {
                            staff[i].showStaffInfo();
                            for (int j = 0; j < 70; j++) System.out.print("-");
                            System.out.println();
                        }
                        System.out.println("\nTotal Staff: " + staffCount);
                    }
                    break;

                case 3:
                    return;

                case 4: {
                    if (staffCount == 0) {
                        System.out.println("\n*** No Staff Records Available ***");
                        break;
                    }
                    String id = InputHandling.readNonEmpty(sc, "\nEnter Staff ID to update: ");
                    boolean ok = Staff.updateById(staff, staffCount, id);
                    if (!ok) System.out.println("\n*** Update Unsuccessful: Please Check the Provided Staff ID. ***");
                    break;
                }

                case 5: {
                    if (staffCount == 0) {
                        System.out.println("\n*** No Staff Records Available ***");
                        break;
                    }
                    String id = InputHandling.readNonEmpty(sc, "\nEnter Staff ID to delete: ");
                    int newCount = Staff.deleteById(staff, staffCount, id);
                    if (newCount != staffCount) staffCount = newCount;
                    else System.out.println("\n*** Deletion Unsuccessful: Please Check the Provided Staff ID. ***");
                    break;
                }

                default:
                    System.out.println("\n*** Invalid Option: Select [ 1 - 5 ]. ***");
            }
        }
    }

    /* --------- HELPER METHODS ----------*/
    private static int readInt() {
        while (true) {
            String line = sc.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.print("\n[!] Invalid Input - Please Try Again: ");
            }
        }
    }
}
