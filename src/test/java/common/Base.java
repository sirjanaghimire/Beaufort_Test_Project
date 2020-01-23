package common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Base {

    public static WebDriver setUpDriverOpenWebsite(String url){
        System.setProperty("webdriver.chrome.driver", "bin/chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get(url);

        return webDriver;
    }
}
