package com.cxtx.service.impl;

import com.cxtx.ImageUtils;
import com.cxtx.dao.TeaSalerDao;
import com.cxtx.entity.Account;
import com.cxtx.entity.TeaSaler;
import com.cxtx.model.CreateTeaSalerModel;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by jinchuyang on 16/10/27.
 */
@Service("TeaSalerService")
public class TeaSalerServiceImpl implements TeaSalerService{
    @Autowired
    private TeaSalerDao teaSalerDao;

    @Override
    public TeaSaler findByAccountAndAlive(Account account) {
        return teaSalerDao.findByAccountAndAlive(account,1);
    }

    @Override
    public TeaSaler addTeaSaler(CreateTeaSalerModel createTeaSalerModel, Account account) {
        TeaSaler teaSaler = new TeaSaler();
        teaSaler.setAlive(1);
        teaSaler.setTel(createTeaSalerModel.getTel());
        teaSaler.setMoney(createTeaSalerModel.getMoney());
        teaSaler.setNickname(createTeaSalerModel.getNickname());
        teaSaler.setLevel(createTeaSalerModel.getLevel());
        teaSaler.setAddress(createTeaSalerModel.getAddress());
        teaSaler.setAccount(account);
        //teaSaler.setHeadUrl(createTeaSalerModel.getHeadUrl());
        teaSaler.setIdCard(createTeaSalerModel.getIdCard());
        teaSaler.setLicenseUrl(createTeaSalerModel.getLicenseUrl());
        teaSaler.setName(createTeaSalerModel.getName());
        teaSaler.setZip(createTeaSalerModel.getZip());
        teaSaler.setCreateDate(new Date());
        teaSaler.setState(0);
        //存头像
        String headContent = createTeaSalerModel.getHeadUrl();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cxtx.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            //return null;
            e1.printStackTrace();
        }
        String headFolderPath = p.getProperty("picPath");
        String uuid1 = UUID.randomUUID().toString().replaceAll("-","");//让图片名字不同
        String imageUrl = headFolderPath + File.separator + uuid1 + ".jpg";
        boolean createHeadImageResult = ImageUtils.GenerateImage(headContent,imageUrl);
        if (!createHeadImageResult){
            imageUrl = headFolderPath + File.separator + "default.jpg";
        }
        teaSaler.setHeadUrl(imageUrl);

        String licenceContent = createTeaSalerModel.getLicenseUrl();
        String licenceFolderPath = p.getProperty("picPath");
        String uuid2 = UUID.randomUUID().toString().replaceAll("-","");//让图片名字不同
        imageUrl = licenceFolderPath + File.separator +uuid2 + ".jpg";
        boolean createLincenceImageResult = ImageUtils.GenerateImage(licenceContent,imageUrl);
        if (!createLincenceImageResult){
            imageUrl = licenceFolderPath + File.separator + "default.jpg";
        }
        return teaSalerDao.save(teaSaler);
    }

    @Override
    public Page<TeaSaler>  searchTeaSaler(String name, int level, String tel, int state, int pageIndex, int pageSize, String sortField, String sortOrder) {
        Sort.Direction direction = Sort.Direction.DESC;
        if (sortOrder.toUpperCase().equals("ASC")) {
            direction = Sort.Direction.ASC;
        }

        Specification<TeaSaler> specification = this.buildSpecification(name, level, tel, state);
        //System.out.println("name" + name+ " " + "level" + level +" tel" + tel);
        Page<TeaSaler> teaSalerPage =
                teaSalerDao.findAll(specification, new PageRequest(pageIndex, pageSize, direction,sortField));
        System.out.println(teaSalerPage.getTotalElements());

        return teaSalerPage;
    }

    @Override
    public TeaSaler findById(long teaSalerId) {
        TeaSaler teaSaler = teaSalerDao.findOne(teaSalerId);
        if (teaSaler != null && teaSaler.getAlive() == 1){
            return  teaSaler;
        }
        return null;
    }


    private Specification<TeaSaler> buildSpecification(final String name, final int level, final String tel, final int state){
        Specification<TeaSaler> specification = new Specification<TeaSaler>() {
            @Override
            public Predicate toPredicate(Root<TeaSaler> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("name"),"%"+name+"%"));
                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("tel"),"%" + tel + "%"));
                if (level != -1) {
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<Integer>get("level"), level));
                }

                if (state != -1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<Integer>get("state"),state));
                }
                return predicate;
            }
        };
        return  specification;
    }
}
