package tests;

import com.denysord88.appium.flutter.FlutterFinder;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static com.denysord88.testng.TestEventsListener.getiOSDriver;
import static org.testng.Assert.assertEquals;

public class MyFirstTestiOS {
    @Test(groups = ("ios"))
    public void accessTest() throws InterruptedException {
        FlutterFinder finder = new FlutterFinder(getiOSDriver());

        finder.byText("Check access").click();
        assertEquals(finder.byValueKey("_accessHeaderTitle").getText(), "Access");

        assertEquals(finder.byValueKey("_locationAccessStatusText").getText(), "press check");
        finder.byValueKey("_checkLocationAccessButton").click();
        assertEquals(finder.byValueKey("_locationAccessStatusText").getText(), "Restricted");
        finder.byValueKey("_requestLocationAccessButton").click();
        Thread.sleep(2000);
        getiOSDriver().context("NATIVE_APP");
        getiOSDriver().findElement(By.xpath("//*[@label='Allow While Using App']")).click();
        getiOSDriver().context("FLUTTER");
        finder.byValueKey("_checkLocationAccessButton").click();
        Thread.sleep(1000);
        assertEquals(finder.byValueKey("_locationAccessStatusText").getText(), "Granted");

        assertEquals(finder.byValueKey("_photosAccessStatusText").getText(), "press check");
        finder.byValueKey("_checkPhotosAccessButton").click();
        assertEquals(finder.byValueKey("_photosAccessStatusText").getText(), "Restricted");
        finder.byValueKey("_requestPhotosAccessButton").click();
        Thread.sleep(2000);
        getiOSDriver().context("NATIVE_APP");
        getiOSDriver().findElement(By.xpath("//*[@label='Allow Access to All Photos']")).click();
        getiOSDriver().context("FLUTTER");
        finder.byValueKey("_checkPhotosAccessButton").click();
        Thread.sleep(1000);
        assertEquals(finder.byValueKey("_photosAccessStatusText").getText(), "Granted");
        getiOSDriver().context("NATIVE_APP");
        getiOSDriver().findElement(By.xpath("//*[@label='Back']")).click();
        getiOSDriver().context("FLUTTER");
        Thread.sleep(1000);
        finder.byText("See the Google Map");
    }
}
