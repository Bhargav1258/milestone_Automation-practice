package tests;

import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseClass;
import pages.AutomationPracticePage;
import utils.WriteExcelwithdp;


@Listeners(listeners.TestListener.class)
public class AutomationPracticeTest extends BaseClass {

	AutomationPracticePage page;
    WriteExcelwithdp excel;

    // alwaysRun = true guarantees this executes before your groups run
    @BeforeClass(alwaysRun = true)
    public void startBrowser() {
        System.out.println("🚀 Initializing browser and layout configurations...");
        setup();
        page = new AutomationPracticePage(driver);

        // Unified Single Excel configuration setup
        excel = new WriteExcelwithdp();
        excel.createExcelFile(); // Generates C:\Users\Admin\exxcel1\TestData123.xlsx with all sheets
    }

    // ==================================
    // DATAPROVIDER
    // ==================================

    @DataProvider(name="userData")
    public Object[][] userData(){

        return new Object[][]{

            {
                "Bhargav",
                "bhargav@gmail.com",
                "9876543210",
                "Kakinada"
            }, {
                "pavan",
                "pavan@gmail.com",
                "9876543210",
                "mumbai"
            }
        };
    }

    // ==================================
    // UNIT 1
    // ==================================

    @Test(priority = 1,
          dataProvider = "userData", groups = { "smoke" })
    public void Unit1_GUIElements(
            String name,
            String email,
            String phone,
            String address) {

        page.enterName(name);

        page.enterEmail(email);

        page.enterPhone(phone);

        page.enterAddress(address);

        page.selectGender("Male");

        page.selectDays();

        page.selectCountry("India");

        page.selectColor("Blue");

        page.selectAnimal("Dog");
    }

    // ==================================
    // UNIT 2
    // ==================================

    @Test(priority = 2,groups = { "regression" })
    public void Unit2_DatePickers() throws InterruptedException {

        page.selectDatePicker1("06/05/2026");
        Thread.sleep(1000);
        page.selectDatePicker2("05/06/2026");
        Thread.sleep(1000);


        page.selectDateRange(
                "01-06-2026",
                "10-06-2026");
        Thread.sleep(1000);

        page.submitDateRange();
    }

    // ==================================
    // UNIT 3
    // ==================================

    @Test(priority = 3, groups = { "smoke" })
    public void Unit3_FileUpload() throws InterruptedException {

        page.uploadSingleFile(
            "C:\\Users\\Admin\\OneDrive\\Pictures\\133907427220636723.jpg");
        Thread.sleep(1000);

        page.uploadMultipleFiles(
            "C:\\Users\\Admin\\OneDrive\\Pictures\\133907427220636723.jpg",
            "C:\\Users\\Admin\\OneDrive\\Pictures\\133907427220636723.jpg");
        Thread.sleep(1000);
    }
    @Test(priority = 4,groups = { "regression" })
    public void Unit4_PaginationWebTable() throws InterruptedException {

        System.out.println(
                "Total Pages = "
                + page.getTotalPages());

        page.selectAllProductsInAllPages();
        Thread.sleep(1000);
    }
    @Test(priority = 5, groups = { "smoke" })
    public void Unit5_Form()
            throws InterruptedException {

        page.fillSection1("Bhargav");
        Thread.sleep(2000);

        page.fillSection2("Automation Testing");
        Thread.sleep(2000);
        page.fillSection3("Selenium TestNG");

        Thread.sleep(2000);

        System.out.println(
                "Form Submitted Successfully");
    }
    @Test(priority = 6,groups = { "smoke" })
    public void printValidLinksTest() throws InterruptedException {
        System.out.println("Executing priority 6 test - printValidLinks");
        Thread.sleep(2000);
        
        // call your method here
        printValidLinks();
    }

    public void printValidLinks() {
        System.out.println("Running link validation logic...");
    
}
 // ==================================
    // UNIT 7
    // ==================================

    @Test(priority = 7,groups = { "regression" })
    public void Unit7_TabsAndWindowsValidation() throws InterruptedException {
        System.out.println("Executing priority 7 test - Wikipedia Search, Tab Swap, & Focus Reset");
        Thread.sleep(2000);
        
        // Data is managed directly inside the POM method layer
        page.searchWikipediaAndNavigateTab();
        
    }
 // ==================================
    // UNIT 8
    // ==================================

    @Test(priority = 8, groups = { "regression" })
    public void Unit8_AlertsAndPopupsValidation() throws InterruptedException {
        System.out.println("Executing priority 8 test - Alerts & Popups");
        Thread.sleep(2000);
        
        // Triggers the entire simple, confirm, and prompt sequential flow
        page.executeUnit8_AlertsAndPopups();
    }
 // ==================================
    // UNIT 9 - ADVANCED MOUSE INTERACTIONS
    // ==================================

    @Test(priority = 9, groups = { "smoke" })
    public void Unit9_AdvancedMouseValidation() {
        System.out.println("Executing priority 9 test - Double Click, Drag-Drop, and Slider Actions");
        
        // Triggers the copy text, canvas box drag, and slider bar drag tasks sequentially
        page.executeUnit9_AdvancedMouseActions();
    }
 // ==================================
    // UNIT 10 - LINK TRAVERSAL VALIDATION
    // ==================================

    @Test(priority = 10, groups = { "regression" })
    public void Unit10_VerifyLaptopAndBrokenLinks() {
        System.out.println("Executing priority 10 test - Physical Traversal for Laptop & Error Code Links");
        
        // Triggers the targeted click-print-return cycle for Apple, Lenovo, Dell and the error links
        page.executeUnit10_LinkTraversal();
    }
}