package hospital;

import java.util.ArrayList;
import java.util.InputMismatchException;
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

    // Keeps asking until the user enters a whole number >= 1.
    private static int readPositiveInt(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = input.nextInt();
                if (value >= 1) {
                    return value;
                }
                System.out.println("Please enter a number >= 1.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a whole number.");
                input.next();   // discard the bad token, otherwise the loop never ends
            }
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int capacity = readPositiveInt(input, "Waiting room capacity: ");
        int doctors  = readPositiveInt(input, "Number of doctors: ");
        int patients = readPositiveInt(input, "Number of patients: ");

        Hospital hospital = new Hospital(capacity, doctors, patients);
        try {
            hospital.start();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}