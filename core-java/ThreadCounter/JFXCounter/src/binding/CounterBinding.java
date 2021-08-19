package binding;

import java.util.concurrent.TimeUnit;

import counter.Counter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

public class CounterBinding extends Task<Void>{

    private Counter counter;
	private StringProperty time;
	private Object stepLock;
	
	public CounterBinding(Counter counter) {
		this.counter = counter;
		stepLock = new Object();
		time = new SimpleStringProperty();
		String message = counter.toString();
		updateMessage(message);
		time.bind(messageProperty());
	}
	
	/*
	 * When counter object is active;
	 * if the counter object is paused
	 * In the call method, while the counter is working, if it's paused, it should stop. In order to stop that, 
	 * synchronized is used for using the wait method with a lock to make the thread enter the waiting status. 
	 */
	protected Void call() {
		while(isCancelled() == false) {
			try {
				
				if(counter.isPaused()) {
					synchronized(stepLock) {
						stepLock.wait();
					}
				} 
				
			    if(counter.step() == false) {
					TimeUnit.MILLISECONDS.sleep(50);
				} else {
					updateMessage(counter.toString());
				}
				
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public StringProperty timeProperty() {
		return time;
	}
	
	public void start() {
		counter.start();
		synchronized(stepLock) {
			stepLock.notifyAll();
		}
	}
	
	public void stop() {
		counter.stop();
	}
	
	public void pause() {
		counter.pause();
	}
	
	public void shutdown() {
		this.cancel();
	}
}
