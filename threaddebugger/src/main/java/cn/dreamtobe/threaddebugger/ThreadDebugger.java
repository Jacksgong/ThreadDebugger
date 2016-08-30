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

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by Jacksgong on 26/08/2016.
 */

public class ThreadDebugger {

    public static IThreadDebugger install() {
        return install(ThreadDebuggers.createWithCommonThreadKey());
    }

    public static IThreadDebugger install(final IThreadDebugger debugger,
                                          ThreadChangedCallback callback) {
        return install(debugger, 2000, callback);
    }

    public static IThreadDebugger install(final IThreadDebugger debugger) {
        return install(debugger, null);
    }

    private static Handler HANDLER;

    public static synchronized IThreadDebugger install(final IThreadDebugger debugger,
                                                       final int updateMilliSecond,
                                                       final ThreadChangedCallback callback) {
        uninstall();

        HandlerThread handlerThread = new HandlerThread("ThreadDebugger");
        handlerThread.start();

        HANDLER = new Handler(handlerThread.getLooper(), new Handler.Callback() {

            @Override
            public boolean handleMessage(Message message) {
                long start = SystemClock.uptimeMillis();
                debugger.refresh();

                if (callback != null && debugger.isChanged()) {
                    callback.onChanged(debugger);

                }
                Log.d("ThreadDebugger", "consume: " + (SystemClock.uptimeMillis() - start));
                HANDLER.sendEmptyMessageDelayed(0, updateMilliSecond);
                return false;
            }
        });

        HANDLER.sendEmptyMessageDelayed(0, updateMilliSecond);

        return debugger;
    }

    public static synchronized boolean uninstall() {
        if (HANDLER == null) {
            return false;
        }
        HANDLER.removeMessages(0);
        HANDLER.getLooper().quit();

        return true;
    }

    public interface ThreadChangedCallback {
        void onChanged(IThreadDebugger debugger);
    }
}
