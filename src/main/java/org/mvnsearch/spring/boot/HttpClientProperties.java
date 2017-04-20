package org.mvnsearch.spring.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * HttpClient properties
 *
 * @author linux_china
 */
@ConfigurationProperties(
        prefix = "spring.httpclient"
)
public class HttpClientProperties {
    /**
     * 代理服务器主机名或者ip
     */
    private String proxyHost;
    /**
     * 代理服务器端口号
     */
    private Integer proxyPort;
    /**
     * 建立连接超时时间(毫秒): timeout for client to try to connect to the server
     */
    private Integer connectTimeout;
    /**
     * 等待http响应的超时时间(毫秒): After establishing the connection, timeout for  the client socket to wait for response after sending the request
     */
    private Integer socketTimeout;
    /**
     * pooling connection manager max connection in the pool.
     */
    private Integer maxConnTotal;
    /**
     * max connections for pre route.
     */
    private Integer maxConnPerRoute;
    /**
     * metric name strategy: METHOD_ONLY, HOST_AND_METHOD(default), QUERYLESS_URL_AND_METHOD
     */
    private String metricNameStrategy = "HOST_AND_METHOD";

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Integer getMaxConnTotal() {
        return maxConnTotal;
    }

    public void setMaxConnTotal(Integer maxConnTotal) {
        this.maxConnTotal = maxConnTotal;
    }

    public Integer getMaxConnPerRoute() {
        return maxConnPerRoute;
    }

    public void setMaxConnPerRoute(Integer maxConnPerRoute) {
        this.maxConnPerRoute = maxConnPerRoute;
    }

    public String getMetricNameStrategy() {
        return metricNameStrategy;
    }

    public void setMetricNameStrategy(String metricNameStrategy) {
        this.metricNameStrategy = metricNameStrategy;
    }
}
