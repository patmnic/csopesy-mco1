package src;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

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

    public void shortestRemainingTimeFirst(int processNum, Process[] processes) {
        System.out.println("Shortest Remaining Time First Algorithm (SRTF)");

        int currentTime = 0;
        int completedProcesses = 0;
        int totalWaitTime = 0;
        int currentProcessIndex = -1; // Index of currently executing process

        List<Process> completedList = new ArrayList<>();
        StringBuilder ganttChart = new StringBuilder();

        while (completedProcesses < processNum) {
            Process shortestJob = null;
            int shortestTime = Integer.MAX_VALUE;
            // CURRENT PROBLEM, NO PRINTING WHEN SWITCHING PROCESSES AND MISPRINT OF SWITCHED PROCESSES
            // Find the process with the shortest remaining burst time at the current time
            for (int i = 0; i < processNum; i++) {
                Process process = processes[i];
                if (!process.completed && process.arrivalTime <= currentTime && process.remainingBurstTime < shortestTime) {
                    shortestJob = process;
                    shortestTime = process.remainingBurstTime;
                }
            }

            if (shortestJob != null) {
                if (currentProcessIndex != -1 && currentProcessIndex != shortestJob.ID) {
                    ganttChart.append("| ");
                }
                ganttChart.append("P[").append(shortestJob.ID).append("] ");
                shortestJob.remainingBurstTime--;

                if (shortestJob.remainingBurstTime == 0) {
                    shortestJob.endTime = currentTime + 1;
                    shortestJob.completed = true;
                    completedProcesses++;
                    totalWaitTime += currentTime + 1 - shortestJob.arrivalTime - shortestJob.burstTime;
                    completedList.add(shortestJob);
                }

                // Update start time if the process just started executing
                if (shortestJob.startTime == 0) {
                    shortestJob.startTime = currentTime;
                }

                currentProcessIndex = shortestJob.ID;
            } else {
                ganttChart.append("| ");
            }

            currentTime++;
        }

        // Print Gantt chart (DOUBLE CHECKING, REMOVE THIS LATER)
        System.out.println("Gantt Chart:");
        System.out.println(ganttChart.toString());
        System.out.println("");

        // Print process details
        for (Process process : completedList) {
            System.out.println("P[" + process.ID +
                    "] Start Time: " + process.startTime +
                    " End Time: " + process.endTime +
                    " Waiting Time: " + (process.startTime - process.arrivalTime));
        }

        // Calculate and print average waiting time
        float avgWaitTime = (float) totalWaitTime / processNum;
        System.out.printf("Average Waiting Time: %.2f\n", avgWaitTime);
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
                    if(order.get(0).arrivalTime <= totalTime) {
                        queue.add(order.get(0));
                        order.remove(0);
                    }
                    else {
                        if (queue.isEmpty()) {
                            totalTime = order.get(0).arrivalTime;
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
                        if(order.get(0).arrivalTime <= totalTime) {
                            queue.add(order.get(0));
                            order.remove(0);
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
