# 中文迭代日志

> [CHANGELOG](https://github.com/Jacksgong/ThreadDebugger/blob/master/CHANGELOG.md)|[中文迭代日志](https://github.com/Jacksgong/ThreadDebugger/blob/master/CHANGELOG_zh.md)

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
