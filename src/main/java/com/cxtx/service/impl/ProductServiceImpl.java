package com.cxtx.service.impl;

import com.cxtx.dao.ProductDao;
import com.cxtx.dao.ProductTypeDao;
import com.cxtx.dao.TeaSalerDao;
import com.cxtx.entity.Product;
import com.cxtx.entity.ProductType;
import com.cxtx.entity.TeaSaler;
import com.cxtx.model.CreateProductModel;
import com.cxtx.model.StartSellProductModel;
import com.cxtx.service.ProductService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ycc on 16/10/24.
 */
@Service("ProductServiceImpl")
//@Component
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductTypeDao productTypeDao;
    @Autowired
    private TeaSalerDao teaSalerDao;


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
    public List<Product> updateProduct(List<CreateProductModel> products){//修改后填入的信息
        int succCount =0 ;
        List<Product> result =new ArrayList<Product>();
        for(CreateProductModel product:products){
            if(null!=product){
                Product pt =productDao.findByIdAndAlive(product.id,1);
                if(pt!=null&&pt.getState()==0){//商品存在且未上架
                    if(product.stock!=-1){
                        pt.setStock(product.stock);
                    }
                    if(product.price!=-1){
                        pt.setPrice(product.price);
                    }
                    if(product.startNum!=-1){
                        pt.setStartNum(product.startNum);
                    }
                    if(product.discount!=-1){
                        pt.setDiscount(product.discount);
                    }
                    if(product.isFree!=-1){
                        pt.setIsFree(product.isFree);
                    }
                    if(product.postage!=-1){
                        pt.setPostage(product.postage);
                    }
                    if(product.deliverLimit!=-1){
                        pt.setDeliverLimit(product.deliverLimit);
                    }
                    if(!product.unit.equals("")){
                        pt.setUnit(product.unit);
                    }
                    if(isUnique(pt)){
                        Product product1= productDao.save(pt);
                        result.add(product1);
                        succCount++;
                    }
                }
            }
        }
        return result;
    }

    /**
     * productType,teaSeller,level,locality,name(进行唯一性检查)
     * @param p
     * @return
     */
    public Boolean isUnique(Product p){
        List<Product> list = productDao.findByProductTypeAndTeaSalerAndLevelAndLocalityAndNameAndAlive(p.getProductType(),p.getTeaSaler(),p.getLevel(),p.getLocality(),p.getName(),1);
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
    public int startSell(List<StartSellProductModel> products){
        int succCount=0;
        for(StartSellProductModel sdm:products){
            Product product=productDao.findByIdAndAlive(sdm.id,1);
            if(product!=null&&product.getState()==0){
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
     * @param lowPrice
     * @param highPrice
     * @param startNum
     * @param discount
     * @param isFree
     * @param teaSeller_name
     * @param state
     * @param pageIndex
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return
     */
    @Override
    public Page<Product> findByConditions(Long productType_id,String remark,String name,int level,String locality,double stock,double lowPrice,double highPrice,
                                          double startNum,double discount,int isFree,String teaSeller_name,int state,Long teaSaler_id,int pageIndex, int pageSize, String sortField, String sortOrder){
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.toUpperCase().equals("DESC")) {
            direction = Sort.Direction.DESC;
        }
        Sort sort = new Sort(direction, sortField);
        Specification<Product> specification = this.buildSpecifications(productType_id,remark,name,level,locality,stock,lowPrice,highPrice,startNum,discount,isFree,teaSeller_name,state,teaSaler_id);
        return  productDao.findAll(Specifications.where(specification), new PageRequest(pageIndex, pageSize, sort));

    }
    private Specification<Product> buildSpecifications(Long productType_id,String remark,String name,int level,String locality,double stock,double lowPrice,double highPrice,
                                                       double startNum,double discount,int isFree,String teaSeller_name,int state,Long teaSaler_id) {
        final ProductType fproductType = productTypeDao.findByIdAndAlive(productType_id,1);
        final String fremark =remark;
        final String fname =name;
        final int flevel=level;
        final String flocality =locality;
        final double fstock =stock;
        final double flowPrice =lowPrice;
        final double fhighPrice =highPrice;
        final double fstartNum=startNum;
        final double fdiscount=discount;
        final int fisFree =isFree;
        final String fteaSeller_name=teaSeller_name;
        final int fstate=state;
        final TeaSaler fteaSaler =teaSalerDao.findByIdAndStateAndAlive(teaSaler_id,1,1);
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
                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("stock"),fstock));
                }
                if(flowPrice>-1){
                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"),flowPrice));
                }
                if(fhighPrice>-1){
                    predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("price"),fhighPrice));
                }
                if(fstartNum>-1){
                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("startNum"),fstartNum));
                }
                if(fdiscount>-1){
                    predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("discount"),fdiscount));
                }
                if(fisFree>-1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("isFree"),fisFree));
                }
                if(fstate>-1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("state"),fstate));
                }
                if(null!=fteaSaler){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<TeaSaler>get("teaSaler"),fteaSaler));
                }
                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("remark"),"%"+fremark+"%"));
                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("name"),"%"+fname+"%"));
                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("locality"),"%"+flocality+"%"));
                predicate.getExpressions().add(criteriaBuilder.like(root.<TeaSaler>get("teaSaler").get("name"),"%"+fteaSeller_name+"%"));
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("alive"),1));
                return criteriaBuilder.and(predicate);
            }
        };
        return specification;
    }
}
