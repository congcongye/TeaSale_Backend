package com.cxtx.predictor;

/**
 * Created by jinchuyang on 17/1/5.
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cxtx.model.TeaModel;
import com.cxtx.utils.DateUtils;
import jxl.Sheet;
import jxl.Workbook;

import jxl.read.biff.BiffException;

public class DataFromXlsFile {

    public static List<TeaModel> getTeaModels(String xlsFile) throws BiffException, IOException {

       List<TeaModel> teaModels = new ArrayList<TeaModel>();
        Workbook workbook = Workbook.getWorkbook(new File(xlsFile));
        Sheet sheet = workbook.getSheet(0);
        int length = sheet.getColumn(0).length;
        for (int i =0; i < length; i++){
            TeaModel teaModel = new TeaModel();
            teaModel.name = sheet.getCell(0,i).getContents();
            teaModel.price = Double.parseDouble(sheet.getCell(1, i).getContents());
            teaModel.date = DateUtils.parse(sheet.getCell(2, i).getContents());
            teaModel.province = sheet.getCell(3,i).getContents();
            teaModel.level = Integer.parseInt(sheet.getCell(4,i).getContents());
            teaModels.add(teaModel);
        }

        workbook.close();


        return teaModels;
    }

    public static double[] GetData(String type, int year, String xlsFile) throws BiffException, IOException {

        double[] prices;
        Workbook workbook = Workbook.getWorkbook(new File(xlsFile));
        Sheet sheet = workbook.getSheet(0);
        int length = sheet.getColumn(0).length;
        int size = getSize4Arr(year, type);
        prices = new double[size];
//        for (int i =0; i < length; i++){
//            TeaModel teaModel = new TeaModel();
//            teaModel.name = sheet.getCell(0,i).getContents();
//            teaModel.price = Double.parseDouble(sheet.getCell(1, i).getContents());
//            teaModel.date = DateUtils.parse(sheet.getCell(2, i).getContents());
//            teaModel.province = sheet.getCell(3,i).getContents();
//            teaModel.level = Integer.parseInt(sheet.getCell(4,i).getContents());
//        }
        for (int i = 0; i < size; i++) {
            // System.out.println(sheet.getCell(2,i).getContents().substring(3,7));
            if (Integer.parseInt(sheet.getCell(2, i).getContents().substring(3, 7)) == 2016)
                prices[i] = Double.parseDouble(sheet.getCell(1, i).getContents());

        }
        workbook.close();


        return prices;
    }

    public static int getSize4Arr(int date, String type) throws BiffException, IOException {

        Workbook workbook = Workbook.getWorkbook(new File("whole.xls"));
        Sheet sheet = workbook.getSheet(0);
        int length = sheet.getColumn(0).length;
        int size = 0;
        for (int i = 0; i < length; i++) {

            int num = Integer.parseInt(sheet.getCell(2, i).getContents().substring(3, 7));
            String typ = sheet.getCell(0, i).getContents();

            if (num == date && typ.equals(type))
                size++;

        }
        return size;
    }

}

