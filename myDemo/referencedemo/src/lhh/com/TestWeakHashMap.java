package lhh.com;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author lihonghao
 * @date 2021/3/31 16:26
 */
public class TestWeakHashMap {
	public static void main(String[] args) {
		myHashmap();
		System.out.println("===========");
		myWeakMap();

	}

	private static void myWeakMap() {
		Map<Integer, String> hashMap = new WeakHashMap<>();
		Integer key = new Integer(1);
		String value = "weakHashMap";

		hashMap.put(key,value);
		System.out.println(hashMap);

		key = null;
		System.out.println(hashMap);

		System.gc();
		System.out.println(hashMap);
	}

	private static void myHashmap() {
		HashMap<Integer, String> hashMap = new HashMap<>();
		Integer key = new Integer(1);
		String value = "HashMap";

		hashMap.put(key,value);
		System.out.println(hashMap);

		key = null;
		System.out.println(hashMap);

		System.gc();
		System.out.println(hashMap);

	}
}
