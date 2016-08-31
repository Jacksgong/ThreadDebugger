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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import cn.dreamtobe.threadpool.IExecutor;

/**
 * Created by Jacksgong on 27/08/2016.
 */

@SuppressWarnings("DefaultFileTemplate")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();

        mRandomThreadWorkBtn.setOnClickListener(new View.OnClickListener() {
            private final String mRandomThreadWorkName = "RandomThreadWork";
            private boolean mIsRunning = false;
            private Thread mRunningThread = null;

            @Override
            public void onClick(View view) {
                if (mIsRunning) {
                    mIsRunning = false;
                    if (mRunningThread != null) {
                        mRunningThread.interrupt();
                        mRunningThread = null;
                    }
                    mRandomThreadWorkBtn.setText(R.string.start_random_thread_loop);
                    return;
                }

                mIsRunning = true;
                mRandomThreadWorkBtn.setText(R.string.stop_random_thread_loop);

                DemoThreadPoolCentral.test1().execute(mRandomThreadWorkName, new Runnable() {
                    private final Random mRandomTool = new Random();

                    @Override
                    public void run() {
                        if (!mIsRunning) {
                            return;
                        }

                        mRunningThread = Thread.currentThread();
                        try {
                            while (mIsRunning) {
                                final int randomCount = mRandomTool.nextInt(4) + 1;
                                for (int i = 0; i < randomCount; i++) {
                                    randomOneThreadWork();
                                    final int waitSec = mRandomTool.nextInt(5) + 1;
                                    try {
                                        Thread.sleep(waitSec * 1000);
                                    } catch (InterruptedException e) {
                                        return;
                                    }
                                }
                            }
                        } finally {
                            mRunningThread = null;
                            mIsRunning = false;
                        }

                    }
                });

            }
        });

        mAddTaskToIoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskToIO();
            }
        });

        mAddTaskToComputationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskToComputation();
            }
        });

        mAddTaskToNetworkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskToNetwork();
            }
        });

        mAddTaskToTest1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskToTest1();
            }
        });

        mAddTaskToTest2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskToTest2();
            }
        });

        mAddTaskToTest3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskToTest3();
            }
        });

        mAddTaskToTest4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskToTest4();
            }
        });
    }

    private static class RandomRunnable implements Runnable {

        private final int mRandomWaitingSec;

        RandomRunnable(int randomWaitingSec) {
            this.mRandomWaitingSec = randomWaitingSec;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(mRandomWaitingSec * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void randomOneThreadWork() {
        final Random random = new Random();
        switch (random.nextInt(4)) {
            case 0:
                addTaskToIO();
                break;
            case 1:
                addTaskToComputation();
                break;
            case 2:
                addTaskToNetwork();
                break;
            case 3:
                addTaskToTest1();
                break;
            case 4:
                addTaskToTest2();
                break;
            case 5:
                addTaskToTest3();
                break;
            case 6:
                addTaskToTest4();
                break;
        }
    }

    private final Random mRandom = new Random();
    private final AtomicInteger mCommandNumber = new AtomicInteger(1);

    private void addTaskToExecutor(IExecutor executor) {
        final int randomWaitSec = mRandom.nextInt(10) + 1;
        executor.execute("Test" + mCommandNumber.getAndIncrement() + ":" + randomWaitSec + "s",
                new RandomRunnable(randomWaitSec));
    }

    private void addTaskToIO() {
        addTaskToExecutor(DemoThreadPoolCentral.io());
    }

    private void addTaskToComputation() {
        addTaskToExecutor(DemoThreadPoolCentral.computation());
    }

    private void addTaskToNetwork() {
        addTaskToExecutor(DemoThreadPoolCentral.network());
    }

    private void addTaskToTest1() {
        addTaskToExecutor(DemoThreadPoolCentral.test1());
    }

    private void addTaskToTest2() {
        addTaskToExecutor(DemoThreadPoolCentral.test2());
    }

    private void addTaskToTest3() {
        addTaskToExecutor(DemoThreadPoolCentral.test3());
    }

    private void addTaskToTest4() {
        addTaskToExecutor(DemoThreadPoolCentral.test4());
    }

    private AppCompatButton mRandomThreadWorkBtn;
    private AppCompatButton mAddTaskToIoBtn;
    private AppCompatButton mAddTaskToComputationBtn;
    private AppCompatButton mAddTaskToNetworkBtn;
    private AppCompatButton mAddTaskToTest1Btn;
    private AppCompatButton mAddTaskToTest2Btn;
    private AppCompatButton mAddTaskToTest3Btn;
    private AppCompatButton mAddTaskToTest4Btn;

    private void assignViews() {
        mRandomThreadWorkBtn = (AppCompatButton) findViewById(R.id.random_thread_work_btn);
        mAddTaskToIoBtn = (AppCompatButton) findViewById(R.id.add_task_to_io_btn);
        mAddTaskToComputationBtn = (AppCompatButton) findViewById(R.id.add_task_to_computation_btn);
        mAddTaskToNetworkBtn = (AppCompatButton) findViewById(R.id.add_task_to_network_btn);
        mAddTaskToTest1Btn = (AppCompatButton) findViewById(R.id.add_task_to_test1_btn);
        mAddTaskToTest2Btn = (AppCompatButton) findViewById(R.id.add_task_to_test2_btn);
        mAddTaskToTest3Btn = (AppCompatButton) findViewById(R.id.add_task_to_test3_btn);
        mAddTaskToTest4Btn = (AppCompatButton) findViewById(R.id.add_task_to_test4_btn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_github:
                openGitHub();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openGitHub() {
        Uri uri = Uri.parse(getString(R.string.app_github_url));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


}
