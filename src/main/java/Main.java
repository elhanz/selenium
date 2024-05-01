import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
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
        des.setCapability("os_version", "10");
        des.setCapability("browser", "chrome");
        des.setCapability("browser_version", "124");

        System.setProperty("webdriver.chrome.driver", "D:\\Пользователи\\User\\Downloads\\chromedriver-win64\\chromedriver.exe");
        driver = new RemoteWebDriver(new URL(url), des);
//        driver = new ChromeDriver();
        driver.manage().window().maximize();


        ExtentReports report = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("D:\\projects\\webdriver\\src\\main\\report\\report.html");
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("Отчет по автоматизированному тестированию");
        spark.config().setReportName("Отчет по тестированию");
        report.attachReporter(spark);
        ExtentTest test = report.createTest("Black box testing");

        //test = report.createTest("Black box testing");
        driver.get("https://aviata.kz/");

        // Load the file.
        OPCPackage pkg = OPCPackage.open(new File("D:\\projects\\webdriver\\src\\TestData.xlsx"));

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
        test.log(Status.INFO, "Searching the flights ");
        mainPage.searchByForm();
        test.log(Status.PASS, "Flights were found");


    }

    @Test(priority = 1)
    public void chooseFlight() throws InterruptedException, IOException {
        test.log(Status.INFO, "Choosing the flight");
        SearchPage searchPage = new SearchPage(driver);
        searchPage.chooseFlight();
        test.log(Status.PASS, "Flight was chose");


    }

    @Test(priority = 2)
    public void fillBookingForm() throws InterruptedException, IOException {
        test.log(Status.INFO, "Booking the flight");
        BookingPage bookingPage = new BookingPage(driver);
        Row row = sheet.getRow(0);
        bookingPage.fillBookingForm(row.getCell(2).getStringCellValue(), row.getCell(3).getStringCellValue(), row.getCell(4).getStringCellValue(), row.getCell(5).getStringCellValue(), row.getCell(6).getStringCellValue(), row.getCell(7).getStringCellValue(), row.getCell(8).getStringCellValue(), row.getCell(9).getStringCellValue(), row.getCell(10).getStringCellValue());
        test.log(Status.INFO, "Form was filled");
        bookingPage.sendBookingForm();
        test.log(Status.PASS, "Flight was booked");
    }

    @Test(priority = 3)
    public void pay() throws InterruptedException, IOException {
        test.log(Status.INFO, "Paying for the flight");
        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.pay();
        test.log(Status.PASS, "Flight was payed");

    }


    @AfterClass
    public void closeTheBrowser() {
        test.log(Status.INFO, "Close the browser");
        driver.quit();
        report.flush();
    }


}