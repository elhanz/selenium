package StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import pageObjects.BookingPage;
import pageObjects.MainPage;
import pageObjects.PaymentPage;
import pageObjects.SearchPage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class Steps {
    public static WebDriver driver;
    public static XSSFWorkbook workbook;
    public static XSSFSheet sheet;
    public static String USERNAME = "yelkhanzhumataye_TRYLwI";
    public static String AUTOMATE_KEY = "AVzSsUsFzSUUVsTwCgdz";
    public static String url = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    private static final String USER_ID = "9b5f49ab-eea9-45f4-9d66-bcf56a531b85";
    private static final String USERNAME2 = "TOOLSQA-Test";
    private static final String PASSWORD = "Test@@123";
    private static final String BASE_URL = "https://bookstore.toolsqa.com";

    private static String token;
    private static Response response;
    private static String jsonString;
    private static String bookId;

    @Given("the user is on a main page")
    public void the_user_is_on_a_main_page() throws IOException, InvalidFormatException {
        DesiredCapabilities des = new DesiredCapabilities();

        des.setCapability("os", "Windows");
        des.setCapability("os_version", "11");
        des.setCapability("browser", "chrome");
        des.setCapability("browser_version", "105");

        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe");
        driver = new RemoteWebDriver(new URL(url), des);
//        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://aviata.kz/");

        OPCPackage pkg = OPCPackage.open(new File("C:\\Users\\Yelkhan\\IdeaProjects\\webdriver\\src\\TestData.xlsx"));

        // Load he workbook.
        workbook = new XSSFWorkbook(pkg);
        // Load the sheet in which data is stored.
        sheet = workbook.getSheetAt(0);
    }

    @When("the user enters place from and place to")
    public void the_user_enters_place_from_and_place_to() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        Row row = sheet.getRow(0);
        mainPage.fillSearchingForm(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue());

        mainPage.searchByForm();


    }

    @When("looking for flight")
    public void looking_for_flight() throws InterruptedException {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.chooseFlight();
    }

    @Then("the user should be booked his flight")
    public void the_user_should_be_booked_his_flight() throws InterruptedException {
        BookingPage bookingPage = new BookingPage(driver);
        Row row = sheet.getRow(0);
        bookingPage.fillBookingForm(row.getCell(2).getStringCellValue(), row.getCell(3).getStringCellValue(), row.getCell(4).getStringCellValue(), row.getCell(5).getStringCellValue(), row.getCell(6).getStringCellValue(), row.getCell(7).getStringCellValue(), row.getCell(8).getStringCellValue(), row.getCell(9).getStringCellValue(), row.getCell(10).getStringCellValue());
        bookingPage.sendBookingForm();
        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.pay();

        driver.quit();
    }

    @Given("I am an authorized user")
    public void iAmAnAuthorizedUser() {

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();

        request.header("Content-Type", "application/json");
        response = request.body("{ \"userName\":\"" + USERNAME + "\", \"password\":\"" + PASSWORD + "\"}")
                .post("/Account/v1/GenerateToken");

        String jsonString = response.asString();
        token = JsonPath.from(jsonString).get("token");

    }

    @Given("A list of books are available")
    public void listOfBooksAreAvailable() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        response = request.get("/BookStore/v1/Books");

        jsonString = response.asString();
        List<Map<String, String>> books = JsonPath.from(jsonString).get("books");
        Assert.assertTrue(books.size() > 0);

        bookId = books.get(0).get("isbn");
    }

    @When("I add a book to my reading list")
    public void addBookInList() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json");

        response = request.body("{ \"userId\": \"" + USER_ID + "\", " +
                        "\"collectionOfIsbns\": [ { \"isbn\": \"" + bookId + "\" } ]}")
                .post("/BookStore/v1/Books");
    }

    @Then("The book is added")
    public void bookIsAdded() {
        Assert.assertEquals(401, response.getStatusCode());
    }


}
