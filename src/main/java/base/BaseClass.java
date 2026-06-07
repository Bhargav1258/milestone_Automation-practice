package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

    public static WebDriver driver;

    @BeforeClass
    public void setup() {

        System.out.println("====================================");
        System.out.println(" BROWSER LAUNCH STARTED");
        System.out.println("====================================");

        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://testautomationpractice.blogspot.com/");
    }

    @AfterClass
    public void tearDown() {

        System.out.println("====================================");
        System.out.println(" BROWSER CLOSING");
        System.out.println("====================================");

        if (driver != null) {
            driver.quit();
        }
    }
}