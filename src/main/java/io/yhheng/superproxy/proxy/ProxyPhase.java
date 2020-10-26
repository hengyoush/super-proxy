package io.yhheng.superproxy.proxy;

public enum ProxyPhase {
    Init, PreRoute, MatchRoute, AfterRoute, ChooseHost, AfterChooseHost, SendUpstreamRequest, OneWay, WaitResponse,Retry, UpstreamResponse, End;
    public ProxyPhase next() {
        if (this == End) {
            return null;
        }
        return ProxyPhase.values()[this.ordinal() + 1];
    }
}
