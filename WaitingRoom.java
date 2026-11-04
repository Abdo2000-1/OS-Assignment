package hospital;

import java.util.ArrayDeque;
import java.util.Queue;

public class WaitingRoom {
    private final Queue<Patient> patients;
    private final int capacity;

    private final Semaphore emptySlots;
    private final Semaphore availablePatients;
    private final Semaphore mutex;

    public WaitingRoom(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.capacity = capacity;
        this.patients = new ArrayDeque<>();
        this.emptySlots = new Semaphore(capacity);
        this.availablePatients = new Semaphore(0);
        this.mutex = new Semaphore(1);
    }

    public boolean tryEnter(Patient patient) throws InterruptedException {
        if (!emptySlots.tryAcquire()) {
            return false;
        }

        mutex.acquire();

        try{
            patients.add(patient);
        } finally {
            mutex.release();
        }
        availablePatients.release();
        return true;
    }

    public void enter(Patient patient) throws InterruptedException {
        emptySlots.acquire();
        mutex.acquire();
        try {
            patients.add(patient);
        } finally {
            mutex.release();
        }
        availablePatients.release();
    }

    public Patient takePatient() throws InterruptedException {
        availablePatients.acquire();
        mutex.acquire();
        Patient patient = null;
        try{
            patient = patients.poll();
        }finally{
            mutex.release();
        }
        emptySlots.release();
        return patient;
    }

    public int getCapacity(){
        return capacity;
    }
} 