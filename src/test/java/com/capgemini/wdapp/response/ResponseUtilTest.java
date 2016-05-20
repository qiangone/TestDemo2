package com.capgemini.wdapp.response;

import org.junit.Test;

import com.google.gson.Gson;

public class ResponseUtilTest {
    
    
    @Test
    public void apiSuccess_test01() {
        ResponseUtil util = new ResponseUtil();
        WDResponse res = util.apiSuccess(null, "null value");
        Gson gson = new Gson();
        String result = "{\'result':{},'info':{'code':'200','msg':'null value'}}";
        System.out.println(gson.toJson(res));
    }
    @Test
    public void apiError_test01() {
        ResponseUtil util = new ResponseUtil();
        WDResponse res = util.apiError("30003", "error value");
        Gson gson = new Gson();
        System.out.println(gson.toJson(res));
    }

}
