package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PercentGraphWriter {
    private double solution;
    private BufferedWriter writer;
    private boolean write;

    public PercentGraphWriter(double solution, String path, boolean write) {
        this.solution = solution;

        this.write = write;

        if(!write) {
            return;
        }

        try {
            writer = new BufferedWriter(new FileWriter(path));
        }
        catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public void write(double num) {
        if(!write) {
            return;
        }

        double value = (num - solution) / solution * 100;

        try {
            writer.write(value+"\n");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if(!write) {
            return;
        }

        try {
            writer.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
