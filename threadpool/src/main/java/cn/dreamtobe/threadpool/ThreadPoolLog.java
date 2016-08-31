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

package cn.dreamtobe.threadpool;

import android.util.Log;

import java.util.Locale;

/**
 * The log printer for ThreadPool.
 * <p>
 * You can add follow rules to proguard-rules.pro to optimize code when you release your apk:
 * <p>
 * -assumenosideeffects class cn.dreamtobe.threadpool {
 * public static void d(...);
 * }
 */

public final class ThreadPoolLog {
    @SuppressWarnings("CanBeFinal")
    public static boolean NEED_LOG = false;

    public static void d(String tag, String format, Object... args) {
        if (NEED_LOG) {
            Log.d(tag, String.format(Locale.ENGLISH, format, args));
        }
    }
}
