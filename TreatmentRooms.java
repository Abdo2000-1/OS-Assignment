package hospital;

import java.util.concurrent.TimeUnit;

// The hospital has exactly two treatment rooms.
public class TreatmentRooms {
    private static final int ROOM_COUNT = 2;
    private final Semaphore roomSemaphore = new Semaphore(ROOM_COUNT);

    public boolean tryAcquireRoom(long timeout, TimeUnit unit) throws InterruptedException {
        // TODO: delegate to the custom semaphore's timed acquire method.
        throw new UnsupportedOperationException("TODO");
    }

    public void releaseRoom() {
        // TODO: release one treatment-room permit.
        throw new UnsupportedOperationException("TODO");
    }
}