import java.util.concurrent.SynchronousQueue;

public class SyncQueue {
	private static SynchronousQueue syncQueue;

	public static SynchronousQueue getSyncQueue() {
		return syncQueue;
	}

	public void setSyncQueue(SynchronousQueue syncQueue) {
		this.syncQueue = syncQueue;
	}
	
	public static String getFirstElement() {
		return (String) syncQueue.poll();
	}
}
