package hospital;

import java.util.concurrent.TimeUnit;

// A doctor consumes patients from the waiting room and treats them when a room is available.
public class Doctor extends Thread {
    private final int doctorId;
    private final WaitingRoom waitingRoom;
    private final TreatmentRooms treatmentRooms;
    private volatile boolean running = true;

    public Doctor(int doctorId, WaitingRoom waitingRoom, TreatmentRooms treatmentRooms) {
        this.doctorId = doctorId;
        this.waitingRoom = waitingRoom;
        this.treatmentRooms = treatmentRooms;
    }

    @Override   
    public void run() {
        // TODO: take a patient; try to acquire a room for 3 seconds;
        // treat and release the room, or return the patient to the waiting room.
        // Use a random treatment duration as required by the assignment.
    }

    public void requestStop() {
        running = false;
        interrupt();
    }

    public int getDoctorId() {
        return doctorId;
    }
}