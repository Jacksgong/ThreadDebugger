# ThreadDebugger

[![ThreadDebugger][thread_debugger_svg]][thread_debugger_link]
[![ThreadPools][thread_pools_svg]][thread_pools_link]
[![Build Status][build_status_svg]][build_status_link]

---

There are several ways to debugger the activity of threads in the application, such as the Allocation Tracking from Android Studio Monitor by the way there are information about the running threads, or recording the Method Profiling from the Android Device Monitor by the way it also present the running threads information, but they are a little too heavy, and not flexible enough sometimes.

With this ThreadDebugger, you don't need to worry about how long duration you recording, and you can find out the changing of the threads activity very easy.

---

## Install

```
```

## Start monitor

 ```java
 IThreadDebugger debugger = ThreadDebugger.install(
         ThreadDebuggers.createWithCommonThreadKey() /** The ThreadDebugger with known thread Categories **/
                 // add Thread Category
                 .add("IO", "IO")
                 .add("computation", "computation")
                 .add("network", "network")
                 .add("test1", "test1")
                 .add("test2", "test2")
                 .add("test3", "test3")
                 .add("test4", "test4"),

         2000, /** The frequent of Updating Thread Activity information **/

         new ThreadDebugger.ThreadChangedCallback() { /** The threads changed callback **/
             @Override
             public void onChanged(IThreadDebugger debugger) {
                 // callback this method when the threads in this application has changed.
                 Log.d(TAG, debugger.drawUpEachThreadInfoDiff());
                 Log.d(TAG, debugger.drawUpEachThreadSizeDiff());
                 Log.d(TAG, debugger.drawUpEachThreadSize());
                 Log.d(TAG, debugger.drawUpEachThreadInfo());
                 Log.d(TAG, debugger.drawUpUnknownInfo());
             }
         });
 ```

## Result

#### drawUpEachThreadInfoDiff

> `adb logcat | grep drawUpEachThreadInfoDiff:`

```
08-31 16:48:38.014 22672 22691 D DemoApplication: drawUpEachThreadInfoDiff: Thread count = 13. Thread differ : +13. main: +1 [(+)main] | Binder: +2 [(+)Binder_2, (+)Binder_1] | Finalizer: +2 [(+)FinalizerWatchdogDaemon, (+)FinalizerDaemon] | RenderThread: +1 [(+)RenderThread] | HeapTaskDaemon: +1 [(+)HeapTaskDaemon] | ReferenceQueueDaemon: +1 [(+)ReferenceQueueDaemon] | JDWP: +1 [(+)JDWP] | unknown: +4 [(+)hwuiTask1, (+)hwuiTask2, (+)ThreadDebugger, (+)Signal Catcher]
08-31 16:50:43.876 22672 22691 D DemoApplication: drawUpEachThreadInfoDiff: Thread count = 15. Thread differ : +2. computation: +1 [(+)computation-1::running-Test1:7s] | test1: +1 [(+)test1-ExceedDiscard-1::running-RandomThreadWork]
08-31 16:50:48.915 22672 22691 D DemoApplication: drawUpEachThreadInfoDiff: Thread count = 16. Thread differ : +1. test1: +1 [(+)test1-ExceedDiscard-2::running-Test2:1s]
08-31 16:50:49.927 22672 22691 D DemoApplication: drawUpEachThreadInfoDiff: Thread count = 17. Thread differ : +1. computation: +1 [(+)computation-2::running-Test3:8s] | test1: SWAP [(+)test1-ExceedDiscard-2::idle, (-)test1-ExceedDiscard-2::running-Test2:1s]
08-31 16:50:50.939 22672 22691 D DemoApplication: drawUpEachThreadInfoDiff: Thread count = 17. Thread differ : 0. computation: SWAP [(+)computation-1::idle, (-)computation-1::running-Test1:7s]
08-31 16:50:51.952 22672 22691 D DemoApplication: drawUpEachThreadInfoDiff: Thread count = 18. Thread differ : +1. computation: +1 [(+)computation-3::running-Test4:9s]
08-31 16:50:52.966 22672 22691 D DemoApplication: drawUpEachThreadInfoDiff: Thread count = 19. Thread differ : +1. network: +1 [(+)network-1::running-Test5:1s]
08-31 16:50:53.981 22672 22691 D DemoApplication: drawUpEachThreadInfoDiff: Thread count = 19. Thread differ : 0. network: SWAP [(+)network-1::idle, (-)network-1::running-Test5:1s]
08-31 16:50:54.995 22672 22691 D DemoApplication: drawUpEachThreadInfoDiff: Thread count = 20. Thread differ : +1. IO: +1 [(+)io-1::running-Test6:1s]
08-31 16:50:56.010 22672 22691 D DemoApplication: drawUpEachThreadInfoDiff: Thread count = 20. Thread differ : 0. IO: SWAP [(+)io-1::idle, (-)io-1::running-Test6:1s]
08-31 16:50:58.029 22672 22691 D DemoApplication: drawUpEachThreadInfoDiff: Thread count = 20. Thread differ : 0. computation: SWAP [(+)computation-2::idle, (-)computation-2::running-Test3:8s] | test1: SWAP [(+)test1-ExceedDiscard-1::idle, (-)test1-ExceedDiscard-1::running-RandomThreadWork]
08-31 16:51:01.052 22672 22691 D DemoApplication: drawUpEachThreadInfoDiff: Thread count = 20. Thread differ : 0. computation: SWAP [(+)computation-3::idle, (-)computation-3::running-Test4:9s]
```

