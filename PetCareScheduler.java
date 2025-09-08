import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;

public class PetCareScheduler {

    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Pet> pets = new HashMap<>();

    public static void main(String[] args) {
        loadPetsFromFile();
        boolean running = true;

        while (running) {
            System.out.println("\n=== Eco-Points Recycling Tracker ===");
            System.out.println("1. Register pets");
            System.out.println("2. Add an Appointment for pet");
            System.out.println("3. Dislpay pet details");
            System.out.println("4. Generate Pet Care Report");
            System.out.println("5. Save and Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerPet();
                    break;
                case "2":
                    addPetAppointment();
                    break;
                case "3":
                    displayPetDetails();
                    break;
                case "4":
                    generatePetCareReport();
                    break;
                case "5":
                    savePetsToFile(); // You can implement this later
                    running = false;
                    System.out.println("Data saved. Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void registerPet() {
        System.out.print("Enter pet ID: ");
        String id = scanner.nextLine().trim();

        if (pets.containsKey(id)) {
            System.out.println("Error: Pet ID already exists.");
            return;
        }

        System.out.print("Enter pet name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter pet species: ");
        String species = scanner.nextLine().trim();

        System.out.print("Enter pet age (in years): ");
        int age = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter pet owner name: ");
        String nameOwner = scanner.nextLine().trim();

        System.out.print("Enter your phone Number (Only digits): ");
        int contactInfo = Integer.parseInt(scanner.nextLine().trim());

        Pet pet = new Pet(id, name, species, age, nameOwner, contactInfo);
        pets.put(id, pet);

        System.out.println("Pet registered successfully on " + pet.getRegistrationDate());
    }

    private static void addPetAppointment() {
        Pet usePet = null;
        System.out.print("Enter ID for the pet you want to book an appointment for: ");
        String id = scanner.nextLine().trim();
        for (Pet pet : pets.values()) {
            if (pet.getId().equals(id)) {
                usePet = pet;
                break;
            }
        }
        if (usePet == null) {
            System.out.println("Error: Pet ID not found.");
            return;
        }
        System.out.print("Enter the type of the appointment\n" +
                         "e.g., vet visit, vaccination, grooming...: ");
        String typeApt = scanner.nextLine().trim();
        try {
            boolean ok = false;
            LocalDate date = null;
            while (!ok) {
                LocalDate today = LocalDate.now();

                System.out.println("Input the date in MM/dd/yyyy format:");
                String datestr = scanner.nextLine();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                date = LocalDate.parse(datestr, dateFormatter);

                if (today.isBefore(date)) {
                    ok = true;
                } else {
                    System.out.println("Invalid appointment date: must be in the future.");
                }
            }
            System.out.println("Input the time in HH:mm:ss format:");
            String timeStr = scanner.nextLine();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime time = LocalTime.parse(timeStr, timeFormatter);
            System.out.println("Add notes about this mood:");
            String notes = scanner.nextLine();
            if (notes.strip().equalsIgnoreCase("")) {
                usePet.getAppointmentsList().add(new Appointment(typeApt, date, time));
            } else {
                usePet.getAppointmentsList().add(new Appointment(typeApt, date, time, notes));
            }
        } catch (Exception e) {
            System.out.println("Incorrect format of date or time. Cannot create mood.\n" + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadPetsFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("pets.ser"))) {
            pets = (Map<String, Pet>) in.readObject();
            System.out.println("Pets data loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    private static void displayPetDetails() {
        if (pets.isEmpty()) {
            System.out.println("No pets registered.");
            return;
        }
        System.out.println("\n=== All Registered Pets ===");
        for (Pet pet : pets.values()) {
            System.out.println(pet);
        }
        System.out.print("\nEnter pet ID to view appointments: ");
        String petId = scanner.nextLine().trim();
        Pet selectedPet = pets.get(petId);

        if (selectedPet == null) {
            System.out.println("Pet not found.");
            return;
        }
        System.out.println("\n=== Appointments for " + selectedPet.getName() + " ===");
        if (selectedPet.getAppointmentsList().isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            for (Appointment apt : selectedPet.getAppointmentsList()) {
                System.out.println(apt);
            }
        }
        System.out.println("\n=== Upcoming Appointments for All Pets ===");
        LocalDate today = LocalDate.now();
        for (Pet pet : pets.values()) {
            for (Appointment apt : pet.getAppointmentsList()) {
                if (apt.getDate().isAfter(today)) {
                    System.out.println("Pet: " + pet.getName() + " → " + apt);
                }
            }
        }
        System.out.println("\n=== Past Appointment History ===");
        for (Pet pet : pets.values()) {
            for (Appointment apt : pet.getAppointmentsList()) {
                if (apt.getDate().isBefore(today)) {
                    System.out.println("Pet: " + pet.getName() + " → " + apt);
                }
            }
        }
    }

    private static void generatePetCareReport() {
        if (pets.isEmpty()) {
            System.out.println("No pets registered.");
            return;
        }
        LocalDate today = LocalDate.now();
        LocalDate oneWeekFromNow = today.plusDays(7);
        LocalDate sixMonthsAgo = today.minusMonths(6);
        // Pets with upcoming appointments in the next 7 days
        System.out.println("\nPets with Upcoming Appointments (Next 7 Days):");
        boolean foundUpcoming = false;
        for (Pet pet : pets.values()) {
            for (Appointment apt : pet.getAppointmentsList()) {
                if (!apt.getDate().isBefore(today) && !apt.getDate().isAfter(oneWeekFromNow)) {
                    System.out.println("Pet: " + pet.getName() +
                                    ", Appointment: " + apt.getAppointmentType() +
                                    ", Date: " + apt.getDate() +
                                    ", Time: " + apt.getTime());
                    foundUpcoming = true;
                }
            }
        }
        if (!foundUpcoming) {
            System.out.println("No upcoming appointments in the next week.");
        }

        // Pets overdue for vet visit (no vet visit in last 6 months)
        System.out.println("\nPets Overdue for Vet Visit (No visit in last 6 months):");
        boolean foundOverdue = false;
        for (Pet pet : pets.values()) {
            boolean hadRecentVetVisit = false;
            for (Appointment apt : pet.getAppointmentsList()) {
                if (apt.getAppointmentType().toLowerCase().contains("vet") &&
                    !apt.getDate().isBefore(sixMonthsAgo)) {
                    hadRecentVetVisit = true;
                    break;
                }
            }
            if (!hadRecentVetVisit) {
                System.out.println("Pet: " + pet.getName() +
                                " (Last vet visit: > 6 months ago or none)");
                foundOverdue = true;
            }
        }
        if (!foundOverdue) {
            System.out.println("All pets are up to date with vet visits.");
        }
    }

    private static void savePetsToFile() {
        try {
            // Create a FileOutputStream to write to the file named "pets.ser"
            ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("pets.ser")
            );
            // Write the entire pets map to the file
            out.writeObject(pets);
            out.close();
            // If successful, no message is printed here — could add confirmation if you like
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }



}