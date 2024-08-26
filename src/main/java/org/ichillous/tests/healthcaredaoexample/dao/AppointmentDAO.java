package org.ichillous.tests.healthcaredaoexample.dao;
import org.ichillous.tests.healthcaredaoexample.jbdc.DatabaseConnection;
import org.ichillous.tests.healthcaredaoexample.model.Appointment;

import java.sql.*;
import java.util.*;


public class AppointmentDAO {
    public void createAppointment(Appointment appt) throws SQLException {
        String query = "INSERT INTO Appointments (appointmentID, patientID, doctorID, appointmentDate, notes) VALUES (?, ?, ?, ?, ?)";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)
            ){
            ps.setInt(1, appt.getAppointmentID());
            ps.setInt(2, appt.getPatientID());
            ps.setInt(3, appt.getDoctorID());
            ps.setString(4, appt.getAppointmentDate());
            ps.setString(5, appt.getNotes());
            ps.executeUpdate();
        }
    }

    public Appointment getAppointmentbyId(int appointmentID) throws SQLException {
        String query = "SELECT * FROM Appointments WHERE appointmentID = ?";
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)
            ){
            ps.setInt(1, appointmentID);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Appointment appt = new Appointment();
                    appt.setAppointmentID(rs.getInt("AppointmentID"));
                    appt.setPatientID(rs.getInt("PatientID"));
                    appt.setDoctorID(rs.getInt("DoctorID"));
                    appt.setAppointmentDate(rs.getString("AppointmentDate"));
                    appt.setNotes(rs.getString("Notes"));
                }
            }
        }
        return null;
    }

    public void updateAppointment(Appointment appt) throws SQLException {
        String query = "UPDATE Appointments SET PatientId=?, DoctorId=?, AppointmentDate=?, Notes=? WHERE AppointmentID = ?";
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)
        ){
            ps.setInt(1, appt.getPatientID());
            ps.setInt(2, appt.getDoctorID());
            ps.setString(3, appt.getAppointmentDate());
            ps.setString(4, appt.getNotes());
            ps.setInt(5, appt.getAppointmentID());
            ps.executeUpdate();
        }
    }

    public void deleteAppointment(int appointmentID) throws SQLException {
        String query = "DELETE FROM Appointments WHERE AppointmentID = ?";
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)
        )
        {
            ps.setInt(1, appointmentID);
            ps.executeUpdate();
        }
    }

    public static List<Appointment> getAllAppointments() throws SQLException{
        String query = "SELECT * FROM Appointment";
        List<Appointment> appointments = new ArrayList<>();
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
        )
        {
            while (rs.next()) {
                Appointment appt = new Appointment();
                appt.setAppointmentID(rs.getInt("AppointmentID"));
                appt.setPatientID(rs.getInt("PatientID"));
                appt.setDoctorID(rs.getInt("DoctorID"));
                appt.setAppointmentDate(rs.getString("AppointmentDate"));
                appt.setNotes(rs.getString("Notes"));
                appointments.add(appt);
            }
        }
        return appointments;
    }

    private static void manageAppointments(AppointmentDAO appointmentDao, Scanner scan) throws SQLException {
        appointmentDao = new AppointmentDAO();
        Appointment appointment = new Appointment();
        System.out.println("1. CREATE \n 2. READ \n 3. UPDATE \n 4. DELETE ");
        Scanner sc = new Scanner(System.in);

        int choice = sc.nextInt();
        scan.nextLine();
        try {
            switch (choice) {
                //Create
                case 1:
                    Appointment newAppointment = new Appointment();
                    System.out.print("Enter AppointmentID: ");
                    newAppointment.setAppointmentID(sc.nextInt());
                    System.out.print("Enter PatientID: ");
                    newAppointment.setPatientID(sc.nextInt());
                    System.out.print("Enter DoctorID: ");
                    newAppointment.setDoctorID(sc.nextInt());
                    System.out.print("Enter Appointment Date: ");
                    newAppointment.setAppointmentDate(sc.nextLine());
                    System.out.print("Enter Notes: ");
                    newAppointment.setNotes(sc.nextLine());
                    appointmentDao.createAppointment(newAppointment);
                    break;
                // READ
                case 2:
                    System.out.print("Enter Doctor Id: ");
                    int appointmentId = sc.nextInt();
                    Appointment apptUp = appointmentDao.getAppointmentbyId(appointmentId);
                    if (apptUp != null) {
                        System.out.println("AppointmentID : " + apptUp.getAppointmentID());
                        System.out.println("PatientID: " + apptUp.getPatientID());
                        System.out.println("DoctorID: " + apptUp.getDoctorID());
                        System.out.println("ApoointmentDate" + apptUp.getAppointmentDate());
                        System.out.println("Notes: " + apptUp.getNotes());
                        appointmentDao.updateAppointment(apptUp);
                    } else {
                        System.out.println("Appointment ID not found ");
                    }
                    break;
                // UPDATE
                case 3:
                    System.out.print("Enter Doctor Id: ");
                    appointmentId = sc.nextInt();
                    appointment = appointmentDao.getAppointmentbyId(appointmentId);
                    if (appointment != null) {
                        System.out.print("Enter AppointmentID: ");
                        appointment.setAppointmentID(sc.nextInt());
                        System.out.print("Enter PatientID: ");
                        appointment.setPatientID(sc.nextInt());
                        System.out.print("Enter DoctorID: ");
                        appointment.setDoctorID(sc.nextInt());
                        System.out.print("Enter Appointment Date: ");
                        appointment.setAppointmentDate(sc.nextLine());
                        System.out.print("Enter Notes: ");
                        appointment.setNotes(sc.nextLine());
                        appointmentDao.updateAppointment(appointment);

                    } else {
                        System.out.println("Doctor ID not found ");
                    }
                    break;
                // DELETE
                case 4:
                    System.out.print("Enter Apppointment ID: ");
                    appointmentId = sc.nextInt();
                    appointmentDao.deleteAppointment(appointmentId);
                    System.out.println("Appointment deleted Successfully");
                    break;
            }
        } finally {
            sc.close();
        }
    }


}
