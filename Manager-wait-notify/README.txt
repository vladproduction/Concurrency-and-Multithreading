What is mutual exclusion Java?
A mutex (or mutual exclusion) is the simplest type of synchronizer – it ensures that only one thread can
execute the critical section of a computer program at a time. To access a critical section, a thread acquires
the mutex, then accesses the critical section, and finally releases the mutex.

app01:
PersonA started
PersonB started
	PersonA took Comp_#1
		-computerInUse by PersonA
		-computer: Comp_#1 <IS FREE NOW>
	PersonA bring back Comp_#1
PersonA finished
	PersonB took Comp_#1
		-computerInUse by PersonB
		-computer: Comp_#1 <IS FREE NOW>
	PersonB bring back Comp_#1
PersonB finished

app02:
Alfred STARTED
Alfred took Comp_#01
	Alfred give back computer, because his profile task status is: (false)
Bob STARTED
	Bob get Comp_#01
		-usingComputer by Bob()thread
		-computer: Comp_#01 <IS FREE NOW>; Bob()thread finished
	Bob give back computer, because he`s done for the moment
Bob FINISHED
profile task status add
profile task status is: true
	Alfred took Comp_#01, he has profile task with status:(true) and ready to work now...
		-usingComputer by Alfred(thread)
		-computer: Comp_#01 <IS FREE NOW>; Alfred(thread) finished
	Alfred give back computer, because he`s done for the moment
Alfred FINISHED

app03:
Alfred STARTED
	Alfred took Comp_#01
	Alfred give back computer, because his profile task status is: (false)
Daniel STARTED
	Daniel took Comp_#01
	Daniel give back computer, because his profile task status is: (false)
Bob STARTED
	Bob get Comp_#01
		-usingComputer by Bob()thread
		-computer: Comp_#01 <IS FREE NOW>; Bob()thread finished
	Bob give back computer, because he`s done for the moment
Bob FINISHED
		-->profile_task_status_add
		profile_task_status_is: true
	Alfred took Comp_#01, he has profile task with status:(true) and ready to work now...
		-usingComputer by Alfred(thread)
		-computer: Comp_#01 <IS FREE NOW>; Alfred(thread) finished
	Alfred give back computer, because he`s done for the moment
Alfred FINISHED
	Daniel took Comp_#01, he has profile task with status:(true) and ready to work now...
		-usingComputer by Daniel(thread)
		-computer: Comp_#01 <IS FREE NOW>; Daniel(thread) finished
	Daniel give back computer, because he`s done for the moment
Daniel FINISHED