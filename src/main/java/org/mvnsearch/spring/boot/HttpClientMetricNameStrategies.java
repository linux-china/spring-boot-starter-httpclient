package org.mvnsearch.spring.boot;


import com.codahale.metrics.httpclient.HttpClientMetricNameStrategy;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.RequestLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

import static com.codahale.metrics.MetricRegistry.name;

public class HttpClientMetricNameStrategies {

    public static final HttpClientMetricNameStrategy METHOD_ONLY =
            (name, request) -> name(HttpClient.class, name, methodNameString(request));

    public static final HttpClientMetricNameStrategy HOST_AND_METHOD =
            (name, request) -> {
                String host = null;
                Header hostHeader = request.getFirstHeader("Host");
                if (hostHeader != null) {
                    host = hostHeader.getValue();
                }
                return name(HttpClient.class, name, host, methodNameString(request));
            };

    public static final HttpClientMetricNameStrategy QUERYLESS_URL_AND_METHOD =
            (name, request) -> {
                try {
                    final RequestLine requestLine = request.getRequestLine();
                    final URIBuilder url = new URIBuilder(requestLine.getUri());
                    return name(HttpClient.class, name, url.removeQuery().build().toString(), methodNameString(request));
                } catch (URISyntaxException e) {
                    throw new IllegalArgumentException(e);
                }
            };

    private static String methodNameString(HttpRequest request) {
        return request.getRequestLine().getMethod().toLowerCase() + "-requests";
    }
}