package com.cxtx.model;

import java.util.List;

/**
 * Created by jinchuyang on 16/11/15.
 */
public class CreateOrderModel {
    public long teaSalerId;
    public long customerId;
    public String name;
    public String address;
    public String zip;
    public String tel;
    public int type;
    public long crowdFundingId;
    /**
     * long productId;
     * double num;
     */
    public List<CreateOrderItemModel> createOrderItemModels;
}
