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

package cn.dreamtobe.threaddebugger.demo;

import android.app.Application;
import android.util.Log;

import cn.dreamtobe.threaddebugger.IThreadDebugger;
import cn.dreamtobe.threaddebugger.ThreadDebugger;
import cn.dreamtobe.threaddebugger.ThreadDebuggers;

/**
 * Created by Jacksgong on 27/08/2016.
 */

@SuppressWarnings("DefaultFileTemplate")
public class DemoApplication extends Application {

    private static final String TAG = "DemoApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        //noinspection UnusedAssignment
        IThreadDebugger debugger = ThreadDebugger.install(
                ThreadDebuggers.createWithCommonThreadKey()
                        .add("IO", "IO") /** add Thread Category **/
                        .add("computation", "computation")
                        .add("network", "network")
                        .add("test1", "test1")
                        .add("test2", "test2")
                        .add("test3", "test3")
                        .add("test4", "test4"),

                1000, /** The frequent of Updating Thread Activity information **/

                new ThreadDebugger.ThreadChangedCallback() {
                    @Override
                    public void onChanged(IThreadDebugger debugger) {
                        // callback this method when the threads in this application has changed.
                        Log.d(TAG, debugger.drawUpEachThreadInfoDiff());
                        Log.d(TAG, debugger.drawUpEachThreadSizeDiff());
                        Log.d(TAG, debugger.drawUpEachThreadInfo());
                        Log.d(TAG, debugger.drawUpEachThreadSize());
                        Log.d(TAG, debugger.drawUpUnknownInfo());
                    }
                });
    }
}
