/*
 * This is where you will be implementing your TreeLock
 */
import java.util.ArrayList;

public class TreeLock {
	int numT;
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
				tree[i][j] = new PetersonNode(i*10 + j);
			}
		}
	}

	public void lock() {
		//get the thread that wants to access the critical area.
		//it will also be the relative position for the thread to be in the "tree"
		String name = Thread.currentThread().getName();
		int threadId = Integer.parseInt(Thread.currentThread().getName());

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
		//get the thread ID that want to unlock the lock it acquires.
		int threadId = Integer.parseInt(Thread.currentThread().getName());
		//record all the relative position this thread has been with.
		ArrayList<Integer> route = new ArrayList<>();

		//find all position that this thread has been in. and add them to the array.
		for(int i = 0; i <= treeLevel; i++) {
			route.add(threadId);
			threadId = (int) Math.floor(threadId/2);
		}

		//now trace the route to unlock the petersonNode object in corresponding position.
		for(int j = treeLevel; j >= 0; j--) {
			tree[j][route.get(j)].unlock(route.get(j));
		}
	}
}
