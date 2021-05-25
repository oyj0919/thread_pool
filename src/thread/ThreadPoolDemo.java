package thread;

import utils.Task;
import utils.ThreadPoolUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * Demo
 *
 * @author oyj0919
 * @time 2020-11-20 19:58:40
 */
public class ThreadPoolDemo {
	public static void main(String[] args) throws Exception {
		// 测试 10000 个任务，每个任务耗时 10ms ,总计 1143 毫秒处理完毕
		long time = System.currentTimeMillis();
		Map<String, Task> map = new HashMap<>();
		for (int i = 0; i < 10000; i++) {
			final int flag = i;
			map.put("task" + i, new Task() {
				@Override
				public Object call() throws Exception {
					Thread.sleep(10); // 模拟远程调用耗时
					return "task" + flag; // 模拟返回远程调用结果
				}
			});
		}

		Map<String, Object> execute1 = ThreadPoolUtil.execute(map);
		System.out.println("long time: " + (System.currentTimeMillis() - time) + "\t execute1 : " + execute1.size() );
	}
}
