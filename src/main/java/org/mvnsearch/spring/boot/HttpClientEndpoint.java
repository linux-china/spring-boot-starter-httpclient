package org.mvnsearch.spring.boot;

import com.codahale.metrics.*;
import com.codahale.metrics.Timer;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;

import java.util.*;

/**
 * Http Client endpoint
 *
 * @author linux_china
 */
public class HttpClientEndpoint extends AbstractEndpoint<Map<String, Object>> {
    private HttpClientProperties properties;
    private MetricRegistry registry;

    public HttpClientEndpoint(HttpClientProperties properties, MetricRegistry registry) {
        super("httpclient");
        this.properties = properties;
        this.registry = registry;
    }

    public Map<String, Object> invoke() {
        Map<String, Object> info = new HashMap<>();
        info.put("author", "linux_china@hotmail.com");
        info.put("httpcomponents", "http://hc.apache.org");
        info.put("metrics", getMetrics());
        info.put("config", properties);
        return info;
    }

    public Map<String, Object> getMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        //gauge
        SortedMap<String, Gauge> gauges = registry.getGauges((name, metric) -> name.startsWith("org.apache.http.conn.HttpClientConnectionManager."));
        for (Map.Entry<String, Gauge> entry : gauges.entrySet()) {
            metrics.put(entry.getKey(), entry.getValue().getValue());
        }
        //timer
        SortedMap<String, com.codahale.metrics.Timer> timers = registry.getTimers((name, metric) -> name.startsWith("org.apache.http.client.HttpClient."));
        for (Map.Entry<String, Timer> entry : timers.entrySet()) {
            metrics.putAll(convertTimerToMap(entry.getKey(), entry.getValue()));
        }
        return metrics;
    }

    public Map<String, Object> convertTimerToMap(String name, Timer timer) {
        Map<String, Object> map = new HashMap<>();
        map.put(name + ".count", timer.getCount());
        map.put(name + ".oneMinuteRate", timer.getOneMinuteRate());
        map.put(name + ".fiveMinuteRate", timer.getFiveMinuteRate());
        map.put(name + ".fifteenMinuteRate", timer.getFifteenMinuteRate());
        map.put(name + ".meanRate", timer.getMeanRate());
        Snapshot snapshot = timer.getSnapshot();
        map.put(name + ".snapshot.mean", snapshot.getMean());
        map.put(name + ".snapshot.max", snapshot.getMax());
        map.put(name + ".snapshot.min", snapshot.getMin());
        map.put(name + ".snapshot.median", snapshot.getMedian());
        map.put(name + ".snapshot.stdDev", snapshot.getStdDev());
        map.put(name + ".snapshot.75thPercentile", snapshot.get75thPercentile());
        map.put(name + ".snapshot.95thPercentile", snapshot.get95thPercentile());
        map.put(name + ".snapshot.98thPercentile", snapshot.get98thPercentile());
        map.put(name + ".snapshot.99thPercentile", snapshot.get99thPercentile());
        map.put(name + ".snapshot.999thPercentile", snapshot.get999thPercentile());
        return map;
    }
}
