/*
 * Copyright (C) 2017 Jacksgong(jacksgong.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.dreamtobe.okdownload.core.dispatcher;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import cn.dreamtobe.okdownload.DownloadListener;
import cn.dreamtobe.okdownload.DownloadTask;
import cn.dreamtobe.okdownload.core.breakpoint.BreakpointInfo;
import cn.dreamtobe.okdownload.core.cause.EndCause;
import cn.dreamtobe.okdownload.core.cause.ResumeFailedCause;
import cn.dreamtobe.okdownload.core.connection.DownloadConnection;

// Dispatch callback to listeners
public class CallbackDispatcher {

    public CallbackDispatcher() {
        transmit = new DownloadListener() {
            private Handler uiHandler = new Handler(Looper.getMainLooper());

            @Override
            public void taskStart(final DownloadTask task) {
                if (task.isAutoCallbackToUIThread()) {
                    uiHandler.post(new Runnable() {
                        @Override public void run() { task.getListener().taskStart(task); }
                    });
                } else {
                    task.getListener().taskStart(task);
                }

            }

            @Override
            public void breakpointData(final DownloadTask task, @Nullable final BreakpointInfo info) {
                if (task.isAutoCallbackToUIThread()) {
                    uiHandler.post(new Runnable() {
                        @Override public void run() { task.getListener().breakpointData(task, info); }
                    });
                } else {
                    task.getListener().breakpointData(task, info);
                }
            }

            @Override
            public void connectStart(final DownloadTask task, final int blockIndex) {
                if (task.isAutoCallbackToUIThread()) {
                    uiHandler.post(new Runnable() {
                        @Override public void run() { task.getListener().connectStart(task, blockIndex); }
                    });
                } else {
                    task.getListener().connectStart(task, blockIndex);
                }
            }

            @Override
            public void connectEnd(final DownloadTask task, final int blockIndex, final DownloadConnection connection, final DownloadConnection.Connected connected) {
                if (task.isAutoCallbackToUIThread()) {
                    uiHandler.post(new Runnable() {
                        @Override public void run() { task.getListener().connectEnd(task, blockIndex, connection, connected); }
                    });
                } else {
                    task.getListener().connectEnd(task, blockIndex, connection, connected);
                }
            }

            @Override
            public void downloadFromBeginning(final DownloadTask task, final BreakpointInfo info, final ResumeFailedCause cause) {
                if (task.isAutoCallbackToUIThread()) {
                    uiHandler.post(new Runnable() {
                        @Override public void run() { task.getListener().downloadFromBeginning(task, info, cause); }
                    });
                } else {
                    task.getListener().downloadFromBeginning(task, info, cause);
                }
            }

            @Override
            public void downloadFromBreakpoint(final DownloadTask task, final BreakpointInfo info) {
                if (task.isAutoCallbackToUIThread()) {
                    uiHandler.post(new Runnable() {
                        @Override public void run() { task.getListener().downloadFromBreakpoint(task, info); }
                    });
                } else {
                    task.getListener().downloadFromBreakpoint(task, info);
                }
            }

            @Override
            public void fetchStart(final DownloadTask task, final int blockIndex, final long contentLength) {
                if (task.isAutoCallbackToUIThread()) {
                    uiHandler.post(new Runnable() {
                        @Override public void run() { task.getListener().fetchStart(task, blockIndex, contentLength); }
                    });
                } else {
                    task.getListener().fetchStart(task, blockIndex, contentLength);
                }
            }

            @Override
            public void fetchProgress(final DownloadTask task, final int blockIndex, final long fetchedBytes) {
                final long minInterval = task.getMinIntervalMillisCallbackProcess();
                if (minInterval > 0 &&
                        SystemClock.uptimeMillis() - task.getLastCallbackProcessTimestamp() < minInterval) {
                    return;
                }

                if (minInterval > 0) task.setLastCallbackProcessTimestamp(SystemClock.uptimeMillis());

                if (task.isAutoCallbackToUIThread()) {
                    uiHandler.post(new Runnable() {
                        @Override public void run() { task.getListener().fetchProgress(task, blockIndex, fetchedBytes); }
                    });
                } else {
                    task.getListener().fetchProgress(task, blockIndex, fetchedBytes);
                }
            }

            @Override
            public void fetchEnd(final DownloadTask task, final int blockIndex, final long contentLength) {
                if (task.isAutoCallbackToUIThread()) {
                    uiHandler.post(new Runnable() {
                        @Override public void run() { task.getListener().fetchEnd(task, blockIndex, contentLength); }
                    });
                } else {
                    task.getListener().fetchEnd(task, blockIndex, contentLength);
                }
            }

            @Override
            public void taskEnd(final DownloadTask task, final EndCause cause, @Nullable final Exception realCause) {
                if (task.isAutoCallbackToUIThread()) {
                    uiHandler.post(new Runnable() {
                        @Override public void run() { task.getListener().taskEnd(task, cause, realCause); }
                    });
                } else {
                    task.getListener().taskEnd(task, cause, realCause);
                }
            }
        };
    }

    public DownloadListener dispatch() {
        return transmit;
    }

    // Just transmit to the main looper.
    final DownloadListener transmit;
}
