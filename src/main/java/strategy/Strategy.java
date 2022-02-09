package strategy;

import model.Client;
import model.Queue;

import java.util.List;

public interface Strategy {
    int addClient(List<Queue> queues, Client client) throws Exception;
}
