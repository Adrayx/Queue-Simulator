package strategy;

import model.Client;
import model.Queue;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    @Override
    public int addClient(List<Queue> queues, Client client) throws Exception {
        int minimalQueueSize = Integer.MAX_VALUE;
        int index = -1;
        for(Queue q: queues)
        {
            if(q.getClientsNumber() < minimalQueueSize)
                if(q.getClientsNumber() < q.getCapacity())
                {
                    minimalQueueSize = q.getClientsNumber();
                    index = q.getIndex();
                }
        }
        if(index != -1)
        {
            queues.get(index - 1).addClient(client);
        }
        else
            throw new Exception("All queues are full");
        return index - 1;
    }
}
