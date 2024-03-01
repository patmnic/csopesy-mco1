package src;

public class Process {
    int ID;
    int arrivalTime;
    int burstTime;
    int waitTime;
    int startTime;
    int endTime;
    int remainingBurstTime;
    boolean completed;

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

    public void print(){
        System.out.printf("P[%d] Start time: %d End time: %d | Waiting time: %d\n", this.ID, this.startTime, this.endTime, this.waitTime);
    }

    public int run(int burst, int start){
        this.startTime = start;
        this.waitTime = start - this.endTime;

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
