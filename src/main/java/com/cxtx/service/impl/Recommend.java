package com.cxtx.service.impl;

import com.cxtx.dao.CustomerDao;
import com.cxtx.dao.OrderItemDao;
import com.cxtx.dao.ProductTypeDao;
import com.cxtx.entity.*;
import org.apache.commons.io.IOUtils;
import org.aspectj.weaver.ast.Or;
import org.json.JSONObject;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

import static com.jayway.restassured.parsing.Parser.JSON;

/**
 * Created by ycc on 17/1/5.
 */
@Service("RECOMMEND")
public class Recommend {

    @Autowired
    private ProductTypeDao productTypeDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private OrderItemDao orderItemDao;

    Map<Long,Object> users=null;//消费者向量值
    List<Customer> customers=null;//所有消费者
    int totalNum =0;//所有的产品类型的数量
    List<ProductType> productTypes=new ArrayList<ProductType>();

    /**
     * 获得所有消费者的行为向量  1执行顺序
     */
    public void changeCustomerToVector(){
        if(users==null){
            customers=getAllCustomer();
            totalNum=getProductTypeNum();
        }
        users =new HashMap<Long,Object>();
        for(Customer customer:customers){
            double vector [] =getNumByCustomer(customer);
            users.put(customer.getId(),vector);
        }
    }

    /**
     * 获得与当前消费者行为与其它消费者的相似度(余弦值越大1越相近) 排序结果  2执行顺序
     * @param customer
     * @return
     */
    public List<Map.Entry<Long,Double>> getMaxSimilarity(Customer customer){
        Map<Long,Double> result =new HashMap<Long,Double>();
        double vector[] =(double [])users.get(customer.getId());
        for(Map.Entry<Long,Object> entry:users.entrySet()){
            if(entry.getKey()!=customer.getId()){
                double [] temp =(double[])entry.getValue();
                double similarity =countSimilarity(temp,vector);
                System.out.println("test: "+entry.getKey()+"  "+similarity+"   ");
                result.put(entry.getKey(),similarity);
            }
        }
        List<Map.Entry<Long,Double>> list = new LinkedList<Map.Entry<Long,Double>>( result.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<Long,Double>>()
        {
            public int compare( Map.Entry<Long,Double> o1, Map.Entry<Long,Double> o2 )
            {
                return (o1.getValue()).compareTo( o2.getValue() );
            }
        } );
//        System.out.println("totalNum "+list.size());
        return list;
    }

    /**
     * 获得推荐商品  3 执行顺序
     * @param list
     * @return
     */
    public HashSet<Product> getProducts(List<Map.Entry<Long,Double>> list){
        List<Customer> simCustomers =new ArrayList<Customer>();
        HashSet<Product> result =new HashSet<Product>();
        System.out.println("相似度高的5个用户  ");
        for(int i=0;i<list.size()&&i<5;i++){
            Long id =list.get(i).getKey();
            Customer customer =customerDao.findByIdAndAlive(id,1);
            simCustomers.add(customer);
        }
        for(Customer customer:simCustomers){
            HashSet<Product> hashSet =getCustomerProduct(customer);
            System.out.println(customer.getId()+"  "+ hashSet.size());
            result.addAll(hashSet);
        }
//        for(Map.Entry<Long,Double> entry:list){
//            System.out.println(entry.getKey()+"   "+entry.getValue());
//        }
        return result;
    }

    /**
     * 总调用函数,传人某个消费者,返回推荐函数
     * @param customer
     * @return
     */
    public HashSet<Product> getSimilarity(Customer customer){
        changeCustomerToVector();
        List<Map.Entry<Long,Double>> list =this.getMaxSimilarity(customer);
        HashSet<Product> result =getProducts(list);
        return result;
    }

