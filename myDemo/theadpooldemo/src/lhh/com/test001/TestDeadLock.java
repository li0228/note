package lhh.com.test001;

class MyThread implements Runnable{
	private String lockA = null;
	private String lockB = null;

	public MyThread() {
	}

	public MyThread(String lockA, String lockB) {
		this.lockA = lockA;
		this.lockB = lockB;
	}

	@Override
	public void run() {
		synchronized(lockA){
			System.out.println(Thread.currentThread().getName() +"自己持有"+lockA);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+"尝试获得"+lockB);
			synchronized (lockB){
				System.out.println(Thread.currentThread().getName()+"持有"+lockB);
			}

		}
	}
}
/**
 * 死锁测试
 *
 * @author lihonghao
 * @date 2021/3/30 19:44
 */
public class TestDeadLock {
	public static void main(String[] args) {
		String lockA = "lockA";
		String lockB = "lockB";
		new Thread(new MyThread(lockA,lockB),"aaa").start();
		new Thread(new MyThread(lockB,lockA),"bbb").start();
	}

}
