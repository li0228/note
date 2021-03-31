package lhh.com;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 前软弱虚引用demo
 *
 * @author lihonghao
 * @date 2021/3/31 15:59
 */
public class TestReference {
	public static void main(String[] args) {
		// 强引用：就算是发生oom也不会回收，new就是强引用
		Object o = new Object();

		// 软引用
//		SoftReference<Object>softReference = new SoftReference<>(o);
//		System.out.println(o);
//		System.out.println(softReference.get());

		// 弱引用：不管够不够用，都回收
//		WeakReference<Object> weakReference  = new WeakReference<>(o);
//		System.out.println(weakReference.get());
//		System.out.println(o);
//		o = null;
//		System.gc();
//		System.out.println("==============");
//
//		System.out.println(o);
//		System.out.println(weakReference.get());

		// 什么场景需要软引用和弱引用
	}

}
