package com.hackzone.concurrencymc.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
public class AtomicExample2 {
	/** 请求总数 */
	public static int clientTotal = 5000;
	/** 并发执行数 */
	public static int threadTotal = 200;
	/** 总数 */
	public static LongAdder count = new LongAdder();

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
		count.add(1);
	}
}
