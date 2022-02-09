package main;

import controller.QueueController;
import view.QueueView;

public class QueueMVC {
    public static void main(String[] args) {
        QueueView view = new QueueView();
        QueueController controller = new QueueController(view);
        view.setVisible(true);
    }
}