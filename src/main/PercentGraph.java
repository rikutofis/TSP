package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import GA.GA;

public class PercentGraph extends JPanel {
    private JFrame frame;
    
    private ArrayList<Double> problem1;
    private ArrayList<Double> problem2;
    private ArrayList<Double> problem3;

    public PercentGraph() {
        problem1 = new ArrayList<>();
        problem2 = new ArrayList<>();
        problem3 = new ArrayList<>();

        this.setPreferredSize(new Dimension(500, 500));

            frame = new JFrame();
            frame.add(this);
            frame.setTitle("Graph");
            frame.setResizable(false);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
    }

    @Override
    public void paintComponent(Graphics g) {
        XYDataset dataset = createDataSet();

        JFreeChart xyChart = ChartFactory.createXYLineChart("Genetic Algorithm", "Generation", "Percentage Difference (%)", dataset);

        NumberAxis numberAxis = (NumberAxis) xyChart.getXYPlot().getRangeAxis();
        numberAxis.setAutoRangeIncludesZero(false);

        xyChart.draw((Graphics2D) g, this.getVisibleRect());
    }

    private XYDataset createDataSet() {
        int startFrom = 0;

        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries data1 = new XYSeries("rat99");     
        for(int i = startFrom; i < problem1.size(); i++) {
            data1.add(i, problem1.get(i));
        }
        dataset.addSeries(data1);

        XYSeries data2 = new XYSeries("kroB200");     
        for(int i = startFrom; i < problem2.size(); i++) {
            data2.add(i, problem2.get(i));
        }
        dataset.addSeries(data2);

        XYSeries data3 = new XYSeries("pr299");     
        for(int i = startFrom; i < problem3.size(); i++) {
            data3.add(i, problem3.get(i));
        }
        dataset.addSeries(data3);

        return dataset;
    }

    public void read(int i, File file) {
        try {
            Scanner reader = new Scanner(file);

            ArrayList<Double> problem;

            if(i == 1) {
                problem = problem1;
            }
            else if(i == 2) {
                problem = problem2;
            }
            else {
                problem = problem3;
            }

            for(int j = 0; j < GA.MAX_GENERATION && reader.hasNextLine(); j++) {
                problem.add(reader.nextDouble());
            }
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
