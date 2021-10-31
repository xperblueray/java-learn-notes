package com.xperblueray.test.util;


import com.xperblueray.util.ZkUtils;
import org.junit.Test;

public class ZkUtilsTest {
    @Test
    public void testAddLog() {
        ZkUtils.addLogTime("methodA", "50:50");
    }

}
