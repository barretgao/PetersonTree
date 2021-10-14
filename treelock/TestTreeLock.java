/*
 *	The class that will test your TreeLock. The TAs will run our own tests against your
 *	handin, but be sure to implement this class to test your program.
 */
class TestTreeLock {

	// Change this to change the number of threads in the system.
	private static final int numThr = 20;

	public static void main(String args[]) throws InterruptedException {
		Counter ctr = new Counter(numThr);

       /**  Write code to test the tree lock implementation here.
         *   1. Instantiate an array of Thread objects
         *   2. Initialize each thread with the Runnable counter object, and call the thread's start() method
         *   3. Be sure to wait until all threads join before printing the final count 
         **/
		Thread threads[] = new Thread[numThr];
		for (int i = 0; i < numThr; i++) {
			threads[i] = new Thread(ctr,""+i);
			threads[i].start(); //Run the threads
		}

		for (Thread t : threads) {
			try{
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("Counter is " + ctr.getCount());
	}
}