#### drawUpEachThreadSizeDiff

> `adb logcat | grep drawUpEachThreadSizeDiff:`

```
08-31 16:48:38.014 22672 22691 D DemoApplication: drawUpEachThreadSizeDiff: Thread count = 13. Thread differ : +13. main: +1 | Binder: +2 | Finalizer: +2 | RenderThread: +1 | HeapTaskDaemon: +1 | ReferenceQueueDaemon: +1 | JDWP: +1 | unknown: +4 [(+)hwuiTask1, (+)hwuiTask2, (+)ThreadDebugger, (+)Signal Catcher]
08-31 16:50:43.877 22672 22691 D DemoApplication: drawUpEachThreadSizeDiff: Thread count = 15. Thread differ : +2. computation: +1 | test1: +1
08-31 16:50:48.915 22672 22691 D DemoApplication: drawUpEachThreadSizeDiff: Thread count = 16. Thread differ : +1. test1: +1
08-31 16:50:49.928 22672 22691 D DemoApplication: drawUpEachThreadSizeDiff: Thread count = 17. Thread differ : +1. computation: +1
08-31 16:50:50.940 22672 22691 D DemoApplication: drawUpEachThreadSizeDiff: Thread count = 17. Thread size has not changed.
08-31 16:50:51.953 22672 22691 D DemoApplication: drawUpEachThreadSizeDiff: Thread count = 18. Thread differ : +1. computation: +1
08-31 16:50:52.967 22672 22691 D DemoApplication: drawUpEachThreadSizeDiff: Thread count = 19. Thread differ : +1. network: +1
08-31 16:50:53.981 22672 22691 D DemoApplication: drawUpEachThreadSizeDiff: Thread count = 19. Thread size has not changed.
08-31 16:50:54.996 22672 22691 D DemoApplication: drawUpEachThreadSizeDiff: Thread count = 20. Thread differ : +1. IO: +1
08-31 16:50:56.010 22672 22691 D DemoApplication: drawUpEachThreadSizeDiff: Thread count = 20. Thread size has not changed.
08-31 16:50:58.030 22672 22691 D DemoApplication: drawUpEachThreadSizeDiff: Thread count = 20. Thread size has not changed.
08-31 16:51:01.053 22672 22691 D DemoApplication: drawUpEachThreadSizeDiff: Thread count = 20. Thread size has not changed.
```

#### drawUpEachThreadSize

> `adb logcat | grep drawUpEachThreadSize:`

```
08-31 16:48:38.015 22672 22691 D DemoApplication: drawUpEachThreadSize: Thread count = 13. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | unknown: 4
08-31 16:50:43.878 22672 22691 D DemoApplication: drawUpEachThreadSize: Thread count = 15. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | computation: 1 | test1: 1 | unknown: 4
08-31 16:50:48.916 22672 22691 D DemoApplication: drawUpEachThreadSize: Thread count = 16. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | computation: 1 | test1: 2 | unknown: 4
08-31 16:50:49.929 22672 22691 D DemoApplication: drawUpEachThreadSize: Thread count = 17. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | computation: 2 | test1: 2 | unknown: 4
08-31 16:50:50.941 22672 22691 D DemoApplication: drawUpEachThreadSize: Thread count = 17. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | computation: 2 | test1: 2 | unknown: 4
08-31 16:50:51.955 22672 22691 D DemoApplication: drawUpEachThreadSize: Thread count = 18. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | computation: 3 | test1: 2 | unknown: 4
08-31 16:50:52.969 22672 22691 D DemoApplication: drawUpEachThreadSize: Thread count = 19. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | computation: 3 | network: 1 | test1: 2 | unknown: 4
08-31 16:50:53.982 22672 22691 D DemoApplication: drawUpEachThreadSize: Thread count = 19. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | computation: 3 | network: 1 | test1: 2 | unknown: 4
08-31 16:50:54.997 22672 22691 D DemoApplication: drawUpEachThreadSize: Thread count = 20. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | IO: 1 | computation: 3 | network: 1 | test1: 2 | unknown: 4
08-31 16:50:56.011 22672 22691 D DemoApplication: drawUpEachThreadSize: Thread count = 20. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | IO: 1 | computation: 3 | network: 1 | test1: 2 | unknown: 4
08-31 16:50:58.030 22672 22691 D DemoApplication: drawUpEachThreadSize: Thread count = 20. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | IO: 1 | computation: 3 | network: 1 | test1: 2 | unknown: 4
08-31 16:51:01.053 22672 22691 D DemoApplication: drawUpEachThreadSize: Thread count = 20. main: 1 | Binder: 2 | Finalizer: 2 | RenderThread: 1 | HeapTaskDaemon: 1 | ReferenceQueueDaemon: 1 | JDWP: 1 | IO: 1 | computation: 3 | network: 1 | test1: 2 | unknown: 4
```

