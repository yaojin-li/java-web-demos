package com.example.javaWebDemo.crawler;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import us.codecraft.webmagic.selector.Html;

/**
 * @Description: 使用 selenium + chrome 实现爬虫功能
 * 注意：此代码仅限运行于 Windows 系统，需要配合 chrome 浏览器及其对应版本的驱动共同完成！
 * 需要在安装 chrome 浏览器及其对应版本的驱动的 Linux 系统中测试！
 * Linux 系统中慎用此方法！！！
 * --------------------------------------
 * @ClassName: SeleniumAndChromeCrawler.java
 * @Date: 2020/2/12 14:58
 * @SoftWare: IntelliJ IDEA
 * --------------------------------------
 * @Author: lixj
 * @Contact: lixj_zj@163.com
 **/
public class SeleniumAndChromeCrawler {
    private final Logger _logger = Logger.getLogger(getClass());

    public void dataCrawler() {
        // 设置chrome选项
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        // 建立selenium 驱动
        WebDriver driver = new ChromeDriver(options);
        // 目标地址
        driver.get("https://ad.thsi.cn/2020/yiqing2020/index.html");

        try {
            // 延迟加载，确保拿到所有数据
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 解析标签，解析具体的 ID 标签
            WebElement webElement = driver.findElement(By.id("easter_egg"));
            String str = webElement.getAttribute("outerHTML");

            Html html = new Html(str);

            // xpath 解析，获取数据
            // 示例：
            // String confirmNum = html.xpath("//*[@class='icon-item confirm']/div[1]/text()").get();

            // 数据解析处理逻辑...

        } catch (Exception e) {
            _logger.error("数据解析异常！异常信息：", e);
        } finally {
            //
            driver.close();
        }
    }

}

