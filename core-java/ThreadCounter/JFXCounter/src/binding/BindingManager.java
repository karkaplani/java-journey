package binding;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import counter.Counter;

public class BindingManager {

	private ExecutorService executor;
	private List<CounterBinding> counters;
	
	public BindingManager() {
		
		counters = new LinkedList<CounterBinding>();
		executor = Executors.newCachedThreadPool();
	}
	
	public void shutdown() {
		for(CounterBinding c: counters) {
			c.shutdown();
		}
		executor.shutdown();
	}
	
	public CounterBinding getNewBinding() {
		CounterBinding c = new CounterBinding(new Counter());
		counters.add(c);
		executor.submit(c);
		return c;
	}
}