#### drawUpEachThreadInfo

> `adb logcat | grep drawUpEachThreadInfo:`

```
08-31 16:48:38.015 22672 22691 D DemoApplication: drawUpEachThreadInfo: Thread count = 13. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerWatchdogDaemon, FinalizerDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | unknown[hwuiTask1, hwuiTask2, ThreadDebugger, Signal Catcher]
08-31 16:50:43.877 22672 22691 D DemoApplication: drawUpEachThreadInfo: Thread count = 15. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | computation[computation-1::running-Test1:7s] | test1[test1-ExceedDiscard-1::running-RandomThreadWork] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:48.916 22672 22691 D DemoApplication: drawUpEachThreadInfo: Thread count = 16. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | computation[computation-1::running-Test1:7s] | test1[test1-ExceedDiscard-1::running-RandomThreadWork, test1-ExceedDiscard-2::running-Test2:1s] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:49.929 22672 22691 D DemoApplication: drawUpEachThreadInfo: Thread count = 17. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | computation[computation-1::running-Test1:7s, computation-2::running-Test3:8s] | test1[test1-ExceedDiscard-1::running-RandomThreadWork, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:50.941 22672 22691 D DemoApplication: drawUpEachThreadInfo: Thread count = 17. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | computation[computation-1::idle, computation-2::running-Test3:8s] | test1[test1-ExceedDiscard-1::running-RandomThreadWork, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:51.954 22672 22691 D DemoApplication: drawUpEachThreadInfo: Thread count = 18. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | computation[computation-1::idle, computation-2::running-Test3:8s, computation-3::running-Test4:9s] | test1[test1-ExceedDiscard-1::running-RandomThreadWork, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:52.968 22672 22691 D DemoApplication: drawUpEachThreadInfo: Thread count = 19. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | computation[computation-1::idle, computation-2::running-Test3:8s, computation-3::running-Test4:9s] | network[network-1::running-Test5:1s] | test1[test1-ExceedDiscard-1::running-RandomThreadWork, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:53.982 22672 22691 D DemoApplication: drawUpEachThreadInfo: Thread count = 19. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | computation[computation-1::idle, computation-2::running-Test3:8s, computation-3::running-Test4:9s] | network[network-1::idle] | test1[test1-ExceedDiscard-1::running-RandomThreadWork, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:54.997 22672 22691 D DemoApplication: drawUpEachThreadInfo: Thread count = 20. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | IO[io-1::running-Test6:1s] | computation[computation-1::idle, computation-2::running-Test3:8s, computation-3::running-Test4:9s] | network[network-1::idle] | test1[test1-ExceedDiscard-1::running-RandomThreadWork, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:56.011 22672 22691 D DemoApplication: drawUpEachThreadInfo: Thread count = 20. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | IO[io-1::idle] | computation[computation-1::idle, computation-2::running-Test3:8s, computation-3::running-Test4:9s] | network[network-1::idle] | test1[test1-ExceedDiscard-1::running-RandomThreadWork, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:58.030 22672 22691 D DemoApplication: drawUpEachThreadInfo: Thread count = 20. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | IO[io-1::idle] | computation[computation-1::idle, computation-2::idle, computation-3::running-Test4:9s] | network[network-1::idle] | test1[test1-ExceedDiscard-1::idle, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:51:01.053 22672 22691 D DemoApplication: drawUpEachThreadInfo: Thread count = 20. main[main] | Binder[Binder_2, Binder_1] | Finalizer[FinalizerDaemon, FinalizerWatchdogDaemon] | RenderThread[RenderThread] | HeapTaskDaemon[HeapTaskDaemon] | ReferenceQueueDaemon[ReferenceQueueDaemon] | JDWP[JDWP] | IO[io-1::idle] | computation[computation-1::idle, computation-2::idle, computation-3::idle] | network[network-1::idle] | test1[test1-ExceedDiscard-1::idle, test1-ExceedDiscard-2::idle] | unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
```

