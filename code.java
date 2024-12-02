import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Patient {
    private String id;
    private String name;
    private int age;
    private String ailment;
    private String assignedDoctor;

    public Patient(String id, String name, int age, String ailment, String assignedDoctor) {
        this.id = id.toUpperCase(); // Ensure IDs are stored in uppercase for consistent comparison
        this.name = name;
        this.age = age;
        this.ailment = ailment;
        this.assignedDoctor = assignedDoctor;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAilment() {
        return ailment;
    }

    public String getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAilment(String ailment) {
        this.ailment = ailment;
    }

    public void setAssignedDoctor(String assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age + ", Ailment: " + ailment + ", Doctor: " + assignedDoctor;
    }
}

public class PatientRecordSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Patient> patients = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome to the Patient Record Management System!");
        while (true) {
            System.out.println("\nAre you an Admin or Patient?");
            System.out.print("Enter 'Admin', 'Patient', or 'Exit' to quit: ");
            String role = scanner.nextLine().trim().toLowerCase();

            switch (role) {
                case "admin":
                    adminMenu();
                    break;
                case "patient":
                    patientMenu();
                    break;
                case "exit":
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }

    // Admin Menu
    private static void adminMenu() {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        if (username.equals("admin") && password.equals("admin123")) {
            System.out.println("Admin logged in successfully.");
            int choice;
            do {
                System.out.println("\n=== Admin Menu ===");
                System.out.println("1. Add Patient Record");
                System.out.println("2. View All Patients");
                System.out.println("3. Update Patient Information");
                System.out.println("4. Delete a Patient Record");
                System.out.println("5. Delete All Patient Records");
                System.out.println("6. Count Patient Records");
                System.out.println("7. Export Data to CSV");
                System.out.println("8. Logout");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> addPatient();
                    case 2 -> viewAllPatients();
                    case 3 -> updatePatient();
                    case 4 -> deletePatient();
                    case 5 -> deleteAllPatients();
                    case 6 -> countPatients();
                    case 7 -> exportDataToCSV();
                    case 8 -> {
                        System.out.println("Logging out...");
                        switchToPatient();
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } while (choice != 8);
        } else {
            System.out.println("Invalid credentials. Exiting Admin Menu...");
        }
    }

    // Switch to Patient Menu after Admin Logout
    private static void switchToPatient() {
        System.out.print("\nWould you like to switch to Patient mode? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            patientMenu();
        } else {
            System.out.println("Returning to main menu...");
        }
    }

    // Patient Menu
    private static void patientMenu() {
        System.out.print("Enter Patient ID to view details: ");
        String id = scanner.nextLine().toUpperCase(); // Convert input to uppercase for case-insensitive matching
        Patient patient = findPatientById(id);

        if (patient != null) {
            System.out.println("Patient Details:");
            System.out.println(patient);
        } else {
            System.out.println("Patient record not found.");
        }
    }

    // Admin Features
    private static void addPatient() {
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine().toUpperCase();
        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Ailment: ");
        String ailment = scanner.nextLine();
        System.out.print("Enter Assigned Doctor: ");
        String assignedDoctor = scanner.nextLine();

        patients.add(new Patient(id, name, age, ailment, assignedDoctor));
        System.out.println("Patient added successfully.");
    }

    private static void viewAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patient records available.");
        } else {
            System.out.println("\nAll Patients:");
            for (Patient patient : patients) {
                System.out.println(patient);
            }
        }
    }

    private static void updatePatient() {
        System.out.print("Enter Patient ID to update: ");
        String id = scanner.nextLine().toUpperCase();
        Patient patient = findPatientById(id);

        if (patient != null) {
            System.out.print("Enter new name (leave blank to keep unchanged): ");
            String name = scanner.nextLine();
            if (!name.isBlank()) patient.setName(name);

            System.out.print("Enter new age (-1 to keep unchanged): ");
            int age = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (age != -1) patient.setAge(age);

            System.out.print("Enter new ailment (leave blank to keep unchanged): ");
            String ailment = scanner.nextLine();
            if (!ailment.isBlank()) patient.setAilment(ailment);

            System.out.print("Enter new doctor (leave blank to keep unchanged): ");
            String doctor = scanner.nextLine();
            if (!doctor.isBlank()) patient.setAssignedDoctor(doctor);

            System.out.println("Patient record updated successfully.");
        } else {
            System.out.println("Patient not found.");
        }
    }

    private static void deletePatient() {
        System.out.print("Enter Patient ID to delete: ");
        String id = scanner.nextLine().toUpperCase();
        Patient patient = findPatientById(id);

        if (patient != null) {
            patients.remove(patient);
            System.out.println("Patient deleted successfully.");
        } else {
            System.out.println("Patient not found.");
        }
    }

    private static void deleteAllPatients() {
        patients.clear();
        System.out.println("All patient records deleted.");
    }

    private static void countPatients() {
        System.out.println("Number of patient records: " + patients.size());
    }

    private static void exportDataToCSV() {
        try (FileWriter writer = new FileWriter("Patients.csv")) {
            writer.write("ID,Name,Age,Ailment,Doctor\n");
            for (Patient patient : patients) {
                writer.write(patient.getId() + "," + patient.getName() + "," + patient.getAge() + "," + patient.getAilment() + "," + patient.getAssignedDoctor() + "\n");
            }
            System.out.println("Patient data exported to Patients.csv.");
        } catch (IOException e) {
            System.out.println("Error exporting data: " + e.getMessage());
        }
    }

    // Utility Method
    private static Patient findPatientById(String id) {
        for (Patient patient : patients) {
            if (patient.getId().equals(id)) {
                return patient;
            }
        }
        return null;
    }
}
