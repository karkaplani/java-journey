package counter;

public class Counter {

	private static final long MILISECOND_100 = 100;

	private long startTime;
	private long currentTime;
	private long accumulatedTime;
	private long pausedTime;
	private boolean paused;

	public Counter() {
		stop();
	}

	public boolean step() {
		if ( isPaused())
			return false;
		currentTime = System.currentTimeMillis();
		if ( startTime == 0) {
			startTime = currentTime;
		}
		if ( currentTime - startTime >= MILISECOND_100) {
			accumulatedTime = currentTime - startTime;
			return true;
		}
		return false;
	}

	public boolean isPaused() {
		return paused;
	}

	public boolean isStoped() {
		return getTime() == 0;
	}

	public long getTime() {
		return pausedTime + accumulatedTime;
	}

	public void start() {
		paused = false;
	}

	public void stop() {
		startTime = 0;
		pausedTime = 0;
		currentTime = 0;
		accumulatedTime = 0;
		paused = true;
	}

	public void pause() {
		if ( isPaused())
			return;
		step();
		startTime = 0;
		pausedTime += accumulatedTime;
		currentTime = 0;
		accumulatedTime = 0;
		paused = true;
	}

	/**
	 * @see https://stackoverflow.com/a/625624/764951
	 */
	@Override
	public String toString() {
		long time = getTime();
		long mil = ( time / 100) % 10;
		long sec = ( time / 1000) % 60;
		long min = ( ( time / ( 1000 * 60)) % 60);
		long hour = ( ( time / ( 1000 * 60 * 60)) % 24);
		return String.format( "%03d:%02d:%02d:%d", hour, min, sec, mil);
	}

	//	public static void main( String[] args) throws InterruptedException {
	//
	//		Counter c = new Counter();
	//		c.start();
	//		for ( int i = 0; i < 100; i++) {
	//			c.step();
	//			TimeUnit.MILLISECONDS.sleep( 100);
	//			System.out.println( c);
	//		}
	//		c.stop();
	//	}
}
