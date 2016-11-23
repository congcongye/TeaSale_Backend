package com.cxtx.service.impl;

import com.cxtx.dao.*;
import com.cxtx.entity.*;
import com.cxtx.model.IdModel;
import com.cxtx.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ycc on 16/11/23.
 */
@Service("CommentServiceImpl")
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentDao commentDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private TeaSalerDao teaSalerDao;

    @Autowired
    private ProductDao productDao;

    /**
     * 订单项打分(新建comment项,更新orderItem项)
     * @param orderItem
     * @param customer
     * @param content
     * @param score
     * @return
     */
    public Comment insertComment(OrderItem orderItem, Customer customer,String content,double score){
        orderItem.setIsComment(1);
        OrderItem newOrderItem = orderItemDao.save(orderItem);
        Comment comment =new Comment();
        comment.setCustomer(customer);
        comment.setOrderItem(newOrderItem);
        comment.setContent(content);
        comment.setScore(score);
        comment.setProduct(orderItem.getProduct());
        comment.setCreateDate(new Date());
        Comment result = commentDao.save(comment);
        return result;
    }

    /**
     * 评论的批量删除
     * @param list
     * @return
     */
    public int deleteComment(List<IdModel> list){
        int succCount=0;
        for(IdModel idModel:list){
            Comment comment =commentDao.findByIdAndAlive(idModel.id,1);
            if(comment!=null){
                comment.setAlive(0);
                commentDao.save(comment);
            }
            succCount++;
        }
        return succCount;
    }

    /**
     * 评论的条件查询
     * @param customer_id
     * @param content
     * @param lowScore
     * @param highScore
     * @param startDate
     * @param endDate
     * @param orderItem_id
     * @param teaSaler_id
     * @param product_id
     * @param pageIndex
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return
     */
    public Page<Comment> searchComment(Long customer_id,String content,double lowScore,double highScore,String startDate,String endDate,Long orderItem_id,Long teaSaler_id,Long product_id,int pageIndex, int pageSize, String sortField, String sortOrder){
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.toUpperCase().equals("DESC")) {
            direction = Sort.Direction.DESC;
        }
        Sort sort = new Sort(direction, sortField);
        Specification<Comment> specification = this.buildSpecifications(customer_id,content,lowScore,highScore,startDate,endDate,orderItem_id,customer_id,teaSaler_id,product_id);
        return  commentDao.findAll(Specifications.where(specification), new PageRequest(pageIndex, pageSize, sort));

    }
    private Specification<Comment> buildSpecifications(Long customer_id,String content,double lowScore,double highScore,String startDatestr,String endDatestr, Long orderItem_id,Long comment_id,Long teaSeller_id,Long product_id) {

        final Customer customer =customerDao.findByIdAndAlive(customer_id,1);
        final String startDatef =startDatestr;
        final String endDatef=endDatestr;
        final OrderItem orderItem =orderItemDao.findByIdAndAlive(orderItem_id,1);
        final double flowScore =lowScore;
        final double fhighScore=highScore;
        final TeaSaler teaSaler =teaSalerDao.findByIdAndStateAndAlive(teaSeller_id,1,1);
        final Product product =productDao.findByIdAndAlive(product_id,1);
        Specification<Comment> specification = new Specification<Comment>() {
            @Override
            public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(null!=customer){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<Customer>get("customer"),customer));
                }
                if(null!=orderItem){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<OrderItem>get("orderItem"),orderItem));
                }
                if(comment_id>0){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("id"),comment_id));
                }
                if(flowScore>-1){
                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("score"),flowScore));
                }
                if(fhighScore>-1){
                    predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("score"),fhighScore));
                }
                if(null!=teaSaler){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<Product>get("product").get("teaSaler"),teaSaler));
                }
                if(null!=product){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<Product>get("product"),product));
                }
                if (null != endDatef && !endDatef.equals("") && (null == startDatef || startDatef.equals(""))) {
                    Date endDate = null;
                    try {
                        endDate = sdf.parse(endDatef);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    predicate.getExpressions().add(criteriaBuilder.lessThan(root.<Date>get("createDate"), endDate));
                }
                if (null != startDatef && !startDatef.equals("") && (null == endDatef || endDatef.equals(""))) {
                    Date startDate = null;
                    try {
                        startDate = sdf.parse(startDatef);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createDate"), startDate));
                }

                if (null != endDatef && !endDatef.equals("") && null != startDatef && !startDatef.equals("")) {
                    Date endDate = null;
                    Date startDate = null;
                    try {
                        endDate = sdf.parse(endDatef);
                        startDate = sdf.parse(startDatef);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    predicate.getExpressions().add(criteriaBuilder.lessThan(root.<Date>get("createDate"), endDate));
                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createDate"), startDate));
                }
                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("content"),"%"+content+"%"));
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("alive"),1));
                return criteriaBuilder.and(predicate);
            }
        };
        return specification;
    }
}
