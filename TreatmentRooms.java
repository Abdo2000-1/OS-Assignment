package hospital;
import java.util.concurrent.TimeUnit;

public class TreatmentRooms {
    private static final int ROOM_COUNT = 2;
    private final Semaphore roomSemaphore = new Semaphore(ROOM_COUNT);

    public boolean tryAcquireRoom(long timeout, TimeUnit unit) throws InterruptedException {
        return roomSemaphore.tryAcquire(timeout, unit);
    }

    public void releaseRoom() {
        roomSemaphore.release();
    }
}