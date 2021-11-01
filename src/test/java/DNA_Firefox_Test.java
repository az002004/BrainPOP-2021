import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class DNA_Firefox_Test {

    WebDriver driver;

    @BeforeSuite
    public void setUpFirefox(){
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @AfterSuite
    public void closeBrowser(){
        driver.quit();
    }

    String dnaPageUrl = "https://www.brainpop.com/health/geneticsgrowthanddevelopment/dna/";
    String playButtonId = "play";
    String pauseButtonId = "pause";
    String topicsResultsNumberXpath = "//*[@class='topics_results_number']";
    String topicsResultsListXpath = "//*[@id='topic_list_results_container']//*[contains(@id, 'topic_result_')]";


    @Test
    public void testVideoIsWorking(){

        driver.get(dnaPageUrl);

        Common.waitForElement(driver, By.id(playButtonId), 20);
        driver.findElement(By.id(playButtonId)).click();

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Common.waitForElement(driver, By.id(pauseButtonId), 30);
        driver.findElement(By.id(pauseButtonId)).click();

        WebElement videoSlider = driver.findElement(By.xpath("//*[@class='playing']"));
        String videoSliderValue = videoSlider.getAttribute("value");
        double videoSliderValueDouble = Double.parseDouble(videoSliderValue);

        boolean videoWorking = false;
        if (videoSliderValueDouble > 1.0 && videoSliderValueDouble < 8.0){
            videoWorking = true;
        }

        Assert.assertTrue(videoWorking);
    }

    @Ignore
    @Test
    public void testVideoEndScreenIsReached(){

        String endScreenXpath = "//span[contains(text(), 'Replay the movie')]";

        driver.get(dnaPageUrl);

        Common.waitForElement(driver, By.id(playButtonId), 20);
        driver.findElement(By.id(playButtonId)).click();

        try {
            Thread.sleep(375000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Common.waitForElement(driver, By.xpath(endScreenXpath), 20);
        WebElement endScreen = driver.findElement(By.xpath(endScreenXpath));

        Assert.assertTrue(endScreen.isDisplayed());
    }

    @Test
    public void testCaptionButton() {

        driver.get(dnaPageUrl);
        String captionButtonXpath = "//*[@data-icon='closed-captioning']";
        String captionOnScreenXpath = "//div[@data-kind='captions']";

        Common.waitForElement(driver, By.id(playButtonId), 20);
        driver.findElement(By.id(playButtonId)).click();

        Common.waitForElement(driver, By.xpath(captionButtonXpath), 20);
        WebElement captionButton = driver.findElement(By.xpath(captionButtonXpath));

        captionButton.click();

        Common.waitForElement(driver, By.xpath(captionOnScreenXpath), 30);
        WebElement captionOnScreen = driver.findElement(By.xpath(captionOnScreenXpath));

        boolean captionOnScreenIsPresent = captionOnScreen.isDisplayed();

        Assert.assertTrue(captionOnScreenIsPresent);
    }

    @Test
    public void testNumberOfTopicsFound() {

        driver.get("https://www.brainpop.com/search/?keyword=Challenge");

        Common.waitForElement(driver, By.xpath(topicsResultsListXpath), 20);
        List<WebElement> topicList = driver.findElements(By.xpath(topicsResultsListXpath));

        Common.waitForElement(driver, By.xpath(topicsResultsNumberXpath), 20);
        String resultsFound = driver.findElement(By.xpath(topicsResultsNumberXpath)).getText();
        int resultsFoundInt = Integer.parseInt(resultsFound);

        Assert.assertEquals(topicList.size(), resultsFoundInt);
    }

    @Test
    public void testRelatedReading() {

        driver.get(dnaPageUrl);
        String relatedReadingId = "feature_related_reading";
        String relatedReadingUrl = "https://www.brainpop.com/health/geneticsgrowthanddevelopment/dna/relatedreading/";

        Common.waitForElement(driver, By.id(relatedReadingId), 20);
        WebElement relatedReadingFeature = driver.findElement(By.id(relatedReadingId));

        relatedReadingFeature.click();

        Assert.assertEquals(driver.getCurrentUrl(), relatedReadingUrl);
    }

    @Test
    public void testFeatureSelection() {

        driver.get(dnaPageUrl);
        String featureId = "feature_quiz";
        String featureUrl = "https://www.brainpop.com/health/geneticsgrowthanddevelopment/dna/quiz/";

        Common.waitForElement(driver, By.id(featureId), 20);
        WebElement feature = driver.findElement(By.id(featureId));

        feature.click();

        Assert.assertEquals(driver.getCurrentUrl(), featureUrl);
    }

}
