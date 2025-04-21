# Java Multithreading and Concurrency Guide

Welcome to the Java Multithreading and Concurrency Guide! 
This guide covers core concepts, thread basics, synchronization, communication, concurrency utilities, thread safety, and advanced topics. 
Whether you are new to Java concurrency or looking to deepen your understanding, this guide will help you navigate the complexities of multithreaded programming.

## Table of Contents
- [Project Structure](#project-structure)
- [Core Concepts](#ch01-core-concepts)
- [Concurrency Utilities](#ch02-concurrency-utilities)
- [Thread Safety](#ch03-thread-safety)
- [Advanced Concepts](#ch04-advanced-concepts)


## Project Structure
``` 
com.vladproduction
├── ch01_core_concepts
│   ├── thread_basics (RunnableExample, ThreadLifecycleDemo, ThreadSubclassExample)
│   ├── thread_communication (ProducerConsumerExample, SpuriousWakeupSafeExample, TaskCoordinator)
│   └── thread_synchronization (BankAccountSynchronized, Counter, InventoryManager)
├── ch02_concurrency_utilities
│   ├── atomic_variables (AtomicVariablesExample)
│   ├── concurrent_collections(ConcurrentCollectionsExample)
│   ├── executor_framework (ThreadPoolsExample, FutureCallableExample, CompletableFutureExample)
│   └── synchronizers (CountDownLatchExample, CyclicBarrierExample, ExchangerExample, PhaserExample, SemaphoreExample)
├── ch03_thread_safety
│   ├── thread_safe_programming (Immutability, Thread-Local Variables, Safe Publication Techniques, Defensive Copying)
│   └── threading_issues (Race Conditions, Deadlocks, Livelocks and Starvation, Thread Confinement)
└── ch04_advanced_concepts
    ├── forkjoin_framework (forkjoinpool, recursive_task_and_action, work_stealing_algo)
    ├── lock_framework (LockComparison, CacheWithReadWriteLock, PointWithStampedLock, BoundedBuffer)
    ├── memory_model (MemoryBarrierExample, MemoryModelExample, SynchronizedMemoryModel, VolatileExample)
    └── parallel_streams (ParallelStreamExample, ParallelStreamPitfalls, StreamPerformanceComparison)
```

## ch. 01 Core Concepts

### Thread Basics
- **RunnableExample** (Creating threads using Runnable interface (preferred))
- **ThreadLifecycleDemo** (Understand the various states of a thread: New, Runnable, Blocked, Waiting, Timed Waiting, and Terminated)
- **ThreadSubclassExampled** (Creating threads by extending Thread class, how to set thread priorities, and how the Java scheduler manages thread execution)

### Thread Communication
- **ProducerConsumerExample** (Producer-Consumer Pattern with wait/notify)
- **SpuriousWakeupSafeExample** (Proper Handling of Spurious Wakeups)
- **TaskCoordinator** (Thread Signaling with wait/notifyAll for Task Coordination)

### Thread Synchronization
- **BankAccountSynchronized** (Race Condition and Synchronized Methods)
- **Counter** (Class-level Locking with Static Synchronized Methods)
- **InventoryManager** (Synchronized Block (more granular than method-level synchronization))


## ch. 02 Concurrency Utilities

### Atomic Variables
- **AtomicVariablesExample (contain demonstration):** 
  * Compare unsynchronized vs atomic counter (demonstrateAtomicInteger(numThreads, incrementsPerThread))
  * AtomicReference for updating reference types (demonstrateAtomicReference())
  * AtomicIntegerArray for array elements (demonstrateAtomicArray(numThreads))
  * AtomicFieldUpdater for existing classes (demonstrateFieldUpdater(numThreads, incrementsPerThread))

### Concurrent Collections
- **ConcurrentCollectionsExample (contain demonstration):** 
  * ConcurrentHashMap vs HashMap
  * CopyOnWriteArrayList
  * BlockingQueue implementations

### Executor Framework
- **ThreadPoolsExample** (Explore the ThreadPoolExecutor basics and different types of thread pools)
- **FutureCallableExample** (Learn about the Future and Callable interfaces for handling asynchronous tasks)
- **CompletableFutureExample** (Discover CompletableFuture for advanced asynchronous programming)

### Synchronizers
- **CountDownLatchExample** (Use CountDownLatch for coordinating threads)
- **CyclicBarrierExample** (Explore CyclicBarrier for synchronizing threads at a common barrier point)
- **SemaphoreExample** (Learn about Semaphore for controlling access to resources)
- **PhaserExample** (Discover Phaser for advanced thread synchronization)
- **ExchangerExample** (Use Exchanger for exchanging data between threads)


## ch. 03 Thread Safety

### Thread-Safe Programming
- **Immutability** (Learn how immutability can help achieve thread safety)
- **Thread-Local Variables** (Use thread-local variables to avoid shared state issues)
- **Safe Publication Techniques** (Explore safe publication techniques for sharing objects between threads)
- **Defensive Copying** (Understand defensive copying for protecting mutable objects)

### Threading Issues
- **Race Conditions** (Identify and address race conditions in concurrent programs)
- **Deadlocks** (Learn techniques for detecting and preventing deadlocks)
- **Livelocks and Starvation** (Understand livelocks and starvation issues and how to mitigate them)
- **Thread Confinement** (Explore thread confinement techniques for ensuring thread safety)


## ch. 04 Advanced Concepts

### Fork/Join Framework
- **package: work_stealing_algo** Understands the work-stealing algorithm used in the Fork/Join framework.
- **package: recursive_task_and_action:** Explore RecursiveTask and RecursiveAction for parallel task execution.
- **package: forkjoinpool** Learn about the ForkJoinPool for managing parallel tasks.

### Lock Framework
- **LockComparison** (Compare ReentrantLock with the synchronized keyword)
- **CacheWithReadWriteLock** (Explore ReadWriteLock for reader/writer scenarios)
- **PointWithStampedLock** (Learn about StampedLock (Java 8+) for advanced locking mechanisms)
- **BoundedBuffer** (Use condition objects for more flexible thread communication)

### Memory Model
- **MemoryModelExample and SynchronizedMemoryModel** (Understand the fundamentals of the Java Memory Model)
- **VolatileExample** (Learn about the volatile keyword and its guarantees)
- **MemoryBarrierExample** (Explore memory barriers and ordering constraints in concurrent programs)

### Parallel Streams
- **ParallelStreamExample** (Learn how to use parallel streams for parallel processing with collections)
- **StreamPerformanceComparison** (Understand when to use parallel streams versus sequential streams)
- **ParallelStreamPitfalls** (Identify and avoid common pitfalls and performance considerations with parallel streams)

---

This guide aims to provide a comprehensive overview of Java multithreading and concurrency. 
Whether you are a beginner or an experienced developer, you will find valuable insights and practical examples to help you master concurrent programming in Java.