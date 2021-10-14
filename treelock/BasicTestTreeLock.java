/*
 * A basic test for your tree lock program. We do not recommend modifiying this 
 * file. 
 */
class BasicTestTreeLock {

	// Change this to change the number of threads in the system.
	private static final int numThr = 1;

	public static void main(String args[]) throws InterruptedException {
		Thread threads[] = new Thread[numThr];
		Counter ctr = new Counter(numThr);

		for (int i = 0; i < numThr; i++) {
			threads[i] = new Thread(ctr,""+i);
			threads[i].start(); //Run the threads
		}
		System.out.println("Counter is " + ctr.getCount());
	}
}
