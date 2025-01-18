package org.example.sortvisualizer;

import javax.swing.*;

abstract public class pattern {

    protected Application app;

    int[] list;

    public pattern(Application app) {
        this.app = app;
    }

    //PATRON METHODE TEMPLATE
    public void sort(int[] list) {
        this.list = list;
        sorting(0, list.length - 1);
        app.lastTrue();
        app.repaint();
    }

    abstract void sorting(int start, int end);


    public void Update() {
        SwingUtilities.invokeLater(() -> app.repaint());
    }
    public void delay(){
        try {
            Thread.sleep(app.getDelay());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
