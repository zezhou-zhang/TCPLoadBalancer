package LoadBalancingAlgorithm;
import java.io.IOException;
import java.net.Socket;

public class SocketAction implements SocketCallBack{
	private Socket socket;
	
	public SocketAction(Socket socket) {
		this.socket = socket;
	}

	public void socketClose() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}