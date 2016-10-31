package com.cxtx.service.impl;

import com.cxtx.dao.ImageDao;
import com.cxtx.dao.ProductDao;
import com.cxtx.entity.Image;
import com.cxtx.entity.Product;
import com.cxtx.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ycc on 16/10/30.
 */
@Service("ImageServiceImpl")
public class ImageServiceImpl implements ImageService{
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private ProductDao productDao;

//    @Override
//    public int newImage(List<Image> lis){
//        for(Map<String,Object>map :list){
//            if(map.containsKey("name")&&map.containsKey("url")&&map.containsKey("product_id")){
//                String name=(String)map.get("name");
//                String url=(String)map.get("url");
//                Long product_id = Long.valueOf((long)(map.get("product_id")));
//                Product p=productDao.findByIdAndAlive(product_id,1);
//                if(p!=null){
//                    Image image= new Image();
//                    image.setName(name);
//                    image.setUrl(url);
//                    image.setProduct(p);
//                    imageDao.save(image);
//                }
//        }
//    }

}
