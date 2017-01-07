package Test;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by jinchuyang on 17/1/7.
 */
public class GeneralTest extends RootTest{
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
