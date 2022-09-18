package main;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JPanel {
    public final static int SCREEN_WIDTH = 1500;
    public final static int SCREEN_LENGTH = 950;

    private final static int CIRCLE_SIZE = 12;

    private City[] cities;
    private Path path;
    private Path best_path;

    private JFrame frame;

    private boolean visualize;

    public GUI(City[] cities, boolean visualize) {
        this.cities = cities;
        this.visualize = visualize;

        path = new Path();
        best_path = new Path();

        if(visualize) {
            this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_LENGTH));
            this.setBackground(Color.BLACK);

            frame = new JFrame();
            frame.add(this);
            frame.setTitle("Travelling Salesman Problem");
            frame.setResizable(false);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //draw best path
        g2.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(7));

        for(int i = 0; i < best_path.length() - 1; i++) {
            int index1 = best_path.get(i);
            int index2 = best_path.get(i+1);

            g2.drawLine(cities[index1].getX(), cities[index1].getY(), cities[index2].getX(), cities[index2].getY());
        }

        //draw path
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        
        for(int i = 0; i < path.length() - 1; i++) {
            int index1 = path.get(i);
            int index2 = path.get(i+1);

            g2.drawLine(cities[index1].getX(), cities[index1].getY(), cities[index2].getX(), cities[index2].getY());
        }

        //draw circles for cities
        g2.setColor(Color.WHITE);
        for(City c : cities) {
            g2.fillOval(c.getX() - CIRCLE_SIZE/2, c.getY() - CIRCLE_SIZE/2, CIRCLE_SIZE, CIRCLE_SIZE);
        }
    }

    public void setPath(Path path) {
        if(!visualize) {
            return;
        }

        this.path = path.clone();

        if(this.path.getDistance() < this.best_path.getDistance()) {
            this.best_path = this.path.clone();
        }

        repaint();
    }
}
