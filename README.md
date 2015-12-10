Spring Boot Starter HttpClient
=============================================
在Spring Boot下整合http client,方便客户端使用.

### 为何要使用spring-boot-starter-httpclient
http client已经非常简单啦,为何还需要创建一个starter httpclient,其主要的目的如下:

* 让使用更简单,如全局proxy,timeout等
* 结合dropwizard metrics,添加对metrics的管理

### 如何使用

* 在Spring Boot项目的pom.xml中添加以下依赖:

          <dependency>
                 <groupId>org.mvnsearch.spring.boot</groupId>
                 <artifactId>spring-boot-starter-httpclient</artifactId>
                 <version>1.0.0-SNAPSHOT</version>
          </dependency>

* 在Spring Boot的application.properties文件中添加http client的一些相关配置,当然也可以不需要配置如下:
                    
          spring.httpclient.connectTimeout=2000

* 接下来在你的代码中直接应用cacheManager，然后就可以啦。
        
            @Autowired
            private HttpClient httpClient;
            @Autowired
            private Executor executor;

### spring-boot-start-httpclient提供的服务

* org.apache.http.client.HttpClient: Http Client
* org.apache.http.client.fluent.Executor: Executor

### 典型的例子:

* http client: 

            reporter.start(1, TimeUnit.SECONDS);
            HttpGet httpGet = new HttpGet("https://www.yahoo.com/tech/s/favorite-iphone-feature-apple-added-ios-9-2-183202009.html");
            HttpResponse response = httpClient.execute(httpGet);
            EntityUtils.toString(response.getEntity());
* executor: 
         
           String content = executor.execute(Request.Get("https://www.yahoo.com/")).returnContent().asString();

### httpclient metrics
HttpClient结合了DropWizard的metrics,这样很方便获取http client的metrics信息, 
目前可以记录的级别主要是: METHOD_ONLY, HOST_AND_METHOD(default), QUERYLESS_URL_AND_METHOD, 以下是metrics的输出:


      -- Gauges ----------------------------------------------------------------------
      org.apache.http.conn.HttpClientConnectionManager.available-connections
                   value = 1
      org.apache.http.conn.HttpClientConnectionManager.leased-connections
                   value = 0
      org.apache.http.conn.HttpClientConnectionManager.max-connections
                   value = 20
      org.apache.http.conn.HttpClientConnectionManager.pending-connections
                   value = 0
      
      -- Timers ----------------------------------------------------------------------
      org.apache.http.client.HttpClient.www.yahoo.com.get-requests
                   count = 1
               mean rate = 1.74 calls/second
           1-minute rate = 0.00 calls/second
           5-minute rate = 0.00 calls/second
          15-minute rate = 0.00 calls/second
                     min = 117.63 milliseconds
                     max = 117.63 milliseconds
                    mean = 117.63 milliseconds
                  stddev = 0.00 milliseconds
                  median = 117.63 milliseconds
                    75% <= 117.63 milliseconds
                    95% <= 117.63 milliseconds
                    98% <= 117.63 milliseconds
                    99% <= 117.63 milliseconds
                  99.9% <= 117.63 milliseconds


### FAQ

* HttpClient线程安全吗? HttpClient implementations are expected to be thread safe.
It is recommended that the same instance of this class is reused for multiple request executions.

* 我用Commons HttpClient 3.x挺好的,为何换到http components上? Http Components是 commons-httpclient的替代者,建议使用http components


### 参考文档

* Http Components: http://hc.apache.org/