package org.apache.dubbo.demo.filter;

import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.rpc.*;

@Activate(group = {Constants.CONSUMER})
public class MyDubboFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ExtensionLoader.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        logger.info("MyDubboFilter");
        RpcContext.getContext().setAttachment("hello", "world");
        Result result = invoker.invoke(invocation);
        return result;
    }
}
