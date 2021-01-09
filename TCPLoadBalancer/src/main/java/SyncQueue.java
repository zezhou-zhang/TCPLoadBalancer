import java.util.concurrent.ArrayBlockingQueue;

public class SyncQueue {

	private static ArrayBlockingQueue syncQueue;
	private static int size;
	
	public SyncQueue(int size) {
		this.size = size;
	}
	public static ArrayBlockingQueue getSyncQueue() {
		return syncQueue;
	}
	
	public static void addToSyncQueue(String thing) {
		SyncQueue.syncQueue.add(thing);
	}
	
	public static void setSyncQueue() {
		SyncQueue.syncQueue = new ArrayBlockingQueue(size);
	}
	
	public static String getFirstElement() {
		return (String) syncQueue.poll();
	}
}
