package utils;

		import java.util.concurrent.Callable;

/**
 *
 * @author oyj0919
 * @time 2020-11-20 19:50:11
 */
public abstract class Task implements Callable {
	public abstract Object call() throws Exception;
}
