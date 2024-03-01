package src;

import java.util.*;

public class Algorithms {
    // public boolean isDone(Process[] processes) {
    //     for (Process process : processes) {
    //         if (process.burstTime != 0)
    //             return false;
    //     }
    //     return true;
    // }

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

    public void roundRobin(int roundRobinTime, int processNum, Process[] processes){
        System.out.println("Round Robin Algorithm (RR)");
        int totalTime = 0, totalWaitTime = 0;

        ArrayList<Process> order = new ArrayList<>();
        Collections.addAll(order, processes);
        order.sort(Comparator.comparingInt(o -> o.arrivalTime));

        Queue<Process> queue = new LinkedList<>();

        while (!order.isEmpty() || !queue.isEmpty()) {
            if (queue.isEmpty()) {
                while (!order.isEmpty()){
                    if(order.getFirst().arrivalTime <= totalTime) {
                        queue.add(order.getFirst());
                        order.removeFirst();
                    }
                    else {
                        if (queue.isEmpty()) {
                            totalTime = order.getFirst().arrivalTime;
                        }
                        break;
                    }
                }
            } else {
                Process process = queue.poll();
                int burst = process.run(roundRobinTime, totalTime);
                totalTime += burst;
                totalWaitTime += process.waitTime;
                if (process.burstTime != 0){
                    while (!order.isEmpty()){
                        if(order.getFirst().arrivalTime <= totalTime) {
                            queue.add(order.getFirst());
                            order.removeFirst();
                        }
                        else break;
                    }
                    queue.add(process);
                }
            }
        }
        float average = (float) totalWaitTime /processNum;
        System.out.printf("Average waiting time: %.2f\n", average);
    }
}
