package com.example.web.crawler;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 使用 webmagic 框架实现爬虫功能
 * 注意：使用 webmagic 框架实现爬虫时，与已有的系统框架存在不兼容问题。
 * 主要体现在 spring 系统启动时注入的 bean 对象获取不到该实例，即代码中的 RedisTemplate 在调用时取值为 null。
 * 官网提供的 Dao 等方式存储数据库未能复现，故最后生产上放弃此技术路线。
 * --------------------------------------
 * @ClassName: WebmaticCrawler.java
 * @Date: 2020/2/12 14:58
 * @SoftWare: IntelliJ IDEA
 * --------------------------------------
 * @Author: lixj
 * @Contact: lixj_zj@163.com
 **/
@Controller
public class WebmagicCrawler implements PageProcessor {
    private final Logger _logger = Logger.getLogger(getClass());

//    @Autowired
//    @Qualifier("redisTemplate")
//    private RedisTemplate RedisTemplate;

    /**
     * 抓取网站的相关配置，包括：编码、抓取间隔(setSleepTime(1000))、重试次数等
     * */
    private Site site = Site.me().setRetryTimes(10).setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");


    @RequestMapping(value = "/dataCrawler", produces = "application/json")
    @ResponseBody
    public String epidemicDataCrawlerTask(HttpServletRequest request) {
        _logger.info("数据爬虫跑批任务开始....");

        Spider.create(new WebmagicCrawler())
                .addUrl("https://ncov.dxy.cn/ncovh5/view/pneumonia")
                .thread(1)
//                .addPipeline(new EpidemicDataPipeLineController())    // 指定爬虫数据的pipeline
                .run();

        _logger.info("数据爬虫跑批任务结束....");
        return "OK";
    }


    @Override
    public void process(Page page) {
        // 延迟加载，确保拿到所有数据
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 拿到页面所有数据
        String content = page.getHtml().get();

        // 解析数据逻辑...
        // ...



        // ！！！此处通过调用spring系统中的实例对象有问题，报错对象为null！！！
        // RedisTemplate.setValue(data_key, data);    // 调用redis存储


        // 将数据传到pipeline中接收
        // 示例：
        // page.putField("data", data);

    }

    @Override
    public Site getSite() {
        return this.site;
    }

}

