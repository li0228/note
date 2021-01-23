package com.lhh;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试OutOfMemoryError异常
 * @author lihonghao
 * @data 2021/1/20 17:03
 */
public class TestOOM {
    static class OOMObject { }
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while (true) {
            list.add(new OOMObject());
        }
    }

}
