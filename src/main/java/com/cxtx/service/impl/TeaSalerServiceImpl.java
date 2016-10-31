package com.cxtx.service.impl;

import com.cxtx.dao.TeaSalerDao;
import com.cxtx.entity.Account;
import com.cxtx.entity.Manager;
import com.cxtx.entity.TeaSeller;
import com.cxtx.model.CreateTeaSalerModel;
import com.cxtx.model.PageListModel;
import com.cxtx.model.UserListCell;
import com.cxtx.service.TeaSalerService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jinchuyang on 16/10/27.
 */
@Service("TeaSalerService")
public class TeaSalerServiceImpl implements TeaSalerService{
    @Autowired
    private TeaSalerDao teaSalerDao;

    @Override
    public TeaSeller findByAccountAndAlive(Account account) {
        return teaSalerDao.findByAccountAndAlive(account,1);
    }

    @Override
    public TeaSeller addTeaSaler(CreateTeaSalerModel createTeaSalerModel, Account account) {
        TeaSeller teaSeller = new TeaSeller();
        teaSeller.setAlive(1);
        teaSeller.setTel(createTeaSalerModel.getTel());
        teaSeller.setMoney(createTeaSalerModel.getMoney());
        teaSeller.setNickname(createTeaSalerModel.getNickname());
        teaSeller.setLevel(createTeaSalerModel.getLever());
        teaSeller.setAddress(createTeaSalerModel.getAddress());
        teaSeller.setAccount(account);
        teaSeller.setHeadUrl(createTeaSalerModel.getHeadUrl());
        teaSeller.setIdCard(createTeaSalerModel.getIdCard());
        teaSeller.setLicenseUrl(createTeaSalerModel.getLicenseUrl());
        teaSeller.setName(createTeaSalerModel.getName());
        teaSeller.setZip(createTeaSalerModel.getZip());
        teaSeller.setCreateDate(new Date());
        teaSeller.setState(0);
        return teaSalerDao.save(teaSeller);
    }

    @Override
    public Page<TeaSeller>  searchTeaSaler(String name, int level, String tel, int pageIndex, int pageSize, String sortField, String sortOrder) {
        Sort.Direction direction = Sort.Direction.DESC;
        if (sortOrder.toUpperCase().equals("ASC")) {
            direction = Sort.Direction.ASC;
        }

        Specification<TeaSeller> specification = this.buildSpecification(name, level, tel);
        System.out.println("name" + name+ " " + "level" + level +" tel" + tel);
        Page<TeaSeller> teaSellerPage =
                teaSalerDao.findAll(specification, new PageRequest(pageIndex, pageSize, direction,sortField));
        System.out.println(teaSellerPage.getTotalElements());

        return teaSellerPage;
    }


    private Specification<TeaSeller> buildSpecification(final String name, final int level, final String tel){
        Specification<TeaSeller> specification = new Specification<TeaSeller>() {
            @Override
            public Predicate toPredicate(Root<TeaSeller> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("name"),"%"+name+"%"));
                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("tel"),"%" + tel + "%"));
                predicate.getExpressions().add(criteriaBuilder.equal(root.<Integer>get("level"),level));
                return predicate;
            }
        };
        return  specification;
    }
}
