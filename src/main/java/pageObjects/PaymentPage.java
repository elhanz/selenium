package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaymentPage {
    private WebDriver driver;

    public PaymentPage(WebDriver driver) {
        this.driver = driver;

    }

    private By payForTicker = By.xpath("//*[@id=\"app\"]/div/div[2]/main/div/div/div[2]/div[2]/div[2]/button");

    public void pay() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(payForTicker));
        Thread.sleep(8000);
        driver.findElement(payForTicker).click();

    }
}
