import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommunicationProxy extends Thread{
	
	private String threadName;
	private PrintWriter incomingWriter;
	private BufferedReader incomingReader;
	private PrintWriter outcomingWriter;
	private BufferedReader outcomingReader;
	
	public CommunicationProxy(String threadName, Socket incomingSocket, Socket outcomingSocket) throws IOException {
		this.threadName = threadName;
		incomingWriter = new PrintWriter(incomingSocket.getOutputStream(),true);
		incomingReader = new BufferedReader( new InputStreamReader(incomingSocket.getInputStream()));
		outcomingWriter = new PrintWriter(outcomingSocket.getOutputStream(),true);
		outcomingReader = new BufferedReader( new InputStreamReader(outcomingSocket.getInputStream()));
	}
	
	public void start() {
		Thread thread = new Thread(this);
		thread.setName(threadName);
		thread.start();
	}
	
	public void run() {
		try {
			establishCommunication();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void establishCommunication() throws IOException {
		String command = null;
		command = incomingReader.readLine();
		outcomingWriter.println(command);
		System.out.println(String.format("Communication Established: %s <=====> %s", command, threadName));
		while(true) {
			try {
				command = incomingReader.readLine();
			}catch(IOException e){}
			if(command == null) {
				 System.out.println("Detect " + threadName + " disconnected.");
		         break;
			}
			outcomingWriter.println(command);
			String serverFeedback = outcomingReader.readLine();
			incomingWriter.println(serverFeedback);
		}
	}
}
