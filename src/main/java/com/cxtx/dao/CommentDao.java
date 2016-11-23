package com.cxtx.dao;

import com.cxtx.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by ycc on 16/11/23.
 */

public interface CommentDao extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

    Comment findByIdAndAlive(Long id, int alive);
}
