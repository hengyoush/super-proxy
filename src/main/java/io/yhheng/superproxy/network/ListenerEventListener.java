package io.yhheng.superproxy.network;

public interface ListenerEventListener {
    String type();
    void onListenerStarted(Listener listener);
}
