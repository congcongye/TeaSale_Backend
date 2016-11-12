package com.cxtx.service;

import com.cxtx.entity.ProductType;
import com.cxtx.model.CreateProductTypeModel;
import com.cxtx.model.UpdateProductTypeModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by ycc on 16/10/22.
 */
public interface ProductTypeService {

     ProductType newProductType(String name,String descript,MultipartFile multipartFile) throws IOException;
     int updateProductType(List<UpdateProductTypeModel> list);
     List<ProductType> getAllProductType(int state);
}
