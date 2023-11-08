package org.xzx.service;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Future;

@Service
@Log4j2
public class Playwright_service {

    @Value("${qq.base.imagePath}")
    private String imagepath;

    @Async("taskExcutor")
    public Future<String> getBaiZhan() {
        log.info("开始下载百战图片");
        try (Playwright playwright = Playwright.create()) {
            //设置请求头
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions());
            BrowserContext context = browser.newContext(new Browser.NewContextOptions().setJavaScriptEnabled(true).setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36"));
            context.setDefaultTimeout(600000);
            Page page = context.newPage();
            //执行classpath下的js文件
            ClassPathResource classPathResource = new ClassPathResource("stealth.min.js");
            page.evaluate(new String(classPathResource.getInputStream().readAllBytes()));
            page.navigate("https://www.jx3box.com/fb/baizhan");
            page.waitForLoadState(LoadState.LOAD);

            log.info("页面加载完成");
            Download download = page.waitForDownload(() -> {
                log.info("点击导出图片按钮");
                page.locator("a").filter(new Locator.FilterOptions().setHasText("导出图片")).click();
            });
            System.out.println(Paths.get(imagepath + "baizhan.png"));
            download.saveAs(Paths.get(imagepath + "baizhan.png"));
            log.info("下载百战图片成功");
            return new AsyncResult<>("success");
        } catch (Exception e){
            log.error("下载百战图片失败", e);
            return null;
        }
    }
}
