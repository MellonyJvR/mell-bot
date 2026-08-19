package com.bot;

import com.microsoft.playwright.*;
import java.util.Arrays;

public class LaptopCoverBot {
    public static void main(String[] args) {
        System.out.println("Hunting laptop covers...");
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                    .setHeadless(true)
                    .setArgs(Arrays.asList("--no-sandbox", "--disable-setuid-sandbox"))
            );
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            
            page.navigate("https://www.takealot.com/all?qsearch=laptop+cover");
            System.out.println("Page loaded: " + page.title());
            
            // Keep bot alive
            while (true) {
                System.out.println("Checking... " + java.time.LocalTime.now());
                Thread.sleep(60000);
                page.reload();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
