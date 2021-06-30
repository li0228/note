package com.lhh.io.bio.four;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lihonghao
 * @date 2021/6/30 15:09
 */
public class HandlerSocketServerPool {
	// 线程池对象
	private ExecutorService executorService;

	public HandlerSocketServerPool(int maxThreadNum,int queueSize) {
		executorService = new ThreadPoolExecutor(3, maxThreadNum, 120, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(queueSize));
	}

	public void execute(Runnable target){
		executorService.execute(target);
	}
}
