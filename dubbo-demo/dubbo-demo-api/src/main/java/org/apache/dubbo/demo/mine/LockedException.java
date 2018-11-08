package org.apache.dubbo.demo.mine;

public class LockedException extends RuntimeException {

    public LockedException() {
        super();
    }

    public LockedException(String message) {
        super(message);
    }
}
