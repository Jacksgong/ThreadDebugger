# Change log

> [CHANGELOG](https://github.com/Jacksgong/ThreadDebugger/blob/master/CHANGELOG.md)|[中文迭代日志](https://github.com/Jacksgong/ThreadDebugger/blob/master/CHANGELOG_zh.md)

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
