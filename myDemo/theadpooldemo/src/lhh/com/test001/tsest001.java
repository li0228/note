package lhh.com.test001;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author lihonghao
 * @date 2021/3/30 16:58
 */
public class tsest001 {
	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newFixedThreadPool(5);

		try {
//			for (int i = 0; i < 10; i++) {
				threadPool.execute(new Thread(){
					@Override
					public void run() {
						System.out.println(Thread.currentThread().getName()+"\t"+"办理业务");
					}
				});
//			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			threadPool.shutdown();
		}
	}


}
