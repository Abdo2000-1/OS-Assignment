package hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hospital {
    private final int doctorCount;
    private final int patientCount;
    private final WaitingRoom waitingRoom;
    private final TreatmentRooms treatmentRooms;
    private final List<Patient> patients = new ArrayList<>();
    private final List<Doctor> doctors = new ArrayList<>();

    public Hospital(int waitingRoomCapacity, int doctorCount, int patientCount) {
        this.doctorCount = doctorCount;
        this.patientCount = patientCount;
        this.waitingRoom = new WaitingRoom(waitingRoomCapacity);
        this.treatmentRooms = new TreatmentRooms();
    }

    public void start() throws InterruptedException 
    {

        for (int i = 1; i <= doctorCount; i++) 
        {
            Doctor doctor = new Doctor(i, waitingRoom, treatmentRooms);

            doctors.add(doctor);
        }


        for (int i = 1; i <= patientCount; i++) {
            Patient patient = new Patient(i, waitingRoom);

            patients.add(patient);
        }


        StringBuilder message = new StringBuilder("Patients arriving:\n");

        for (Patient patient : patients) {
            message.append(patient).append(" ");
        }

        Logger.log(message.toString());


        for (Doctor doctor : doctors) 
        {
            doctor.start();
        }

        for (Patient patient : patients) 
        {
            patient.start();
        }


        for (Patient patient : patients)
        {
            patient.join();
        }

   
        for (Doctor doctor : doctors) 
        {
            doctor.requestStop();
        }

        for (Doctor doctor : doctors) 
        {
            doctor.join();
        }

    
        Logger.log("All patients have been treated.");
        Logger.log("Simulation completed.");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Waiting room capacity: ");
        int capacity = input.nextInt();
        System.out.print("Number of doctors: ");
        int doctors = input.nextInt();
        System.out.print("Number of patients: ");
        int patients = input.nextInt();

        Hospital hospital = new Hospital(capacity, doctors, patients);
        try {
            hospital.start();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}