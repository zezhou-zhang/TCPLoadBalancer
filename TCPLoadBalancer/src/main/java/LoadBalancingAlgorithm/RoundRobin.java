package LoadBalancingAlgorithm;

import java.util.List;

public class RoundRobin implements LoadBalance{
	
	private List<String> serverList;
	private volatile Integer position;
	
	public RoundRobin(List<String> serverList) {
		this.serverList = serverList;
		this.position = 0;
	}
	
	@Override
	public String getServer() {
		String target = null;
        synchronized (position) {
            if (position > serverList.size() - 1) {
                position = 0;
            }
            target = serverList.get(position);
            position++;
        }
        return target;
	}
}
