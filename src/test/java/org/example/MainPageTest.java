package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainPageTest {
    private WebDriver driver;


    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://www.github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.bing.com/");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void search() {
        String input = "Selenium";
        WebElement searchField = driver.findElement(By.cssSelector("#sb_form_q"));

        searchField.sendKeys(input);
        searchField.submit();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.and(
                ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(":not(.b_adurl) > cite"), "selenium.dev"),
                ExpectedConditions.elementToBeClickable(By.cssSelector(":not(.b_adurl) > cite"))
        ));
        List<WebElement> results = driver.findElements(By.cssSelector("h2 > a[href]"));
        for (WebElement el : results) {
            System.out.println(el.getText());
        }
        clickElements(results, 0);
        ArrayList tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabs.size() > 1) driver.switchTo().window(tabs.get(1).toString());
        wait.until(
                ExpectedConditions.urlContains("selenium.dev")
        );
        assertTrue(driver.getCurrentUrl().contains("selenium.dev"));

    }

    public void clickElements(List<WebElement> results, int num) {
        System.out.println(results.get(num).getText());
        results.get(num).click();

    }

    @Test
    public void example() {
        List<String> strings = new ArrayList<>();
        String a = "First string";
        String b = "Second string";
        strings.add(a);
        strings.add(b);
    }
}


