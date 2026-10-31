package hospital;

import java.util.ArrayDeque;
import java.util.Queue;

// The bounded buffer shared by Patient threads and Doctor threads.
public class WaitingRoom {
    private final Queue<Patient> patients = new ArrayDeque<>();
    private final int capacity;

    // Semaphores used for the bounded-buffer synchronization.
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore emptySlots;
    private final Semaphore availablePatients = new Semaphore(0);

    public WaitingRoom(int capacity) {
        this.capacity = capacity;
        this.emptySlots = new Semaphore(capacity);
    }

    public void put(Patient patient) throws InterruptedException {
        // TODO: wait for an empty slot, lock the queue, add patient, unlock, signal availablePatients.
        throw new UnsupportedOperationException("TODO");
    }

    public Patient take() throws InterruptedException {
        // TODO: wait for an available patient, lock the queue, remove patient, unlock, signal emptySlots.
        throw new UnsupportedOperationException("TODO");
    }

    public int getCapacity() {
        return capacity;
    }
}