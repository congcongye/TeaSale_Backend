package com.cxtx.service;

import com.cxtx.entity.Account;
import com.cxtx.entity.Image;
import com.cxtx.entity.Product;
import com.cxtx.entity.TeaSaler;
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

    int uploadHeadPic(MultipartFile picture, Account account) throws IOException;

    int uploadLicencePic(MultipartFile picture, TeaSaler teaSaler) throws IOException;

    int uploadBase64LicencePic(String picture, TeaSaler teaSaler);
}
