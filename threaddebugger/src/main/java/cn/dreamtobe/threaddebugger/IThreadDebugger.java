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
 * The thread debugger.
 *
 * @see ThreadDebuggers
 */

public interface IThreadDebugger {
    /**
     * @param key the key used to compare the start of thread-name and as the alias for this category.
     */
    IThreadDebugger add(String key);

    /**
     * @param startWithKey the key used to compare the start of thread-name for this category.
     * @param alias        the alias for this category.
     */
    IThreadDebugger add(String startWithKey, String alias);

    /**
     * Refresh threads situation.
     */
    void refresh();

    /**
     * @return the content of the size of each thread.
     */
    String drawUpEachThreadSize();

    /**
     * @return the content of the information of each thread.
     */
    String drawUpEachThreadInfo();

    /**
     * @return the content of the changed size of each changed thread.
     */
    String drawUpEachThreadSizeDiff();

    /**
     * @return the content of the information of the each changed thread.
     */
    String drawUpEachThreadInfoDiff();

    /**
     * @return the content of unknown threads info.
     */
    String drawUpUnknownInfo();

    /**
     * @return Whether threads size has changed.
     */
    boolean isSizeChanged();

    /**
     * @return Whether threads has changed.
     */
    boolean isChanged();
}
