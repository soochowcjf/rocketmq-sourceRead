/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.rocketmq.remoting;

import org.apache.rocketmq.remoting.exception.RemotingConnectException;
import org.apache.rocketmq.remoting.exception.RemotingSendRequestException;
import org.apache.rocketmq.remoting.exception.RemotingTimeoutException;
import org.apache.rocketmq.remoting.exception.RemotingTooMuchRequestException;
import org.apache.rocketmq.remoting.netty.NettyRequestProcessor;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;

import java.util.List;
import java.util.concurrent.ExecutorService;

public interface RemotingClient extends RemotingService {

    /**
     * 将传入的 addrs，更新为新的 NameServerAddress
     *
     * @param addrs
     */
    void updateNameServerAddressList(final List<String> addrs);

    List<String> getNameServerAddressList();

    /**
     * 同步调用
     */
    RemotingCommand invokeSync(final String addr, final RemotingCommand request, final long timeoutMillis)
            throws InterruptedException, RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException;

    /**
     * 异步调用
     */
    void invokeAsync(final String addr, final RemotingCommand request, final long timeoutMillis, final InvokeCallback invokeCallback)
            throws InterruptedException, RemotingConnectException, RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException;

    /**
     * 单次调用
     */
    void invokeOneway(final String addr, final RemotingCommand request, final long timeoutMillis)
            throws InterruptedException, RemotingConnectException, RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException;

    /**
     * 注册处理器
     */
    void registerProcessor(final int requestCode, final NettyRequestProcessor processor, final ExecutorService executor);

    /**
     * 获取执行回调任务的线程池
     */
    ExecutorService getCallbackExecutor();

    void setCallbackExecutor(final ExecutorService callbackExecutor);

    boolean isChannelWritable(final String addr);
}
