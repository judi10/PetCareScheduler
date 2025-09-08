import java.time.LocalDate;
import java.time.LocalTime;
import java.io.Serializable;


public class Appointment implements Serializable {
    private String appointmentType;
    private LocalDate date;
    private LocalTime time;
    private String notes;

    public Appointment(String appointmentType, LocalDate date, LocalTime time){
        this.appointmentType = appointmentType;
        this.date = date;
        this.time = time;
    }

    public Appointment(String appointmentType, LocalDate date, LocalTime time, String notes){
        this.appointmentType = appointmentType;
        this.date = date;
        this.time = time;
        this.notes = notes;
    }


    //Setters
    public void setAppointmentType(String appointmentType){
        this.appointmentType = appointmentType;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public void setTime(LocalTime time){
        this.time = time;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }


    //Getters
    public String getAppointmentType(){
        return appointmentType;
    }

    public LocalDate getDate(){
        return date;
    }

    public LocalTime getTime(){
        return time;
    }

    public String getNotes(){
        return notes;
    }

}