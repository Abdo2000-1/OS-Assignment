package hospital;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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
        Random random = new Random();
        while (running) {
            try {
                Patient patient = waitingRoom.takeNext();
                
                Logger.log("Doctor " + doctorId + " is assigned to " + patient + ".");
                Logger.log("Doctor " + doctorId + " is trying to acquire a treatment room.");

                boolean acquired = treatmentRooms.tryAcquireRoom(3, TimeUnit.SECONDS);

                if (acquired) {
                    Logger.log("Doctor " + doctorId + " acquired a treatment room.");
                    Logger.log("Doctor " + doctorId + " begins treating " + patient + ".");

                    int treatmentTime = 1000 + random.nextInt(4000);
                    Thread.sleep(treatmentTime);

                    Logger.log("Doctor " + doctorId + " finished treating " + patient + ".");
                    treatmentRooms.releaseRoom();
                    Logger.log("Doctor " + doctorId + " released the treatment room.");
                    
                    patient.finishTreatment();
                } else {
                    Logger.log("Doctor " + doctorId + " could not acquire a treatment room.");
                    Logger.log("Doctor " + doctorId + " is sending " + patient + " back to the waiting room.");
                    
                    patient.sentBack();
                }

                Logger.log("Doctor " + doctorId + " is now available.");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void requestStop() {
        running = false;
        interrupt();
    }

    public int getDoctorId() {
        return doctorId;
    }
}