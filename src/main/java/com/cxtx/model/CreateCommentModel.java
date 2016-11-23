package com.cxtx.model;

import com.cxtx.entity.Customer;
import com.cxtx.entity.OrderItem;

/**
 * Created by ycc on 16/11/23.
 */
public class CreateCommentModel {

    public Long orderItem_id;
    public Long customer_id;
    public String content;
    public double score=100;
}
