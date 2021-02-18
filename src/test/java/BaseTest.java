import com.thoughtworks.gauge.AfterSuite;
import com.thoughtworks.gauge.BeforeSuite;
import helper.ElementHelper;
import helper.StoreHelper;
import model.ElementInfo;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class BaseTest {

    @BeforeSuite
    public void senaryoOncesi() throws InterruptedException {
        System.out.println("---Senaryo Basladi---");
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        action = new Actions(driver);
        driver.manage().window().maximize();
        System.out.println("Driver Calisti");
    }

    public static void getUrl(String url) {
        driver.get(url);
    }

    static WebDriver driver;
    static Actions action;

    public WebElement findElement(String key) {
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
        WebElement webElement = webDriverWait
                .until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }

    public List<WebElement> findElements(String key) {
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        return driver.findElements(infoParam);
    }

    public void clickEnterTheElement(String key){
        findElement(key).sendKeys(Keys.ENTER);
    }

    public void isElementsListEmpty(String key){
        WebElement fullList = findElement(key);
        List<WebElement> ulFullList = fullList.findElements(By.tagName("li"));
        int listSize = ulFullList.size();
        int expectedEmptyListSize = 0;
        Assert.assertEquals(expectedEmptyListSize,listSize);
    }

    public void assertControl(String assertName, String expectedName) {
        String assertName1 = findElement(assertName).getText();
        System.out.println(assertName1);
        Assert.assertEquals(assertName1, expectedName);
    }

    public void sendkeysElement(String by, String text) {
        findElement(by).sendKeys(text);
    }

    public String textElements(String by,int index){
        String text=findElements(by).get(index).getText();
        return text;
    }

    public void enterClick(String by){
        findElement(by).sendKeys(Keys.ENTER); //Enter ile tıklanmayi sağliyor.
    }

    public void checkPositons(String key, String text1, String text2){
        List<String> listTexts = forListElements(key);
        int firstIndex = listTexts.indexOf(text1);
        int secondIndex = listTexts.indexOf(text2);
        Assert.assertTrue(firstIndex > secondIndex);
    }

    public void clickTheItemAttirbute(String key, String text, String keytwo){
        List<WebElement> ulToDoList = findElements(key);
        List<String> list = forListElements(key);
        int index = list.indexOf(text);
        WebElement element = ulToDoList.get(index).findElement(findElementBy(keytwo));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public void checkAndClickTheRadioButton(String key, String text, String keytwo){
        if (isClickedRadioButton(key,text) == false){
            clickTheItemAttirbute(key, text, keytwo);
        }
    }

    public boolean isClickedRadioButton(String key, String text){
        List<WebElement> ulToDoList = findElements(key);
        List<String> list = forListElements(key);
        int index = list.indexOf(text);
        String isChecked;
        isChecked = ulToDoList.get(index).getAttribute("class");
        String checkedString = "todo completed";
        return (isChecked.equals(checkedString));
    }

    public boolean isNotClickedTheRadioButton(String key, String text){
        List<WebElement> ulToDoList = findElements(key);
        List<String> list = forListElements(key);
        int index = list.indexOf(text);
        String isChecked = ulToDoList.get(index).getAttribute("class");
        String checkedString = "todo";
        return (isChecked.equals(checkedString));
    }

    public void assertRadioButtonStatus(boolean condition){
        Assert.assertTrue("Radio button aktif degil",condition);
    }

    public By findElementBy(String key){
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        return infoParam;
    }

    public List<String> forListElements(String key){
        List<WebElement> ulToDoList = findElements(key);
        List<String> list = new ArrayList<>();
        for(WebElement i : ulToDoList){
            list.add(i.getText());
        }
        return list;
    }

    public void isElementDeleted(String key, String text){
        List<String> list = forListElements(key);
        Assert.assertFalse("Element Var",list.contains(text));
    }

    @AfterSuite
    public void bitir() {
        driver.quit();
        System.out.println("---Senaryo Sonlandi---");
    }
}