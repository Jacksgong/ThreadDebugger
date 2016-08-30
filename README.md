# ThreadDebugger

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
                 .add("ThreadPoolCentral-IO", "IO")
                 .add("ThreadPoolCentral-computation", "computation")
                 .add("ThreadPoolCentral-network", "network")
                 .add("ThreadPoolCentral-test1", "test1")
                 .add("ThreadPoolCentral-test2", "test2")
                 .add("ThreadPoolCentral-test3", "test3")
                 .add("ThreadPoolCentral-test4", "test4"),

         2000, /** The frequent of Updating Thread Activity information **/

         new ThreadDebugger.ThreadChangedCallback() { /** The threads changed callback **/
             @Override
             public void onChanged(IThreadDebugger debugger) {
                 // callback this method when the threads in this application has changed.
                 Log.d(TAG, debugger.drawUpEachThreadDiffInfo());
                 Log.d(TAG, debugger.drawUpEachThreadDiffSize());
                 Log.d(TAG, debugger.drawUpEachThreadSize());
                 Log.d(TAG, debugger.drawUpEachThreadInfo());
                 Log.d(TAG, debugger.drawUpUnknownInfo());
             }
         });
 ```

## Result

#### drawUpEachThreadDiffInfo

```
```

#### drawUpEachThreadDiffSize

```
```

#### drawUpEachThreadSize

```
```

#### drawUpEachThreadInfo

```
```

#### drawUpUnknownInfo

```
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

## Demo Project

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
