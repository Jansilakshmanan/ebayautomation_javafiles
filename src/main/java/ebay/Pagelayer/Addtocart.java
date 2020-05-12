package ebay.Pagelayer;

import ebay.Baselayer.TestBase;
import ebay.Utilitylayer.Testutil;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;


public class Addtocart extends TestBase {


    @FindBy(xpath = "//*[@class='gh-tb ui-autocomplete-input']")
    WebElement searchbar;
    @FindBy(xpath = "//*[@id='gh-cat']")
    WebElement categorydrpdown;
    @FindBy(xpath = "//*[@class='ui-menu-item ghAC_visible']")
    List<WebElement> lstcategories;
    @FindBy(xpath = "//*[@class='s-item__link']")
    List<WebElement> lstproducts;
    Testutil util = new Testutil();
    ProductsList pl = new ProductsList();

    public Addtocart() {
        super();
        PageFactory.initElements(driver, this);
    }

    @BeforeMethod
    public void setup() {
        initialization();

    }

    @DataProvider
    public Object[] gettestdata() throws IOException {

        Object[][] data = util.readexceldata();

        return data;
    }

    @Test(priority = 1, dataProvider = "gettestdata")
    public void searchincategories(String Test_desc, String Test_name, String Search_text, String Suggestionlist_item, String cartitem_no, String color, String quantity, String Bulk_savings) throws InterruptedException {


        Select s = new Select(categorydrpdown);
        s.selectByVisibleText("All Categories");
        //gets search_text parameter from dataprovider
        searchbar.sendKeys(Search_text);

        List<WebElement> lstcategories = driver.findElements(By.xpath("//*[@class='ui-menu-item ghAC_visible']"));
        for (int i = 0; i < lstcategories.size(); i++) {
            try {

                WebElement element = driver.findElements(By.xpath("//*[@class='ui-menu-item ghAC_visible']")).get(i);
                //suggestionlist_item parameter is from dataprovider
                if ((element.getText()).equalsIgnoreCase(Suggestionlist_item)) {
                    System.out.println(element.getText());
                    element.click();
                    break;
                }
            } catch (Exception e) {

            }
        }


        List<WebElement> lst = driver.findElements(By.xpath("//*[@class='s-item__link']"));
        int noofprod = lst.size();
        //calls randomnogenerator function from utility class
        int ran_no = util.randomnogenerator(noofprod);
      //clicks random item from the list
        WebElement element = driver.findElements(By.xpath("//*[@class='s-item__link']")).get(ran_no);
        System.out.println("Product added to cart " + element.getText());
        element.click();


        Boolean isPlacebidPopulated = false;
    try {
        WebElement placebid = driver.findElement(By.xpath("//*[@id='bidBtn_btn']"));
        if (placebid.isDisplayed()) {
            driver.findElement(By.xpath("//*[@id='bidBtn_btn']"));
            driver.navigate().to("https://www.ebay.com/");
            System.out.println("navigated back to:" + driver.getTitle());
            isPlacebidPopulated = true;
        }
    }
    catch(NoSuchElementException e1) {
        System.out.println("handled no suchelement exception for placebid");

    }

    if (!isPlacebidPopulated) {
        try {

            WebElement e = driver.findElement(By.id("msku-sel-1"));

            Select s1 = new Select(e);
            s1.selectByIndex(1);

try {

    WebElement bulk= driver.findElement(By.xpath("//*[@id='vi-vpqp-pills-2']"));
    bulk.click();
   Thread.sleep(3000);

}
catch(NoSuchElementException e2)
            {
                System.out.println("handled no suchelement exception for bulk savings");
            }

        } catch (NoSuchElementException e) {
            System.out.println("handled no suchelement exception");
        }
        finally {
            driver.findElement(By.id("atcRedesignId_btn")).click();
        }
    }

    }
    @AfterTest
    public void teardown()
    {
        driver.quit();
    }

}

