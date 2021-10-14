/*
 * This is the counter that will be incremented by all the threads. This also serves
 * as the tester class that will make use of your TreeLock implementation. You should 
 * not need to change any of the code in this file.
*/
class Counter implements Runnable {

	private int count = 0;
	private final int timesThrIncs;
	private TreeLock tlock;

	/* 
		When a million is not evenly divisible my numThr, the final result 
		will not be a million. That should be okay as long as the final counter 
		output is equal to ((1000000 / numThr) * numThr) every time you run the 
		program
	 */
	Counter(int numThr) {
		timesThrIncs = 1000000 / numThr;
		tlock = new TreeLock(numThr);
	}

	public void run() {
		for (int i = 0; i < timesThrIncs; i++) {
            tlock.lock();
			count++;
            tlock.unlock();
		}
	}
	
	public int getCount() {
		return count;
	}
}
