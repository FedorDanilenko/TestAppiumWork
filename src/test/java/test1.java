import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class test1 {

    private AndroidDriver driver;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "pixel");
        // Имя ОС на мобильном устройстве
        caps.setCapability("platformName", "Android");
        // Версия ОС
        caps.setCapability("platformVersion", "9.0");
        // Уникальный индефикатор подключенного устройства
        caps.setCapability("udid", "emulator-5554");
        // Открытие начального экрана
        caps.setCapability("appActivity", "com.android.launcher3.Launcher");




        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), caps);
    }


    @Test
    public void testTap() throws InterruptedException {
        TouchAction action = new TouchAction(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        int sizeX = driver.manage().window().getSize().width;
        int sizeY = driver.manage().window().getSize().height;


        // Открытие домашнего экрана
        driver.pressKey(new KeyEvent(AndroidKey.HOME));

        // Открытие браузера
        driver.findElementByAccessibilityId("Chrome").click();


        Thread.sleep(20); //Задержка перед нажатием
        // Нажатие на строку поиска
        action.tap(PointOption.point(500, 650)).perform();

        // Ввод требуемого запроса
        Thread.sleep(20); //Задержка перед нажатием
        driver.getKeyboard().sendKeys("pizza");
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));


    }

    @AfterMethod
    public void end() { driver.quit(); }

}
