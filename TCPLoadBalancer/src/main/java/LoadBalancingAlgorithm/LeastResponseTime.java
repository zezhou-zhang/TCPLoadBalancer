package LoadBalancingAlgorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class LeastResponseTime implements LoadBalance{
	private List<String> serverList;
	
	public LeastResponseTime(List<String> serverList) {
		this.serverList = serverList;
	}
	
	@Override
	public String getServer() {
		SyncQueue syncQueue = new SyncQueue(serverList.size());
		syncQueue.setSyncQueue();
		for (String server : serverList) {
			String serverIP = server.split(":")[0];
			int serverPort = Integer.valueOf(server.split(":")[1]);
			Socket socket;
			try {
				socket = new Socket(serverIP, serverPort);
				PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				SocketAction socketAction = new SocketAction(socket);
				System.out.println("Performing load test on server " + server);
				new LoadTest(syncQueue, socketAction, server, pr, bf).start();
			} catch (IOException e) {
				System.out.println(String.format("Cannot connect to server %s, skip to next candidate server", server));
			}
		}

		while (true) {
			if (!syncQueue.getSyncQueue().isEmpty()) {
				return syncQueue.getFirstElement();
			}
		}
	}
}
