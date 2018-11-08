package org.apache.dubbo.demo.mine;

public interface Lockable {

    void lock();

    void unlock();

    boolean locked();

    void setState(int state);
}
