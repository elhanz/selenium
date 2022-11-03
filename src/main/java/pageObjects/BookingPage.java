package pageObjects;

import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BookingPage {
    private WebDriver driver;

    public BookingPage(WebDriver driver) {
        this.driver = driver;
    }

    private By lastName = By.xpath("//*[@id=\"app\"]/div/div[2]/main/div/div[2]/form/section[1]/section/div[2]/label[1]/div/input");
    private By firstName = By.xpath("//*[@id=\"app\"]/div/div[2]/main/div/div[2]/form/section[1]/section/div[2]/label[2]/div/input");
    private By genderMale = By.xpath("//*[@id=\"app\"]/div/div[2]/main/div/div[2]/form/section[1]/section/div[2]/div/div/label[1]/img");
    private By genderFemale = By.xpath("//*[@id=\"app\"]/div/div[2]/main/div/div[2]/form/section[1]/section/div[2]/div/div/label[2]/img");
    private By birthDate = By.xpath("//*[@id=\"app\"]/div/div[2]/main/div/div[2]/form/section[1]/section/div[2]/label[3]/div/input");
    private By documentNumber = By.xpath("//*[@id=\"app\"]/div/div[2]/main/div/div[2]/form/section[1]/section/div[2]/label[5]/div/input");
    private By expirationDate = By.xpath("//*[@id=\"app\"]/div/div[2]/main/div/div[2]/form/section[1]/section/div[2]/label[6]/div/input");
    private By iin = By.xpath("//*[@id=\"app\"]/div/div[2]/main/div/div[2]/form/section[1]/section/div[3]/label/div/input");
    private By phoneNumber = By.xpath("//*[@id=\"app\"]/div/div[2]/main/div/div[2]/form/section[2]/div/label[2]/div/input");
    private By email = By.xpath("//*[@id=\"app\"]/div/div[2]/main/div/div[2]/form/section[2]/div/label[3]/div[2]/input");
    private By agreeWithTerms = By.xpath("//*[@id=\"app\"]/div/div[2]/main/div/div[2]/form/section[4]/label/input");
    private By bookTheFlight = By.xpath("//*[@id=\"btnBook\"]");
    private By permissionToArrive = By.xpath("//*[@id=\"app\"]/div/div[2]/main/div/div[2]/div[2]/div/div/button");

    public void fillBookingForm(String surname, String name, String gender, String dateOfBirth,
                                String docNum, String expDate, String iiNum,
                                String phoneNum, String mail) throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastName));
        driver.findElement(lastName).sendKeys(surname);
        driver.findElement(firstName).sendKeys(name);
        if (gender.equals("Male")) driver.findElement(genderMale).click();
        else if (gender.equals("Female")) driver.findElement(genderFemale).click();
        driver.findElement(birthDate).sendKeys(dateOfBirth);
        driver.findElement(documentNumber).sendKeys(docNum);
        driver.findElement(expirationDate).sendKeys(expDate);
        driver.findElement(iin).sendKeys(iiNum);
        driver.findElement(phoneNumber).sendKeys(phoneNum);
        driver.findElement(email).sendKeys(mail);

    }

    public PaymentPage sendBookingForm() throws InterruptedException {


        driver.findElement(agreeWithTerms).click();
        Thread.sleep(10000);
        driver.findElement(bookTheFlight).click();
        Thread.sleep(6000);
        driver.findElement(permissionToArrive).click();
        return new PaymentPage(driver);

    }
}
