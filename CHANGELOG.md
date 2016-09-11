# Change log

> [CHANGELOG](https://github.com/Jacksgong/ThreadDebugger/blob/master/CHANGELOG.md)|[中文迭代日志](https://github.com/Jacksgong/ThreadDebugger/blob/master/CHANGELOG_zh.md)

## Version 1.3.2

_2016-09-11_

#### Fixes

- Fix(thread-pool): For `exceed-wait-pool`, Mistake still to wait for a command in the main queue when there are commands in the `exceed-queue` need to execute.

## Version 1.3.1

_2016-09-11_

#### Fixes

- Fix: The construct method of `ExceedWait.Queue` is `package visible`, result in can't create it directly.

## Version 1.3.0

_2016-09-11_

#### New Interfaces

- Add `ExceedWait.java`: Expose components of the `exceed-wait-pool` to make it more flexible to extend.

#### Enhancement

- Improve Practicability: Downgrade the min SDK version of the thread-pool library: 9->7.

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
