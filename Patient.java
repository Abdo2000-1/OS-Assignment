package hospital;
public class Patient extends Thread {

    private enum Outcome { NONE, TREATED, SENT_BACK }
    private final int patientId;
    private final WaitingRoom waitingRoom;
    private final Semaphore outcomeSignal = new Semaphore(0);
    private volatile Outcome outcome = Outcome.NONE;

    public Patient(int patientId, WaitingRoom waitingRoom) {
        this.patientId = patientId;
        this.waitingRoom = waitingRoom;
    }

    @Override
    public void run() {
        try {
            Logger.log(this + " arrived.");

            boolean treated = false;
            while (!treated) {
                enterWaitingRoom();

                outcomeSignal.acquire();
                if (outcome == Outcome.TREATED) {
                    treated = true;
                } else {
                    outcome = Outcome.NONE;
                }
            }

            Logger.log(this + " left.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Non-blocking attempt first; if full, announce it and block until a seat frees up.
    private void enterWaitingRoom() throws InterruptedException {
        if (waitingRoom.tryEnter(this)) {
            Logger.log(this + " entered the waiting room.");
        } else {
            Logger.log("The waiting room is full.");
            Logger.log(this + " is waiting outside for an available seat.");
            waitingRoom.enter(this);           // blocking
            Logger.log(this + " entered the waiting room.");
        }
    }

    public void finishTreatment() {
        outcome = Outcome.TREATED;
        outcomeSignal.release();
    }


    public void sentBack() {
        outcome = Outcome.SENT_BACK;
        outcomeSignal.release();
    }

    public int getPatientId() { return patientId; }

    @Override
    public String toString() { return "P" + patientId; }
}