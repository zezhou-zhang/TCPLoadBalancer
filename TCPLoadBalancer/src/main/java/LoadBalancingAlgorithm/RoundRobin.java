package LoadBalancingAlgorithm;

import java.util.List;

public class RoundRobin implements LoadBalance{
	
	private List<String> serverList;
	private static Integer position = 0;
	
	public RoundRobin(List<String> serverList) {
		this.serverList = serverList;
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
