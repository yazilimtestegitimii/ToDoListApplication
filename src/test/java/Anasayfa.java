import com.thoughtworks.gauge.Step;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Anasayfa extends BaseTest {

    @Step("<url> anasayfasi acilir")
    public void anaSayfayaGit(String url) {
        getUrl(url);
        System.out.println("Anasayfa yuklendi");
    }

    @Step("<saniye> saniye bekle")
    public void waitElement(int key) throws InterruptedException {
        Thread.sleep(key * 1000);
        System.out.println(key + " saniye beklendi");
    }

    @Step("<key> elementi var mi")
    public void findElementsList(String key) {
        try {
            findElement(key);
        } catch (Exception e) {
            System.out.println("Element bulunamadi");
        }
    }

    @Step("<key> element listesinde <text> yoksa <keytwo> elementine ekle")
    public void addIfIsNotThere(String key, String text, String keytwo) {
        checkAndAddToList(key, text, keytwo);
    }

    public void checkAndAddToList(String key, String text, String keytwo){
        List<WebElement> list = findElements(key);
        if (list.isEmpty() != true){
            List<String> listTexts = forListElements(key);
            boolean result = listTexts.contains(text);
            if(result == false){
                sendText(keytwo,text);
                clickEnterTheElement(keytwo);
            }
        }else{
            sendText(keytwo,text);
            clickEnterTheElement(keytwo);
        }
    }

    @Step("<key> listesindeki <text> iteminin <keytwo> elementine tikla")
    public void clickItemAttirbute(String key, String text, String keytwo) {
        clickTheItemAttirbute(key, text, keytwo);
        System.out.println("Elementine tiklandi");
    }

    @Step("<key> listesindeki <text> isaretli degilse <keytwo> isaretle")
    public void checkAndClickRadioButon(String key, String text, String keytwo) {
        checkAndClickTheRadioButton(key, text, keytwo);
    }

    @Step("<key> listesindeki <text> isaretli mi")
    public void checkTheRadioButton(String key, String text) {
        assertRadioButtonStatus(isClickedRadioButton(key, text));
    }

    @Step("<key> listesindeki <text> isaretsiz mi")
    public void isNotChecked(String key, String text) {
        assertRadioButtonStatus(isNotClickedTheRadioButton(key, text));
    }

    public void sendText(String key, String text){
        findElement(key).sendKeys(text);
    }

    @Step("<key> element listesi bos mu")
    public void checkElementsListEmpty(String key) {
        isElementsListEmpty(key);
    }

    @Step("<inputToDo> yeni todo ekle <newTodo>")
    public void newToDo(String inputToDo, String newTodo) {
        sendkeysElement(inputToDo, newTodo);
        enterClick(inputToDo);
    }

    @Step("<todoControl> todo var mi <inputToDo>")
    public void toDoControl(String todoControl, String inputToDo) {
        String text = textElements(todoControl, 0);
        assertControl(todoControl, text);
        System.out.println("Elementi var");
    }

    @Step("<key> 'nde <text1> elementi <text2> iteminin altinda mi")
    public void checkPositionItems(String key, String text1, String text2) {
        checkPositons(key, text1, text2);
        System.out.println("Elementinin altinda");
    }

    @Step("<key> elementinde <text> silindi mi")
    public void isDeleted(String key, String text) {
        isElementDeleted(key, text);
        System.out.println("Elementi silindi");
    }
}