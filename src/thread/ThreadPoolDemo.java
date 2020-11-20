package thread;

import utils.Task;
import utils.ThredPoolUtil;

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
		// 测试20个个接口，每个接口耗时 300ms
		Map<String, Task> map = new HashMap<>();
		for (int i = 0; i < 20; i++) {
			final int flag = i;
			map.put("task" + i, new Task() {
				@Override
				public Object call() throws Exception {
					Thread.sleep(300); // 模拟远程调用耗时
					return "task" + flag; // 模拟返回远程调用结果
				}
			});
		}
		long time = System.currentTimeMillis();
		Map<String, Object> execute1 = ThredPoolUtil.execute(map);
		System.out.println("long time: " + (System.currentTimeMillis() - time) + "\t execute1 : " + execute1.toString());
	}
}
