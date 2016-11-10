package com.cxtx.service;

import com.cxtx.entity.ProductType;
import com.cxtx.model.CreateProductTypeModel;
import com.cxtx.model.UpdateProductTypeModel;

import java.io.IOException;
import java.util.List;

/**
 * Created by ycc on 16/10/22.
 */
public interface ProductTypeService {

     int newProductType(List<CreateProductTypeModel> productTypes) throws IOException;
     int updateProductType(List<UpdateProductTypeModel> list);
     List<ProductType> getAllProductType(int state);
}
