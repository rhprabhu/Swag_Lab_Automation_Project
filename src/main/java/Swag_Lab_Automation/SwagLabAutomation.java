package Swag_Lab_Automation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class SwagLabAutomation {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        String[] products = {"Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt", "Sauce Labs Onesie"};
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5000));
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("#login-button")).click();

        //Waiting for the All Items page
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Swag Labs']")));
        Thread.sleep(1000);
        // Menu Click operations
        driver.findElement(By.id("react-burger-menu-btn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_sidebar_link")));
        driver.findElement(By.id("inventory_sidebar_link")).click();
        driver.findElement(By.id("react-burger-cross-btn")).click();
        addItems(driver, products);


        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("app_logo")));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,0)");

        //remove product from the list
        removeItem(driver, "Sauce Labs Backpack");
        Thread.sleep(1000);
        js.executeScript("window.scrollBy(0,500)");
        Thread.sleep(1000);
        driver.findElement(By.id("checkout")).click();

        Thread.sleep(1000);
        //checkout page step-1
        driver.findElement(By.name("firstName")).sendKeys("Harshitha");
        driver.findElement(By.name("lastName")).sendKeys("Prabhu");
        driver.findElement(By.name("postalCode")).sendKeys("575001");
        Thread.sleep(2000);
        driver.findElement(By.name("continue")).click();

        //Checkout page step-2
        js.executeScript("window.scrollBy(0,500)");
        driver.findElement(By.name("finish")).click();

        driver.findElement(By.id("react-burger-menu-btn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_sidebar_link")));
        driver.findElement(By.id("about_sidebar_link")).click();
        driver.navigate().back();
        Thread.sleep(2000
        );
//        Set<String> windows = driver.getWindowHandles();
//        Iterator<String> it = windows.iterator();
//        String ParentId = it.next();
//        String ChildId = it.next();
//
//        driver.switchTo().window(ChildId);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='MuiBox-root css-lwb5go']")));
//        driver.switchTo().window(ParentId);l̥

        driver.findElement(By.id("react-burger-menu-btn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_sidebar_link")));
        driver.findElement(By.id("logout_sidebar_link")).click();
    }


    public static void addItems(WebDriver driver, String[] products) {

        List<WebElement> allProduct = driver.findElements(By.className("inventory_item_name"));

        for (int i = 0; i < allProduct.size(); i++) {
            WebElement productElement = allProduct.get(i);
            String productName = productElement.getText();
            System.out.println(productName);
            // Check if the current product name is in the array
            for (String product : products) {
                if (productName.equals(product)) {
                    // Click on the "Add to cart" button for the matching product
                    WebElement buttonClick = driver.findElements(By.cssSelector("button[class*='btn_inventory']")).get(i);
                    buttonClick.click();
                    break;  // Exit the inner loop since the product was found
                }
            }
        }
    }


    public static void removeItem(WebDriver driver, String itemName) {
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));

        for (WebElement cartItem : cartItems) {
            String productName = cartItem.findElement(By.className("inventory_item_name")).getText();

            if (productName.equals(itemName)) {
                // Assuming there is a "Remove" button associated with each item
                WebElement removeButton = cartItem.findElement(By.cssSelector("button[class*='btn_secondary']"));
                removeButton.click();
                break;  // Exit the loop since the item was found and removed
            }
        }
    }
}




