package strategy;

import model.Client;
import model.Queue;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public int addClient(List<Queue> queues, Client client) throws Exception
    {
        int minimalWaitingTime = Integer.MAX_VALUE;
        int index = -1;
        for(Queue q: queues)
        {
            if(q.getWaitingTime().intValue() < minimalWaitingTime)
                if(q.getClientsNumber() < q.getCapacity())
                {
                    minimalWaitingTime = q.getWaitingTime().intValue();
                    index = q.getIndex();
                }
        }
        if(index != -1)
        {
            queues.get(index - 1).addClient(client);
        }
        else
            throw new Exception("All queues are full!");
        return index - 1;
    }
}
