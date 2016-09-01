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
 * A no-op implementation of the thread debuggers factory.
 */

public class ThreadDebuggers {
    private final static class LazyLoader {
        private final static IThreadDebugger EMPTY = new IThreadDebugger() {
            @Override
            public IThreadDebugger add(String key) {
                return this;
            }

            @Override
            public IThreadDebugger add(String startWithKey, String alias) {
                return this;
            }

            @Override
            public void refresh() {
            }

            @Override
            public String drawUpEachThreadSize() {
                return "";
            }

            @Override
            public String drawUpEachThreadInfo() {
                return "";
            }

            @Override
            public String drawUpEachThreadSizeDiff() {
                return "";
            }

            @Override
            public String drawUpEachThreadInfoDiff() {
                return "";
            }

            @Override
            public String drawUpUnknownInfo() {
                return "";
            }

            @Override
            public boolean isSizeChanged() {
                return false;
            }

            @Override
            public boolean isChanged() {
                return false;
            }
        };
    }


    public static IThreadDebugger create() {
        return LazyLoader.EMPTY;
    }

    public static IThreadDebugger createWithCommonThreadKey() {
        return create();
    }
}
