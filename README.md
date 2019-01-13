**N26 coding challenge**

**Data Structure Choice**

Referring to the requirement: 

"The POST /transactions and GET /statistics endpoints MUST execute in constant time and memory ie O(1)"

I decided to use as data structure an array of fixed size of 
60000, implemented as a circular array. 
Each cell corresponds to a specific millisecond within a minute.

Choosing milliseconds I focused on accuracy knowing that, 
with a granularity of a second, the error margin could be relevant.

The algorithm complexity is O(1) because it works on a set of 60000 elements 

**Packaging** 

The division is done for "functionality", and then in three layers (web, service, model)

**Concurrency** 

Concurrency is handled inside the class "ConcurrentTransactionService",
that contains the circular array that represents our monitored object.
The chosen lock is "ReentrantReadWriteLock". The write lock is used
during the inserts and the deletes of transaction, while the read lock
is used to get the statistics.

**Testing**

I developed Unit tests and a concurrency integration test 
(possibly activated by a profile), considering that
some integration tests were already present, and the overall coverage was
already enough, in fact the coverage excluding the main is more than 95%

**Exceptions**

I handled all the http return codes as it was demanding the exercise using 
an exception handler. 
It was not necessary to handle the invalid json case, because it was already 
managed.

**Usage** 

As requested, the project builds with maven. 
In addition, to activate the concurrent integration test you should 
enable the profile "concurrency". 