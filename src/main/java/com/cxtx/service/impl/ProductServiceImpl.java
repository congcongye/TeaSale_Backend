package com.cxtx.service.impl;

import com.cxtx.dao.ProductDao;
import com.cxtx.dao.ProductTypeDao;
import com.cxtx.dao.TeaSellerDao;
import com.cxtx.entity.Product;
import com.cxtx.entity.ProductType;
import com.cxtx.entity.TeaSeller;
import com.cxtx.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
@Service("ProductService")
//@Component
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductTypeDao productTypeDao;
    @Autowired
    private TeaSellerDao teaSellerDao;


    /**
     * 茶产品的新增,验证了product,productType,teaseller不能未空,product里面的填入信息,应该在前台进行验证
     * @param product
     * @return
     */
    @Override
    public Product newProduct(Product product){
        Product result = productDao.save(product);
        return result;
    }

    /**
     * 茶产品的批量修改只有(stock,price,startNum,discount,isFree,postage,deliverLimit,unit)可以修改,其它不能修改
     * @param products
     * @return
     */
    @Override
    public int updateProduct(List<Product> products){
        int succCount =0 ;
        for(Product product:products){
            if(null!=product&&product.getState()==0){//产品存在并且产品的状态为未上架
                if(isUnique(product)){
                    productDao.save(product);
                    succCount++;
                }
            }
        }
        return succCount;
    }

    /**
     * productType,teaSeller,level,locality,name(进行唯一性检查)
     * @param p
     * @return
     */
    public Boolean isUnique(Product p){
        List<Product> list = productDao.findByProductTypeAndTeaSellerAndLevelAndLocalityAndNameAndAlive(p.getProductType(),p.getTeaSeller(),p.getLevel(),p.getLocality(),p.getName(),1);
        boolean flag=false;
        if(null==list|| list.isEmpty()){
            return true;
        }
        if(list.size()==1){
            if(list.get(0).getId()==p.getId()){
                return true;
            }
        }
        return flag;
    }
    /**
     * 茶产品的批量上架
     * @param products
     * @return
     */
    @Override
    public int startSell(List<Product> products){
        int succCount=0;
        for(Product product:products){
            if(product!=null&&product.getAlive()==1&&product.getState()==0){
                product.setState(1);
                product.setCreateDate(new Date());//填入上架时间
                productDao.save(product);
                succCount++;
            }
        }
        return succCount;
    }


    /**
     * 对茶产品的条件查找
     * @param productType_id
     * @param remark
     * @param name
     * @param level
     * @param locality
     * @param stock
     * @param price
     * @param startNum
     * @param discount
     * @param isFree
     * @param teaSeller_name
     * @param pageIndex
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return
     * @throws ParseException
     */
    @Override
    public Page<Product> findByConditions(Long productType_id,String remark,String name,int level,String locality,double stock,double price,
                                          double startNum,double discount,int isFree,String teaSeller_name,int state,int pageIndex, int pageSize, String sortField, String sortOrder){
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.toUpperCase().equals("DESC")) {
            direction = Sort.Direction.DESC;
        }
        Sort sort = new Sort(direction, sortField);
        Specification<Product> specification = this.buildSpecifications(productType_id,remark,name,level,locality,stock,price,startNum,discount,isFree,teaSeller_name,state);
        return  productDao.findAll(Specifications.where(specification), new PageRequest(pageIndex, pageSize, sort));

    }
    private Specification<Product> buildSpecifications(Long productType_id,String remark,String name,int level,String locality,double stock,double price,
                                                       double startNum,double discount,int isFree,String teaSeller_name,int state) {
        final ProductType fproductType = productTypeDao.findByIdAndAlive(productType_id,1);
        final String fremark =remark;
        final String fname =name;
        final int flevel=level;
        final String flocality =locality;
        final double fstock =stock;
        final double fprice =price;
        final double fstartNum=startNum;
        final double fdiscount=discount;
        final int fisFree =isFree;
        final String fteaSeller_name=teaSeller_name;
        final int fstate=state;
        Specification<Product> specification = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if(null!=fproductType){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<ProductType>get("productType"),fproductType));
                }
                if(flevel>-1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("level"),flevel));
                }
                if(fstock>-1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("stock"),fstock));
                }
                if(fprice>-1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("price"),fprice));
                }
                if(fstartNum>-1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("startNum"),fstartNum));
                }
                if(fdiscount>-1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("discount"),fdiscount));
                }
                if(fisFree>-1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("isFree"),fisFree));
                }
                final String fremark =remark;
                final String fname =name;
                final String flocality =locality;
                final String fteaSeller_name=teaSeller_name;
                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("remark"),"%"+fremark+"%"));
                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("name"),"%"+fname+"%"));
                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("locality"),"%"+flocality+"%"));
                predicate.getExpressions().add(criteriaBuilder.like(root.<TeaSeller>get("teaseller").get("name"),"%"+fteaSeller_name+"%"));
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("alive"),1));
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("state"),fstate));
                return criteriaBuilder.and(predicate);
            }
        };
        return specification;
    }
}
