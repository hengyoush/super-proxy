package io.yhheng.superproxy.network;

public interface ListenerEventListener {
    String type();
    void onListenerStart(Listener listener);
}
