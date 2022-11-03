import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import pageObjects.BookingPage;
import pageObjects.MainPage;
import pageObjects.PaymentPage;
import pageObjects.SearchPage;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Main {
    public static WebDriver driver;
    public static ExtentReports report;

    public static ExtentTest test;
    public static String USERNAME = "yelkhanzhumataye_TRYLwI";
    public static String AUTOMATE_KEY = "AVzSsUsFzSUUVsTwCgdz";
    public static String url = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    public static XSSFWorkbook workbook;
    public static XSSFSheet sheet;


    @BeforeClass
    public void start() throws IOException, InvalidFormatException {
        DesiredCapabilities des = new DesiredCapabilities();

        des.setCapability("os", "Windows");
        des.setCapability("os_version", "11");
        des.setCapability("browser", "chrome");
        des.setCapability("browser_version", "105");

        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe");
        driver = new RemoteWebDriver(new URL(url), des);
//        driver = new ChromeDriver();
        driver.manage().window().maximize();
        report = new com.relevantcodes.extentreports.ExtentReports("C:\\Users\\Yelkhan\\IdeaProjects\\webdriver\\src\\main\\report\\report.html");
        test = report.startTest("Extent report");
        driver.get("https://aviata.kz/");

        // Load the file.
        OPCPackage pkg = OPCPackage.open(new File("C:\\Users\\Yelkhan\\IdeaProjects\\webdriver\\src\\TestData.xlsx"));

        // Load he workbook.
        workbook = new XSSFWorkbook(pkg);
        // Load the sheet in which data is stored.
        sheet = workbook.getSheetAt(0);


    }

    @Test(priority = 0)
    public void searchByForm() throws InterruptedException, IOException {
        MainPage mainPage = new MainPage(driver);
        Row row = sheet.getRow(0);
        mainPage.fillSearchingForm(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue());
        test.log(LogStatus.INFO, "Searching the flights ");
        mainPage.searchByForm();
        test.log(LogStatus.PASS, "Flights were found");


    }

    @Test(priority = 1)
    public void chooseFlight() throws InterruptedException, IOException {
        test.log(LogStatus.INFO, "Choosing the flight");
        SearchPage searchPage = new SearchPage(driver);
        searchPage.chooseFlight();
        test.log(LogStatus.PASS, "Flight was chose");


    }

    @Test(priority = 2)
    public void fillBookingForm() throws InterruptedException, IOException {
        test.log(LogStatus.INFO, "Booking the flight");
        BookingPage bookingPage = new BookingPage(driver);
        Row row = sheet.getRow(0);
        bookingPage.fillBookingForm(row.getCell(2).getStringCellValue(), row.getCell(3).getStringCellValue(), row.getCell(4).getStringCellValue(), row.getCell(5).getStringCellValue(), row.getCell(6).getStringCellValue(), row.getCell(7).getStringCellValue(), row.getCell(8).getStringCellValue(), row.getCell(9).getStringCellValue(), row.getCell(10).getStringCellValue());
        test.log(LogStatus.INFO, "Form was filled");
        bookingPage.sendBookingForm();
        test.log(LogStatus.PASS, "Flight was booked");
    }

    @Test(priority = 3)
    public void pay() throws InterruptedException, IOException {
        test.log(LogStatus.INFO, "Paying for the flight");
        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.pay();
        test.log(LogStatus.PASS, "Flight was payed");

    }


    @AfterClass
    public void closeTheBrowser() {
        test.log(LogStatus.INFO, "Close the browser");
        driver.quit();
        report.endTest(test);
        report.flush();
    }


}