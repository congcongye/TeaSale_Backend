package com.cxtx.dao;

import com.cxtx.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ycc on 16/10/23.
 */
public interface ImageDao extends JpaRepository<Image,Long> {
}
