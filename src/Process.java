package src;

public class Process {
    int ID;
    int arrivalTime;
    int burstTime;
    int waitTime;
    int startTime;
    int previousTime;
    int endTime;
    int remainingBurstTime;
    boolean completed;

    public Process(int ID, int arrivalTime, int burstTime){
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.waitTime = 0;
        this.startTime = 0;
        this.previousTime = 0;
        this.endTime = 0;
        this.remainingBurstTime = burstTime;
        this.completed = false;
    }
}
