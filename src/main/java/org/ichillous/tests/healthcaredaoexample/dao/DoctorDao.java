package org.ichillous.tests.healthcaredaoexample.dao;

import org.ichillous.tests.healthcaredaoexample.jbdc.DatabaseConnection;
import org.ichillous.tests.healthcaredaoexample.model.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DoctorDao {

    public void createDoctor(Doctor doctor) throws SQLException {
        String query = "INSERT INTO Doctors (doctorId, email, firstName, lastName, specialty) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()){
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, doctor.getDoctorId());
            ps.setString(2, doctor.getEmail());
            ps.setString(3, doctor.getFirstName());
            ps.setString(4, doctor.getLastName());
            ps.setString(5, doctor.getSpecialty());
            ps.executeUpdate();
        }
    }


    public Doctor getDoctor(int id) throws SQLException {
        String query = "SELECT * FROM Doctors WHERE DoctorId = ?";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)
            ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Doctor doctor = new Doctor();
                    doctor.setDoctorId(rs.getInt("doctorId"));
                    doctor.setEmail(rs.getString("email"));
                    doctor.setFirstName(rs.getString("firstName"));
                    doctor.setLastName(rs.getString("lastName"));
                    doctor.setSpecialty(rs.getString("specialty"));
                    return doctor;
                }
            }
            return null;
        }
    };

    public void updateDoctor(Doctor doctor) throws SQLException {
        String query = "UPDATE Doctors SET email=?, firstName = ?, lastName = ?, specialty = ? WHERE doctorId = ?";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)
            ){
            ps.setString(1, doctor.getEmail());
            ps.setString(2, doctor.getFirstName());
            ps.setString(3, doctor.getLastName());
            ps.setString(4, doctor.getSpecialty());
            ps.setInt(5, doctor.getDoctorId());
            ps.executeUpdate();
        }
    }

    public void deleteDoctor(int id) throws SQLException {
        String query = "DELETE FROM Doctors WHERE doctorId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(query) ){
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public static List<Doctor> getDoctors() throws SQLException {
        String query = "SELECT * FROM Doctors";
        List<Doctor> doctors = new ArrayList<>(); // Creating a list of doctors
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()
            ) {
            while(rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setDoctorId(rs.getInt("DoctorId"));
                doctor.setEmail(rs.getString("Email"));
                doctor.setFirstName(rs.getString("FirstName"));
                doctor.setLastName(rs.getString("LastName"));
                doctor.setSpecialty(rs.getString("Specialty"));
                doctors.add(doctor); // adding doctors to line 77
            }
        }
        return doctors;
    }

    public static void manageDoctors(DoctorDao doctorDao, Scanner scan) throws SQLException {
        doctorDao = new DoctorDao();
        System.out.println("1. CREATE \n 2. READ \n 3. UPDATE \n 4. DELETE ");
        Scanner sc = new Scanner(System.in);

        int choice = sc.nextInt();
        sc.nextLine();
        try {
            switch (choice) {
                //Create
                case 1:
                    Doctor newDoctor = new Doctor();
                    System.out.print("Enter email: ");
                    newDoctor.setEmail(sc.nextLine());
                    System.out.print("Enter first name: ");
                    newDoctor.setFirstName(sc.nextLine());
                    System.out.print("Enter last name: ");
                    newDoctor.setLastName(sc.nextLine());
                    System.out.print("Enter specialty: ");
                    newDoctor.setSpecialty(sc.nextLine());
                    doctorDao.createDoctor(newDoctor);
                    break;
                // READ
                case 2:
                    System.out.print("Enter Doctor Id: ");
                    int doctorId = sc.nextInt();
                    Doctor doctor = doctorDao.getDoctor(doctorId);
                    if (doctor != null) {
                        System.out.println("DoctorId: " + doctor.getDoctorId());
                        System.out.println("Email: " + doctor.getEmail());
                        System.out.println("FirstName: " + doctor.getFirstName());
                        System.out.println("LastName: " + doctor.getLastName());
                        System.out.println("Specialty: " + doctor.getSpecialty());
                    } else {
                        System.out.println("Doctor ID not found ");
                    }
                    break;
                // UPDATE
                case 3:
                    System.out.print("Enter Doctor Id: ");
                    doctorId = sc.nextInt();
                    doctor = doctorDao.getDoctor(doctorId);
                    if (doctor != null) {
                        System.out.print("Enter email: ");
                        doctor.setEmail(sc.nextLine());
                        System.out.print("Enter first name: ");
                        doctor.setFirstName(sc.nextLine());
                        System.out.print("Enter last name: ");
                        doctor.setLastName(sc.nextLine());
                        System.out.print("Enter specialty: ");
                        doctor.setSpecialty(sc.nextLine());
                        doctorDao.updateDoctor(doctor);

                    } else {
                        System.out.println("Doctor ID not found ");
                    }
                    break;
                // DELETE
                case 4:
                    System.out.print("Enter Doctor Id: ");
                    doctorId = sc.nextInt();
                    doctorDao.deleteDoctor(doctorId);
                    System.out.println("Doctor deleted Successfully");
                    break;
            }
        } finally {
            sc.close();
        }
    }

}
