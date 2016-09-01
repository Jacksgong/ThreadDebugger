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

import java.util.Collections;
import java.util.List;

/**
 * A no-op implementation of the thread category.
 */

public class ThreadCategory implements Cloneable {

    public String getAlias() {
        return "";
    }

    public List<String> diff(ThreadCategory category) {
        return Collections.EMPTY_LIST;
    }

    public boolean isDiff(ThreadCategory category) {
        return false;
    }


    public static class Builder {

        public Builder(String key) {
            this(key, key);
        }

        public Builder(String startWithKey, String alias) {
        }

        public boolean process(final int hashCode, final String threadName) {
            return false;
        }

        public void add(int hashCode, String threadName) {
        }

        public void reset() {
        }

        public ThreadCategory build() {
            return LazyLoader.EMPTY;
        }
    }

    private final static class LazyLoader {
        private final static ThreadCategory EMPTY = new ThreadCategory();
    }

    @SuppressWarnings("CloneDoesntDeclareCloneNotSupportedException")
    @Override
    protected ThreadCategory clone() {
        return LazyLoader.EMPTY;
    }

}
