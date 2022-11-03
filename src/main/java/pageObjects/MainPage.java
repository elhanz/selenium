package pageObjects;

import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;


    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//*[@id=\"search-route-0\"]/div[1]/label/input")
    private WebElement fromField;
    @FindBy(xpath = "//*[@id=\"search-route-0\"]/div[1]/div[2]/div/ul/li/div")
    private WebElement confirmFromField;
    @FindBy(xpath = "//*[@id=\"search-route-0\"]/div[3]/label/input")
    private WebElement toField;
    @FindBy(xpath = "//*[@id=\"search-route-0\"]/div[3]/div[2]/div/ul/li/div")
    private WebElement confirmToField;
    @FindBy(xpath = "//*[@id=\"app\"]/section[1]/div/form/div[1]/div[2]/button")
    private WebElement findFlight;


    public void fillSearchingForm(String from, String to) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(fromField));
        fromField.sendKeys(from);
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(confirmFromField));
        confirmFromField.click();
        wait.until(ExpectedConditions.visibilityOf(toField));
        toField.sendKeys(to);
        wait.until(ExpectedConditions.elementToBeClickable(confirmToField));
        confirmToField.click();

    }

    public SearchPage searchByForm() throws InterruptedException {

        findFlight.click();
        return new SearchPage(driver);

    }


}
