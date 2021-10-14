/* 
    This class will be useful in having your threads receive unique thread-local 
    IDs starting from 0. 

    The reason we give this to you is because by the time you are able to create
    threads in your program, the JVM has already spawned several threads of its 
    own, thus reserving the first few thread IDs. Thus if you do:
        Thread.currentThread().getId()
    Your first thread will probably get some value around 8 or 9, though in
    actuality this is arbitrary (and either way not what you'd probably want).
*/
public class ThreadID {
    private static volatile int nextID = 0;
    private static class ThreadLocalID extends ThreadLocal<Integer> {
        protected synchronized Integer initialValue() {
            return nextID++;
        }
    }

    private static ThreadLocalID threadID = new ThreadLocalID();
    public static int get() {
        return threadID.get();
    }
    public static void set(int index) {
        threadID.set(index);
    }
}