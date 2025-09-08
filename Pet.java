import java.util.*;
import java.time.LocalDate;
import java.util.List;
import java.io.Serializable;



public class Pet implements Serializable {
    
    private String id;
    private String name;
    private String species;
    private int age;
    private String ownerName;
    private int contactInfo;
    private LocalDate registrationDate;
    private List<Appointment> appointmentsList;

    public Pet(String id, String name, String species, int age, String ownerName, int contactInfo) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.age = age;
        this.ownerName = ownerName;
        this.contactInfo = contactInfo;
        this.registrationDate = LocalDate.now(); // initialise à la date du jour
        this.appointmentsList = new ArrayList<>(); // initialise une liste vide
    }


    //Setter
    public String getId() { return id; }
    public String getName() { return name; }
    public String getSpecies() { return species; }
    public int getAge() { return age; }
    public String getOwnerName() { return ownerName; }
    public int getContactInfo() { return contactInfo; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public List<Appointment> getAppointmentsList() { return appointmentsList; }

    //Getter
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSpecies(String species) {
        this.species = species;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public void setContactInfo(int contactInfo) {
        this.contactInfo = contactInfo;
    }
    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
    public void setAppointmentsList(List<Appointment> appointmentsList) {
        this.appointmentsList = appointmentsList;
    }


    public void addAppointment(Appointment appointment) {
        this.appointmentsList.add(appointment);
    }


    @Override
    public String toString() {
        return "Pet ID: " + id +
            "\nName: " + name +
            "\nSpecies: " + species +
            "\nAge: " + age + " years" +
            "\nOwner: " + ownerName +
            "\nContact: " + contactInfo +
            "\nRegistered on: " + registrationDate +
            "\nAppointments: " + appointmentsList.size() + " scheduled\n";
    }

}