package com.cxtx.service;

import com.cxtx.entity.Image;
import com.cxtx.entity.Product;
import com.cxtx.model.DeleteImageModel;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by ycc on 16/10/30.
 */
public interface ImageService {

     int uploadImages(MultipartFile pictures[], Product product, Long image_id,int type) throws IOException;
     int delete (List<DeleteImageModel> list);
     List<Image> getAllByProductAndTypeAndAlive(Product product,int type,int alive);
}
