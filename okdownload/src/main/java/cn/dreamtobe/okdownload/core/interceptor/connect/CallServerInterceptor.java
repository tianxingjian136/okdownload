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

package cn.dreamtobe.okdownload.core.interceptor.connect;

import java.io.IOException;

import cn.dreamtobe.okdownload.core.connection.DownloadConnection;
import cn.dreamtobe.okdownload.core.download.DownloadChain;
import cn.dreamtobe.okdownload.core.interceptor.Interceptor;

public class CallServerInterceptor implements Interceptor.Connect {
    @Override
    public DownloadConnection.Connected interceptConnect(DownloadChain chain) throws IOException {
        return chain.getConnectionOrCreate().execute();
    }
}
