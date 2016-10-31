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
     * @param productType_id
     * @param teaSeller_id
     * @return
     */
    @Override
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
    @Override
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
    @Override
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
