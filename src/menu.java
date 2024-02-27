package src;

import java.util.*;
import java.io.*;

class Process {
    int ID;
    int arrivalTime;
    int burstTime;
    int waitTime;
    int startTime;
    int previousTime;
}
public class menu {
    private static int algorithm;
    private static int processNum;
    private static int roundRobinTime;
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String fileName;

        System.out.print("Enter name of file: ");
        fileName = scanner.nextLine();

        File file = new File("Inputs/" + fileName + ".txt");
        Scanner fileScanner = new Scanner(file);

        algorithm = fileScanner.nextInt();
        processNum = fileScanner.nextInt();
        roundRobinTime = fileScanner.nextInt();

        System.out.println(algorithm);
        System.out.println(processNum);
        System.out.println(roundRobinTime);

        for (int i = 0; i < processNum; i++){

        }
    }
}
