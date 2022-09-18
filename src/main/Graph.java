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

    private ArrayList<Double> data;

    private boolean visualize;

    public Graph(boolean visualize) {
        this.visualize = visualize;

        data = new ArrayList<>();

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
        XYSeries data = new XYSeries("GA");
        
        for(int i = 0; i < this.data.size(); i++) {
            data.add(i, this.data.get(i));
        }

        dataset.addSeries(data);

        return dataset;
    }

    public void addData(double value) {
        if(visualize) {
            data.add(value);
            repaint();
        }
    }
}
