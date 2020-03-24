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

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * random load balance.
 *
 * 基于权重随机算法。
 * 比如服务提供者[A、B、C]设置的权重分别为[5、3、2]，则他们的权重总和为10。
 * 现在把这些权重值平铺在一维坐标值上，[0, 5) 区间属于服务器 A，[5, 8) 区间属于服务器 B，[8, 10) 区间属于服务器 C。
 * 通过随机数生成器生成一个范围在 [0, 10) 之间的随机数，然后计算这个随机数会落到哪个区间上。
 * 比如随机数3会落到[0, 5)区间上，则返回服务提供者A。权重越大，随机到的概率越高。
 * 值幺随机数的产生的分布均匀很好，则每个服务器被选中的次数比例接近其权重比例。
 *
 */
public class RandomLoadBalance extends AbstractLoadBalance {

    public static final String NAME = "random";

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        // 服务提供者数量
        int length = invokers.size(); // Number of invokers
        // 每个服务提供者是否权重相同
        boolean sameWeight = true; // Every invoker has the same weight?
        // 获取第一个服务提供者的权重
        int firstWeight = getWeight(invokers.get(0), invocation);
        // 权重总和
        int totalWeight = firstWeight; // The sum of weights
        // 遍历除第一个服务提供者，计算权重总和
        for (int i = 1; i < length; i++) {
            int weight = getWeight(invokers.get(i), invocation);
            // 计算权重总和
            totalWeight += weight; // Sum
            // 判断是否相同权重
            if (sameWeight && weight != firstWeight) {
                sameWeight = false;
            }
        }
        if (totalWeight > 0 && !sameWeight) {
            // If (not every invoker has the same weight & at least one invoker's weight>0), select randomly based on totalWeight.
            // 如果不是每个服务提供者权重都相同，且至少有一个服务提供者的权重大于0，则基于权重总和随机选择一个服务提供者。
            // 随机数用于落到权重区间
            int offset = ThreadLocalRandom.current().nextInt(totalWeight);
            // Return a invoker based on the random value.
            for (int i = 0; i < length; i++) {
                offset -= getWeight(invokers.get(i), invocation);
                // 权重小于等于0的服务提供者不会被选中
                if (offset < 0) {
                    return invokers.get(i);
                }
            }
        }
        // If all invokers have the same weight value or totalWeight=0, return evenly.
        // 如果所有服务提供者的权重相同或者权重总和为0，则随机返回一个服务提供者。
        return invokers.get(ThreadLocalRandom.current().nextInt(length));
    }

}
