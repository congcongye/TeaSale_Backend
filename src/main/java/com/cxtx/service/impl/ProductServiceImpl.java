package com.cxtx.service.impl;

import com.cxtx.dao.ProductDao;
import com.cxtx.dao.ProductTypeDao;
import com.cxtx.dao.TeaSellerDao;
import com.cxtx.entity.Product;
import com.cxtx.entity.ProductType;
import com.cxtx.entity.TeaSeller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ycc on 16/10/24.
 */
public class ProductServiceImpl {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductTypeDao productTypeDao;
    @Autowired
    private TeaSellerDao teaSellerDao;


    /**
     * 茶产品的新增,验证了product,productType,teaseller不能未空,product里面的填入信息,应该在前台进行验证
     * @param product
     * @param productType_id
     * @param teaSeller_id
     * @return
     */
    public Map<String,Object> newProduct(Product product,Long productType_id,Long teaSeller_id){
        Map<String,Object> result =new HashMap<String,Object>();
        if(product==null){
            result.put("code",2);
            result.put("msg","产品信息未传入");
            return result;
        }
        ProductType pt =productTypeDao.findByIdAndAlive(productType_id,1);
        TeaSeller ts =teaSellerDao.findByIdAndStateAndAlive(teaSeller_id,1,1);//存在且审核通过的茶农
        if(pt==null){
            result.put("code",3);
            result.put("msg","产品类型信息不存在");
            return result;
        }
        if(ts ==null){
            result.put("code",3);
            result.put("msg","茶农不存在或未审核通过");
            return result;
        }
        if(product==null){
            result.put("code",3);
            result.put("msg","产品信息未填");
            return result;
        }
        product.setProductType(pt);
        product.setTeaSeller(ts);
        product.setAlive(1);//存在
        product.setState(0);//未上架
        productDao.save(product);
        result.put("code",1);
        result.put("msg","新增产品成功");
        return result;
    }

    /**
     * 茶产品的批量修改
     * @param products
     * @return
     */
    public Map<String,Object> updateProduct(List<Product> products){
        Map<String,Object> result =new HashMap<String,Object>();
        if(products==null ||products.isEmpty()){
            result.put("code",2);
            result.put("msg","茶产品不存在或者处于销售状态,无法修改");
            return result;
        }
        int succCount =0 ;
        for(Product product:products){
            if(null!=product&&product.getState()==0){//产品存在并且产品的状态为未上架
                productDao.save(product);
                succCount++;
            }
        }
        if(products.size()!=succCount){
            result.put("code",3);
            result.put("msg","修改成功的数目为: "+succCount+" ; 修改失败的数目为: "+(products.size()-succCount));
            return result;
        }
        result.put("code",1);
        result.put("msg","茶产品信息全部修改成功");
        return result;
    }

    /**
     * 茶产品的批量上架
     * @param products
     * @return
     */
    public Map<String,Object> startSell(List<Product> products){
        Map<String,Object> result =new HashMap<String,Object>();
        if(products==null ||products.isEmpty()){
            result.put("code",2);
            result.put("msg","茶产品信息未传入");
            return result;
        }
        int succCount=0;
        for(Product product:products){
            if(product!=null&&product.getAlive()==1){
                product.setState(1);
                product.setCreateDate(new Date());//填入上架时间
                productDao.save(product);
                succCount++;
            }
        }
        if(products.size()!=succCount){
            result.put("code",3);
            result.put("msg","上架成功的数目: "+succCount+" ; 上架失败的数目是: "+(products.size()-succCount));
            return result;
        }
        result.put("code",1);
        result.put("msg","全部成功");
        return result;
    }

//    private Specification<Product> buildSpecifications() {
//        final ReceiveFileType receiveFileType = receiveFileTypeDao.findOne(receiveFileType_id);
//        final Project project=projectDao.findOne(project_id);
//        final EngNoInfo engNoInfo=engNoInfoDao.findOne(engNoInfo_id);
//        final int attachmentStatef =attachmentState;
//        final int replyStatusf =replyStatus;
//        final int sendStatef =sendState;
//        final String endDateStr1=endDateStr;
//        final String startDateStr1=startDateStr;
//        final String codeStr=code;
//        final String nameStr=name;
//        final String dcncodeStr=dcncode;
//
//        Specification<ReceiveFile> specification = new Specification<ReceiveFile>() {
//            @Override
//            public Predicate toPredicate(Root<ReceiveFile> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                Predicate predicate = criteriaBuilder.conjunction();
//                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//                if (receiveFileType != null) {
//                    predicate.getExpressions().add(criteriaBuilder.equal(root.<ReceiveFileType>get("receiveFileType"),receiveFileType));
//                }
//                if(null != project){
//                    predicate.getExpressions().add(criteriaBuilder.equal(root.<Project>get("project"),project));
//                }
//                if(null != engNoInfo){
//                    predicate.getExpressions().add(criteriaBuilder.equal(root.<EngNoInfo>get("engNoInfo"),engNoInfo));
//                }
//                if(attachmentStatef > -1){
//                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("attachmentState"),attachmentStatef));
//                }
//                // replyStatus作为计数器后的修改
//                if(replyStatusf == 0){
//                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("replyStatus"),0));
//                } else if (replyStatusf > 0) {
//                    predicate.getExpressions().add(criteriaBuilder.greaterThan(root.<Integer>get("replyStatus"), 0));
//                }
//                if(sendStatef > -1){
//                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("sendState"),sendStatef));
//                }
//                if (null != endDateStr1 && !endDateStr1.equals("") && (null == startDateStr1 || startDateStr1.equals(""))) {
//                    Date endDate = null;
//                    try {
//                        endDate = sdf.parse(endDateStr1);
//                        Long afterTime=endDate.getTime()+24*60*60*1000;
//                        endDate=new Date(afterTime);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    predicate.getExpressions().add(criteriaBuilder.lessThan(root.<Date>get("receiveDate"), endDate));
//                }
//                if (null != startDateStr1 && !startDateStr1.equals("") && (null == endDateStr1 || endDateStr1.equals(""))) {
//                    Date startDate = null;
//                    try {
//                        startDate = sdf.parse(startDateStr1);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("receiveDate"), startDate));
//
//                }
//                if (null != endDateStr1 && !endDateStr1.equals("") && null != startDateStr1 && !startDateStr1.equals("")) {
//                    Date endDate = null;
//                    Date startDate = null;
//                    try {
//                        endDate = sdf.parse(endDateStr1);
//                        startDate = sdf.parse(startDateStr1);
//                        Long afterTime=endDate.getTime()+24*60*60*1000;
//                        endDate=new Date(afterTime);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    predicate.getExpressions().add(criteriaBuilder.lessThan(root.<Date>get("receiveDate"), endDate));
//                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("receiveDate"), startDate));
//                }
//                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("code"),"%"+codeStr+"%"));
//                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("name"),"%"+nameStr+"%"));
//                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("dcncode"),"%"+dcncodeStr+"%"));
//                predicate.getExpressions().add(criteriaBuilder.equal(root.get("alive"),1));
//                return criteriaBuilder.and(predicate);
//            }
//        };
//        return specification;
//    }
}
