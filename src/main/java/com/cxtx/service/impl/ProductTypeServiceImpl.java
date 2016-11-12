package com.cxtx.service.impl;

import com.cxtx.dao.ProductTypeDao;
import com.cxtx.entity.ProductType;
import com.cxtx.model.UpdateProductTypeModel;
import com.cxtx.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ycc on 16/10/22.
 */
@Service("ProductTypeService")
public class ProductTypeServiceImpl implements ProductTypeService{

    @Autowired
    private ProductTypeDao productTypeDao;

    /**
     * 产品类型的新增和修改(修改只能把state变成0)
     * @param name
     * @param descript
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @Override
    public ProductType newProductType(String name,String descript,MultipartFile multipartFile) throws IOException {
        ProductType result=null;
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
        //获取图片后缀
        Pattern pictureNamePattern = Pattern.compile(".*(\\.[a-zA-Z\\s]+)");
        ProductType productType=new ProductType();
        productType.name=name;
        productType.descript=descript;
        if(Unique(productType)){
            Matcher matcher = pictureNamePattern.matcher(multipartFile.getOriginalFilename());
            String uuid="";
            if (matcher.find()) {//如果是图片的话
                uuid = UUID.randomUUID().toString().replaceAll("-","");//让图片名字不同
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
                    productType.url=uuid + matcher.group(1);
                    }finally {
                        if(in!=null){
                            in.close();
                        }
                        if(op!=null){
                            op.close();
                        }
                    }
                }
             result=productTypeDao.save(productType);
            }
        return result;
    }

    /**
     * 产品类型的修改
     * @param list
     * @return
     */
    public int updateProductType(List<UpdateProductTypeModel> list){
        int succCount=0;
        for(UpdateProductTypeModel updateProductTypeModel:list){
            ProductType productType =productTypeDao.findByIdAndAlive(updateProductTypeModel.id,1);
            if(productType!=null){
                productType.state=updateProductTypeModel.state;
                productTypeDao.save(productType);
                succCount++;
            }
        }
        return succCount;
    }

    private boolean Unique(ProductType productType){//判断茶产品类型不存在或者是当前要修改的茶产品类型
        List<ProductType> ps=productTypeDao.findByNameAndDescriptAndStateAndAlive(productType.name,productType.descript,productType.state,1);
        boolean flag=false;
        if(null==ps || ps.isEmpty()){
            return true;
        }
        if(ps.size()==1){
            if(ps.get(0).id==productType.id){
                return true;
            }
        }
        return flag;
    }
    @Override
    public List<ProductType> getAllProductType(int state){
        List<ProductType> list = productTypeDao.findByAliveAndState(1,state);//存在且可用的全部茶产品类型,可以使用的state=1
        if(list==null){
            list=new ArrayList<ProductType>();
        }
        return list;
    }




}

