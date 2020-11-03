package io.yhheng.superproxy.announce;

import io.yhheng.superproxy.common.utils.TwoTypeSupportObjectRegistry;
import io.yhheng.superproxy.config.AnnouncerConfig;

import java.util.function.Function;

public class Announcers extends TwoTypeSupportObjectRegistry<Function<AnnouncerConfig, Announcer>> {
    public static final Announcers INSTANCE = new Announcers();

    static {
        INSTANCE.register("dubbo", "zookeeper", DubboZookeeperAnnouncer::new);
    }

    @Override
    protected String desc() {
        return "Announcer";
    }

//    private static class AnnouncerEntryRegistry extends AbstractObjectRegistry<Function<AnnouncerConfig.AnnounceEntryConfig, AnnouncerEntry>> {
//        public static final AnnouncerEntryRegistry INSTANCE = new AnnouncerEntryRegistry();
//
//        static {
//            INSTANCE.register("dubbo", DubboServiceAnnounceEntry::of);
//        }
//
//        @Override
//        protected String desc() {
//            return "AnnouncerEntry";
//        }
//    }
}
