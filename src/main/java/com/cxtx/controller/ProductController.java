package com.cxtx.controller;

import com.alibaba.fastjson.util.IOUtils;
import com.cxtx.dao.ProductDao;
import com.cxtx.dao.ProductTypeDao;
import com.cxtx.dao.TeaSalerDao;
import com.cxtx.entity.Product;
import com.cxtx.entity.ProductType;
import com.cxtx.entity.TeaSaler;
import com.cxtx.model.*;
import com.cxtx.predictor.PredicateUtils;
import com.cxtx.predictor.Predictor;
import com.cxtx.service.ProductService;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ycc on 16/10/30.
 */
@Controller
public class ProductController extends ApiController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTypeDao productTypeDao;
    @Autowired
    private TeaSalerDao teaSalerDao;
    @Autowired
    private ProductDao productDao;
    /**
     * 茶产品的新增,product对象中必须传入的数据应该由前台来限制
     * @param product
     * @param productType_id
     * @param teaSeller_id
     * @return
     */
    @RequestMapping(value = "/product/new", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult newProduct(@RequestBody Product product, @RequestParam (value="productType_id",defaultValue = "-1")Long productType_id, @RequestParam (value="teaSeller_id",defaultValue = "-1")Long teaSeller_id){
        checkParameter(product!=null,"产品为空");
        ProductType pt =productTypeDao.findByIdAndAlive(productType_id,1);
        TeaSaler ts =teaSalerDao.findByIdAndStateAndAlive(teaSeller_id,1,1);//存在且审核通过的茶农
        checkParameter(pt!=null,"产品类型不存在");
        checkParameter(ts!=null,"茶农不存在");
        Product result=null;
        product.setProductType(pt);
        product.setUrl(pt.url);
        product.setTeaSaler(ts);
        product.setAlive(1);//存在
        product.setState(0);//未上架
        if(productService.isUnique(product)){//检查是否重复
            result = productService.newProduct(product);
            return ServiceResult.success(result);
        }else{
            return ServiceResult.fail(500,"该产品已经重复");
        }
    }

    /**
     * 茶产品的批量修改,哪些数据能修改,哪些不能应该由前台控制
     * 只有(stock,price,startNum,discount,isFree,postage,deliverLimit,unit)可以修改,其它不能修改
     * @param productList
     * @return
     */
    @RequestMapping(value = "/products/update", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult updateProduct(@RequestBody List<CreateProductModel> productList){
        checkParameter(productList!=null&&!productList.isEmpty(),"产品为空");
        List<Product> list= productService.updateProduct(productList);
        int succCount=list.size();
        if(succCount!=productList.size()){
            return ServiceResult.fail(500, "成功的数量是: "+succCount+" ; 失败的数量是: "+(productList.size()-succCount));
        }
        return ServiceResult.success(list);
    }

    /**
     * 茶产品的批量上架处理
     * @param productList
     * @return
     */
    @RequestMapping(value = "/products/startSell", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult startSell(@RequestBody List<StartSellProductModel> productList){
        checkParameter(productList!=null&&!productList.isEmpty(),"产品为空");
        int succCount = productService.startSell(productList);
        if(succCount!=productList.size()){
            return ServiceResult.fail(500, "成功的数量是: "+succCount+" ; 失败的数量是: "+(productList.size()-succCount));
        }
        return ServiceResult.success("全部上架成功");
    }

    /**
     * 茶产品的条件查询,不需要查找的条件不写或者数字填-1,字符串填""
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
    @RequestMapping(value = "/products/search", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult findProductByConditions(@RequestParam(value = "productType_id",defaultValue = "-1") Long productType_id, @RequestParam(value = "remark",defaultValue = "") String remark, @RequestParam(value = "name",defaultValue = "")String name,
                                                 @RequestParam(value = "level",defaultValue = "-1")int level, @RequestParam(value = "locality",defaultValue = "")String locality,@RequestParam(value = "stock",defaultValue = "-1") double stock,
                                                 @RequestParam(value = "lowPrice",defaultValue = "-1")double lowPrice, @RequestParam(value = "highPrice",defaultValue = "-1")double highPrice, @RequestParam(value = "startNum",defaultValue = "-1")double startNum, @RequestParam(value = "discount",defaultValue = "-1")double discount,
                                                 @RequestParam(value = "isFree",defaultValue = "-1")int isFree, @RequestParam(value = "teaSeller_name",defaultValue = "")String teaSeller_name, @RequestParam(value = "state",defaultValue = "-1")int state,@RequestParam(value = "teaSaler_id",defaultValue = "-1")Long teaSaler_id,
                                                 @RequestParam(value="pageIndex", defaultValue="0") int pageIndex, @RequestParam(value="pageSize", defaultValue="10") int pageSize, @RequestParam(value="sortField", defaultValue="price") String sortField, @RequestParam(value="sortOrder", defaultValue="ASC") String sortOrder){

        Page<Product> products = productService.findByConditions(productType_id,remark,name,level,locality,stock,lowPrice,highPrice,
        startNum, discount, isFree, teaSeller_name,state,teaSaler_id,pageIndex,pageSize, sortField,sortOrder);
        return ServiceResult.success(products);
    }

    /**
     * 根据销量推荐商品
     * @return
     */
    @RequestMapping(value = "/products/commend/rankBySalesVolume", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult commendBySalesVolume(){
        List<Product> products = productService.commend();
        if (products != null && products.size() != 0){
            return ServiceResult.success(products);
        }
        return ServiceResult.fail(500,"网络错误");
    }

    /**
     * 根据id查产品
     * @param id
     * @return
     */
    @RequestMapping(value = "/products/getById", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult findProductById(@RequestParam(value = "id",defaultValue = "-1")Long id){
        Product product=productDao.findByIdAndAlive(id,1);
        if(product==null){
            product=new Product();
        }
        return ServiceResult.success(product);
    }

    /**
     * 根据茶农和state获得产品
     * @param teaSaler_id
     * @param state
     * @return
     */
    @RequestMapping(value = "/products/getByTeaSalerAndState", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult findByTeaSalerAndState(@RequestParam(value = "teaSaler_id",defaultValue = "-1")Long teaSaler_id,@RequestParam(value = "state",defaultValue = "-1")int state){
        TeaSaler teaSaler =teaSalerDao.findByIdAndStateAndAlive(teaSaler_id,1,1);
        checkParameter(teaSaler!=null,"茶农不存在");
        List<Product> list =new ArrayList<Product>();
        if(state>-1){
            list =productDao.findByTeaSalerAndStateAndAliveAndType(teaSaler,state,1,0);
        }else{
            list=productDao.findByTeaSalerAndAliveAndType(teaSaler,1,0);
        }
        return ServiceResult.success(list);

    }

    /**
     * 茶产品的批量删除
     * @param list
     * @return
     */
    @RequestMapping(value = "/products/delete", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult deleteProduct(@RequestBody List<DeleteImageModel> list){
        checkParameter(list!=null,"产品为空");
        int succCount=0;
        for(DeleteImageModel model:list){
            Product product =productDao.findByIdAndAlive(model.id,1);
            if(null!=product&&product.getState()==0){
                product.setAlive(0);
                productDao.save(product);
                succCount++;
            }
        }
        if(succCount!=list.size()){
            return ServiceResult.fail(500, "成功的数量是: "+succCount+" ; 失败的数量是: "+(list.size()-succCount));
        }
        return ServiceResult.success("全部删除成功");
    }

    /**
     * 某一商品的评分与评价
     * @param id
     * @return
     */
    @RequestMapping(value = "/products/comment/getById", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult findCommentById(@RequestParam(value = "id",defaultValue = "-1")Long id){
        checkParameter(id > 0,"invalid id");
        CommentModel commentModel = productService.getComment(id);
        if (commentModel == null){
            return ServiceResult.fail(500, "该评价不存在");
        }
        return ServiceResult.success(commentModel);
    }

    @RequestMapping(value = "/products/price/predicte", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getPrediction() throws IOException, BiffException {

        //String json = com.alibaba.fastjson.JSON.toJSONString(list,true);
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cxtx.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String folderPath = p.getProperty("predicateFile");
        File file=new File(folderPath);
        if (!file.exists()){
            new PredicateUtils().doPredicate();
        }
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String tempString = null;

        // 一次读入一行，直到读入null为文件结束
        while ((tempString = reader.readLine()) != null) {
            sb.append(tempString);
        }
        reader.close();
        String json = sb.toString();
        List<TeaModel> result  = com.alibaba.fastjson.JSON.parseArray(json, TeaModel.class);
        List<List<TeaModel>> datas = parse(result);
        return ServiceResult.success(datas);

    }

    @RequestMapping(value = "/products/price/lastpredicte", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getLastPrediction () throws IOException, BiffException {

        //String json = com.alibaba.fastjson.JSON.toJSONString(list,true);
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cxtx.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String folderPath = p.getProperty("predicateFile");
        File file=new File(folderPath);
        if (!file.exists()){
            new PredicateUtils().doPredicate();
        }
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String tempString = null;

        // 一次读入一行，直到读入null为文件结束
        while ((tempString = reader.readLine()) != null) {
            sb.append(tempString);
        }
        reader.close();
        String json = sb.toString();
        List<TeaModel> result  = com.alibaba.fastjson.JSON.parseArray(json, TeaModel.class);
        List<List<TeaModel>> datas = parse(result);
        List<TeaModel> lastPredicate = getLastPre(datas);
        return ServiceResult.success(lastPredicate);

    }

    private List<TeaModel> getLastPre(List<List<TeaModel>> datas) {
        List<TeaModel> lastPredicate = new ArrayList<TeaModel>();
        for (List<TeaModel> models : datas){
            lastPredicate.add(models.get(models.size() -1));
        }
        return lastPredicate;
    }


    private List<List<TeaModel>> parse(List<TeaModel> teaModels) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<List<TeaModel>> datas = new ArrayList<List<TeaModel>>();
        TeaModel model = new TeaModel();
        model.name = "";
        model.level = -1;
        List<TeaModel> data = new ArrayList<TeaModel>();
        for (TeaModel teaModel : teaModels) {
            if (!((teaModel.name.equals(model.name)) && (teaModel.level == model.level))) {
                datas.add(data);
                model = teaModel;
                data = new ArrayList<TeaModel>();
            }
            teaModel.dateStr = sdf.format(teaModel.date);
            data.add(teaModel);
        }
        datas.add(data);
        datas.remove(0);
        return datas;
    }


    @RequestMapping(value = "/products/down", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult downProduct(@RequestParam(value = "product_id",defaultValue = "-1")Long product_id){
        int result =productService.downProduct(product_id);
        if(result==0){
            return ServiceResult.success("商品下架成功");
        }else {
            return ServiceResult.fail(500,"普通商品不存在");
        }

    }
}
