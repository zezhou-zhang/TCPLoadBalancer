import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import LoadBalancingAlgorithm.LeastResponseTime;
import LoadBalancingAlgorithm.RoundRobin;
import configuration.GetBalancingAlgorithm;
import configuration.readServerConfig;

public class Application {
	private static final String LOAD_BALANCER = "Load Balancer";
	private static final int DEFAULT_PORT = 2888;

	public static void main(String[] args) throws IOException {
		int portNum = args.length == 1 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
		// Get the ip address from current server and print it out
		InetAddress tcpServerAddress = getMyServerAddress();
		System.out.println("The local IP address is: " + tcpServerAddress.getHostAddress());
		ServerSocket tcpServerSocket = new ServerSocket(portNum, 50, tcpServerAddress);
		System.out.println(LOAD_BALANCER + " is listening on port " + portNum);

		readServerConfig serverConfig = new readServerConfig();
		List<String> serverList = null;
		String balanceAlgorithm = null;
		try {
			serverList = serverConfig.getPropertiesValue();
			System.out.println("Candidate server list: " + serverList);
			balanceAlgorithm = GetBalancingAlgorithm.getBalancingAlgorithm();
			System.out.println("The load balancing algorithm: " + balanceAlgorithm);
			
		} catch (FileNotFoundException e) {
			System.out.println("property file not found in the classpath");
			System.out.println("exit...");
			System.exit(0);
		}
		// Add default policy
		RoundRobin roundRobin = new RoundRobin(serverList);
		
		while (true) {
			Socket incomingSocket = tcpServerSocket.accept();

			ServerSelection serverSelection = new ServerSelection(serverList, balanceAlgorithm, roundRobin);
			String selectedServer = serverSelection.chooseServer();
			System.out.println(selectedServer + " is selected.");

			String selectedServerIP = selectedServer.split(":")[0];
			int selectedServerPort = Integer.valueOf(selectedServer.split(":")[1]);

			Socket outcomingSocket = new Socket(selectedServerIP, selectedServerPort);

			new CommunicationProxy(selectedServer, incomingSocket, outcomingSocket).start();

		}

	}

	private static InetAddress getMyServerAddress() {
		InetAddress myServerAddress = null;
		try {
			myServerAddress = InetAddress.getLocalHost();

		} catch (UnknownHostException e) {
			System.out.println("Exception: Cannot find localhost address.");
		}
		return myServerAddress;
	}

}
