package com.cxtx.controller;

import com.cxtx.dao.CustomerDao;
import com.cxtx.dao.OrderItemDao;
import com.cxtx.entity.Comment;
import com.cxtx.entity.Customer;
import com.cxtx.entity.OrderItem;
import com.cxtx.model.CreateCommentModel;
import com.cxtx.model.IdModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ycc on 16/11/23.
 */
@Controller
public class CommentController extends ApiController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private CustomerDao customerDao;

    @RequestMapping(value = "/comment/new", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult newProduct(@RequestBody CreateCommentModel createCommentModel){
        checkParameter(createCommentModel!=null,"数据为空");
        OrderItem orderItem =orderItemDao.findByIdAndAlive(createCommentModel.orderItem_id,1);
        Customer customer=customerDao.findByIdAndAlive(createCommentModel.customer_id,1);
        checkParameter(orderItem!=null,"订单项为空");
        checkParameter(customer!=null,"消费者不存在");
        if(orderItem.getIsComment()==1){
            return ServiceResult.fail(500,"product has been commented");
        }
        Comment result =commentService.insertComment(orderItem,customer,createCommentModel.content,createCommentModel.score);
        return ServiceResult.success(result);
    }

    @RequestMapping(value = "/comment/delete", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult deleteProduct(@RequestBody List<IdModel> list){
        checkParameter(list!=null,"data is empty");
        int succCount = commentService.deleteComment(list);
        if(succCount!=list.size()){
            return ServiceResult.fail(500, "the num of succeed is "+succCount+" ; the fail number is "+(list.size()-succCount));
        }
        return ServiceResult.success("all succeed");
    }

    @RequestMapping(value = "/comment/search", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult findProductByConditions(@RequestParam(value = "customer_id",defaultValue = "-1") Long customer_id, @RequestParam(value = "content",defaultValue = "") String content, @RequestParam(value = "lowScore",defaultValue = "-1")double lowScore,
                                                 @RequestParam(value = "highScore",defaultValue = "-1")double highScore, @RequestParam(value = "startDate",defaultValue = "")String startDate,@RequestParam(value = "endDate",defaultValue = "") String endDate,
                                                 @RequestParam(value = "orderItem_id",defaultValue = "-1")Long orderItem_id, @RequestParam(value = "teaSaler_id",defaultValue = "-1")Long teaSaler_id, @RequestParam(value = "product_id",defaultValue = "-1")Long product_id,
                                                 @RequestParam(value="pageIndex", defaultValue="0") int pageIndex, @RequestParam(value="pageSize", defaultValue="10") int pageSize, @RequestParam(value="sortField", defaultValue="score") String sortField, @RequestParam(value="sortOrder", defaultValue="ASC") String sortOrder){

        Page<Comment> comments = commentService.searchComment(customer_id,content,lowScore,highScore,startDate,endDate,orderItem_id,teaSaler_id,product_id,pageIndex,pageSize, sortField,sortOrder);
        return ServiceResult.success(comments);
    }
}
