package tests;

import com.denysord88.appium.flutter.FlutterFinder;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static com.denysord88.conf.Configuration.*;
import static com.denysord88.testng.TestEventsListener.getAndroidDriver;
import static com.denysord88.testng.TestEventsListener.getiOSDriver;
import static org.testng.Assert.assertEquals;

public class MyFirstTestAndroid {
    //@Test(groups = ("android"))
    public void accessTest() throws InterruptedException {
        FlutterFinder finder = new FlutterFinder(getAndroidDriver());

        finder.byText("Check access").click();
        assertEquals(finder.byValueKey("_accessHeaderTitle").getText(), "Access");

        assertEquals(finder.byValueKey("_locationAccessStatusText").getText(), "press check");
        finder.byValueKey("_checkLocationAccessButton").click();
        assertEquals(finder.byValueKey("_locationAccessStatusText").getText(), "Restricted");
        finder.byValueKey("_requestLocationAccessButton").click();
        Thread.sleep(2000);
        getAndroidDriver().context("NATIVE_APP");
        getAndroidDriver().findElement(By.xpath("//*[@text='While using the app']")).click();
        getAndroidDriver().context("FLUTTER");
        finder.byValueKey("_checkLocationAccessButton").click();
        Thread.sleep(2000);
        assertEquals(finder.byValueKey("_locationAccessStatusText").getText(), "Granted");

        assertEquals(finder.byValueKey("_photosAccessStatusText").getText(), "press check");
        finder.byValueKey("_checkPhotosAccessButton").click();
        assertEquals(finder.byValueKey("_photosAccessStatusText").getText(), "Restricted");
        finder.byValueKey("_requestPhotosAccessButton").click();
        Thread.sleep(3000);
        getAndroidDriver().context("NATIVE_APP");
        getAndroidDriver().findElement(By.xpath("//*[@text='Allow']")).click();
        getAndroidDriver().context("FLUTTER");
        finder.byValueKey("_checkPhotosAccessButton").click();
        Thread.sleep(2000);
        assertEquals(finder.byValueKey("_photosAccessStatusText").getText(), "Granted");
        finder.pageBack();
        Thread.sleep(2000);
        finder.byText("See the Google Map");
        finder.byToolTip("Back").click();
        Thread.sleep(2000);
        finder.byText("See the Google Map");
    }

    //@Test(groups = ("android"))
    public void webviewTest() throws InterruptedException {
        FlutterFinder finder = new FlutterFinder(getAndroidDriver());

        finder.byValueKey("_webViewTextField").sendKeys(WEBVIEW_URL);
        finder.byText("Open the WebView").click();
        Thread.sleep(10000);
        assertEquals(finder.byValueKey("_webviewHeaderTitle").getText(), "WebView");

        //Set<String> contextNames = getiOSDriver().getContextHandles();

        getAndroidDriver().context("NATIVE_APP");
        Thread.sleep(2000);
        getAndroidDriver().findElement(By.xpath("//*[@*='Увійти']")).click();

        Thread.sleep(8000);
        getAndroidDriver().findElement(By.xpath("//*[@*='Електронна адреса або номер телефону']")).click();
        getAndroidDriver().findElement(By.xpath("//*[@*='Електронна адреса або номер телефону']")).sendKeys(EMAIL);
        getAndroidDriver().findElement(By.xpath("//*[@*='Далі']")).click();

        Thread.sleep(8000);
        getAndroidDriver().findElement(By.xpath("//*[@*='Введіть пароль']")).click();
        getAndroidDriver().findElement(By.xpath("//*[@*='Введіть пароль']")).sendKeys(PASSWORD);
        getAndroidDriver().findElement(By.xpath("//*[@*='Далі']")).click();

        Thread.sleep(8000);
        getAndroidDriver().findElement(By.xpath("//*[@*='Google offered in:']"));
        //getAndroidDriver().findElement(By.xpath("//*[@*='Back']")).click();
        getAndroidDriver().context("FLUTTER");
        finder.byToolTip("Back").click();
        Thread.sleep(2000);
        finder.byText("See the Google Map");
    }

    @Test(groups = ("android"))
    public void cttTest() throws InterruptedException {
        FlutterFinder finder = new FlutterFinder(getAndroidDriver());

        finder.byText("ACCEDI").click();
        finder.byText("2").click();
        Thread.sleep(3000);
        finder.byText("Open Browser").click();
        Thread.sleep(5000);

        getAndroidDriver().context("NATIVE_APP");
        getAndroidDriver().findElement(By.xpath("//*[@*='Accept & continue']")).click();
        Thread.sleep(10000);
        getAndroidDriver().findElement(By.xpath("//*[@*='No thanks']")).click();
        Thread.sleep(15000);
        getAndroidDriver().findElement(By.xpath("//*[@*='Увійти']")).click();

        Thread.sleep(8000);
        getAndroidDriver().findElement(By.xpath("//*[@*='Електронна адреса або номер телефону']")).click();
        getAndroidDriver().findElement(By.xpath("//*[@*='Електронна адреса або номер телефону']")).sendKeys(EMAIL);
        getAndroidDriver().findElement(By.xpath("//*[@*='Далі']")).click();

        Thread.sleep(8000);
        getAndroidDriver().findElement(By.xpath("//*[@*='Введіть пароль']")).click();
        getAndroidDriver().findElement(By.xpath("//*[@*='Введіть пароль']")).sendKeys(PASSWORD);
        getAndroidDriver().findElement(By.xpath("//*[@*='Далі']")).click();

        Thread.sleep(8000);
        getAndroidDriver().findElement(By.xpath("//*[@*='Google offered in:']"));
        //getAndroidDriver().findElement(By.xpath("//*[@*='Back']")).click();
        getAndroidDriver().context("FLUTTER");
        finder.byToolTip("Back").click();
        Thread.sleep(2000);
        finder.byText("See the Google Map");
    }

    //@Test(groups = ("android"))
    public void googleMapTest() throws InterruptedException {
        FlutterFinder finder = new FlutterFinder(getAndroidDriver());

        finder.byText("See the Google Map").click();
        Thread.sleep(15000);
        assertEquals(finder.byValueKey("_mapHeaderTitle").getText(), "Google Map page");

        getAndroidDriver().context("NATIVE_APP");
        Thread.sleep(1000);
        getAndroidDriver().findElements(By.className("android.widget.ImageView")).forEach(el -> {
            System.out.println(el.getTagName());
            el.click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread.sleep(3000);

        getAndroidDriver().context("FLUTTER");
        finder.byToolTip("Back").click();
        Thread.sleep(2000);
        finder.byText("See the Google Map");
    }
}
