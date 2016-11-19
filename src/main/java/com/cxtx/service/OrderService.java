package com.cxtx.service;

import com.cxtx.entity.OrderEn;
import com.cxtx.model.CreateOrderModel;
import org.springframework.data.domain.Page;

/**
 * Created by jinchuyang on 16/11/15.
 */
public interface OrderService {
    OrderEn insertOrder(CreateOrderModel createOrderModel);

    Page<OrderEn> search(long customerId, long teaSalerId, int state, int isSend, int isConfirm, int isComment, int type, int customerDelete, int adminDelete, int salerDelete, int refund_state, String name, String address, String tel, int pageIndex, int pageSize, String sortField, String sortOrder);
}
