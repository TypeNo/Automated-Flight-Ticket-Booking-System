public class RoundRobin {
    int burstTime;
    int arrivalTime;
    int completionTime;
    int turnAroundTime;
    int waitingTime;
    int remainingBurstTime;

    public RoundRobin(int burstTime, int arrivalTime) {
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.remainingBurstTime = burstTime;
        this.waitingTime = 0;
        this.turnAroundTime = 0;
        this.completionTime = 0;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public void setTurnAroundTime() {
        this.turnAroundTime = completionTime - arrivalTime;
    }

    public void setWaitingTime() {
        this.waitingTime = turnAroundTime - burstTime;
    }

    public void setRemainingBurstTime(int remainingBurstTime) {
        this.remainingBurstTime = remainingBurstTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getRemainingBurstTime() {
        return remainingBurstTime;
    }

    public void print() {
        System.out.printf("%-35s%-20s%-20s%-20s%-20s\n", burstTime, arrivalTime,
                completionTime, turnAroundTime, waitingTime);
    }
}
