package com.cxtx.service.impl;

import com.cxtx.dao.AccountDao;
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
import java.util.Date;

/**
 * Created by jinchuyang on 16/10/26.
 */
@Service("CustomerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private AccountDao accountDao;


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
        customer.setTel(createCustomerModel.getTel());
        customer.setAccount(account);
        customer.setAddress(createCustomerModel.getAddress());
        customer.setLevel(createCustomerModel.getLevel());
        customer.setNickname(createCustomerModel.getNickname());
        customer.setZip(createCustomerModel.getZip());
        customer.setAlive(1);
        customer.setCreateDate(new Date());
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

    @Override
    public Customer updateCustomer(CreateCustomerModel createCustomerModel) {
        Account account = accountDao.findByTelAndAlive(createCustomerModel.getTel(),1);
        if (account != null){
            if (createCustomerModel.getPassword() != null){
                account.setPassword(createCustomerModel.getPassword());
                accountDao.save(account);
            }
            Customer customer = customerDao.findByAccountAndAlive(account,1);
            if (customer != null){
                customer.setNickname(createCustomerModel.getNickname());
                customer.setAddress(createCustomerModel.getAddress());
                customer.setZip(createCustomerModel.getZip());
                return customerDao.save(customer);
            }
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
