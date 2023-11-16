Project show such questions as:
--------------------------------------------
*app01 Starvation

Processor does not execute threads in parallel.
In reality, it just shares its time between tasks.
Task that has min priority will get less Process time.
And we can think that it is starvation.

--------------------------------------------
*app02 Race condition
In accordance of which thread win the race to get a resource access,
result of the execution all the time could be different
 Example:
 100 000
 i++
 i--

Not give us "0" if run parallel

 Operations ++ -- making by two steps:
 1) get current value i
 2) increment or decrement 1 from current value

Problem is that while IncrementThread doing point 1)
DecrementThread already done it`s points 1) and 2), and change value i on -1;
Now it is:  i = -1;
But IncrementThread think, that value is still i = 0
And add(increment) 1 to the value
In that case loosing  work of DecrementThread

After both operations (i++ i--) in different threads
at the end we have a result = 1;

Next time we could probably lose work of IncrementThread

--------------------------------------------

 Threads safety code:
That means we have not RaceCondition in that code
In other words: different run program--> same result;

 Synchronized
The synchronized keyword solves the RaceCondition problem and makes the code thread safe
But synchronized is the source of another problem - DeadLock

--------------------------------------------
*app03 DeadLock
we have couple resources: -xBox и -drums;
Bob - plying xBox, and after wants to play drums;
John - play drums, and after wants to play xBox;

And it turned out that John waits until its free xBox,
and Bob waits until its free drums;
At that moment they block each other,
this behavior is called DeadLock;

//resolve:
To get rid of DeadLock, all threads need to try to receive resources in the same order
Bob -  playing xBox, then play drums
John - playing xBox, then play drums

--------------------------------------------
 *app04:
 -a few examples of implementing Runnable --> how run() could be override


