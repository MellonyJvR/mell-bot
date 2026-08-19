package com.bot;

import com.microsoft.playwright.*;
import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;

public class LaptopCoverBot {
    public static void main(String[] args) {
        String searchUrl = "https://www.takealot.com/all?qsearch=laptop+cover+15+inch";
        double TARGET = 270.00;
        String MY_PHONE = "27783437797";
        
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
        new BrowserType.LaunchOptions()
             .setHeadless(true)
             .setArgs(Arrays.asList("--no-sandbox", "--disable-setuid-sandbox"))
             );
            
            System.out.println("Hunting laptop covers...");
            page.navigate(searchUrl);
            page.waitForTimeout(8000);
            page.mouse().wheel(0, 1000);
            page.waitForTimeout(3000);

            var priceElements = page.locator("span, div").all();
            int count = 0;
            for (var el : priceElements) {
                try {
                    String txt = el.innerText().trim();
                    if (txt.startsWith("R ") && txt.length() < 10) {
                        String clean = txt.replaceAll("[^0-9]", "");
                        if (clean.length() >= 3) {
                            double price = Double.parseDouble(clean);
                            if (price > 50 && price < 2000) {
                                count++;
                                System.out.println("Cover " + count + ": " + txt);
                                
                                if (price <= TARGET) {
                                    System.out.println(">>> CHEAP FOUND R" + price + " <<<");
                                    String msg = "Mello! CHEAP laptop cover R" + price + " found! Target R270. Link: " + searchUrl;
                                    String waLink = "https://wa.me/" + MY_PHONE + "?text=" + URLEncoder.encode(msg, "UTF-8");
                                    Desktop.getDesktop().browse(new URI(waLink));
                                    java.awt.Toolkit.getDefaultToolkit().beep();
                                }
                                if (count >= 10) break;
                            }
                        }
                    }
                } catch (Exception ignore) {}
            }
            
            System.out.println("Done! Checked " + count + " covers.");
            page.waitForTimeout(10000);
            browser.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
