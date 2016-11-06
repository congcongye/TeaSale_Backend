package com.cxtx.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by ycc on 16/10/30.
 */
public interface ImageService {

    int uploadImage(MultipartFile pictures[],Long product_Id) throws IOException;
}
