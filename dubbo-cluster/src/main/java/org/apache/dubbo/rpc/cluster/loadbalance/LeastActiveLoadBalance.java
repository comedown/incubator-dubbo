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
package org.apache.dubbo.rpc.cluster.loadbalance;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcStatus;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * LeastActiveLoadBalance
 * 基于加权最少活跃调用数算法。
 * 活跃调用数越小，表明该服务提供者效率越高，单位时间内可处理更多的请求。
 * 每个服务提供者对应一个活跃数 active。初始情况下，所有服务提供者活跃数均为0。
 * 收到一个请求，活跃数加1，完成请求后则将活跃数减1。
 */
public class LeastActiveLoadBalance extends AbstractLoadBalance {

    public static final String NAME = "leastactive";

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        // 服务提供者数量
        int length = invokers.size(); // Number of invokers
        // 所有服务提供者中最小活跃数
        int leastActive = -1; // The least active value of all invokers
        // 相同最小活跃数的服务提供者数量
        int leastCount = 0; // The number of invokers having the same least active value (leastActive)
        // 拥有最小活跃数的服务提供者下标
        int[] leastIndexes = new int[length]; // The index of invokers having the same least active value (leastActive)
        // 热机权重总和
        int totalWeight = 0; // The sum of with warmup weights
        // 初始权重，用于比较
        int firstWeight = 0; // Initial value, used for comparision
        // 所有服务提供者的权重是否相同
        boolean sameWeight = true; // Every invoker has the same weight value?
        for (int i = 0; i < length; i++) {
            Invoker<T> invoker = invokers.get(i);
            // 获取指定方法对应的活跃数
            int active = RpcStatus.getStatus(invoker.getUrl(), invocation.getMethodName()).getActive(); // Active number
            // 获取在热机之后的权重
            int afterWarmup = getWeight(invoker, invocation);
            // 如果最小活跃数为初始值-1，或者当前服务提供者小于之前最小活跃数
            // 重新寻找更小的活跃数服务提供者
            if (leastActive == -1 || active < leastActive) { // Restart, when find a invoker having smaller least active value.
                // 覆盖最小活跃数
                leastActive = active; // Record the current least active value
                // 重置最小活跃数的服务提供者数量为1
                leastCount = 1; // Reset leastCount, count again based on current leastCount
                // 重置觉有最小活跃数的服务提供者下标
                leastIndexes[0] = i; // Reset
                // 重置拥有最小活跃数的服务提供者权重
                totalWeight = afterWarmup; // Reset
                // 重置第一个服务提供者权重
                firstWeight = afterWarmup; // Record the weight the first invoker
                // 重置权重判重标记
                sameWeight = true; // Reset, every invoker has the same weight value?
            }
            // 否则最小活跃数和上一个服务提供者相同
            else if (active == leastActive) { // If current invoker's active value equals with leaseActive, then accumulating.
                // 记录服务提供者下标和数量
                leastIndexes[leastCount++] = i; // Record index number of this invoker
                // 计算权重总和
                totalWeight += afterWarmup; // Add this invoker's with warmup weight to totalWeight.
                // If every invoker has the same weight?
                // 判断拥有最小活跃数的服务提供者是否拥有相同权重
                if (sameWeight && i > 0
                        && afterWarmup != firstWeight) {
                    sameWeight = false;
                }
            }
        }
        // assert(leastCount > 0)
        // 只有一个活跃度最小的服务提供者，直接使用
        if (leastCount == 1) {
            // If we got exactly one invoker having the least active value, return this invoker directly.
            return invokers.get(leastIndexes[0]);
        }
        // 如果存在不同权重，且权重总和大于0
        if (!sameWeight && totalWeight > 0) {
            // If (not every invoker has the same weight & at least one invoker's weight>0), select randomly based on totalWeight.
            // 随机生成基于权重总和的随机数
            int offsetWeight = ThreadLocalRandom.current().nextInt(totalWeight) + 1;
            // Return a invoker based on the random value.
            // 在具有最小活跃度的服务提供者中根据权重随机数来选择
            for (int i = 0; i < leastCount; i++) {
                int leastIndex = leastIndexes[i];
                offsetWeight -= getWeight(invokers.get(leastIndex), invocation);
                if (offsetWeight <= 0) {
                    return invokers.get(leastIndex);
                }
            }
        }
        // If all invokers have the same weight value or totalWeight=0, return evenly.
        // 如果所有服务提供者的权重相同或者权重总和为0，则随机返回一个服务提供者。
        return invokers.get(leastIndexes[ThreadLocalRandom.current().nextInt(leastCount)]);
    }
}
