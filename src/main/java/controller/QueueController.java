package controller;

import model.SimulationManager;
import view.QueueView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class QueueController {
    private final QueueView m_view;

    public QueueController(QueueView view)
    {
        m_view = view;

        view.addTestListener(new TestListener());
        view.addOpenListener(new OpenListener());
    }

    private class TestListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e)
        {
            try {
                m_view.getButton().setEnabled(false);
                m_view.getGeneratedClients().setText("");
                m_view.getOutput().setText("");
                int numberOfClients = Integer.parseInt(m_view.getClientsNumber());
                int numberOfQueues = Integer.parseInt(m_view.getQueuesNumber());
                int timeLimit = Integer.parseInt(m_view.getTimeLimit());
                int minArrivalTime = Integer.parseInt(m_view.getMinArrivalTime());
                int maxArrivalTime = Integer.parseInt(m_view.getMaxArrivalTime());
                int minServiceTime = Integer.parseInt(m_view.getMinServiceTime());
                int maxServiceTime = Integer.parseInt(m_view.getMaxServiceTime());

                new FileWriter("./log.txt", false).close();
                SimulationManager sim = new SimulationManager(numberOfClients, numberOfQueues, timeLimit, minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime, m_view);
                Thread thread = new Thread(sim);
                thread.start();
                //thread.join();

            }catch(NumberFormatException | IOException err)
            {
                m_view.getButton().setEnabled(true);
                m_view.showError(err.getMessage());
            }
        }
    }

    private class OpenListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e)
        {
            try {
                Desktop.getDesktop().open(new File("./log.txt"));
            } catch (IOException ioException) {
                m_view.showError(ioException.getMessage());
            }
        }
    }
}