    /**
     * 预先计算所有用户,存入文件,以后每次读取文件
     * @param customer
     * @return
     * @throws IOException
     */
    public Map<String,Object> getAllSimilarity(Customer customer) throws IOException {
        changeCustomerToVector();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cxtx.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String folderPath = p.getProperty("recommendFile");
        File file=new File(folderPath);
        if(!file.exists()){
            file.createNewFile();
        }
        FileInputStream fileInputStream=new FileInputStream(file);
        Map<String,Object> map =new HashMap<String,Object>();
        com.alibaba.fastjson.JSONObject jsonObject = null;
        System.out.println("inputs"+fileInputStream);
        try {
            if(fileInputStream!=null){
                jsonObject = com.alibaba.fastjson.JSON.parseObject(IOUtils.toString(fileInputStream, "UTF-8"));
            }
        } catch (IOException e) {
            map.put("msg","JSON 格式不正确");
            map.put("content","");
            return map;
        }
         Object content=null;
//        System.out.println(jsonObject);
        if(jsonObject==null){ //如果文件中没有,则计算每个用户的推荐产品
            FileWriter fileWriter=new FileWriter(file,true);
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
           Map<Long,Object> temp =new HashMap<Long,Object>();
           for(Customer c:customers){
               List<Map.Entry<Long,Double>> list =this.getMaxSimilarity(c);
               HashSet<Product> result =getProducts(list);
               temp.put(c.getId(),result);
           }
               JSONObject object=new JSONObject(temp);
               bufferedWriter.write(object.toString());
               bufferedWriter.flush();

            if(object!=null){
                content= object.get(customer.getId()+"");
            }
        }else{
            if(null!=jsonObject.get(customer.getId()+"")){
                content=jsonObject.get(customer.getId()+"");
            }
        }
        map.put("msg","获取成功");
        map.put("content",content);
        return map;
    }

    public void deleteFile(){
        changeCustomerToVector();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cxtx.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String folderPath = p.getProperty("recommendFile");
        File file=new File(folderPath);
        if(file.exists()){
            file.delete();
        }
    }
    /**
     * 获得所有产品类型的数量
     * @return
     */
    public int getProductTypeNum(){
        List<ProductType> list = productTypeDao.findByAliveAndState(1,1);
        int totalNum =0;
        if(null!=list){
            totalNum=list.size();
            productTypes=list;
        }
        return totalNum;
    }

    /**
     * 获得所有的消费者列表
     */
    public List<Customer> getAllCustomer(){
        List<Customer> list =customerDao.findByAlive(1);
        List <Customer> result =new ArrayList<Customer>();
        for(Customer customer:list){
            if(customer.getAccount().getLabel()==2){//所有消费者
                result.add(customer);
            }
        }
        return result;
    }

    /**
     * 获得某个消费者的行为向量
     * @param customer
     * @return
     */
    public double[] getNumByCustomer(Customer customer){
        List<OrderItem> list =orderItemDao.findByCustomerAndAliveAndState(customer.getId(),1,2);
        double [] vectore =new double[totalNum];
        for(ProductType type:productTypes){
            int index=0;
            for(OrderItem orderItem:list){
                if(orderItem.getProduct().getProductType().id==type.id){
                    vectore[index]=vectore[index]+orderItem.getNum();
                }
            }
            vectore[index]=vectore[index];
            index++;
        }
        return vectore;
    }

    /**
     * 计算两个向量直接的余弦值
     * @param a
     * @param b
     * @return
     */
    public double countSimilarity(double [] a,double [] b){
        double total=0;
        double alength=0;
        double blength=0;
        for(int i=0;i<a.length;i++){
            total=total+a[i]*b[i];
            alength=alength+a[i]*a[i];
            blength=blength+b[i]*b[i];
        }
        double down=Math.sqrt(alength)*Math.sqrt(blength);
        double result=0;
        if(down!=0){
            result =total/down;
        }
        return result;
    }

    /**
     * 获得一个用户买过的所有商品
     * @param customer
     * @return
     */
    public HashSet<Product> getCustomerProduct(Customer customer){
        List<OrderItem> list =orderItemDao.findByCustomerAndAliveAndState(customer.getId(),1,2);
        HashSet<Product> result =new HashSet<Product>();
        for(OrderItem orderItem:list){
            result.add(orderItem.getProduct());
        }
        return result;
    }

}