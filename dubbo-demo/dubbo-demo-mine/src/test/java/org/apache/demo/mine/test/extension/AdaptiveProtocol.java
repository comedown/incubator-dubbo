package org.apache.demo.mine.test.extension;

import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Protocol;
import org.junit.Test;

import java.util.List;

public class AdaptiveProtocol {

    @Test
    public void testProtocol() {
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        System.out.println(protocol.getClass().getName());
    }

    @Test
    public void testCustomFilter() {
        URL url = new URL("dubbo", "localhost", 8080);
        List<Filter> filters  = ExtensionLoader.getExtensionLoader(Filter.class).getActivateExtension(url, Constants.SERVICE_FILTER_KEY, "");

        for (Filter filter : filters) {
            System.out.println(filter.getClass().getName());
        }

    }

}
