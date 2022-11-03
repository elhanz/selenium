package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class SearchPage {

    private WebDriver driver;


    public SearchPage(WebDriver driver) {
        this.driver = driver;

    }

    private By pickFlight = By.xpath("//*[@id=\"app\"]/div/main/div[2]/div[2]/div/div[4]/div/div[2]/button");
    private By confirmFlight = By.xpath("//*[@id=\"app\"]/div/main/div[3]/div/div/div/div/div/div[2]/div/button");

    public BookingPage chooseFlight() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(pickFlight));
        driver.findElement(pickFlight).click();

        wait.until(ExpectedConditions.elementToBeClickable(pickFlight));
        driver.findElement(confirmFlight).click();

        return new BookingPage(driver);
    }

}
