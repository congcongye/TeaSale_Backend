package com.cxtx.service.impl;

import com.cxtx.dao.ImageDao;
import com.cxtx.dao.ProductDao;
import com.cxtx.entity.Image;
import com.cxtx.entity.Product;
import com.cxtx.service.ImageService;
import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ycc on 16/10/30.
 */
@Service("ImageServiceImpl")
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageDao imageDao;
    @Autowired
    private ProductDao productDao;

    private Map<String, String> logoNameMap = new HashMap<String, String>();

    /**
     * 上传图片文件
     * @param pictures
     * @param product_Id
     * @return
     * @throws IOException
     */
    public int uploadImages(MultipartFile pictures[],Long product_Id) throws IOException {
        //获取存储路径
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cxtx.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String folderPath = p.getProperty("logoPath");
        File folder = new File(folderPath);
        if(!folder.exists()&&!folder.isDirectory()){
            folder.mkdir();
        }
        int succCount=0;
        //获取图片后缀
        Pattern pictureNamePattern = Pattern.compile(".*(\\.[a-zA-Z\\s]+)");
        if(pictures==null){
           return succCount;
        }
        for(MultipartFile multipartFile : pictures){
            Matcher matcher = pictureNamePattern.matcher(multipartFile.getOriginalFilename());
            if (matcher.find()) {//如果是图片的话
                String uuid = UUID.randomUUID().toString().replaceAll("-","");//让图片名字不同
                //保存文件
                File pictureToStore = null;
                File pic = null;
                InputStream in=null;
                OutputStream op=null;
                try {
                    pictureToStore = File.createTempFile(uuid, matcher.group(1),folder);
                    pic = new File(folderPath+File.separator + uuid + matcher.group(1));
                    in = multipartFile.getInputStream();
                    op=new FileOutputStream(pictureToStore);
                    byte [] buffer =new byte[1024];
                    int num=0;
                    while((num= in.read(buffer))!=-1){
                        op.write(buffer,0,num);
                    }
                    pictureToStore.renameTo(pic);
                }finally {
                    if(in!=null){
                        in.close();
                    }
                    if(op!=null){
                        op.close();
                    }
                }
                Image image = new Image();
                Product product = productDao.findByIdAndAlive(product_Id,1);
                if(product!=null){
                    image.setProduct(product);
                    image.setUrl(pictureToStore.getAbsolutePath());
                    image.setName(multipartFile.getOriginalFilename());
                    imageDao.save(image);
                    succCount++;
                }
            }
        }
        return succCount;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        //获取存储路径
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cxtx.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String folderPath = p.getProperty("headPicPath");
        File folder = new File(folderPath);
        if (multipartFile == null){
            return null;
        }
        //获取图片后缀
        Pattern pictureNamePattern = Pattern.compile(".*(\\.[a-zA-Z\\s]+)");
        Matcher matcher = pictureNamePattern.matcher(multipartFile.getOriginalFilename());
        if (matcher.find()) {//如果是图片的话
            String uuid = UUID.randomUUID().toString().replaceAll("-","");//让图片名字不同
            //保存文件
            File pictureToStore = File.createTempFile(uuid, matcher.group(1),folder);
            File pic = new File(folderPath+File.separator + uuid + matcher.group(1));
            InputStream in = multipartFile.getInputStream();
            //FileUtils.cop(in, pictureToStore);
            pictureToStore.renameTo(pic);
            return  pictureToStore.getAbsolutePath();
        }
        return null;
    }




////    public int newImage (Image [] image)

}
