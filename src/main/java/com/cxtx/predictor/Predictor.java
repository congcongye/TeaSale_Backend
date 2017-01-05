package com.cxtx.predictor;

/**
 * Created by jinchuyang on 17/1/5.
 */

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.SupervisedTrainingElement;
import org.neuroph.core.learning.TrainingElement;
import org.neuroph.core.learning.TrainingSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.LMS;

import jxl.read.biff.BiffException;


public class Predictor {
    private NeuralNetwork neuralNet;
    // static double[] data;
    private double datamax = -9999.0D;
    private double datamin = 9999.0D;
    private TrainingSet testSet;
    private TrainingSet trainingSet;
    private int maxIterations;
    private double[] priceData;

    public double Predicte(String name) throws BiffException, IOException {

        neuralNetConfig();
        double[] data = DataFromXlsFile.GetData("Biluochun", 2016, "whole.xls");

        setDataminDatamax(data);
        neuralNetTraining(data);
        return neuralNetTesting();

    }

    private  double neuralNetTesting() {
        double pred = 0;
        testSet.addElement(new TrainingElement(new double[] { (200.00 - datamin) / datamax, (500.00 - datamin) / datamax, (600.00 - datamin) / datamax, (500.00 - datamin) / datamax }));
        for (TrainingElement testElement : testSet.trainingElements()) {
            neuralNet.setInput(testElement.getInput());
            neuralNet.calculate();
            Vector<Double> networkOutput = neuralNet.getOutput();
            pred = (networkOutput.elementAt(0)) * datamax + datamin;
            //System.out.printf("Predicted price of the selcted tea type for the next month =%4.2f\n", pred);
        }

        //System.out.println("Time stamp N3:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));
        //System.exit(0);
        return pred;
    }

    private  void neuralNetTraining(double[] data) {
        for (int i = 0; i < data.length - 5; i++) {
            System.out
                    .println(data[i] + " " + data[i + 1] + " " + data[i + 2] + " " + data[i + 3] + "->" + data[i + 4]);
            trainingSet.addElement(new SupervisedTrainingElement(new double[] { (data[i] - datamin) / datamax, (data[i + 1] - datamin) / datamax, (data[i + 2] - datamin) / datamax, (data[i + 3] - datamin) / datamax },
                    new double[] { (data[i + 4] - datamin) / datamax }));

        }

        System.out.println("Training network please wait...");

        neuralNet.learnInSameThread(trainingSet);
        System.out.println("Time stamp N2:" + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:MM").format(new Date()));

    }

    private void setDataminDatamax(double[] data) {
        for (int i = 0; i < data.length; i++) {

            if (data[i] > datamax) {
                datamax = data[i];
            }
            if (data[i] < datamin) {
                datamin = data[i];
            }
        }

        datamax = datamax * 1.2D;
        datamin = datamin * 0.8D;
    }

    private void neuralNetConfig() {

        neuralNet = new MultiLayerPerceptron(4, 9, 1);
        trainingSet = new TrainingSet();
        testSet = new TrainingSet();
        maxIterations = 100000;
        ((LMS) neuralNet.getLearningRule()).setMaxError(0.001);// 0-1
        ((LMS) neuralNet.getLearningRule()).setLearningRate(0.7);// 0-1
        ((LMS) neuralNet.getLearningRule()).setMaxIterations(maxIterations);

    }

}
