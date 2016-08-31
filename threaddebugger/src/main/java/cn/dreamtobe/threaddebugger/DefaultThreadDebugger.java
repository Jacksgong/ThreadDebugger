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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The default thread debugger.
 */

class DefaultThreadDebugger implements IThreadDebugger {

    private int mPreviousSize = 0;
    private int mSize = 0;

    private List<ThreadCategory> mCurThreadCategoryList;
    private List<ThreadCategory> mPreThreadCategoryList;


    private ThreadCategory mCurUnknowCategory;
    private ThreadCategory mPreUnknowCategory;

    private final Builder mBuilder = new Builder();

    @SuppressWarnings("unchecked")
    private static class Builder {
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        private final List<ThreadCategory.Builder> mTCBuilderList = new ArrayList<>();

        private final ThreadCategory.Builder mUnknowCategoryBuilder =
                new ThreadCategory.Builder("unknown");

        void process(final int hashCode, final String threadName) {
            final List<ThreadCategory.Builder> builderList = (List<ThreadCategory.Builder>)
                    ((ArrayList) mTCBuilderList).clone();
            for (ThreadCategory.Builder builder : builderList) {
                if (builder.process(hashCode, threadName)) {
                    return;
                }
            }

            mUnknowCategoryBuilder.add(hashCode, threadName);
        }

        void add(String key) {
            add(key, key);
        }

        void add(String startWithKey, String alias) {
            mTCBuilderList.add(new ThreadCategory.Builder(startWithKey, alias));
        }

        void reset() {
            final List<ThreadCategory.Builder> builderList = (List<ThreadCategory.Builder>)
                    ((ArrayList) mTCBuilderList).clone();
            for (ThreadCategory.Builder builder : builderList) {
                builder.reset();
            }

            mUnknowCategoryBuilder.reset();
        }

        List<ThreadCategory> cloneList() {
            final List<ThreadCategory.Builder> builderList = (List<ThreadCategory.Builder>)
                    ((ArrayList) mTCBuilderList).clone();

            final List<ThreadCategory> categoryList = new ArrayList<>(builderList.size());
            for (ThreadCategory.Builder builder : builderList) {
                categoryList.add(builder.build().clone());
            }

            return categoryList;
        }

        ThreadCategory cloneUnknowCategory() {
            return mUnknowCategoryBuilder.build().clone();
        }
    }

    @Override
    public IThreadDebugger add(String key) {
        mBuilder.add(key);
        return this;
    }

    @Override
    public IThreadDebugger add(String startWithKey, String alias) {
        mBuilder.add(startWithKey, alias);
        return this;
    }

    @Override
    public void refresh() {
        Set<Thread> threads = Thread.getAllStackTraces().keySet();

        mBuilder.reset();

        for (Thread thread : threads) {
            mBuilder.process(thread.hashCode(), thread.getName());
        }


        mPreThreadCategoryList = mCurThreadCategoryList;
        mCurThreadCategoryList = mBuilder.cloneList();
        mPreUnknowCategory = mCurUnknowCategory;
        mCurUnknowCategory = mBuilder.cloneUnknowCategory();
        mPreviousSize = mSize;
        mSize = threads.size();
    }

    @Override
    public String drawUpEachThreadSize() {
        final StringBuilder builder = createBasicInfoStringBuilder("drawUpEachThreadSize");

        if (builder == null) {
            return noData();
        }

        appendSize(builder);

        final List<ThreadCategory> threadCategoryList = (List<ThreadCategory>)
                ((ArrayList) mCurThreadCategoryList).clone();
        final ThreadCategory unknowCategory = mCurUnknowCategory.clone();

        for (ThreadCategory threadCategory : threadCategoryList) {
            if (threadCategory.size() > 0) {
                builder.append(threadCategory.getAlias()).append(": ").
                        append(threadCategory.size());
                appendSplit(builder);
            }
        }

        if (unknowCategory.size() > 0) {
            builder.append(unknowCategory.getAlias()).append(": ").append(unknowCategory.size());
        } else {
            deleteLastSplit(builder);
        }

        return builder.toString();
    }

    @Override
    public String drawUpEachThreadInfo() {
        final StringBuilder builder = createBasicInfoStringBuilder("drawUpEachThreadInfo");

        if (builder == null) {
            return noData();
        }

        appendSize(builder);

        final List<ThreadCategory> threadCategoryList = (List<ThreadCategory>)
                ((ArrayList) mCurThreadCategoryList).clone();
        final ThreadCategory unknowCategory = mCurUnknowCategory.clone();

        for (ThreadCategory threadCategory : threadCategoryList) {
            if (threadCategory.size() > 0) {
                builder.append(threadCategory);
                appendSplit(builder);
            }
        }


        if (unknowCategory.size() > 0) {
            builder.append(unknowCategory);
        } else {
            deleteLastSplit(builder);
        }

        return builder.toString();
    }

    @Override
    public String drawUpEachThreadSizeDiff() {
        return drawUpEachThreadDiff(false);

    }

    @Override
    public String drawUpEachThreadInfoDiff() {
        return drawUpEachThreadDiff(true);
    }

