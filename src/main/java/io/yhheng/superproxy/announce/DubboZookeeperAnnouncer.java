package io.yhheng.superproxy.announce;

import org.apache.dubbo.common.URL;

import io.yhheng.superproxy.announce.dest.ZookeeperAnnounceDestination;
import io.yhheng.superproxy.announce.entry.DubboServiceAnnounceEntry;
import io.yhheng.superproxy.config.AnnouncerConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class DubboZookeeperAnnouncer implements Announcer<DubboServiceAnnounceEntry> {
    private static final Logger log = LoggerFactory.getLogger(DubboZookeeperAnnouncer.class);

    private List<DubboServiceAnnounceEntry> dubboServiceAnnounceEntries;
    private ZookeeperAnnounceDestination announceDestination;
    private CuratorFramework client;

    public DubboZookeeperAnnouncer(AnnouncerConfig announcerConfig) {
        announceDestination = new ZookeeperAnnounceDestination(announcerConfig.getAnnounceDestinationConfig());
        dubboServiceAnnounceEntries =
                announcerConfig.getAnnounceEntries().stream().map(DubboServiceAnnounceEntry::of).collect(Collectors.toList());

    }

    @Override
    public void announce(DubboServiceAnnounceEntry entry) {
        String s = asRegisterUrl(entry);
        try {
            client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(announceDestination.getPrefixPath() + "/" + entry.getInterfaceName() + "/" + "providers" + "/" + s);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void start() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(announceDestination.getAddress(), retryPolicy);
        client.start();

        dubboServiceAnnounceEntries.forEach(this::announce);
    }

    @Override
    public void shutdown() {
        if (client != null) {
            client.close();
        }
    }

    private String asRegisterUrl(DubboServiceAnnounceEntry entry) {
        URL url = new URL("dubbo", entry.getHost(), entry.getPort(), entry.getInterfaceName())
                .addParameter("group", entry.getGroup())
                .addParameter("version", entry.getVersion());
        return url.toString();
    }
}
