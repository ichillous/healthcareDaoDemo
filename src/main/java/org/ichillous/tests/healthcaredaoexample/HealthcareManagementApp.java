package org.ichillous.tests.healthcaredaoexample;

import org.ichillous.tests.healthcaredaoexample.dao.AppointmentDAO;
import org.ichillous.tests.healthcaredaoexample.dao.DoctorDao;
import org.ichillous.tests.healthcaredaoexample.dao.PatientDAO;
import org.ichillous.tests.healthcaredaoexample.model.Appointment;
import org.ichillous.tests.healthcaredaoexample.model.Doctor;
import org.ichillous.tests.healthcaredaoexample.model.Patient;

import java.sql.SQLException;
import java.util.Scanner;

public class HealthcareManagementApp {
    public static void main(String[] args) throws SQLException{
        PatientDAO patientDAO = new PatientDAO();
        DoctorDao doctorDAO = new DoctorDao();
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        Scanner sc = new Scanner(System.in);
        System.out.println("Select from the following menu options: ");
        System.out.println("1. Manage Patients \n 2. Manage Doctors \n 3. Manage Appointments");

        int choice = sc.nextInt();
        sc.nextLine();
        try{
            switch (choice) {
                case 1:
                    
                    break;
                case 2:
                    break;
                case 3:
                   break;
            }
        } finally {
            sc.close();
        }

    }
}
