package com.cxtx.service;

import com.cxtx.entity.Comment;
import com.cxtx.entity.Customer;
import com.cxtx.entity.OrderItem;
import com.cxtx.model.IdModel;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by ycc on 16/11/23.
 */
public interface CommentService {
     Comment insertComment(OrderItem orderItem, Customer customer, String content, double score);
     int deleteComment(List<IdModel> list);
     Page<Comment> searchComment(Long customer_id, String content, double lowScore, double highScore, String startDate, String endDate, Long orderItem_id, Long teaSaler_id, Long product_id, int pageIndex, int pageSize, String sortField, String sortOrder);

    }
