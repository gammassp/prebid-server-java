package org.rtb.vexing.metric;

import com.codahale.metrics.MetricRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class CookieSyncMetrics extends UpdatableMetrics {

    private final Function<String, BidderCookieSyncMetrics> bidderCookieSyncMetricsCreator;
    // not thread-safe maps are intentionally used here because it's harmless in this particular case - eventually
    // this all boils down to metrics lookup by underlying metric registry and that operation is guaranteed to be
    // thread-safe
    private final Map<String, BidderCookieSyncMetrics> bidderCookieSyncMetrics;

    CookieSyncMetrics(MetricRegistry metricRegistry, CounterType counterType) {
        super(Objects.requireNonNull(metricRegistry), Objects.requireNonNull(counterType),
                metricName -> String.format("usersync.%s", metricName.name()));
        bidderCookieSyncMetricsCreator = bidder -> new BidderCookieSyncMetrics(metricRegistry, counterType, bidder);
        bidderCookieSyncMetrics = new HashMap<>();
    }

    public BidderCookieSyncMetrics forBidder(String bidder) {
        Objects.requireNonNull(bidder);
        return bidderCookieSyncMetrics.computeIfAbsent(bidder, bidderCookieSyncMetricsCreator);
    }

    public static class BidderCookieSyncMetrics extends UpdatableMetrics {
        BidderCookieSyncMetrics(MetricRegistry metricRegistry, CounterType counterType, String bidder) {
            super(Objects.requireNonNull(metricRegistry), Objects.requireNonNull(counterType), nameCreator(bidder));
        }

        private static Function<MetricName, String> nameCreator(String bidder) {
            Objects.requireNonNull(bidder);
            return metricName -> String.format("usersync.%s.%s", bidder, metricName.name());
        }
    }
}