import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class URLChecker {
	public static final String CAROUSEL_CLASS = "carousel-list";
	public static final int index = 1;
	public static final String expectedCode = "1069956";
    
	public static void main(String[] args) {
		
        // Create a new instance of the html unit driver
        WebDriver driver = new HtmlUnitDriver();

        // And now use this to visit Google
        driver.get("http://www.viki.com");

        // Find the text input element by its name
        WebElement element = driver.findElement(By.className(CAROUSEL_CLASS));

        // Get the element we are looking for
        WebElement processedCarouselItem = element.findElement(By.xpath("li["+index+"]"));

        WebElement playNowButton = processedCarouselItem.findElement(By.className("vkal-track btn btn-firm btn-carousel-cta mbm"));
        
        //Click the button
        playNowButton.click();
        
        //Assert that the url is having the code we are looking
        String url = driver.getCurrentUrl();
        System.out.println(url);
        Assert.assertTrue("The Url doesn't contain expected code",url.contains(expectedCode));

        driver.quit();
    }
}
