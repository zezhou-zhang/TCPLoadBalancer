import java.util.concurrent.SynchronousQueue;

public class SyncQueue {
	private static SynchronousQueue syncQueue;

	public static SynchronousQueue getSyncQueue() {
		return syncQueue;
	}
	
	
	public static void setSyncQueue() {
		SyncQueue.syncQueue = new SynchronousQueue();
	}
	
	public static String getFirstElement() {
		return (String) syncQueue.poll();
	}
}
