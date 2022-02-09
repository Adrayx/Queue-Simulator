package model;


import strategy.ConcreteStrategyQueue;
import strategy.ConcreteStrategyTime;
import strategy.Strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scheduler {
    private final List<Queue> queues;
    private final int maxNoQueues;
    private final int maxClientsPerQueue;
    private Strategy strategy;
    private double totalWaitingTime;
    private double totalServiceTime;
    private double peekHour;
    private int maxClients;


    public Scheduler(int maxNoQueues, int maxClientsPerQueue, int capacity)
    {
        this.maxNoQueues = maxNoQueues;
        this.maxClientsPerQueue = maxClientsPerQueue;
        queues = Collections.synchronizedList(new ArrayList<>(maxNoQueues));
        totalWaitingTime = 0;
        totalServiceTime = 0;
        peekHour = -1;
        maxClients = 0;
        changeStrategy(SelectionPolicy.SHORTEST_TIME);
        for(int i = 1; i <= maxNoQueues; i++)
        {
            Queue q = new Queue(capacity, i);
            queues.add(q);
            Thread t = new Thread(null, q, String.valueOf(i));
            t.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy)
    {
        if(policy == SelectionPolicy.SHORTEST_QUEUE)
        {
            strategy = new ConcreteStrategyQueue();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME)
        {
            strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchClient(Client c) throws Exception
    {
        int serviceTime = c.getServiceTime();
        int index = strategy.addClient(this.queues, c);
        totalWaitingTime += queues.get(index).getWaitingTime().doubleValue();
        totalServiceTime += serviceTime;
    }

    public double averageServiceTime(int numberOfClients)
    {
        return totalServiceTime / numberOfClients;
    }

    public double averageWaitingTime(int numberOfClients)
    {
        return totalWaitingTime / numberOfClients;
    }

    public void verifyPeakHour(int time)
    {
        int currentNumber = 0;
        for(Queue q: queues)
        {
            currentNumber += q.getClientsNumber();
        }
        if(currentNumber > maxClients)
        {
            peekHour = time;
            maxClients = currentNumber;
        }
    }

    public double getPeakHour() {
        return peekHour;
    }

    public List<Queue> getQueues()
    {
        return queues;
    }

    public int activeQueues()
    {
        int active = 0;
        for(Queue q: queues)
        {
            if(q.getClientsNumber() != 0)
                active++;
        }
        return active;
    }

    public void stopThreads()
    {
        for(Queue q : queues)
            q.stopQueue();
    }
}
