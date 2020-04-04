package com.example.web.crawler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * @Description:  HttpClients 实现爬虫功能，在服务器无法正确安装浏览器驱动并且爬取数据为静态数据时使用
 * --------------------------------------
 * @ClassName: HttpClientsCrawler.java
 * @Date: 2020/2/12 14:42
 * @SoftWare: IntelliJ IDEA
 * --------------------------------------
 * @Author: lixj
 * @Contact: lixj_zj@163.com
 **/
public class HttpClientsCrawler {
    private final Logger _logger = Logger.getLogger(getClass());

    public void dataCrawler() {
        //1.生成httpclient，相当于该打开一个浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        // 目标网址
        String url = "https://ad.thsi.cn/2020/yiqing2020/index.html";

        //2.创建get请求，相当于在浏览器地址栏输入网址
        HttpGet request = new HttpGet(url);

        try {
            //3.执行get请求，相当于在输入地址栏后敲回车键
            response = httpClient.execute(request);
            //4.判断响应状态为200，进行处理
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //5.获取响应内容
                HttpEntity httpEntity = response.getEntity();
                String html = EntityUtils.toString(httpEntity, "utf-8");

                // 余下提取页面标签数据逻辑。。。
                // 。。。

            } else {
                //如果返回状态不是200，比如404（页面不存在）等，根据情况做处理，这里略
                _logger.error("爬虫请求返回状态异常！");
            }
        } catch (Exception e) {
            _logger.error(String.format("爬虫异常！异常信息为：[%s]", e));
        } finally {
            //6.关闭
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }

    }

}
