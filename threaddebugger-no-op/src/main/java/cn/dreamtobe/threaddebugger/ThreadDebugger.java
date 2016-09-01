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
 * A no-op implementation of the thread debugger.
 */

public class ThreadDebugger {

    public static boolean NEED_PRINT_COST = false;

    public static IThreadDebugger install() {
        return install(ThreadDebuggers.create());
    }

    public static IThreadDebugger install(final IThreadDebugger debugger,
                                          ThreadChangedCallback callback) {
        return install(debugger, 2000, callback);
    }

    public static IThreadDebugger install(final IThreadDebugger debugger) {
        return install(debugger, null);
    }

    public static synchronized IThreadDebugger install(final IThreadDebugger debugger,
                                                       final int updateMilliSecond,
                                                       final ThreadChangedCallback callback) {
        return debugger;
    }

    public static synchronized boolean uninstall() {
        return true;
    }

    public interface ThreadChangedCallback {
        void onChanged(IThreadDebugger debugger);
    }
}
