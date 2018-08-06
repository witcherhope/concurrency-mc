package com.hackzone.concurrencymc.example.count;

import com.hackzone.concurrencymc.example.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author maxwell
 */
@Slf4j
@ThreadSafe
public class CountExample2 {
	/** 请求总数 */
	public static int clientTotal = 5000;
	/** 并发执行数 */
	public static int threadTotal = 200;
	/** 总数 */
	public static AtomicInteger count = new AtomicInteger(0);

	public static void main(String[] args) throws InterruptedException {
		// ExecutorService executorService = Executors.newCachedThreadPool();
		ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(threadTotal);
		final Semaphore semaphore = new Semaphore(threadTotal);
		final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
		for (int i = 0; i < clientTotal; i++) {
			scheduledThreadPoolExecutor.execute(() -> {
				try {
					semaphore.acquire();
					add();
					semaphore.release();
				} catch (InterruptedException e) {
					log.error("Exception: {}", e);
				}
				countDownLatch.countDown();
			});
		}
		countDownLatch.await();
		scheduledThreadPoolExecutor.shutdown();
		log.info("count:{}", count);
	}

	private static void add() {
		count.addAndGet(1);
	}
}
