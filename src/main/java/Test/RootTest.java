package Test;

import com.cxtx.hello.Application;
import com.cxtx.predictor.Predictor;
import jxl.read.biff.BiffException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jinchuyang on 17/1/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class RootTest {

    @Test
    public void test1() throws IOException, BiffException {
//        DecimalFormat df   = new DecimalFormat("######0.00");
//        String[] types ={"West Lake Longjing","Tieguanyin","Biluochun"};
//        Map<String, Double> result = new HashMap<String, Double>();
//        Predictor predictor = new Predictor();
//        for (String type : types) {
//            double price = predictor.Predicte(type);
//            result.put(type, Double.parseDouble(df.format(price)));
//        }
//        String json = com.alibaba.fastjson.JSON.toJSONString(result,true);
//        File file = new File("src/main/resources/price.properties");
//        if (!file.exists()){
//            file.createNewFile();
//        }
//        //System.out.println(file.exists());
//        FileOutputStream oFile = new FileOutputStream(file);
//        Properties properties = new Properties();
//        properties.setProperty("price",json);
//        properties.store(oFile,"predicate price");
//        //oFile.flush();
//        oFile.close();
    }

    @Test
    public void test2() throws IOException {
        //this.getClass().getClassLoader().getClass().getResourceAsStream("price.properties");
        File file = new File("src/main/resources/price.properties");
        if (!file.exists()){
            file.createNewFile();
        }
        //System.out.println(file.exists());
        FileOutputStream oFile = new FileOutputStream(file);
        Properties properties = new Properties();
        properties.setProperty("price","114");
        properties.store(oFile,"test");
        //oFile.flush();
        oFile.close();

    }
}