/*
 * This is where you will be implementing your TreeLock
 */
import java.util.ArrayList;

public class TreeLock {
	//store the maximum level needed with given threads.
	final private int treeLevel;
	//the 2-d array that used as a tree to process competing lock operations.
	final private PetersonNode[][] tree;

	public TreeLock(int _numThr) {
		//get how many level is needed for given threads number.
		//since the peterson's algorithm is designed for maximum two threads,
		//we will divide the threads into group of twos.
		treeLevel = (int) Math.ceil(((Math.log(_numThr)/Math.log(2))));
		//initialize the tree and make all element a new perterson Node
		tree = new PetersonNode[treeLevel+1][_numThr];
		for(int i = 0; i <= treeLevel; i++) {
			for(int j = 0; j < _numThr; j++) {
				//the index is just a useless parameter.
				tree[i][j] = new PetersonNode(i*10 + j);
			}
		}
	}

	public void lock() {
		//get the thread that wants to access the critical area.
		//it will also be the relative position for the thread to be in the "tree"
		int threadId;
		String name = Thread.currentThread().getName();
		//the case to check if the thread access is the main thread.
		if (name.equals("main")) {
			threadId = 0;
		}
		else{
			threadId = Integer.parseInt(Thread.currentThread().getName());
		}

		//the for loop that will go from top to bottom, where the tree[treeLevel][0] is the root node
		//the thread that enter this position will be the one that acquire the final lock and enter the critical section
		for(int i = 0; i <= treeLevel; i++) {
			//lock the ith level for that threadId.
			//for example, if threadID = 5,
			//then the first row of the 2D array will try to acquire the lock for thread 5 at position tree[0][5].
			//If the thread successfully acquire the lock, then it will enter the next level to compete with other threads who won the dual in the previous row.
			// If the thread does not win the dual, then it will be waiting in the while-loop that is in the PetersonNode.lock() method.
			tree[i][threadId].lock(threadId);
			//the number of threads that won previous duals got halved, so that is why we divide the relative position
			//by 2 here. For example, both thread 5 and thread 4 will have relative position 2 for the second row.
			// thread 0 and thread 1 will have relative position 0 for second row, etc. Since it will divide the relatve
			//position 2 for each round, it will eventually divide the relative position into 0, since we already know
			// that the number of thread is less than or equal to 2^treelevel
			threadId = (int) Math.floor(threadId/2);
		}

	}

	public void unlock() {
		int threadId;
		String name = Thread.currentThread().getName();
		//deal with the main thread situation.
		if (name.equals("main")) {
			threadId = 0;
		}
		else {
			//get the thread ID that want to unlock the lock it acquires.
			threadId = Integer.parseInt(Thread.currentThread().getName());
		}
		//trace the same path of the thread that acquires the lock, and unlock all the locks alone the way.
		for(int i = 0; i <= treeLevel; i++) {
			tree[i][threadId].unlock(threadId);
			threadId = (int) Math.floor(threadId/2);
		}
	}
}
