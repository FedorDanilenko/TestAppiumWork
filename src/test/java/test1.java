import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
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

        // Открытие домашнего экрана
        driver.pressKey(new KeyEvent(AndroidKey.HOME));

        // Открытие браузера
        driver.findElementByAccessibilityId("Chrome").click();

        Thread.sleep(2000); //Задержка перед нажатием
        // Нажатие на строку поиска
        action.tap(PointOption.point(500, 650)).perform();

        // Ввод требуемого запроса
//        Thread.sleep(20); //Задержка перед нажатием
        driver.getKeyboard().sendKeys("pizza");
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));

        // Скролинг вниз
        Thread.sleep(2000);
        swipeScreen(Direction.UP);
        action.tap(PointOption.point(500, 500)).perform();

    }

    @AfterMethod
    public void end() { driver.quit(); }

    public void swipeScreen (Direction dir) {

        final int ANIMATION_TIME=200; // Время для анимации
        final int PRESS_TIME=200; // Время нажатия на экран

        int edgeBotter=10; // Граница экрана

        PointOption pointOptionStart, pointOptionEnd;

        Dimension sizeScreen = driver.manage().window().getSize();

        // Точки начала скролинка
        pointOptionStart = PointOption.point(sizeScreen.width / 2, sizeScreen.height / 2);

        // Задаем направление свайпа
        switch (dir) {
            case UP:
                pointOptionEnd = PointOption.point(sizeScreen.width/2, (int) (sizeScreen.height * 0.2));
                break;
            case DOWN:
                pointOptionEnd = PointOption.point(sizeScreen.width/2, sizeScreen.height-edgeBotter);
                break;
            case LEFT:
                pointOptionEnd = PointOption.point(edgeBotter, sizeScreen.height);
                break;
            case RIGHT:
                pointOptionEnd = PointOption.point(sizeScreen.width - edgeBotter, sizeScreen.height);
                break;
            default:
                throw new IllegalArgumentException("swipeScreen(): dir: '" + dir + "' NOT supported");
        }

        try {
            new TouchAction(driver)
                    .press(pointOptionStart)
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
        } catch (Exception e) {
            System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
            return;
        }

        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;

    }
}
