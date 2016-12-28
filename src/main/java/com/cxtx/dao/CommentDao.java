package com.cxtx.dao;

import com.cxtx.entity.Comment;
import com.cxtx.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by ycc on 16/11/23.
 */

public interface CommentDao extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

    Comment findByIdAndAlive(Long id, int alive);

    //@Query("SELECT c FROM Comment c WHERE c.alive = :alive AND c.product.id = :productId")
    List<Comment> findByProductAndAlive(Product product, int alive);
}
