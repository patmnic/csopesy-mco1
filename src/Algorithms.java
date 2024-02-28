package src;

public class Algorithms {
    public void firstComeFirstServe(int processNum, Process[] processes){
        System.out.println("First Come First Serve Algorithm (FCFS)");
        int totalTime = 0, totalWaitTime = 0;
        float avgTime = 0;
        processes[0].endTime = processes[0].burstTime;
        totalTime = processes[0].burstTime;

        for(int i = 1; i < processNum; i++){ // start, end, waiting time for process
            processes[i].startTime = totalTime;
            processes[i].waitTime = processes[i].startTime - processes[i].arrivalTime;
            processes[i].endTime = processes[i].startTime + processes[i].burstTime;
            totalTime = processes[i].endTime;
        }

        for(int j = 0; j < processNum; j++){ // total wait time
            totalWaitTime = totalWaitTime + processes[j].waitTime;
        }
        avgTime = (float) totalWaitTime / processNum; // average time

        for(int k = 0; k < processNum; k++){ // print process
            System.out.print("P[" + processes[k].ID + "] ");
            System.out.print("Start time: " + processes[k].startTime + " ");
            System.out.print("End time: " + processes[k].endTime + " | ");
            System.out.println("Waiting time: " + processes[k].waitTime);
        }
        System.out.printf("Average waiting time: %.2f", avgTime);
    }



    public void shortestJobFirst(){
        System.out.println("Shortest Job First Algorithm (SJF)");
    }

    public void shortestRemainingTimeFirst(){
        System.out.println("Shortest Remaining Time First Algorithm (SRTF)");
    }

    public void roundRobin(){
        System.out.println("Round Robin Algorithm (RR)");
    }
}
