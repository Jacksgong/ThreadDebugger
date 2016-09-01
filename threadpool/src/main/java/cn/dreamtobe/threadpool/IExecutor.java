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

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

/**
 * The executor running command with exactly their own task-name.
 */
public interface IExecutor {
    /**
     * Executes the given {@code runnable} at some time in the feature.
     *
     * @param name    the name of the {@code command}.
     * @param command the runnable task.
     */
    void execute(String name, Runnable command);

    /**
     * Submits a Runnable task for execution and returns a Future
     * representing that task. The Future's {@code get} method will
     * return the given result upon successful completion.
     *
     * @param name   the name of the {@code task}.
     * @param task   the task to submit.
     * @param result the result to return.
     * @param <T>    the type of the result.
     * @return a Future representing pending completion of the task.
     * @throws RejectedExecutionException if the task cannot be
     *                                    scheduled for execution
     * @throws NullPointerException       if the task is null.
     */
    <T> Future<T> submit(String name, Runnable task, T result);

    /**
     * Submits a Runnable task for execution and returns a Future
     * representing that task. The Future's {@code get} method will
     * return {@code null} upon <em>successful</em> completion.
     *
     * @param name the name of the {@code task}.
     * @param task the task to submit.
     * @return a Future representing pending completion of the task.
     * @throws RejectedExecutionException if the task cannot be
     *                                    scheduled for execution
     * @throws NullPointerException       if the task is null.
     */
    Future<?> submit(String name, Runnable task);

    /**
     * Submits a value-returning task for execution and returns a
     * Future representing the pending results of the task. The
     * Future's {@code get} method will return the task's result upon
     * successful completion.
     * <p>
     * <p>
     * If you would like to immediately block waiting
     * for a task, you can use constructions of the form
     * {@code result = exec.submit(aCallable).get();}
     * <p>
     * <p>Note: The {@link Executors} class includes a set of methods
     * that can convert some other common closure-like objects,
     * for example, {@link java.security.PrivilegedAction} to
     * {@link Callable} form so they can be submitted.
     *
     * @param name the name of the {@code task}.
     * @param task the task to submit.
     * @param <T>  the type of the task's result.
     * @return a Future representing pending completion of the task.
     * @throws RejectedExecutionException if the task cannot be
     *                                    scheduled for execution
     * @throws NullPointerException       if the task is null.
     */
    <T> Future<T> submit(String name, Callable<T> task);

    /**
     * Removes this task from the executor's internal queue if it is
     * present, thus causing it not to be run if it has not already
     * started.
     *
     * @param command the task to remove
     * @return {@code true} if the task was removed
     */
    boolean remove(Runnable command);

    /**
     * @return Whether this executor has already in the situation of shutdown.
     * @see ThreadExecutor.Exposed#shutdown()
     * @see ThreadExecutor.Exposed#shutdownNow()
     */
    boolean isShutdown();

    /**
     * @return The prefix name of this executor.
     */
    String getPrefixName();
}