import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]) {

        Scanner input = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("Welcome to the Flight Booking System");
            System.out.println("\n---------------------");
            System.out.println("1. Round Robin");
            System.out.println("2. Shortest Remaining Time First");
            System.out.println("3. Exit");
            System.out.println("---------------------\n");
            System.out.print("Please select the algorithm you want to use: ");
            choice = input.nextInt();
            System.out.println();

            switch (choice) {
                case 1:
                    roundRobin(input);
                    break;
                case 2:
                    shortestRemainingTime(input);
                    break;
                case 3:
                    System.out.println("Thank you for using the Flight Booking System!");
                    break;
                default:
                    System.out.println("Invalid choice!\n");
            }
        } while (choice != 3);

        input.close();
    }

    public static void roundRobin(Scanner input) {
        int numProcess, quantum;
        int burstTime, arrivalTime;
        float avergaeWaitingTime = 0, averageTurnAroundTime = 0;

        ArrayList<Integer> order = new ArrayList<Integer>();
        ArrayList<Integer> startTime = new ArrayList<Integer>();
        ArrayList<RoundRobin> roundRobin = new ArrayList<RoundRobin>();

        System.out.println("Round Robin Flight Booking System\n");
        System.out.print("Enter the number of Customer: ");
        numProcess = input.nextInt();
        System.out.println(numProcess);
        System.out.println();

        for (int i = 0; i < numProcess; i++) {
            System.out.print("Enter Number of Ticket of Customer " + (i + 1) + ": ");
            burstTime = input.nextInt();
            System.out.print("Enter Arrival Time of Customer " + (i + 1) + "(in minutes): ");
            arrivalTime = input.nextInt();
            roundRobin.add(new RoundRobin(burstTime, arrivalTime));
            roundRobin.get(i).setRemainingBurstTime(burstTime);
            System.out.println();
        }
        System.out.print("Enter the time quantum(in minutes): ");
        quantum = input.nextInt();
        int totalTime = 0, count = 0, remainTime = 0, loop = 0;
        do {
            for (int i = 0; i < numProcess; i++) {
                if (roundRobin.get(i).arrivalTime > totalTime) {
                    if (loop >= numProcess) {
                        totalTime++;
                        loop = 0;
                        continue;
                    }
                    loop++;
                    continue;
                }
                loop = 0;
                if (roundRobin.get(i).remainingBurstTime == 0) {
                    count++;
                    continue;
                }
                // If the latest process is not the same
                if (order.size() == 0 || order.get(order.size() - 1) != i) {
                    order.add(i);
                    startTime.add(totalTime);
                }
                remainTime = quantum;
                if (roundRobin.get(i).remainingBurstTime > quantum) {
                    roundRobin.get(i).setRemainingBurstTime(roundRobin.get(i).getRemainingBurstTime() - quantum);
                    totalTime += quantum;
                } else if (roundRobin.get(i).remainingBurstTime >= 0) {
                    remainTime = roundRobin.get(i).remainingBurstTime;
                    roundRobin.get(i).setRemainingBurstTime(0);
                    totalTime += remainTime;
                }
                roundRobin.get(i).setCompletionTime(totalTime);
            }
        } while (count != numProcess);

        System.out.print(
                "\n\n--------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("\n%-15s%-35s%-20s%-20s%-20s%-20s\n", "Customer", "Burst Time(Number of Ticket)",
                "Arrival Time",
                "Completion Time",
                "Turnaround Time", "Waiting Time");
        System.out.print(
                "--------------------------------------------------------------------------------------------------------------------------\n");
        for (int i = 0; i < numProcess; i++) {
            roundRobin.get(i).setTurnAroundTime();
            roundRobin.get(i).setWaitingTime();
            avergaeWaitingTime += roundRobin.get(i).getWaitingTime();
            averageTurnAroundTime += roundRobin.get(i).getTurnAroundTime();

            System.out.printf("%-15s", "C" + (i + 1));
            roundRobin.get(i).print();
        }

        avergaeWaitingTime /= numProcess;
        averageTurnAroundTime /= numProcess;

        System.out.println("\n\nAverage waiting Time = " + avergaeWaitingTime + " (minutes)");
        System.out.println("Average turnaround time = " + averageTurnAroundTime + " (minutes)");

        System.out.print(
                "\n\n--------------------------------------");
        System.out.printf("\n%-15s%-15s%-15s\n", "Customer", "Start Time", "End Time");
        System.out.print(
                "--------------------------------------\n");
        for (int i = 0; i < order.size(); i++) {
            if (i == order.size() - 1) {
                System.out.printf("%-15s%-15s%-15s\n", "C" + (order.get(i) + 1), startTime.get(i),
                        totalTime);
                break;
            }
            System.out.printf("%-15s%-15s%-15s\n", "C" + (order.get(i) + 1), startTime.get(i),
                    startTime.get(i + 1));
        }
        System.out.println("\n");
    }

    public static void shortestRemainingTime(Scanner input) {
        int burstTime, arrivalTime;

        System.out.println("Shortest Remaining Time First Flight Booking System\n");
        System.out.print("Enter the number of Customer: ");
        int numProcess = input.nextInt();
        Process process[] = new Process[numProcess];
        System.out.println();

        for (int i = 0; i < numProcess; i++) {
            System.out.print("Enter Number of Ticket of Customer " + (i + 1) + ": ");
            burstTime = input.nextInt();
            System.out.print("Enter Arrival Time of Customer " + (i + 1) + "(in minutes):");
            arrivalTime = input.nextInt();
            System.out.println();

            process[i] = new Process(i + 1, burstTime, arrivalTime);
        }

        ShortestRemainingTime.findavgTime(process, process.length);
        System.out.println("\n");
    }
}