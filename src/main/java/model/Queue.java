package model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue implements Runnable {
    private ArrayBlockingQueue<Client> clients;
    private AtomicInteger waitingTime;
    private final int index;
    private final int capacity;
    private boolean stopCondition = false;

    public Queue(int capacity, int i)
    {
        this.capacity = capacity;
        clients = new ArrayBlockingQueue<>(capacity);
        waitingTime = new AtomicInteger(0);
        index = i;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setClients(ArrayBlockingQueue<Client> clients) {
        this.clients = clients;
    }

    public AtomicInteger getWaitingTime() {
        return waitingTime;
    }

    public void addClient(Client client)
    {
        clients.add(client);
        waitingTime.addAndGet(client.getServiceTime());
    }

    public Client[] getClients(int capacity)
    {
        return clients.toArray(new Client[capacity]);
    }

    public int getIndex() {
        return index;
    }

    public void stopQueue()
    {
        stopCondition = true;
    }

    public void
    run() {
        while(true)
            if(!clients.isEmpty()) {
                try {
                    Client c = clients.peek();
                    int time = c.getServiceTime();
                    int time_copy = time;
                    while(time > 0) {
                        Thread.sleep(1000);
                        time--;
                        c.setServiceTime(time);
                        waitingTime = new AtomicInteger(waitingTime.intValue() - 1);
                    }
                    //waitingTime = new AtomicInteger(waitingTime.intValue() - time_copy);
                    if(stopCondition)
                        break;
                    c.setServiceTime(time_copy);
                    clients.remove();
                } catch (InterruptedException exception) {
                    System.out.println("Process suddenly interrupted!");
                }
            }
            else if(stopCondition)
                break;
    }

    public int getClientsNumber() {
        return clients.size();
    }

    @Override
    public String toString()
    {
        String result = "";
        if(clients.isEmpty())
        {
            result += "closed\n";
            return result;
        }
        for(Client client: clients)
        {
            result += client.toString() +"; ";
        }
        result += "\n";
        return result;
    }
}
