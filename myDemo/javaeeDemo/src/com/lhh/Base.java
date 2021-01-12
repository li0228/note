package com.lhh;

/**
 * @author lihonghao
 * @data 2021/1/11 17:41
 */
public class Base
{
    private String baseName = "base";
    public Base()
    {
        callName();
    }

    public void callName()
    {
        System. out. println(baseName);
    }

    static class Sub extends Base
    {
        private String baseName = "sub";
        public void callName1()
        {
            System. out. println (baseName) ;
        }
    }
    public static void main(String[] args)
    {
        Base b = new Sub();
    }
}