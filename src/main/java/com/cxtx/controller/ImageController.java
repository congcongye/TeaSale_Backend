package com.cxtx.controller;

import com.cxtx.dao.ProductDao;
import com.cxtx.entity.Image;
import com.cxtx.entity.Product;
import com.cxtx.model.CreateImageModel;
import com.cxtx.model.CreateManagerModel;
import com.cxtx.model.DeleteImageModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.service.ImageService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by ycc on 16/11/6.
 */
@Controller
public class ImageController extends  ApiController{

    @Autowired
    private ImageService imageService;
    @Autowired
    private ProductDao productDao;

    /**
     * 上传logo文件,上传成功后，可以拿到上传的文件名(image新增和修改)
     * @param pictures
     * @param product_id
     * @param image_id
     * @param type
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/image/upload", method = RequestMethod.POST)//,produces = "text/plain;charset=UTF-8"
    @ResponseBody
    public ServiceResult uploadLogo(@RequestParam("pictures") MultipartFile [] pictures,
                                   @RequestParam(value = "product_id",defaultValue = "-1")Long product_id,
                                    @RequestParam(value = "image_id",defaultValue = "-1")Long image_id,@RequestParam(value = "type",defaultValue = "0")int type) throws IOException {//, HttpServletRequest request
        checkParameter(pictures!=null,"pictures are empty");
        checkParameter(pictures.length!=0,"pictures are empty");
        Product product=productDao.findByIdAndAlive(product_id,1);
        checkParameter(product!=null,"product is empty");
        int succCount= imageService.uploadImages(pictures,product,image_id,type);
        if(succCount!=pictures.length){
            return ServiceResult.fail(500, "the num of succeed is "+succCount+" ; the fail number is "+(pictures.length-succCount));
        }
        return ServiceResult.success("all succeed ");
    }

    /**
     * 通过url获取图片
     * @param response
     * @param url
     * @throws IOException
     */
    @RequestMapping(value = "/image/getByUrl", method = RequestMethod.GET)
    @ResponseBody
    public void downloadLogo(HttpServletResponse response,
                             @RequestParam(value="url") String url) throws IOException {// @PathVariable(value="url") String url
        OutputStream os = response.getOutputStream();
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cxtx.properties");
            Properties p = new Properties();
            p.load(inputStream);
            String folderPath = p.getProperty("logoPath");
            response.reset();
            File file = new File(folderPath+File.separator+url);
            response.setContentType("application/octet-stream; charset=UTF-8");
            os.write(FileUtils.readFileToByteArray(file));
            os.flush();
        } finally {
            if(null != os) {
                os.close();
            }
        }
    }

    /**
     * 图片的批量删除
     * @param list
     * @return
     */
    @RequestMapping(value = "/image/delete", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult startSell(@RequestBody List<DeleteImageModel> list){
        checkParameter(list!=null&&!list.isEmpty(),"images are empty");
        int succCount = imageService.delete(list);
        if(succCount!=list.size()){
            return ServiceResult.fail(500, "the num of succeed is "+succCount+" ; the fail number is "+(list.size()-succCount));
        }
        return ServiceResult.success("all succeed");
    }

    /**
     * 通过产品获得所有的图片
     * @param product_id
     * @return
     */
    @RequestMapping(value = "/image/getImageByProduct", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getAllByProduct(@RequestParam (value="product_id",defaultValue = "-1")Long product_id,
                                         @RequestParam(value = "type",defaultValue = "-1")int type){
        Product product =productDao.findByIdAndAlive(product_id,1);
        checkParameter(product!=null,"product is empty");
        List<Image> list =imageService.getAllByProductAndTypeAndAlive(product,type,1);
        return ServiceResult.success(list);
    }

}
