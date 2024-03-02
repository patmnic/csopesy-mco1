package src;

public class Process {
    int ID; // ID of process
    int arrivalTime; // when did process arrive
    int burstTime; // CPU burst of process
    int waitTime; // wait time of process
    int startTime; // when did the process run in the CPU
    int endTime; // when did the process finish
    int remainingBurstTime;
    boolean completed; // complete = true or incomplete = false

    public Process(int ID, int arrivalTime, int burstTime){
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.waitTime = 0;
        this.startTime = 0;
        this.endTime = arrivalTime;
        this.remainingBurstTime = burstTime;
        this.completed = false;
    }

    public Process(Process process){
        this.ID = process.ID;
        this.arrivalTime = process.arrivalTime;
        this.burstTime = process.burstTime;
        this.waitTime = process.waitTime;
        this.startTime = process.startTime;
        this.endTime = process.endTime;
        this.remainingBurstTime = process.remainingBurstTime;
        this.completed = process.completed;
    }

    public void print(){
        System.out.printf("P[%d] Start time: %d End time: %d | Waiting time: %d\n", this.ID, this.startTime, this.endTime, this.waitTime);
    }

    public int run(int burst, int start){
        this.startTime = start;
        this.waitTime += start - this.endTime;

        if (this.burstTime <= burst) {
            burst = burstTime;
            this.endTime = start + this.burstTime;
            this.burstTime = 0;
        } else {
            this.endTime = start + burst;
            this.burstTime -= burst;
        }
        this.print();
        return burst;
    }
}
