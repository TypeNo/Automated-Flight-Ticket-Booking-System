import java.util.*;

class Process{
    int processID;
    int burstTime; 
    int arrivalTime; 
    int completionTime; 

    public Process(int pid, int bt, int art){
        processID = pid;
        burstTime = bt;
        arrivalTime = art;
    }
    public void setCompletionTime(int cT){
        completionTime = cT;
    }
}

public class ShortestRemainingTime {
    // Method to find the sequence for all processes
    static void findSequence(Process proc[], int n) {
        int totalTime = 0;

        ArrayList<Integer> burstTimeList = new ArrayList<Integer>();
        ArrayList<Integer> processIDList = new ArrayList<Integer>();
        ArrayList<Integer> startTimeList = new ArrayList<Integer>();

        // Copy the burst time into burstTimeList
        for (int i = 0; i < n; i++) {
            burstTimeList.add(proc[i].burstTime);
            totalTime = totalTime + proc[i].burstTime;
        }

        int complete = 0, t = 0, minRemainTime = Integer.MAX_VALUE;
        int shortest = 0;
        boolean check = false;
        int currentProcessID = 0;
        System.out.print(
                "\n\n--------------------------------------");
        System.out.printf("\n%-15s%-15s%-15s\n", "Customer", "Start Time", "End Time");
        System.out.print(
                "--------------------------------------\n");
        // Process until all processes gets completed
        while (complete != n) {

            for (int j = 0; j < n; j++) {
                if ((proc[j].arrivalTime <= t) && (burstTimeList.get(j) < minRemainTime) && burstTimeList.get(j) > 0) {
                    minRemainTime = burstTimeList.get(j);
                    shortest = j;
                    check = true;
                }
            }

            // System.out.println("proc[shortest].arrivalTime: " +
            // proc[shortest].arrivalTime);
            // System.out.println("totalTime: " + totalTime);
            // System.out.println("proc[shortest].processID: " + proc[shortest].processID);
            // System.out.println("t: " + t);

            if (proc[shortest].arrivalTime > t) {
                totalTime++;
            } else {
                if (proc[shortest].processID != currentProcessID) {
                    processIDList.add(proc[shortest].processID);
                    startTimeList.add(t);
                    currentProcessID = proc[shortest].processID;
                }
            }

            if (check == false) {
                t++;
                continue;
            }

            // Decrease remaining time by one
            burstTimeList.set(shortest, burstTimeList.get(shortest) - 1);

            // Update minimum
            minRemainTime = burstTimeList.get(shortest);

            if (minRemainTime == 0)
                minRemainTime = Integer.MAX_VALUE;

            // If a process completely executed
            if (burstTimeList.get(shortest) == 0) {
                complete++;
                check = false;
            }

            // Increment time
            t++;
        }

        for (int i = 0; i < processIDList.size(); i++) {
            if (i == processIDList.size() - 1) {
                System.out.printf("%-15s%-15s%-15s\n", processIDList.get(i), startTimeList.get(i), totalTime);
                break;
            } else {
                System.out.printf("%-15s%-15s%-15s\n", processIDList.get(i), startTimeList.get(i),
                        startTimeList.get(i + 1));
            }
        }
    }

    static void findWaitingTime(Process proc[], int n, int wt[]) {
        ArrayList<Integer> burstTimeList = new ArrayList<Integer>();
        ArrayList<Integer> completionTimeList = new ArrayList<Integer>();

        // Copy the burst time into burstTimeList
        for (int i = 0; i < n; i++) {
            burstTimeList.add(proc[i].burstTime);
        }

        int complete = 0, t = 0, minm = Integer.MAX_VALUE;
        int shortest = 0, finish_time = 0;
        boolean check = false;

        // Process until all processes completed
        while (complete != n) {
            for (int j = 0; j < n; j++) {
                if ((proc[j].arrivalTime <= t) && (burstTimeList.get(j) < minm) && burstTimeList.get(j) > 0) {
                    minm = burstTimeList.get(j);
                    shortest = j;
                    check = true;
                }
            }

            if (check == false) {
                t++;
                continue;
            }

            // Decrease remaining time by one
            burstTimeList.set(shortest, burstTimeList.get(shortest) - 1);

            // Update minimum
            minm = burstTimeList.get(shortest);
            if (minm == 0)
                minm = Integer.MAX_VALUE;

            // If a process completely executed
            if (burstTimeList.get(shortest) == 0) {
                complete++;
                check = false;

                // Find finish time of current process
                finish_time = t + 1;

                // Calculate waiting time
                wt[shortest] = finish_time - proc[shortest].burstTime - proc[shortest].arrivalTime;
                completionTimeList.add(finish_time);
                proc[shortest].setCompletionTime(finish_time);
                if (wt[shortest] < 0)
                    wt[shortest] = 0;
            }
            t++;
        }

    }

    // Method to calculate turn around time
    static void findTurnAroundTime(Process proc[], int n, int wt[], int tat[]) {
        // calculating turnaround time by adding
        for (int i = 0; i < n; i++)
            tat[i] = proc[i].burstTime + wt[i];
    }

    // Method to calculate average time
    static void findavgTime(Process proc[], int n) {
        int wt[] = new int[n], tat[] = new int[n];
        int total_wt = 0, total_tat = 0;

        // Function to find waiting time of all processes
        findWaitingTime(proc, n, wt);

        // Function to find turn around time for all processes
        findTurnAroundTime(proc, n, wt, tat);

        // Display processes along with all details
        System.out.print(
                "\n\n----------------------------------------------------------------------------------------------------------------");
        System.out.printf("\n%-20s%-20s%-20s%-20s%-20s%-20s", "Processes", "Arrival time", "Burst time", "Completion time", "Turnaround time", "Waiting time");
        System.out.print(
                "\n----------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            total_wt = total_wt + wt[i];
            total_tat = total_tat + tat[i];
            System.out.printf("\n%-20s%-20s%-20s%-20s%-20s%-20s", proc[i].processID, proc[i].arrivalTime,proc[i].burstTime, proc[i].completionTime,tat[i], wt[i]);
        }

        // Calculate average waiting time and total turnaround time
        System.out.println("\n\n\nAverage waiting time = " + (float) total_wt / (float) n);
        System.out.println("Average turn around time = " + (float) total_tat / (float) n);
        findSequence(proc, n);
    }
}
