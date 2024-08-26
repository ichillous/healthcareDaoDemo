package org.ichillous.tests.healthcaredaoexample.model;


public class Appointment {

    private int appointmentID;
    private int patientID;
    private int doctorID;
    private String appointmentDate;
    private String notes;

    public Appointment(){}
    public Appointment(int appointmentID, int patientID, int doctorID, String appointmentDate, String notes) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentDate = appointmentDate;
        this.notes = notes;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public int getPatientID() {
        return patientID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }


    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentDate='" + appointmentDate + '\'' +
                ", appointmentID=" + appointmentID +
                ", patientID=" + patientID +
                ", doctorID=" + doctorID +
                ", notes='" + notes + '\'' +
                '}';
    }
}
