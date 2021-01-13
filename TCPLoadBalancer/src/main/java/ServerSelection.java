import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import LoadBalancingAlgorithm.LeastResponseTime;
import LoadBalancingAlgorithm.LoadTest;
import LoadBalancingAlgorithm.RoundRobin;
import LoadBalancingAlgorithm.SocketAction;
import LoadBalancingAlgorithm.SyncQueue;

public class ServerSelection {

	private List<String> serverList;
	private String balanceAlgorithm;
	private RoundRobin roundRobin;
	public ServerSelection(List<String> serverList, String balanceAlgorithm, RoundRobin roundRobin) {
		this.serverList = serverList;
		this.balanceAlgorithm = balanceAlgorithm;
		this.roundRobin = roundRobin;
	}

	public String chooseServer() {
		switch (balanceAlgorithm) {
			case "roundRobin":
				return roundRobin.getServer();
			case "leastResponseTime":
				LeastResponseTime leastResponseTime = new LeastResponseTime(serverList);
				return leastResponseTime.getServer();
			default:
				return roundRobin.getServer();
		}
	}

}
