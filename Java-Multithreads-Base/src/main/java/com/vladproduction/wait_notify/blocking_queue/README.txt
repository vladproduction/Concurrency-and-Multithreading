*as we see in class BlockingQueue, method get() synchronized;
*could work only one thread and hold monitor, as monitor we have here current task (element of BlockingQueue);
*it means that get() method - 'blocking method', when tasks is empty, it has to wait();
*so the current thread holding monitor, and no task - wait();
*wait()-inside synchronized method making monitor as free,
 but thread(this monitor holder) could not unblock by itself;
 so until evoke this monitor by method notify() it will block;

*now we have to put some task in 'tasks container'-create method put() inside class BlockingQueue;
*put() also marked as synchronized, has method add(task);
*and inside put() we create method - notify(); so our method get() will know about adding task;
*after that thread(been blocked) in while() will be waking up at wait() place;
*and when performance of method put() ends, include notify(), monitor at that moment will be free for thread
 (monitor holder) which were been blocked before;

*ok, so we have BlockingQueue, now we have to create Thread to use logic from class BlockingQueue;
*main (runAction):
 -BlockingQueue example;
 -Thread (will be work with task) - worker;
 -worker.start(); will work all the time, till tasks exist in 'task container'
 -for loop: put some tasks(5 items for example), see how they are working;

 //worker do task one by one
 //and program stop, because no tasks have;
 //But all main() does not stop; worker just wait for another task;
 //To stop all process we have to terminate by hand (Stop MainApp)

 P.S.
 BlockingQue used methods (wait(),notify())
 wait()-block thread till task not exist;
 notify()-waking up thread;
 both of them has to be in synchronized block or method;
 execution that methods possible only by thread which currently hold the monitor of synchronization

