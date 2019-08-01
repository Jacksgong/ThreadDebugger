/*
 * Copyright (C) 2015-2016 Jacksgong(blog.dreamtobe.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.dreamtobe.threaddebugger;

/**
 * The thread debuggers factory.
 */

public class ThreadDebuggers {

    public static IThreadDebugger create() {
        return new DefaultThreadDebugger();
    }

    public static IThreadDebugger createWithCommonThreadKey() {
        return create().add(CommonThreadKey.OpenSource.OKHTTP)
                .add(CommonThreadKey.OpenSource.OKIO)
                .add(CommonThreadKey.OpenSource.RETROFIT)
                .add(CommonThreadKey.OpenSource.CRASHLYTICS)
                .add(CommonThreadKey.OpenSource.LEAKCANARY)
                .add(CommonThreadKey.OpenSource.RX_JAVA, "RxJava")
                .add(CommonThreadKey.OpenSource.PICASSO)
                .add(CommonThreadKey.OpenSource.FILEDOWNLOADER)
                .add(CommonThreadKey.System.MAIN)
                .add(CommonThreadKey.System.CHROME)
                .add(CommonThreadKey.System.ASYNC_TASK)
                .add(CommonThreadKey.System.BINDER)
                .add(CommonThreadKey.System.FINALIZER)
                .add(CommonThreadKey.System.WIFI)
                .add(CommonThreadKey.System.RENDER_THREAD)
                .add(CommonThreadKey.System.HEAP_TASK_DAEMON)
                .add(CommonThreadKey.System.REFERENCE_QUEUE_DAEMON)
                .add(CommonThreadKey.System.FINALIZER_DAEMON)
                .add(CommonThreadKey.System.FINALIZER_WATCHDOG_DAEMON)
                .add(CommonThreadKey.System.JDWP)
                .add(CommonThreadKey.Media.AUDIO)
                .add(CommonThreadKey.Media.MEDIA)
                .add(CommonThreadKey.Media.EXO_PLAYER)
                .add(CommonThreadKey.Others.THREAD_DEBUGGER)
                .add(CommonThreadKey.Others.QUEUE);
    }
}
