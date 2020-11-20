package utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 并发工具类
 */
public final class ThredPoolUtil {

	/**
	 * 线程池
	 */
	private static volatile ThreadPoolExecutor threadPool;

	/**
	 * 超时时间
	 */
	public final static long TIME_OUT = 30;

	/**
	 * 无返回值
	 *
	 * @param runnable
	 */
	public static void execute(Runnable runnable) {
		getThreadPool().execute(runnable);
	}

	/**
	 * 无返回值
	 *
	 * @param thread)
	 */
	public static void execute(Thread thread) {
		getThreadPool().execute(thread);
	}
	/**
	 * 返回值直接执行
	 *
	 * @param tasks
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T> Map<String, Object> execute(Map<String, Task> tasks) throws Exception {
		// 返回值
		Map<String, Object> resMap = new HashMap<>();
		// 获得任务list
		List<Task> taskList = new ArrayList<>();
		List<String> taskName = new ArrayList<>();
		for (Map.Entry<String, Task> entry : tasks.entrySet()) {
			taskList.add(entry.getValue());
			taskName.add(entry.getKey());
		}
		// 如果执行任务大于0
		if (taskList.size() > 0) {
			List<Future<T>> futures = invokeAll(taskList);
			// 将结果与name绑定
			for (int i = 0; i < taskName.size(); i++) {
				resMap.put(taskName.get(i), futures.get(i).get());
				System.out.println(taskName.get(i)+"\t "+futures.get(i).get());
			}
		}
		return resMap;
	}

	/**
	 * 设置超时时间30秒
	 *
	 * @param tasks
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	private static <T> List<Future<T>> invokeAll(List tasks) throws Exception {
		return getThreadPool().invokeAll(tasks, TIME_OUT, TimeUnit.SECONDS);
	}

	/**
	 * dcs获取线程池,这里应该还有可以优化的地方，根据自己需要进行配置
	 */
	private static ThreadPoolExecutor getThreadPool() {
		if (threadPool != null) {
			return threadPool;
		} else {
			synchronized (ThredPoolUtil.class) {
				if (threadPool == null) {
					threadPool = new ThreadPoolExecutor(8 , 16, 60, TimeUnit.SECONDS,
							new LinkedBlockingQueue<>(32), new ThreadPoolExecutor.CallerRunsPolicy());
				}
				return threadPool;
			}
		}
	}
}
