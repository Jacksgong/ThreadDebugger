# Change log

> [CHANGELOG](https://github.com/Jacksgong/ThreadDebugger/blob/master/CHANGELOG.md)|[中文迭代日志](https://github.com/Jacksgong/ThreadDebugger/blob/master/CHANGELOG_zh.md)

## Version 1.6.3

_2019-12_15_

- Fix: Fix low case for stack overflow when ExceedWait no active and exceed-queue more then one wait job
- Fix: Fix small case exceed queue can't be consume but wait for new task

## Version 1.6.2

_2019-08-01_

- Fix: fix the count of thread not right exactly.
- Fix: fix thread category can't match since not ignore its case

## Version 1.6.0

_2019-07-29_

- Prefs: optimize getting all threads operation without get their stack traces

## Version 1.5.3

_2018-09-28_

- Fix stackoverflow on ExceedWait when worker count is meet max-pool-size and one-worker-thread is blocked waiting for queue available and cpu time slice not alloc for blocked thread after queue available

## Version 1.5.2

_2017-03-23_

#### New Interfaces

- Add `ThreadDebugger#ignoreUnknownCategory`: Only print thread info which you explicitly add through `ThreadDebugger#add` for method `ThreadDebugger#drawUpEachThreadInfo`, `ThreadDebugger#drawUpEachThreadInfo`, `ThreadDebugger#drawUpEachThreadSizeDiff`, `ThreadDebugger#drawUpEachThreadInfoDiff`, `ThreadDebugger#isChanged`, `ThreadDebugger#isSizeChanged`.

#### Fixes

- Fix: fix delete split on wrong opportunity.
- Fix: fix can't match start-with-thread-name exactly.

## Version 1.4.0

_2016-10-09_

#### New Interfaces

- Add `ThreadPools#newNoCorePool`: If there are `threadCount` tasks are running, the further task will be enqueued to the waiting queue, and will be executed when the size of running tasks less than `threadCount`. If the thread in this pool is turn to idle and the interval time of waiting for new tasks more `keepAliveTime`, it will be terminate to reduce the cost of resources.

#### Enhancement

- Improve Stability: If `corePoolSize` is equal to` 0` when `ThreadPools.newExceedWaitPool` is called, then we will create` NoCorePool` instead of `ExceedWaitPool` to avoid the small probability of `StackOverflow` because `getActiveCount()` may return `0` when `corePoolSize` is `0` in `ExceedWaitPool` and performs a large number of tasks at high frequencies, but the actual running thread The number is greater than `0`.

## Version 1.3.3

_2016-09-11_

#### New Interfaces

- Add `ExceedWait.java`: Expose components of the `exceed-wait-pool` to make it more flexible to extend.

#### Enhancement

- Improve Practicability: Downgrade the min SDK version of the thread-pool library: 9->7.

#### Fixes

- Fix(thread-pool): Cover the case of only the `exceed-queue` has commands need to execute with the main queue is waiting for a command or there is no active command in the `exceed-wait-pool`.
- Fix(thread-pool): For `exceed-wait-pool`, Mistake still to wait for a command in the main queue when there are commands in the `exceed-queue` need to execute.
- Fix: The construct method of `ExceedWait.Queue` is `package visible`, result in can't create it directly.

## Version 1.2.1

_2016-09-09_

#### Enhancement

- Improve Performance: Using `String#compareToIgnoreCase()` instead of `String#toLowCase()` and `String#startWith()` to reduce cost time in the `IThreadDebugger#refresh()` method.
- Improve Performance: Optimize the code style in thread-debugger library to improve the performance.

## Version 1.2.0

_2016-09-02_

#### New Interfaces

- Add `threaddebugger-no-op`: is lighter, it does not has any operation of `threaddebugger`, only has some empty classes, for don't import `threaddebugger` when release compile.

## Version 1.1.0

_2016-09-01_

#### New Interfaces

- Add `ThreadDebugger.NEED_PRINT_COST`: Control whether need print how time cost in refreshing debugger.
- Add `IExecutor#getPrefixName(void):String`: Get the prefix name of the executor.

## Version 1.0.1

_2016-09-01_

#### Enhancement

- Improve Practicability: Correct the java-doc in `ThreadPools.java`.

## Version 1.0.0

_2016-09-01_

- initial release
