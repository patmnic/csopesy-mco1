package src;

import java.util.*;
import java.io.*;

public class Menu {
    private static int algorithm;
    private static int processNum;
    private static int roundRobinTime;
    private static int ID;
    private static int arrivalTime;
    private static int burstTime;
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String fileName;
        Algorithms algorithms = new Algorithms();

        System.out.print("Enter name of file: ");
        fileName = scanner.nextLine();

        File file = null;
        file = new File("Inputs/" + fileName + ".txt");

        scanner.close();

        Scanner fileScanner = null;

        try {
            fileScanner = new Scanner(file);
        } catch (Exception e) {
            System.out.printf("%s.txt not found.\n", fileName);
            return;
        }

        assert fileScanner != null;
        algorithm = fileScanner.nextInt();
        processNum = fileScanner.nextInt();
        roundRobinTime = fileScanner.nextInt();

        /*
        System.out.println(algorithm);
        System.out.println(processNum);
        System.out.println(roundRobinTime);
         */

        Process[] process = new Process[processNum];


        for (int i = 0; i < processNum; i++) {
            fileScanner.nextLine();
            ID = fileScanner.nextInt();
            arrivalTime = fileScanner.nextInt();
            burstTime = fileScanner.nextInt();

            process[i] = new Process(ID, arrivalTime, burstTime);
            /*
            System.out.print(process[i].ID + " ");
            System.out.print(process[i].arrivalTime + " ");
            System.out.println(process[i].burstTime);
             */
        }

        switch (algorithm) {
            case 0 -> algorithms.firstComeFirstServe(processNum, process);
            case 1 -> algorithms.shortestJobFirst(processNum, process);
            case 2 -> algorithms.shortestRemainingTimeFirst(processNum, process);
            case 3 -> algorithms.roundRobin(roundRobinTime, processNum, process);
        }
        
        fileScanner.close();
        return;
    }
}
