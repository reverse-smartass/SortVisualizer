package org.example.sortvisualizer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class Application extends JFrame {

    static int WIDTH = 800;
    static int HEIGHT = 300;
    public GraphCanvas canvas;
    private int[] list;
    private int delay = 50;
    private double barWidth;
    private JTextField textField;
    private mergeSort mergeSort = new mergeSort(this);
    private quickSort quickSort = new quickSort(this);
    private ArrayList<pattern> patterns = new ArrayList<>();
    private pattern curPattern = mergeSort;
    private Thread thread;
    private int checking;
    private int checking1;
    boolean last = false;

    public void initialize(){
        // Border for panel sections
        Border panelBorder = BorderFactory.createLineBorder(Color.GRAY, 2);


        // First section
        JPanel panel1 = new JPanel(new GridBagLayout());
        panel1.setPreferredSize(new Dimension(800, 70));
        panel1.setBorder(BorderFactory.createTitledBorder(panelBorder, "Control Panel"));

        // Layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;


        JPanel sliderComboPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sliderComboPanel.add(new JLabel("Slider:"));
        JSlider slider = new JSlider(0,200, delay);
        sliderComboPanel.add(slider);
        JLabel spd = new JLabel("Delay: " + delay + " ms");
        sliderComboPanel.add(spd);
        sliderComboPanel.add(new JLabel("Options:"));
        JComboBox<String> comboBox = new JComboBox<>(new String[]{
                "merge sort", "quick sort"});
        sliderComboPanel.add(comboBox);

        panel1.add(sliderComboPanel, gbc);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evnt) {
                delay = slider.getValue();
                spd.setText("Delay: " + delay + " ms");
            }
        });

        comboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int i = comboBox.getSelectedIndex();
                curPattern = patterns.get(i);
            }
        });


        // Second section
        canvas = new GraphCanvas();
        canvas.setPreferredSize(new Dimension(800, 310));
        canvas.setBackground(Color.black);

        // Third section
        JPanel panel3 = new JPanel(new GridBagLayout());
        panel3.setPreferredSize(new Dimension(800, 70));
        panel3.setBorder(BorderFactory.createTitledBorder(panelBorder, "Control Buttons"));

        GridBagConstraints gbcPanel3 = new GridBagConstraints();
        gbcPanel3.gridx = 1;
        gbcPanel3.gridy = 0;
        gbcPanel3.anchor = GridBagConstraints.EAST;
        gbcPanel3.weightx = 1.0;
        gbcPanel3.weighty = 1.0;


        JButton stop = new JButton("Stop");
        JButton start = new JButton("Start");

        panel3.add(new JLabel("Array of numbers:"), gbcPanel3);
        gbcPanel3.gridx++;
        textField = new JTextField( 30);
        //demonstration array
        textField.setText("100,99,98,97,96,95,94,93,92,91,90,89,88,87,86,85,84,83,82,81,80,79,10,20,30,40,50,60,56,45,34,23,12,32,43,54,65,76,87,98,09,40,38,47");
        panel3.add(textField, gbcPanel3);
        gbcPanel3.gridx++;
        panel3.add(stop, gbcPanel3);
        gbcPanel3.gridx++;
        panel3.add(start, gbcPanel3);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thread = new Thread(this::sorting);
                thread.start();
            }

            //PATRON METHODE TEMPLATE
            private void sorting() {
                last = false;
                getArray();
                curPattern.sort(list);
            }
        });


        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //delay = (int)Double.POSITIVE_INFINITY;
                thread.interrupt();
            }
        });


        add(panel1, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
        add(panel3, BorderLayout.SOUTH);

        patterns.add(mergeSort);
        patterns.add(quickSort);
        getArray();
        setVisible(true);
    }

    public Application() {

        setTitle("Sorting Visualizer");
        setSize(820, 482);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        initialize();

    }

    public void setChecking(int c){
        this.checking = c;
    }

    public void setChecking1(int c){
        this.checking1 = c;
    }


    public void getArray(){
        String num = this.textField.getText();
        String[] nums = num.split(",");
        int[] list = new int[nums.length];
        for(int i = 0; i < nums.length; i++){
            list[i] = Integer.parseInt(nums[i].trim());
            System.out.println(list[i]);
        }
        setBarWidth(Application.WIDTH/list.length);
        repaint();
        this.list = list;
    }


    class GraphCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int len = list.length;
            double n = (double) Application.WIDTH /len;
            double m = (double) Application.HEIGHT /getMax();

            for (int i = 0; i < len; i++) {
                if((curPattern instanceof quickSort && (i == checking || i == checking1)) || (curPattern instanceof mergeSort) && (i == checking) || last){
                    g.setColor(Color.green);
                } else {
                    g.setColor(Color.white);
                }


                g.fillRect((int)(n)*(i), (int) (300-(m)*list[i]),
                        (int)barWidth,
                        (int) ((m)*list[i]));
                g.setColor(Color.black);
                g.drawRect((int)(n)*(i), (int) (300-(m)*list[i]),
                        (int)barWidth,
                        (int) ((m)*list[i]));
                System.out.println(list[i] +" " + getMax());
            }
        }
    }

    public int getDelay(){
        return this.delay;
    }

    public int getMax(){
        int max = list[0];
        for (int i = 1; i < list.length; i++) {
            if (list[i] > max) max = list[i];
        }
        return max;
    }

    public void lastTrue(){
        this.last = true;
    }

    public void setBarWidth(int width){
        this.barWidth = width;
    }

    public void repaint() {
        SwingUtilities.invokeLater(() -> this.canvas.repaint());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Application::new);
    }
}
