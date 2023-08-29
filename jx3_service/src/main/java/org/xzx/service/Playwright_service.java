package org.xzx.service;

import com.microsoft.playwright.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.concurrent.Future;

@Service
public class Playwright_service {
    @Async("taskExcutor")
    public Future<String> getBaiZhan() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(true));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://www.jx3box.com/fb/baizhan");
            Download download = page.waitForDownload(() -> {
                page.locator("a").filter(new Locator.FilterOptions().setHasText("导出图片")).click();
            });
            download.saveAs(Paths.get("D:\\codesoft\\gocqhttp\\data\\images\\baizhan.png"));
            return new AsyncResult<>("success");
        }
    }
}
