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

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * The unit test for ThreadPool.
 */

public class ThreadPoolTest {

    @Test
    public void prefixNameAssignmentCorrect() {
        final String prefixName = "cn.dreamtobe";
        IExecutor executor = ThreadPools.newExceedWaitPool(1, 2, 3, TimeUnit.SECONDS, prefixName);
        Assertions.assertThat(executor.getPrefixName()).isEqualTo(prefixName);
    }
}
