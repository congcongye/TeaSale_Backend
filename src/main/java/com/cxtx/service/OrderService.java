package com.cxtx.service;

import com.cxtx.entity.OrderEn;
import com.cxtx.model.CreateOrderModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.model.UpdateOrderModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by jinchuyang on 16/11/15.
 */
public interface OrderService {
   // OrderEn insertOrder(CreateOrderModel createOrderModel);

    Page<OrderEn> search(long customerId, long teaSalerId, String teaSalerName, int state, int isSend, int isConfirm, int isComment,
                         int customerDelete, int adminDelete, int salerDelete, int refund_state, String name, String address,
                         String tel, String beginDateStr, String endDateStr,
                         int pageIndex, int pageSize, String sortField, String sortOrder);

    Map<String,Object> insertOrders(List<CreateOrderModel> createOrderModels);

    OrderEn confirmOrder(UpdateOrderModel updateOrderModel);

    OrderEn sendOrder(UpdateOrderModel updateOrderModel);

    ServiceResult cancelOrder(Long id);

    ServiceResult payUnFinished(Long id);
}
