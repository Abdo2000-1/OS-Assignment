package hospital;

import java.util.concurrent.CountDownLatch;

// A patient arrives, enters (or waits for) the waiting room, and leaves after treatment.
public class Patient extends Thread {
    private final int patientId;
    private final WaitingRoom waitingRoom;
    private final CountDownLatch treatmentFinished = new CountDownLatch(1);

    public Patient(int patientId, WaitingRoom waitingRoom) {
        this.patientId = patientId;
        this.waitingRoom = waitingRoom;
    }

    @Override
    public void run() {
        // TODO: print arrival, call waitingRoom.put(this), wait for treatment, then print leaving.
    }

    public void finishTreatment() {
        treatmentFinished.countDown();
    }

    public void waitForTreatment() throws InterruptedException {
        treatmentFinished.await();
    }

    public int getPatientId() {
        return patientId;
    }

    @Override
    public String toString() {
        return "P" + patientId;
    }
}