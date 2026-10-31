package hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Creates and connects the shared resources, patients, and doctors.
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

    public void start() throws InterruptedException {
        // TODO: create and start doctor and patient threads; wait for patients;
        // stop and join doctors; print the final simulation message.
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