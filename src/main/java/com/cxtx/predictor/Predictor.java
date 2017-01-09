package com.cxtx.predictor;

/**
 * Created by jinchuyang on 17/1/5.
 */

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.cxtx.model.TeaModel;
import com.cxtx.utils.DateUtils;
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

    public List<List<TeaModel>> Predicte() throws BiffException, IOException {

        neuralNetConfig();
        double[] data ;
        List<TeaModel> teaModels = DataFromXlsFile.getTeaModels("whole.xls");
        List<List<TeaModel>> datas = parse(teaModels);
        for (List<TeaModel> list : datas){
            neuralNetConfig();
            data = getDataFromModel(list);
            setDataminDatamax(data);
            neuralNetTraining(data);
            double nextMonthPrice = neuralNetTesting();

            TeaModel teaModel = list.get(list.size()-1);
            TeaModel t = new TeaModel();
            t.date = DateUtils.nextMonth(teaModel.date);
            t.price = nextMonthPrice;
            t.province = teaModel.province;
            t.name = teaModel.name;
            t.level = teaModel.level;
            t.dateStr = teaModel.dateStr;
            list.add(t);
        }
        return datas;

    }

    private double[] getDataFromModel(List<TeaModel> list) {
        double[] data = new double[list.size()];
        for (int i = 0;i < list.size(); i++){
            data[i] = list.get(i).price;
        }
        return data;
    }

    private List<List<TeaModel>> parse(List<TeaModel> teaModels) {
        List<List<TeaModel>> datas = new ArrayList<List<TeaModel>>();
        TeaModel model = new TeaModel();
        model.name = "";
        model.level = -1;
        List<TeaModel> data = new ArrayList<TeaModel>();
        for (TeaModel teaModel : teaModels){
            if (!((teaModel.name.equals(model.name)) && (teaModel.level == model.level))){
                datas.add(data);
                model = teaModel;
                data = new ArrayList<TeaModel>();
            }
            data.add(teaModel);
        }
        datas.add(data);
        datas.remove(0);
        return datas;
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
