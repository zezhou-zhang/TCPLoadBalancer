import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ServerSelection {
	
	private List<String> serverList;
	
	public ServerSelection(List<String> serverList) {
		this.serverList = serverList;
	}
	
	public String chooseServer() {
		
		SyncQueue syncQueue = new SyncQueue(serverList.size());
		SyncQueue.setSyncQueue();
		for (String server: serverList) {
			String serverIP = server.split(":")[0];
			int serverPort = Integer.valueOf(server.split(":")[1]);
			Socket socket;
			try {
				socket = new Socket(serverIP, serverPort);
				PrintWriter pr = new PrintWriter(socket.getOutputStream(),true);
				BufferedReader bf = new BufferedReader( new InputStreamReader(socket.getInputStream()));
				SocketAction socketAction = new SocketAction(socket);
				System.out.println("Performing load test on server " + server);
				new LoadTest(syncQueue, socketAction, server, pr, bf).start();
			} catch (IOException e) {
				System.out.println(String.format("Cannot connect to server %s, skip to next candidate server", server));
			}
		}
		
		while(true) {
			if (!syncQueue.getSyncQueue().isEmpty()) {
				return syncQueue.getFirstElement();
			}
		}
//		for(int i = 0; i<threads; i++) {
//			Thread t = getThreadByName("Thread"+i);
//			//if return null, it means that thread already finished before its turn in original order
//			if (t != null) {
//				try {
//					System.out.println("The thread name is : " + t.getName());
//					System.out.println("The thread alive status is: " + t.isAlive());
//					if(t.isAlive())
//						t.join();
//					else
//						return t.getName();
//				} catch (InterruptedException e) {}
//			}
//		}
//		return null;
		
		
	}
	
//	private Thread getThreadByName(String threadName) {
//		for (Thread t : Thread.getAllStackTraces().keySet()) {
//	        if (t.getName().equals(threadName)) return t;
//	    }
//	    return null;
//	}
}
