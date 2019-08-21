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
package org.apache.dubbo.rpc.proxy;

import org.apache.dubbo.common.Constants;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcInvocation;
import org.apache.dubbo.rpc.support.RpcUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * InvokerHandler
 * <p>Invoker处理器，代理类调用方法将有此处理器处理。
 */
public class InvokerInvocationHandler implements InvocationHandler {

    private final Invoker<?> invoker;

    public InvokerInvocationHandler(Invoker<?> handler) {
        this.invoker = handler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(invoker, args);
        }
        // toString、hashCode、equals方法同一实现
        if ("toString".equals(methodName) && parameterTypes.length == 0) {
            return invoker.toString();
        }
        if ("hashCode".equals(methodName) && parameterTypes.length == 0) {
            return invoker.hashCode();
        }
        if ("equals".equals(methodName) && parameterTypes.length == 1) {
            return invoker.equals(args[0]);
        }

        // 构造rpc调用参数
        RpcInvocation invocation;
        // 异步方法
        if (RpcUtils.hasGeneratedFuture(method)) {
            Class<?> clazz = method.getDeclaringClass();
            String syncMethodName = methodName.substring(0, methodName.length() - Constants.ASYNC_SUFFIX.length());
            Method syncMethod = clazz.getMethod(syncMethodName, method.getParameterTypes());
            invocation = new RpcInvocation(syncMethod, args);
            invocation.setAttachment(Constants.FUTURE_GENERATED_KEY, "true");
            invocation.setAttachment(Constants.ASYNC_KEY, "true");
        }
        // 同步方法
        else {
            invocation = new RpcInvocation(method, args);
            // 方法返回值是CompletableFuture类型
            if (RpcUtils.hasFutureReturnType(method)) {
                invocation.setAttachment(Constants.FUTURE_RETURNTYPE_KEY, "true");
                invocation.setAttachment(Constants.ASYNC_KEY, "true");
            }
        }
        return invoker.invoke(invocation).recreate();
    }


}
