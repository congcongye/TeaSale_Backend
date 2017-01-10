package com.cxtx.predictor;

import com.cxtx.model.TeaModel;
import jxl.read.biff.BiffException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by jinchuyang on 17/1/9.
 */
public class PredicateUtils {
    public  void doPredicate() throws IOException, BiffException {
        Predictor predictor = new Predictor();

        List<List<TeaModel>> datas = predictor.Predicte();
        List<TeaModel> list = new ArrayList<TeaModel>();
        for (List<TeaModel> teaModelList : datas){
            for (TeaModel teaModel : teaModelList){
                list.add(teaModel);
            }
        }
        String json = com.alibaba.fastjson.JSON.toJSONString(list,true);

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cxtx.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String folderPath = p.getProperty("predicateFile");
        File file=new File(folderPath);

        if (!file.exists()){
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(json);
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
