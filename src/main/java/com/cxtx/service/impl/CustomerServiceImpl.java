package com.cxtx.service.impl;

import com.cxtx.ImageUtils;
import com.cxtx.dao.CustomerDao;
import com.cxtx.entity.Account;
import com.cxtx.entity.Customer;
import com.cxtx.model.CreateCustomerModel;
import com.cxtx.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by jinchuyang on 16/10/26.
 */
@Service("CustomerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;
    @Override
    public Customer findByAccountAndAlive(Account account) {
        return customerDao.findByAccountAndAlive(account, 1);
    }

    @Override
    public Customer addCustomer(CreateCustomerModel createCustomerModel, Account account) {
        if (createCustomerModel == null || account == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setMoney(createCustomerModel.getMoney());
        customer.setTel(createCustomerModel.getTel());
        customer.setAccount(account);
        customer.setAddress(createCustomerModel.getAddress());
        customer.setLevel(createCustomerModel.getLevel());
        customer.setNickname(createCustomerModel.getNickname());
        customer.setZip(createCustomerModel.getZip());
        customer.setAlive(1);
        customer.setCreateDate(new Date());
        //存头像
        String headContent = createCustomerModel.getHeadUrl();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cxtc.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            //return null;
            e1.printStackTrace();
        }
        String folderPath = p.getProperty("picPath");
        String uuid = UUID.randomUUID().toString().replaceAll("-","");//让图片名字不同
        String imageUrl = folderPath + File.separator + uuid + ".jpg";
        boolean createHeadImageResult = ImageUtils.GenerateImage(headContent,imageUrl);
        if (!createHeadImageResult){
            imageUrl = folderPath + File.separator + "default.jpg";
        }
        customer.setHeadUrl(imageUrl);
        return customerDao.save(customer);
    }

    @Override
    public Page<Customer> searchCustomer(String name, int level, String tel, int pageIndex, int pageSize, String sortField, String sortOrder) {
        Sort.Direction direction = Sort.Direction.DESC;
        if (sortOrder.toUpperCase().equals("ASC")) {
            direction = Sort.Direction.ASC;
        }

        Specification<Customer> specification = this.buildSpecification(name, level, tel);
        Page<Customer> customerPage =
                customerDao.findAll(specification, new PageRequest(pageIndex, pageSize, direction,sortField));

        return customerPage;
    }

    @Override
    public Customer findById(long customerId) {
        Customer customer = customerDao.findOne(customerId);
        if (customer != null && customer.getAlive() == 1){
            return customer;
        }
        return null;
    }

    private Specification<Customer> buildSpecification(final String name, final int level, final String tel){
        Specification<Customer> specification = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("nickname"),"%"+name+"%"));
                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("tel"),"%" + tel + "%"));
                if (level != -1) {
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<Integer>get("level"), level));
                }

                return predicate;
            }
        };
        return  specification;
    }
}
