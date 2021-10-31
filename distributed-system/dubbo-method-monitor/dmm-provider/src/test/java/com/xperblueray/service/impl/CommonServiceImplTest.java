package com.xperblueray.service.impl;

import com.xperblueray.service.CommonService;
import org.junit.Test;

public class CommonServiceImplTest {
    private CommonService service = new CommonServiceImpl();
    @Test
    public void methodA() throws Exception {
        System.out.println(service.methodA());
    }

    @Test
    public void methodB() throws Exception {
        System.out.println(service.methodB());
    }

    @Test
    public void methodC() throws Exception {
        System.out.println(service.methodC());
    }
}