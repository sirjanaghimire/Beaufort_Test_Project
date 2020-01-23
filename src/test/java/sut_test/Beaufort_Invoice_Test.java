package sut_test;

import common.Base;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class Beaufort_Invoice_Test extends Base {

    private String baseUrl = "http://34.225.240.91/#/tables";
    private static String invoiceId = String.valueOf(10000 + new Random().nextInt(90000));
    private static String companyName = "Beaufort Automation";
    private static String type = "Automation Tester";
    private static String price = "50000";
    private static String selectStatus = "Past Due";
    private static String dueDate = "2020-01-23";
    private static String comment = "Testing For the create new  invoice.";
    private WebDriver driver;

    @BeforeTest()
    public void beforeTest() {
        driver = setUpDriverOpenWebsite(baseUrl);
    }

    @Test(priority = 1)
    public void createInvoice() throws InterruptedException {

        fillForm(invoiceId);
        driver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[3]/a")).click();
        Thread.sleep(2000);
        driver.findElement(By.linkText(invoiceId)).getText();
        assertEquals(driver.findElement(By.linkText(invoiceId)).getText(), invoiceId);
    }


    @Test(priority = 2)
    public void checkDuplicateEntries() throws InterruptedException {
        if (!driver.getCurrentUrl().contains("addInvoice")) {
            driver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[4]/a")).click();
        }
        fillForm(invoiceId);
        System.out.println("test");
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        assertTrue(alertText.contains(" invoice numbers cannot be the same."));
        alert.accept();

    }


    @Test(priority = 3)
    public void reviseInvoiceAndCheckAllFields() {
        if (!driver.getCurrentUrl().contains("tables")) {
            driver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[3]/a")).click();
        }
        driver.findElement(By.linkText(invoiceId)).click();
        assertEquals(invoiceId, driver.findElement(By.name("invoice")).getAttribute("value"));
        assertEquals(companyName, driver.findElement(By.name("company")).getAttribute("value"));
        assertEquals(type, driver.findElement(By.name("type")).getAttribute("value"));
        assertEquals(price, driver.findElement(By.name("price")).getAttribute("value"));
        assertEquals(selectStatus, driver.findElement(By.id("selectStatus")).getAttribute("value"));
        assertEquals(dueDate, driver.findElement(By.name("dueDate")).getAttribute("value"));
        assertEquals(comment, driver.findElement(By.name("comment")).getAttribute("value"));
    }

    private void fillForm(String invoiceNum) throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[4]/a")).click();
        Thread.sleep(2000);
        driver.findElement(By.name("invoice")).sendKeys(invoiceNum);
        driver.findElement(By.name("company")).sendKeys(companyName);
        driver.findElement(By.name("type")).sendKeys(type);
        driver.findElement(By.name("price")).sendKeys(price);
        Select select = new Select(driver.findElement(By.id("selectStatus")));
        select.selectByVisibleText(selectStatus);
        driver.findElement(By.name("dueDate")).sendKeys(dueDate);
        driver.findElement(By.name("comment")).sendKeys(comment);
        driver.findElement(By.id("createButton")).click();
        Thread.sleep(3000);
    }

    @AfterTest
    public void afterClass() {
        driver.close();
    }
}

