package org.mvnsearch.spring.boot;


import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.httpclient.HttpClientMetricNameStrategy;
import com.codahale.metrics.httpclient.InstrumentedHttpClients;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Executor;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.MetricsDropwizardAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * HttpClient auto configuration
 *
 * @author linux_china
 */
@SuppressWarnings("ALL")
@Configuration
@ConditionalOnClass(HttpClient.class)
@AutoConfigureAfter(MetricsDropwizardAutoConfiguration.class)
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientAutoConfiguration {
    @Autowired
    private HttpClientProperties properties;
    @Autowired
    private MetricRegistry metrics;

    @Bean
    public RequestConfig httpClientRequestConfig() {
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        if (properties.getConnectTimeout() != null) {
            requestConfigBuilder.setConnectionRequestTimeout(properties.getConnectTimeout());
        }
        if (properties.getSocketTimeout() != null) {
            requestConfigBuilder.setSocketTimeout(properties.getSocketTimeout());
        }
        if (properties.getProxyHost() != null) {
            requestConfigBuilder.setProxy(new HttpHost(properties.getProxyHost(), properties.getProxyPort()));
        }
        return requestConfigBuilder.build();
    }

    @Bean
    public HttpClient httpClient(RequestConfig requestConfig) {
        HttpClientMetricNameStrategy nameStrategy = HttpClientMetricNameStrategies.HOST_AND_METHOD;
        if (properties.getMetricNameStrategy() != null) {
            if (properties.getMetricNameStrategy().equalsIgnoreCase("QUERYLESS_URL_AND_METHOD")) {
                nameStrategy = HttpClientMetricNameStrategies.QUERYLESS_URL_AND_METHOD;
            } else if (properties.getMetricNameStrategy().equalsIgnoreCase("METHOD_ONLY")) {
                nameStrategy = HttpClientMetricNameStrategies.METHOD_ONLY;
            }
        }
        HttpClientBuilder clientBuilder = InstrumentedHttpClients.custom(metrics, nameStrategy);
        clientBuilder.setDefaultRequestConfig(requestConfig);
        clientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
        return clientBuilder.build();
    }

    @Bean
    public Executor httpClientExecutor(HttpClient httpClient) {
        return Executor.newInstance(httpClient);
    }

    @Bean
    public HttpClientEndpoint httpClientEndpoint(HttpClientProperties properties, MetricRegistry metrics) {
        return new HttpClientEndpoint(properties, metrics);
    }
}