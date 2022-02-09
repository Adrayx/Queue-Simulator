package model;

public class Client implements Comparable<Client> {
    private final int id;
    private final int arrivalTime;
    private int serviceTime;

    public Client(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    @Override
    public int compareTo(Client c) {
        return Integer.compare(arrivalTime, c.arrivalTime);
    }

    @Override
    public String toString() {
        return "(" + getId() + ", " + getArrivalTime() + ", " + getServiceTime() + ")";
    }
}
