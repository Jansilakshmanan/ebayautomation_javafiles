package ebay.Pagelayer;


import ebay.Baselayer.TestBase;
import ebay.Utilitylayer.Testutil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ProductsList extends TestBase {

    //Web elements using Page factory
    @FindBy(xpath = "//*[@class='gh-tb ui-autocomplete-input']")
    WebElement searchbar;
    @FindBy(xpath = "//*[@id='gh-cat']")
    WebElement categorydrpdown;
    @FindBy(xpath = "//*[@class='ui-menu-item ghAC_visible']")
    List<WebElement> lstcategories;
    @FindBy(xpath = "//*[@class='s-item__link']")
    List<WebElement> lstproducts;
    Testutil util = new Testutil();

    public ProductsList() {
        //initializing base class constructor
        super();
        //initializing elements using constructor
        PageFactory.initElements(driver, this);
    }

    @BeforeMethod
    public void setup() {
        initialization();
    }


    public void searchincategories() {
        //importing select class to select from dropdown
        Select s = new Select(categorydrpdown);
        s.selectByVisibleText("All Categories");
        searchbar.sendKeys("watches");
        //Getting all webelements in a list and iterating
        List<WebElement> lstcategories = driver.findElements(By.xpath("//*[@class='ui-menu-item ghAC_visible']"));
        for (int i = 0; i < lstcategories.size(); i++) {
            try {
                //since,the DOM structure will be changed,
                //webelements are again iterated inside the for loop to avoid stalelement exception
                WebElement element = driver.findElements(By.xpath("//*[@class='ui-menu-item ghAC_visible']")).get(i);
                System.out.println("current list item : " + i + " " + element.getText());
                //iteartes each element in the list and checks its text with i/p text,if so element is clicked
                if ((element.getText()).equalsIgnoreCase("watches for kids")) {
                    System.out.println(element.getText());
                    element.click();
                    break;
                }
            } catch (Exception e) {
                System.out.println("Exception-Element# " + i + " not found");
            }
        }
    }

    public void getcategoriesname() {
        searchincategories();
    }

    //get nth product from the list which is a dynamic value
    public void getnthproduct() {
        searchincategories();
        String nthprod = driver.findElements(By.xpath("//*[@class='s-item__link']")).get(0).getText();
        System.out.println("nthprod is" + nthprod);
    }

    public void getallproducts() {
        searchincategories();
        List<WebElement> lstproducts = driver.findElements(By.xpath("//*[@class='s-item__link']"));
        for (int i = 0; i < lstproducts.size(); i++) {
            String text = driver.findElements(By.xpath("//*[@class='s-item__link']")).get(i).getText();
            System.out.println("index: " + i + " text" + " " + text);
            System.out.println(lstproducts.size());
        }
    }

    //Scrolling down the page using Action class
    public void getallproductsviascrolldown() throws InterruptedException {
        List<WebElement> lstproducts = driver.findElements(By.xpath("//*[@class='s-item__link']"));
        Actions act = new Actions(driver);
        for (int i = 0; i < lstproducts.size(); i++) {
            WebElement element = driver.findElements(By.xpath("//*[@class='s-item__link']")).get(i);
            String text = element.getText();
            act.moveToElement(element).build().perform();
            System.out.println("index: " + i + " text" + " " + text);
            Thread.sleep(1000);
        }
    }

    //re-usable function to scrtoll down the window which takes X and Y coordinates as parameters
    public void scrolldownwithjs(int X, int Y) {
        String jsscript = String.format("window.scrollBy(%d,%d)", X, Y);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(jsscript);
    }

    //scrolling down the page using javascript and prints last product in the list
    public void scrolldownwithjs() {
        List<WebElement> lstproducts = driver.findElements(By.xpath("//*[@class='s-item__link']"));
        for (int i = 0; i < lstproducts.size(); i++) {
            WebElement element = driver.findElements(By.xpath("//*[@class='s-item__link']")).get(i);
            int prodno = (lstproducts.size()) - 1;
            int X = element.getLocation().x;
            int Y = element.getLocation().y;
            util.scrolldownwithjs(X, Y);//calling the scroll down function
            if (i == prodno) {
                System.out.println("Last product" + i + " " + element.getText());
            }
        }
    }
}