#### drawUpUnknownInfo

> `adb logcat | grep drawUpUnknownInfo:`

```
➜  ~  >adb logcat | grep drawUpUnknownInfo:
08-31 16:48:38.015 22672 22691 D DemoApplication: drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = +4. unknown[hwuiTask1, hwuiTask2, ThreadDebugger, Signal Catcher]
08-31 16:50:43.878 22672 22691 D DemoApplication: drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:48.917 22672 22691 D DemoApplication: drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:49.929 22672 22691 D DemoApplication: drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:50.941 22672 22691 D DemoApplication: drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:51.956 22672 22691 D DemoApplication: drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:52.969 22672 22691 D DemoApplication: drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:53.983 22672 22691 D DemoApplication: drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:54.997 22672 22691 D DemoApplication: drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:56.012 22672 22691 D DemoApplication: drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:50:58.030 22672 22691 D DemoApplication: drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
08-31 16:51:01.053 22672 22691 D DemoApplication: drawUpUnknownInfo: Unknow thread count = 4. Unknow thread differ = 0. unknown[hwuiTask2, hwuiTask1, ThreadDebugger, Signal Catcher]
```

## Method in IThreadDebugger

| Method | Function
| --- | ---
| add(key:String) | add the thread category with the key used to compare the start of thread-name and also as its alias to the Debugger.
| add(startWithKey:String , alias:String) | add the thread category with the startWithKey and its alias to the Debugger.
| refresh() | Refresh threads situation.
| drawUpEachThreadSize() | the content of the size of each thread.
| drawUpEachThreadInfo() | the content of the information of each thread.
| drawUpEachThreadDiffSize() | the content of the changed size of each changed thread.
| drawUpEachThreadDiffInfo() | the content of the information of the each changed thread.
| drawUpUnknownInfo() | the content of unknown threads info.
| isSizeChanged() | Whether threads size has changed.
| isChanged() | Whether threads has changed.

## Method in ThreadPools

> The executor from this thread pool factory will require each task in it to provide its exact name to facilitate debugging.

> - Entrance: [ThreadPools](https://github.com/Jacksgong/ThreadDebugger/blob/master/threadpool/src/main/java/cn/dreamtobe/threadpool/ThreadPools.java)
> - Demo: [DemoThreadPoolCentral](https://github.com/Jacksgong/ThreadDebugger/blob/master/demo/src/main/java/cn/dreamtobe/threaddebugger/demo/DemoThreadPoolCentral.java)

| Method | Function
| --- | ---
| newExceedWaitPool | If the size of active command equal to the {@code maximumPoolSize}, the further command will be enqueued to the waiting queue, and will be executed when the size of active command less than the {@code maximumPoolSize}.
| newExceedDiscardPool | If the size of active command equal to the {@code maximumPoolSize}, the further command will be discard.
| newExceedCallerRunsPool | If the size of active command equal to the {@code maximumPoolSize}, the further command will be executed immediately in the caller thread.
| newExceedCallImmediatelyPool | If the size of active command equal to the {@code maximumPoolSize}, the further command will be executed immediately in the global temporary unbound thread pool.
| newSinglePool | The same to the {@link Executors#newSingleThreadExecutor()}.
| newFixedPool | The same to the {@link Executors#newFixedThreadPool(int)}.
| newCachedPool | The same to the {@link Executors#newCachedThreadPool()}.


## Demo Project

![][demo_screenshot_png]

## License

```
Copyright (C) 2015-2016 Jacksgong(blog.dreamtobe.cn)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[demo_screenshot_png]: https://raw.githubusercontent.com/Jacksgong/ThreadDebugger/master/art/demo_screenshot.png
[thread_pools_svg]: https://img.shields.io/badge/Thread-Pools-green.svg
[thread_pools_link]: https://github.com/Jacksgong/ThreadDebugger/blob/master/threadpool/src/main/java/cn/dreamtobe/threadpool/ThreadPools.java
[thread_debugger_svg]: https://img.shields.io/badge/Thread-Debugger-orange.svg
[thread_debugger_link]: https://github.com/Jacksgong/ThreadDebugger/blob/master/threaddebugger/src/main/java/cn/dreamtobe/threaddebugger/ThreadDebugger.java
[build_status_svg]: https://travis-ci.org/Jacksgong/ThreadDebugger.svg?branch=master
[build_status_link]: https://travis-ci.org/Jacksgong/ThreadDebugger