    @Override
    public String drawUpUnknownInfo() {
        if (mCurUnknowCategory == null) {
            return noData();
        }

        final StringBuilder builder = createBasicInfoStringBuilderUnChecked("drawUpUnknownInfo");

        builder.append("Unknow thread count = ")
                .append(mCurUnknowCategory.size()).append(". ");
        final int diff = mPreUnknowCategory == null ?
                mCurUnknowCategory.size() : mCurUnknowCategory.size() - mPreUnknowCategory.size();
        builder.append("Unknow thread differ = ");
        if (diff > 0) {
            builder.append("+");
        }

        builder.append(diff).append(". ");

        return builder.append(mCurUnknowCategory.toString()).toString();
    }

    @Override
    public boolean isSizeChanged() {
        return mSize != mPreviousSize;
    }

    @Override
    public boolean isChanged() {
        if (mCurThreadCategoryList == null) {
            return false;
        }
        if (isSizeChanged()) {
            return true;
        }

        if (mCurUnknowCategory != null) {
            if (mCurUnknowCategory.isDiff(mPreUnknowCategory)) {
                return true;
            }
        }
        final List<ThreadCategory> preThreadCategoryList = mPreThreadCategoryList == null ?
                null : (List<ThreadCategory>) ((ArrayList) mPreThreadCategoryList).clone();
        final List<ThreadCategory> curThreadCategoryList = (List<ThreadCategory>)
                ((ArrayList) mCurThreadCategoryList).clone();


        for (int i = 0; i < curThreadCategoryList.size(); i++) {
            final ThreadCategory curThreadCategory = curThreadCategoryList.get(i);
            final ThreadCategory preThreadCategory = preThreadCategoryList == null ?
                    null : preThreadCategoryList.get(i);
            if (curThreadCategory.isDiff(preThreadCategory)) {
                return true;
            }
        }

        return false;
    }

    private String drawUpEachThreadDiff(boolean showDetail) {
        final StringBuilder builder = createBasicInfoStringBuilder("drawUpEachThread" +
                (showDetail ? "Info" : "Size") + "Diff");

        if (builder == null) {
            return noData();
        }

        appendSize(builder);

        if (!showDetail && mPreviousSize == mSize) {
            return builder.append("Thread size has not changed.").toString();
        }

        final List<ThreadCategory> preThreadCategoryList = mPreThreadCategoryList == null ?
                null : (List<ThreadCategory>) ((ArrayList) mPreThreadCategoryList).clone();
        final List<ThreadCategory> curThreadCategoryList = (List<ThreadCategory>)
                ((ArrayList) mCurThreadCategoryList).clone();

        final ThreadCategory preUnknowCategory = mPreUnknowCategory == null ?
                null : mPreUnknowCategory.clone();
        final ThreadCategory curUnknowCategory = mCurUnknowCategory == null ?
                null : mCurUnknowCategory.clone();

        builder.append("Thread differ : ");
        final int diff = mSize - mPreviousSize;
        if (diff > 0) {
            builder.append("+");
        }
        builder.append(diff).append(". ");

        for (int i = 0; i < curThreadCategoryList.size(); i++) {
            final ThreadCategory curThreadCategory = curThreadCategoryList.get(i);
            final ThreadCategory preThreadCategory = preThreadCategoryList == null ?
                    null : preThreadCategoryList.get(i);
            if (appendDiff(builder, preThreadCategory, curThreadCategory, showDetail)) {
                appendSplit(builder);
            }
        }

        if (curUnknowCategory == null ||
                !appendDiff(builder, preUnknowCategory, curUnknowCategory, true)) {
            deleteLastSplit(builder);
        }

        return builder.toString();
    }

    private boolean appendDiff(StringBuilder builder, ThreadCategory preThreadCategory,
                               ThreadCategory curThreadCategory, boolean showDetail) {
        final int diff = preThreadCategory == null ? curThreadCategory.size() :
                curThreadCategory.size() - preThreadCategory.size();

        if (showDetail) {
            final List<String> diffNameList = curThreadCategory.diff(preThreadCategory);
            if (diffNameList == null || diffNameList.size() == 0) {
                return false;
            }

            builder.append(curThreadCategory.getAlias()).append(": ");
            if (diff > 0) {
                builder.append("+");
            }

            if (diff == 0) {
                builder.append("SWAP");
            } else {
                builder.append(diff);
            }

            builder.append(" ").append(diffNameList);

            return true;
        } else if (diff == 0) {
            return false;
        } else {
            builder.append(curThreadCategory.getAlias()).append(": ");
            if (diff > 0) {
                builder.append("+");
            }

            builder.append(diff);
            return true;
        }
    }


    private StringBuilder createBasicInfoStringBuilder(String methodName) {
        if (mCurThreadCategoryList == null) {
            return null;
        }

        return createBasicInfoStringBuilderUnChecked(methodName);
    }

    private StringBuilder createBasicInfoStringBuilderUnChecked(String methodName) {
        StringBuilder builder = new StringBuilder();
        builder.append(methodName)
                .append(": ");

        return builder;
    }


    private StringBuilder appendSize(StringBuilder builder) {
        return builder.append("Thread count = ")
                .append(mSize)
                .append(". ");
    }

    @SuppressWarnings("SameReturnValue")
    private String noData() {
        return "No data";
    }

    private void deleteLastSplit(StringBuilder builder) {
        builder.delete(builder.length() - " | ".length(), builder.length());
    }

    private void appendSplit(StringBuilder builder) {
        builder.append(" | ");
    }

}
