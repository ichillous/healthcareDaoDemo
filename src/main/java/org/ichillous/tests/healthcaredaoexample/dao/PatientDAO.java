package org.ichillous.tests.healthcaredaoexample.dao;

import org.ichillous.tests.healthcaredaoexample.jbdc.DatabaseConnection;
import org.ichillous.tests.healthcaredaoexample.model.Doctor;
import org.ichillous.tests.healthcaredaoexample.model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PatientDAO {
    public void createPatient(Patient patient) throws SQLException {
        String query = "INSERT INTO Patients (FirstName, LastName, DateOfBirth, Email, PhoneNumber) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, patient.getFirstName());
            stmt.setString(2, patient.getLastName());
            stmt.setString(3, patient.getDateOfBirth());
            stmt.setString(4, patient.getEmail());
            stmt.setString(5, patient.getPhoneNumber());
            stmt.executeUpdate();
        }
    }

    public Patient getPatientById(int patientId) throws SQLException {
        String query = "SELECT * FROM Patients WHERE PatientID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patientId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Patient patient = new Patient();
                    patient.setPatientId(rs.getInt("PatientID"));
                    patient.setFirstName(rs.getString("FirstName"));
                    patient.setLastName(rs.getString("LastName"));
                    patient.setDateOfBirth(rs.getString("DateOfBirth"));
                    patient.setEmail(rs.getString("Email"));
                    patient.setPhoneNumber(rs.getString("PhoneNumber"));
                    return patient;
                }
            }
        }
        return null;
    }

    public void updatePatient(Patient patient) throws SQLException {
        String query = "UPDATE Patients SET FirstName = ?, LastName = ?, DateOfBirth = ?, Email = ?, PhoneNumber = ? WHERE PatientID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, patient.getFirstName());
            stmt.setString(2, patient.getLastName());
            stmt.setString(3, patient.getDateOfBirth());
            stmt.setString(4, patient.getEmail());
            stmt.setString(5, patient.getPhoneNumber());
            stmt.setInt(6, patient.getPatientId());
            stmt.executeUpdate();
        }
    }

    public void deletePatient(int patientId) throws SQLException {
        String query = "DELETE FROM Patients WHERE PatientID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patientId);
            stmt.executeUpdate();
        }
    }

    public List<Patient> getAllPatients() throws SQLException {
        String query = "SELECT * FROM Patients";
        List<Patient> patients = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setPatientId(rs.getInt("PatientID"));
                patient.setFirstName(rs.getString("FirstName"));
                patient.setLastName(rs.getString("LastName"));
                patient.setDateOfBirth(rs.getString("DateOfBirth"));
                patient.setEmail(rs.getString("Email"));
                patient.setPhoneNumber(rs.getString("PhoneNumber"));
                patients.add(patient);
            }
        }
        return patients;
    }

    private static void managePatients(PatientDAO patientDAO, Scanner scan) throws SQLException {
        patientDAO = new PatientDAO();
        System.out.println("1. CREATE \n 2. READ \n 3. UPDATE \n 4. DELETE ");
        Scanner sc = new Scanner(System.in);

        int choice = sc.nextInt();
        scan.nextLine();
        try {
            switch (choice) {
                //Create
                case 1:
                    Patient newPatient = new Patient();
                    System.out.print("Enter email: ");
                    newPatient.setEmail(sc.nextLine());
                    System.out.print("Enter first name: ");
                    newPatient.setFirstName(sc.nextLine());
                    System.out.print("Enter last name: ");
                    newPatient.setLastName(sc.nextLine());
                    System.out.print("Enter phone number: ");
                    newPatient.setPhoneNumber(sc.nextLine());
                    patientDAO.createPatient(newPatient);
                    break;
                // READ
                case 2:
                    System.out.print("Enter Patient Id: ");
                    int patientId = sc.nextInt();
                    Patient patient = patientDAO.getPatientById(patientId);
                    if (patient != null) {
                        System.out.println("PatientId: " + patient.getPatientId());
                        System.out.println("Email: " + patient.getEmail());
                        System.out.println("FirstName: " + patient.getFirstName());
                        System.out.println("LastName: " + patient.getLastName());
                        System.out.println("PhoneNumber: " + patient.getPhoneNumber());
                    } else {
                        System.out.println("Patient ID not found ");
                    }
                    break;
                // UPDATE
                case 3:
                    System.out.print("Enter Doctor Id: ");
                    patientId = sc.nextInt();
                    patient = patientDAO.getPatientById(patientId);
                    if (patient != null) {
                        newPatient = new Patient();
                        System.out.print("Enter email: ");
                        newPatient.setEmail(sc.nextLine());
                        System.out.print("Enter first name: ");
                        newPatient.setFirstName(sc.nextLine());
                        System.out.print("Enter last name: ");
                        newPatient.setLastName(sc.nextLine());
                        System.out.print("Enter phone number: ");
                        newPatient.setPhoneNumber(sc.nextLine());
                        patientDAO.updatePatient(newPatient);

                    } else {
                        System.out.println("Doctor ID not found ");
                    }
                    break;
                // DELETE
                case 4:
                    System.out.print("Enter Patient Id: ");
                    patientId = sc.nextInt();
                    patientDAO.deletePatient(patientId);
                    System.out.println("Patient deleted Successfully");
                    break;
            }
        } finally {
            sc.close();
        }
    }


}
