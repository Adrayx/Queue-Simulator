package model;

import view.QueueView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SimulationManager implements Runnable {
    private final int numberOfClients;
    private final int numberOfQueues;
    private final int timeLimit;
    private final int minArrivalTime;
    private final int maxArrivingTime;
    private final int minProcessingTime;
    private final int maxProcessingTime;

    private SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    private void createStrategy()
    {
        selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    }

    private void changeSelectionPolicy()
    {
        if(selectionPolicy == SelectionPolicy.SHORTEST_QUEUE)
            selectionPolicy = SelectionPolicy.SHORTEST_TIME;
        else
            selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
    }

    private final Scheduler scheduler;
    private final QueueView view;
    private ArrayList<Client> generatedClients;

    public SimulationManager(int numberOfClients, int numberOfQueues, int timeLimit,
                             int minArrivalTime, int maxArrivingTime, int minProcessingTime,
                             int maxProcessingTime, QueueView m_view){
        this.numberOfClients = numberOfClients;
        this.numberOfQueues = numberOfQueues;
        this.timeLimit = timeLimit;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivingTime = maxArrivingTime;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        scheduler = new Scheduler(this.numberOfQueues, this.numberOfClients, 50);
        view = m_view;
        generateRandomClients();
        createStrategy();
    }

    private void generateRandomClients(){
        generatedClients = new ArrayList<>();
        for(int i = 0; i < numberOfClients; i++)
        {
            Random random = new Random();
            int processingTime = minProcessingTime + random.nextInt(maxProcessingTime - minProcessingTime);
            int arrivalTime = minArrivalTime + random.nextInt(maxArrivingTime - minArrivalTime);
            generatedClients.add(new Client(i + 1, arrivalTime, processingTime));
        }
        Collections.sort(generatedClients);
        Print.file(generatedClients, true);
        //Print.screen(generatedClients, true);
        Print.gui(view, generatedClients, true);
        System.out.println();
    }

    @Override
    public void run() {
        int currentTime = 0;
        boolean stillActiveQueues = true;
        while (currentTime < timeLimit) {
            try {
                if (!generatedClients.isEmpty())
                    if (currentTime >= generatedClients.get(0).getArrivalTime()) {
                        try {
                            for (int i = 0; i < numberOfQueues && !generatedClients.isEmpty(); i++) {
                                if (currentTime >= generatedClients.get(0).getArrivalTime()) {
                                    Client newClient = new Client(generatedClients.get(0).getId(), generatedClients.get(0).getArrivalTime(), generatedClients.get(0).getServiceTime());
                                    scheduler.dispatchClient(newClient);
                                    generatedClients.remove(0);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                //Print.screen(generatedClients, false);
                Print.file(generatedClients, false);
                Print.gui(view, generatedClients, false);
                //Print.screen(currentTime, scheduler.getQueues(), false);
                Print.file(currentTime, scheduler.getQueues(), false);
                Print.gui(view, currentTime, scheduler.getQueues(), false);
                if (generatedClients.isEmpty() && scheduler.activeQueues() == 0) {
                    stillActiveQueues = false;
                    scheduler.stopThreads();
                    break;
                }
                scheduler.verifyPeakHour(currentTime);
                Thread.sleep(1000);
                currentTime++;
            } catch (InterruptedException exception) {
                System.out.println("Interruption unexpected!");
                currentTime = timeLimit;
                exception.printStackTrace();
            }
        }
        System.out.println("Task is done!");
        if (stillActiveQueues) {
            scheduler.stopThreads();
            Print.file(currentTime, scheduler.getQueues(), true);
            //Print.screen(currentTime, scheduler.getQueues(), true);
            Print.gui(view, currentTime, scheduler.getQueues(), true);
        } else {
            System.out.println("All queues are empty!\n");
        }
        Print.file(scheduler.averageWaitingTime(numberOfClients), 0);
        //Print.screen(scheduler.averageWaitingTime(), 0);
        Print.gui(view, scheduler.averageWaitingTime(numberOfClients), 0);
        Print.file(scheduler.averageServiceTime(numberOfClients), 1);
        //Print.screen(scheduler.averageServiceTime(numberOfClients), 1);
        Print.gui(view, scheduler.averageServiceTime(numberOfClients), 1);
        Print.file(scheduler.getPeakHour(), 2);
        //Print.screen(scheduler.getPeakHour(), 2);
        Print.gui(view, scheduler.getPeakHour(), 2);
        view.getButton().setEnabled(true);
    }
}
