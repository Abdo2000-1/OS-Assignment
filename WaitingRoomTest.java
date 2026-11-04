package hospital;

import java.util.concurrent.TimeUnit;

public class WaitingRoomTest {

    public static void main(String[] args) {
        Logger.log("=== Starting Synchronization Unit Test ===");

        testSemaphore();
        testTreatmentRooms();
        testWaitingRoomProducerConsumer();

        Logger.log("=== All Tests Completed Successfully ===");
    }

    private static void testSemaphore() {
        Logger.log("\n--- Testing Semaphore ---");
        Semaphore semaphore = new Semaphore(1);

        try {
            semaphore.acquire();
            Logger.log("[PASS] Semaphore acquired successfully.");

            boolean acquired = semaphore.tryAcquire(100, TimeUnit.MILLISECONDS);
            if (!acquired) {
                Logger.log("[PASS] Timed acquire correctly timed out when no permits available.");
            } else {
                Logger.log("[FAIL] Timed acquire should have failed!");
            }

            semaphore.release();
            Logger.log("[PASS] Semaphore released successfully.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Logger.log("Semaphore test interrupted: " + e.getMessage());
        }
    }

    private static void testTreatmentRooms() {
        Logger.log("\n--- Testing TreatmentRooms (Capacity = 2) ---");
        TreatmentRooms rooms = new TreatmentRooms();

        try {
            boolean room1 = rooms.tryAcquireRoom(1, TimeUnit.SECONDS);
            boolean room2 = rooms.tryAcquireRoom(1, TimeUnit.SECONDS);

            if (room1 && room2) {
                Logger.log("[PASS] Acquired both treatment rooms successfully.");
            }

            boolean room3 = rooms.tryAcquireRoom(500, TimeUnit.MILLISECONDS);
            if (!room3) {
                Logger.log("[PASS] 3rd acquisition attempt correctly timed out (only 2 rooms available).");
            } else {
                Logger.log("[FAIL] Acquired 3rd room when capacity was only 2!");
            }

            rooms.releaseRoom();
            rooms.releaseRoom();
            Logger.log("[PASS] Released treatment rooms.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Logger.log("TreatmentRooms test interrupted: " + e.getMessage());
        }
    }

    private static void testWaitingRoomProducerConsumer() {
        Logger.log("\n--- Testing WaitingRoom Producer-Consumer ---");
        int capacity = 2;
        WaitingRoom waitingRoom = new WaitingRoom(capacity);

        Thread patientThread = new Thread(() -> {
            try {
                Patient p1 = new Patient(101, waitingRoom);
                Logger.log("Patient " + p1.getPatientId() + " trying to enter waiting room...");
                waitingRoom.enter(p1); 
                
                Logger.log("[PASS] Patient " + p1.getPatientId() + " entered waiting room.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread doctorThread = new Thread(() -> {
            try {
                Thread.sleep(200);

                Patient p = waitingRoom.takeNext();
                
                Logger.log("[PASS] Doctor took Patient " + p.getPatientId() + " from waiting room.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        patientThread.start();
        doctorThread.start();

        try {
            patientThread.join();
            doctorThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}