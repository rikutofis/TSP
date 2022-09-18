package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graph extends JPanel {
    private JFrame frame;

    private ArrayList<Double> average;
    private ArrayList<Double> best;

    private double solution;

    private boolean visualize;
    private boolean visualize_average;
    private boolean visualize_best;

    public Graph(boolean visualize, boolean visualize_average, boolean visualize_best) {
        this.visualize = visualize;
        this.visualize_average = visualize_average;
        this.visualize_best = visualize_best;

        average = new ArrayList<>();
        best = new ArrayList<>();

        if(visualize) {
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
    }

    @Override
    public void paintComponent(Graphics g) {
        XYDataset dataset = createDataSet();

        JFreeChart xyChart = ChartFactory.createXYLineChart("Genetic Algorithm", "Generation", "Distance", dataset);

        NumberAxis numberAxis = (NumberAxis) xyChart.getXYPlot().getRangeAxis();
        numberAxis.setAutoRangeIncludesZero(false);

        xyChart.draw((Graphics2D) g, this.getVisibleRect());
    }

    private XYDataset createDataSet() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        if(visualize_average) {
            XYSeries data = new XYSeries("Average");     
            for(int i = 0; i < this.average.size(); i++) {
                data.add(i, this.average.get(i));
            }
            dataset.addSeries(data);
        }

        if(visualize_best) {
            XYSeries best = new XYSeries("Best");
            for(int i = 0; i < this.best.size(); i++) {
                best.add(i, this.best.get(i));
            }
            dataset.addSeries(best);
        }

        if(this.solution > 0) {
            XYSeries solution = new XYSeries("Solution");
            solution.add(0, this.solution);
            solution.add(this.average.size() - 1, this.solution);
            dataset.addSeries(solution);
        }


        return dataset;
    }

    public void addAverage(double value) {
        if(visualize) {
            average.add(value);
            repaint();
        }
    }

    public void addBest(double value) {
        if(visualize) {
            best.add(value);
            repaint();
        }
    }

    public void setSolution(double solution) {
        this.solution = solution;
    }
}
