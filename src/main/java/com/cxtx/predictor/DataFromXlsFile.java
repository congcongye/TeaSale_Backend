package com.cxtx.predictor;

/**
 * Created by jinchuyang on 17/1/5.
 */
import java.io.File;
import java.io.IOException;
import jxl.Sheet;
import jxl.Workbook;

import jxl.read.biff.BiffException;

public class DataFromXlsFile {

    public static double[] GetData(String type, int year, String xlsFile) throws BiffException, IOException {

        String type1 = "Biluochun", type2 = "Tieguanyin";
        String location1 = "Shandong Province", location2 = "Fujian Province";
        int date1 = 2015, date2 = 2016;

        double[] prices;
        Workbook workbook = Workbook.getWorkbook(new File(xlsFile));
        Sheet sheet = workbook.getSheet(0);
        int length = sheet.getColumn(0).length;
        int size = getSize4Arr(year, type);
        prices = new double[size];
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

