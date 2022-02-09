package model;

import view.QueueView;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Print {
    public static void file(int time, List<Queue> queues, boolean finalQueues) {
        File logFile = new File("./log.txt");
        try {
            if(!logFile.exists()) {
                logFile.createNewFile();
            }
            FileWriter writer = new FileWriter(logFile, true);
            if(finalQueues) {
                writer.append("Final queues:\n");
            }
            else {
                writer.append("Time: " + time + "\n");
            }
            for(Queue q: queues)
            {
                writer.append("Queue: " + q.getIndex() + "\n");
                writer.append(q.toString() + "\n");
            }
            writer.append("--------------------\n");
            writer.close();
        } catch(IOException e)
        {
            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }

    public static void file(ArrayList<Client> clients, boolean first)
    {
        File logFile = new File("./log.txt");
        try{
            if(!logFile.exists())
                logFile.createNewFile();
            FileWriter writer = new FileWriter(logFile, true);
            if(clients.isEmpty()){
                writer.append("No more waiting clients\n");
            }
            else {
                if(first) {
                    writer.append("Generated clients: \n");
                }
                else
                    writer.append("Waiting clients: \n");
                for (Client c : clients) {
                    writer.append(c.toString() + "; ");
                }
                writer.append("\n");
            }
            writer.close();
        } catch(IOException e)
        {
            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }

    public static void screen(int currentTime, List<Queue> queues, boolean finalQueues)
    {
        if(finalQueues)
            System.out.println("Final queues:\n");
        else
            System.out.println("Time: " + currentTime);
        for(Queue q: queues)
        {
            System.out.println("Queue: " + q.getIndex());
            System.out.println(q.toString());
            System.out.println();
        }
        System.out.println("--------------------");
    }

    public static void screen(ArrayList<Client> clients, boolean first)
    {
        if(clients.isEmpty()) {
            System.out.println("No more waiting clients");
            return;
        }
        if(first) {
            System.out.println("Generated clients: ");
        }
        else {
            System.out.println("Waiting clients:");
        }
        for(Client c: clients)
        {
            System.out.print(c.toString() + "; ");
        }
        System.out.println();
    }

    public static void file(double time, int type)
    {
        File logFile = new File("./log.txt");
        try{
            if(!logFile.exists())
                logFile.createNewFile();
            FileWriter writer = new FileWriter(logFile, true);
            if(type == 0)
                writer.append("Average waiting time: " + time + "\n");
            else if(type == 1)
                writer.append("Average service time: " + time + "\n");
            else
                writer.append("Peek hour: " + time + "\n");
            writer.close();
        } catch(IOException e)
        {
            System.out.println("Error occurred!");
            e.printStackTrace();
        }
    }

    public static void screen(double time, int type)
    {
        if(type == 0)
            System.out.println("Average waiting time: " + time);
        else if(type == 1)
            System.out.println("Average service time: " + time);
        else
            System.out.println("Peak hour: " + time);
    }

    public static void gui(QueueView view, double time, int type)
    {
        if(type == 0)
            view.getOutput().append("Average waiting time: " + time + "\n");
        else if(type == 1)
            view.getOutput().append("Average service time: " + time + "\n");
        else
            view.getOutput().append("Peak hour: " + time + "\n");
    }

    public static void gui(QueueView view, ArrayList<Client> clients, boolean first)
    {
        JTextArea area;
        if(first)
            area = view.getGeneratedClients();
        else
            area = view.getOutput();
        if(!first)
            if(clients.isEmpty())
                area.append("No more waiting clients!\n");
            else
                area.append("Waiting clients:\n");
        for(Client c: clients)
        {
            area.append(c.toString() + "; ");
        }
        area.append("\n\n");
    }

    public static void gui(QueueView view, int currentTime, List<Queue> queues, boolean finalQueues)
    {
        JTextArea area = view.getOutput();
        if(finalQueues)
            area.append("Final queues:\n");
        else
            area.append("Time: " + currentTime + "\n\n");
        for(Queue q: queues)
        {
            area.append("Queue: " + q.getIndex() + "\n");
            area.append(q.toString() + "\n");
        }
        area.append("------------------------------\n");
    }
}
