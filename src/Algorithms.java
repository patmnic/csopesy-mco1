package src;

import java.util.*;
import java.util.ArrayList;

public class Algorithms {
    public void firstComeFirstServe(int processNum, Process[] processes){
        System.out.println("First Come First Serve Algorithm (FCFS)");

        Process tempProcess; // selection sort by arrival time
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

        Process tempProcess; // for sorting
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
//        int currentTime = 0, totalWaitTime = 0, lastID;
//        ArrayList<Process> waiting = new ArrayList<>();
//
//
//        order.sort(Comparator.comparingInt(o -> o.arrivalTime));
//
//
//
//        while (!order.isEmpty() || !waiting.isEmpty()){
//
//        }

        int currentTime = 0;
        int totalWaitTime = 0;
        ArrayList<Process> toArrive = new ArrayList<>();
        Collections.addAll(toArrive, processes);
        toArrive.sort(Comparator.comparingInt(o -> o.arrivalTime));

        ArrayList<Process> toBurst = new ArrayList<>();
        Process next = toArrive.get(0);

        while(!toArrive.isEmpty() || !toBurst.isEmpty()) {
            if (!toArrive.isEmpty()) {
                while (!toArrive.isEmpty()){
                    if(toArrive.get(0).arrivalTime <= currentTime) {
                        toBurst.add(toArrive.get(0));
                        toArrive.remove(0);
                        continue;
                    }

                    next = toArrive.get(0);
                    // if no arrived processes, skip to next arrival time
                    if (toBurst.isEmpty()) {
                        currentTime = next.arrivalTime;
                    }
                    break;
                }
                toBurst.sort(Comparator.comparingInt(o -> o.burstTime));
            }

            if(!toBurst.isEmpty()){
                if (toArrive.isEmpty()) {
                    // if all processes arrive, burst all waiting processes
                    for (Process process : toBurst) {
                        currentTime += process.run(process.burstTime, currentTime);
                        totalWaitTime += process.waitTime;
                    }
                    break;
                } else {
                    Process shortestJob = toBurst.get(0);
                    int shortestJobBurst;
                    int runBurst = 0;
                    while (runBurst > -1 && next != null) {
                        // predict current job's remaining burst if it stops at next arrival
                        runBurst = next.arrivalTime - currentTime;
                        shortestJobBurst = shortestJob.burstTime - runBurst;

                        // add next arrival to list of waiting
                        toBurst.add(next);
                        toArrive.remove(0);
                        // if shortest job finishes or the newer job has a shorter burst,
                        // run current job from start to arrival
                        if (shortestJobBurst <= 0 || shortestJobBurst > next.burstTime) {
                            currentTime += shortestJob.run(runBurst, currentTime);
                            totalWaitTime += shortestJob.waitTime;
                            if (shortestJobBurst <= 0){
                                // remove job if done
                                toBurst.remove(0);
                            } else {
                                // return to waiting if not done
                                toBurst.set(0, shortestJob);
                            }
                            runBurst = -1;
                        }
                        // get next to arrive if possible
                        if (toArrive.isEmpty()) {
                            next = null;
                        }
                        else {
                            next = toArrive.get(0);
                        }
                    }
                    // sort waiting processes by burst time
                    toBurst.sort(Comparator.comparingInt(o -> o.burstTime));
                }
            }
        }

        // Calculate and print average waiting time
        float avgWaitTime = (float) totalWaitTime / processNum;
        System.out.printf("Average Waiting Time: %.2f\n", avgWaitTime);
    }

    public void roundRobin(int roundRobinTime, int processNum, Process[] processes){
        System.out.println("Round Robin Algorithm (RR)");
        int totalTime = 0, totalWaitTime = 0;

        // sort processes by arrival time
        ArrayList<Process> order = new ArrayList<>();
        Collections.addAll(order, processes);
        order.sort(Comparator.comparingInt(o -> o.arrivalTime));

        // round robin queue
        Queue<Process> queue = new LinkedList<>();

        while (!order.isEmpty() || !queue.isEmpty()) {
            // if round-robin queue is empty, get arrived processes
            if (queue.isEmpty()) {
                while (!order.isEmpty()){
                    if(order.get(0).arrivalTime <= totalTime) {
                        queue.add(order.get(0));
                        order.remove(0);
                    }
                    else {
                        // if no arrived processes, skip to next arrival time
                        if (queue.isEmpty()) {
                            totalTime = order.get(0).arrivalTime;
                        }
                        break;
                    }
                }
            } else {
                // run latest
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
