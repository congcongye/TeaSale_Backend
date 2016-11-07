package com.cxtx.controller;

import com.cxtx.model.ServiceResult;
import com.cxtx.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by ycc on 16/11/6.
 */
@Controller
public class ImageController extends  ApiController{

    @Autowired
    private ImageService imageService;

    /**
     * 上传logo文件,上传成功后，可以拿到上传的文件名
     * @param pictures
     * @param product_id
     * @param
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/image/upload", method = RequestMethod.POST)//,produces = "text/plain;charset=UTF-8"
    @ResponseBody
    public ServiceResult uploadLogo(@RequestParam("picture") MultipartFile [] pictures,
                             @RequestParam("product_id") Long product_id) throws IOException {//, HttpServletRequest request
//        String uploadPath = request.getSession().getServletContext().getRealPath("/");
        int succCount= imageService.uploadImage(pictures,product_id);
        if(succCount!=pictures.length){
            return ServiceResult.fail(500, "the num of succeed is "+succCount+" ; the fail number is "+(pictures.length-succCount));
        }
        return ServiceResult.success("all succeed");
    }
}
