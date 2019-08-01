# 中文迭代日志

> [CHANGELOG](https://github.com/Jacksgong/ThreadDebugger/blob/master/CHANGELOG.md)|[中文迭代日志](https://github.com/Jacksgong/ThreadDebugger/blob/master/CHANGELOG_zh.md)

## Version 1.6.1

_2019-08-01_

- 修复: 修复线程数计算不够准确的问题

## Version 1.6.0

_2019-07-29_

- 性能: 优化获取所有线程数的耗时（不再会获取对应的stacktrace)

## Version 1.5.3

_2018-09-28_

修复当`worker count`等于`max-pool-size`并且有一个`worker-thread`正阻塞在等待队列有新的任务并且在队列有新任务了但是cpu时间片没有分配到该阻塞`worker-thread`的时候，ExceedWait出现Stackoverflow的问题

## Version 1.5.2

_2017-03-23_

#### 新接口

- 新增 `ThreadDebugger#ignoreUnknownCategory`: 在进行输出时忽略未通过`ThreadDebugger#add`主动添加分类的线程变化。

#### 修复

- 修复: 修复了在错误的时机删除的输出分隔符。
- 修复: 修复在一些场景下，无法准确的匹配线程名的开头部分。

## Version 1.4.0

_2016-10-09_

#### 新接口

- 新增 `ThreadPools#newNoCorePool`: 如果有`threadCount`个数的任务正在运行，再增加进来的任务将会进入等待队列中直到有线程空闲出来。如果空闲线程空闲的时间大于`keepAliveTime`，那么它将会被回收。

#### 性能与提高

- 提高稳定性: 如果在调用`ThreadPools.newExceedWaitPool`时，所提供的`corePoolSize`等于`0`，那么我们会创建`NoCorePool`而非`ExceedWaitPool`，以此避免在高并发调度`ExceedWaitPool`的时候小概率出现`StackOverflow`的异常，因为在`ExceedWaitPool`中当`corePoolSize`为`0`并且高频率执行大量任务的时候，`getActiveCount()`的返回值有可能是`0`，但是实际的运行中的线程数是大于`0`的。

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
