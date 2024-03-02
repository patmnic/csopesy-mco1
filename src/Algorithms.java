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

        Process tempProcess = new Process(0,0,0); // selection sort by arrival time
        for(int i = 0; i < processNum - 1; i++){ // sort by arrival time
            int minimum = i;
            for(int j = i + 1; j < processNum; j++){
                if(processes[j].arrivalTime < processes[minimum].arrivalTime){
                    minimum = j;
                }
            }

            tempProcess = processes[minimum];
            processes[minimum] = processes[i];
            processes[i] = tempProcess;
        }

        int totalTime = processes[0].burstTime;
        processes[0].endTime = processes[0].burstTime;

        for(int i = 1; i < processNum; i++){ // start, end, waiting time for process
            while(totalTime < processes[i].arrivalTime){
                totalTime++;
                processes[i].waitTime++;
            }
            processes[i].startTime = totalTime;
            processes[i].waitTime = processes[i].startTime - processes[i].arrivalTime;
            processes[i].endTime = processes[i].startTime + processes[i].burstTime;
            totalTime = processes[i].endTime;
        }

        printResults(processNum, processes);
    }



    public void shortestJobFirst(int processNum, Process[] processes){
        System.out.println("Shortest Job First Algorithm (SJF)");
        int totalTime = 0;
        boolean nonZero = false; // check if all processes have zero arrival time (true) or not (false)

        Process tempProcess = new Process(0,0,0); // for sorting
        for(int i = 0; i < processNum - 1; i++){ // sort by arrival time
            int minimum = i;
            for(int j = i + 1; j < processNum; j++){
                if(processes[j].arrivalTime < processes[minimum].arrivalTime){
                    minimum = j;
                }
                else if(processes[j].arrivalTime == processes[minimum].arrivalTime && processes[j].ID < processes[minimum].ID){
                    minimum = j;
                }
            }

            tempProcess = processes[minimum];
            processes[minimum] = processes[i];
            processes[i] = tempProcess;
        }

        // check if all processes have zero arrival time or if there is at least one nonzero arrival time
        for(int i = 0; i < processNum; i++){
            if (processes[i].arrivalTime != 0) {
                nonZero = true;
                break;
            }
        }

        if(nonZero){ // allow 0 arrival time process to start immediately
            processes[0].startTime = totalTime;
            processes[0].endTime = processes[0].startTime + processes[0].burstTime;
            processes[0].waitTime = processes[0].startTime - processes[0].arrivalTime;
            totalTime = processes[0].endTime;
            processes[0].completed = true;
        }

        for(int i = 0; i < processNum; i++){ // start, end, waiting time for process
            if(nonZero && i == 0){
                i++;
            }
            if(nonZero) {
                for (int j = i; j < processNum - 1; j++) { // selection sort algorithm according to burst
                    int minimum = j;
                    for (int k = j + 1; k < processNum; k++) {
                        if ((!processes[k].completed && processes[k].arrivalTime < totalTime) && ((processes[k].burstTime < processes[minimum].burstTime) || // if burst time is smaller, and the process has not yet been completed, set minimum to k
                                (processes[k].burstTime == processes[minimum].burstTime && processes[k].ID < processes[minimum].ID))) { // or same burst time but lower ID
                            minimum = k;
                        }
                    }
                    tempProcess = processes[minimum];
                    processes[minimum] = processes[j];
                    processes[j] = tempProcess;
                }
            }
            else{ // for all zero arrival time
                for (int j = 0; j < processNum - 1; j++) { // selection sort algorithm according to burst
                    int minimum = j;

                    for (int k = j + 1; k < processNum; k++) {
                        if (!processes[k].completed && ((processes[k].burstTime < processes[minimum].burstTime) ||
                                (processes[k].burstTime == processes[minimum].burstTime && processes[k].ID < processes[minimum].ID))) {
                            minimum = k; // change minimum to k
                        }
                    }
                    tempProcess = processes[minimum];
                    processes[minimum] = processes[j];
                    processes[j] = tempProcess;
                }
            }

            while(totalTime < processes[i].arrivalTime){ // in case there is no process available at the time
                totalTime++;
                processes[i].waitTime++;
            }

            processes[i].startTime = totalTime;
            processes[i].waitTime = processes[i].startTime - processes[i].arrivalTime;
            processes[i].endTime = processes[i].startTime + processes[i].burstTime;
            totalTime = processes[i].endTime;
            processes[i].completed = true;
        }

        printResults(processNum, processes);
    }

    public void printResults(int processNum, Process[] processes){
        int totalWaitTime = 0;
        float avgTime;
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

    public void shortestRemainingTimeFirst(int processNum, Process[] processes) {
        System.out.println("Shortest Remaining Time First Algorithm (SRTF)");

        int currentTime = 0;
        int completedProcesses = 0;
        int totalWaitTime = 0;
        int startTime = 0;

        List<Process> completedList = new ArrayList<>();

        Process currentSRTF = null;

        while(completedProcesses < processNum) {
            Process shortestJob = null;
            int shortestTime = Integer.MAX_VALUE;
            //Finds shortestjob
            for (int i = 0; i < processNum; i++) {
                Process process = processes[i];
                if (!process.completed && process.arrivalTime <= currentTime && process.remainingBurstTime < shortestTime) {
                    shortestJob = process;
                    shortestTime = process.remainingBurstTime;
                }
            }
            //facilitate switches before setting shortest to current
            if (currentSRTF != null){ //idea here it compares a from b, then adds a in appropriately.
                if (currentSRTF.ID != shortestJob.ID && !currentSRTF.completed){
                    currentSRTF.startTime = startTime;
                    currentSRTF.waitTime = startTime - currentSRTF.endTime;
                    currentSRTF.endTime = currentTime;
                    completedList.add(new Process(currentSRTF));
                    currentSRTF.arrivalTime = shortestJob.startTime; //currentSRTF switches, thus waits now on this arrival.
                    startTime = currentTime;
                }
            } 

            currentSRTF = shortestJob;

            if (shortestJob != null) {
                shortestJob.remainingBurstTime--;

                if (shortestJob.remainingBurstTime == 0) {
                    shortestJob.waitTime = currentTime + 1 - shortestJob.arrivalTime - shortestJob.burstTime;
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
            } 

            currentTime++;
        }

        // Print process details
        for (Process process : completedList) {
            process.print();
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
