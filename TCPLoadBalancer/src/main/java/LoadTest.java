import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class LoadTest extends Thread{
	
	private PrintWriter pr;
	private BufferedReader bf;
	private String threadName;
	private SocketCallBack socketCallBack;
	public LoadTest(SocketCallBack socketCallBack, String threadName, PrintWriter pr, BufferedReader bf) {
		this.socketCallBack = socketCallBack;
		this.threadName = threadName;
		this.pr = pr;
		this.bf = bf;
	}
	
	public void start() {
		Thread thread = new Thread(this);
		thread.setName(threadName);
		thread.start();
	}
	public void run() {
		performLoadTest();
	}
	
	public void performLoadTest() {

		long[] time = new long[5];
		for (int i=0;i<5;i++) {
			long start = System.currentTimeMillis();
			pr.println("load test " + String.valueOf(i+1));
			System.out.println("load test " + String.valueOf(i+1));
			try {
				String feedback = bf.readLine();
				System.out.println("Server feedback: " + feedback);
			} catch (IOException e) {
				e.printStackTrace();
			}
			long elapse = System.currentTimeMillis() - start;
			System.out.println(String.format("Request/Response time from server %s: %dms", threadName,elapse));
			time[i] = elapse;
		}
		long max = getMaxFromArray(time);
		long min = getMinFromArray(time);
		long average = getAverageFromArray(time);
		
		System.out.println(String.format("Approximate round trip times in milli-seconds: Minimum = %dms, Maximum = %dms, Average = %dms",
			min, max, average));
		
		socketCallBack.socketClose();
		SyncQueue.getSyncQueue().add(threadName);
	}
	
	
	
	
	private static long getMaxFromArray(long[] arr) {
		long max = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > max) 
				max = arr[i]; 
		}
		return max;
	}
	
	private static long getMinFromArray(long[] arr) {
		long min = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < min) 
				min = arr[i]; 
		}         
		return min;
	}
	
	private static long getAverageFromArray(long[] arr) {
		long sum = 0;
		for (int i = 0; i< arr.length; i++) {
			sum = sum + arr[i];
		}
		long average = sum/arr.length;
		return average;
	}
}
