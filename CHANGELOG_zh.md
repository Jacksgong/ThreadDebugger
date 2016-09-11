# 中文迭代日志

> [CHANGELOG](https://github.com/Jacksgong/ThreadDebugger/blob/master/CHANGELOG.md)|[中文迭代日志](https://github.com/Jacksgong/ThreadDebugger/blob/master/CHANGELOG_zh.md)

## Version 1.3.3

_2016-09-11_

#### 新接口

- 新增 `ExceedWait.java`: 暴露`exceed-wait-pool`的组件，使得它更加易于拓展。

#### 性能与提高

- 提高实用性: 降低`thread-pool`库的minSDK版本: 9->7。

#### 修复

- 修复(thread-pool): 覆盖当仅仅只有`exceed-queue`中有需要执行的任务的时候，主队列还在等待任务入队或者没有执行中的任务的情况。
- 修复(thread-pool): 对于`exceed-wait-pool`，当`exceed-queue`中存在任务需要执行的时候，错误的还在等待主队列，导致`exceed-queue`中的队列无法及时的被执行。
- 修复: `ExceedWait.Queue`的构造函数是`package visible`导致无法直接创建的问题。

## Version 1.2.1

_2016-09-09_

#### 性能与提高

- 提高性能: 使用`String#compareToIgnoreCase()`代替`String#toLowCase()`+`String#startWith()`来减少在`IThreadDebugger#refresh()`中的耗时。
- 提高性能: 优化thread-debugger中的代码编写风格来优化其性能。

## Version 1.2.0

_2016-09-02_

#### 新接口

- 新增 `threaddebugger-no-op`: 十分轻量,不包含任何`threaddebugger`的代码, 目前只包含一些空Class, 便于release编译时不带上`threaddebugger`。

## Version 1.1.0

_2016-09-01_

#### 新接口

- 新增 `ThreadDebugger.NEED_PRINT_COST`: 用于控制是否需要输出刷新调试器所消耗的时间。
- 新增 `IExecutor#getPrefixName(void):String`: 获取当前Executor名称的前缀。

## Version 1.0.1

_2016-09-01_

#### 性能与提高

- 提高实用性: 修正`ThreadPools.java`中的Java-doc。

## Version 1.0.0

_2016-09-01_

- initial release
