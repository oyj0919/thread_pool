thread_pool线程池并发器 <br\>
解决接口性能问题用
例如一个接口需要远程调运5个接口，每个接口耗时300毫秒，实际接口性能为5 * 300ms,使用此工具，耗时仅为最长接口耗时
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
