package com.cxtx.service.impl;

import com.cxtx.dao.AccountDao;
import com.cxtx.dao.ImageDao;
import com.cxtx.dao.ProductDao;
import com.cxtx.dao.TeaSalerDao;
import com.cxtx.entity.Account;
import com.cxtx.entity.Image;
import com.cxtx.entity.Product;
import com.cxtx.entity.TeaSaler;
import com.cxtx.model.DeleteImageModel;
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
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TeaSalerDao teaSalerDao;


    /**
     * 创建和修改image
     * @param pictures
     * @param product
     * @param image_id
     * @return
     * @throws IOException
     */
    public int uploadImages(MultipartFile pictures[],Product product,Long image_id,int type) throws IOException {
        //获取存储路径
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cxtx.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String folderPath = p.getProperty("picPath");
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
                Image image = imageDao.findByIdAndAlive(image_id,1);
                if(image==null){ //image不存在修改
                    image=new Image();
                }
                if(product!=null){
                    image.setProduct(product);
                    image.setUrl(uuid + matcher.group(1));
                    image.setName(multipartFile.getOriginalFilename());
                    image.setCreateDate(new Date());
                    image.setType(type);
                    imageDao.save(image);
                    if(type==1){//如果主图,则更新product中主图的url
                        product.setUrl(uuid + matcher.group(1));
                        productDao.save(product);
                    }
                    succCount++;
                }
            }
        }
        return succCount;
    }

    @Override
    public int uploadHeadPic(MultipartFile picture, Account account) throws IOException{
        //获取存储路径
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cxtx.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String folderPath = p.getProperty("picPath");
        File folder = new File(folderPath);
        if(!folder.exists()&&!folder.isDirectory()){
            folder.mkdir();
        }
        int succCount=0;
        //获取图片后缀
        Pattern pictureNamePattern = Pattern.compile(".*(\\.[a-zA-Z\\s]+)");
        if(picture==null){
            return succCount;
        }
        Matcher matcher = pictureNamePattern.matcher(picture.getOriginalFilename());
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
                in = picture.getInputStream();
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
            account.setHeadURL(uuid + matcher.group(1));
            accountDao.save(account);
            return 1;
        }
        return 0;
    }

    @Override
    public int uploadLicencePic(MultipartFile picture, TeaSaler teaSaler) throws  IOException{
        //获取存储路径
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cxtx.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String folderPath = p.getProperty("picPath");
        File folder = new File(folderPath);
        if(!folder.exists()&&!folder.isDirectory()){
            folder.mkdir();
        }
        int succCount=0;
        //获取图片后缀
        Pattern pictureNamePattern = Pattern.compile(".*(\\.[a-zA-Z\\s]+)");
        if(picture==null){
            return succCount;
        }
        Matcher matcher = pictureNamePattern.matcher(picture.getOriginalFilename());
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
                in = picture.getInputStream();
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
            teaSaler.setLicenseUrl(uuid + matcher.group(1));
            teaSalerDao.save(teaSaler);
            return 1;
        }
        return 0;
    }

    @Override
    public int uploadBase64LicencePic(String picture, TeaSaler teaSaler) {
//        //获取存储路径
//        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cxtx.properties");
//        Properties p = new Properties();
//        try {
//            p.load(inputStream);
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        String folderPath = p.getProperty("picPath");
//        File folder = new File(folderPath);
//        if(!folder.exists()&&!folder.isDirectory()){
//            folder.mkdir();
//        }
//        int succCount=0;
//        //获取图片后缀
//        Pattern pictureNamePattern = Pattern.compile(".*(\\.[a-zA-Z\\s]+)");
//        if(picture==null){
//            return succCount;
//        }
        return 0;
    }

    /**
     *image的批量删除
     * @param list
     * @return
     */
    public int delete (List<DeleteImageModel> list){
        int succCount=0;
        if(list==null || list.isEmpty()){
            return succCount;
        }
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cxtx.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String folderPath = p.getProperty("picPath");
        for(DeleteImageModel model:list){
            Image image=imageDao.findOne(model.id);
            if(image!=null){
                File file = new File(folderPath+File.separator+image.getUrl());
                if(file!=null){
                    file.delete();
                }
                image.setAlive(0);//逻辑删除
                imageDao.save(image);
                succCount++;
            }
        }
        return succCount;
    }


    /**
     * 获取某个产品的所有图片(type=1主图  type=0其它图片  type=-1查询全部图片
     * @param product
     * @return
     */
    public List<Image> getAllByProductAndTypeAndAlive(Product product,int type,int alive){
        List<Image> list = new ArrayList<Image> ();
        if(type>-1){//传入类型时
            list = imageDao.findByProductAndTypeAndAlive(product,type,1);
        }else{
            list = imageDao.findByProductAndAlive(product,1);
        }
        return list;
    }


}
