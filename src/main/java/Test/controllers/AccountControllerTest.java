package Test.controllers;

import Test.RootTest;
import com.cxtx.controller.AccountController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jinchuyang on 17/1/7.
 */
 public class AccountControllerTest extends RootTest{
    @Autowired
    private AccountController accountController;

    @Test
    public void testRecharge(){
        accountController.recharge(100,1);
    }

}
