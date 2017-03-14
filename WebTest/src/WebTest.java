
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * As a user,
 * I would like to see reddit links in all sorts of ways,
 * So that I can know what is happening in the world
 * @author rpp25
 *
 */

public class WebTest {

	static WebDriver driver = new HtmlUnitDriver();
	
	// Start at the home page for each test
	@Before
	public void setUp() throws Exception {
		driver.get("https://cs1632ex.herokuapp.com/");
	}
	
	// Given that I am on the main page
	// The page shall display the text "Welcome, friend, to a land of pure calculation"
	// as well as "Used for CS1632 Software Quality Assurance, taught by Bill Laboon"
	@Test
	public void testHomeMessages() {
		
		// find the elements containing the messages
		// The page should include "Welcome, friend, to a land of pure calculation"
		// and "Used for CS1632 Software Quality Assurance, taught by Bill Laboon"
		String message1 = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/p")).getText();
		String message2 = driver.findElement(By.xpath("//div[contains(@class,'row')]/p")).getText();
		assertTrue((message1.contains("Welcome, friend,") && message1.contains("to a land of pure calculation")));
		assertTrue(message2.contains("Used for CS1632 Software Quality Assurance, taught by Bill Laboon"));
	}	

	@Test
	public void testHasCorrectHeaderLinks() {
		// Check for the 5 links - if any of
		// these are not found, fail the test
		try {
			driver.findElement(By.linkText("CS1632 D3 Home"));
			driver.findElement(By.linkText("Factorial"));
			driver.findElement(By.linkText("Fibonacci"));
			driver.findElement(By.linkText("Hello"));
			driver.findElement(By.linkText("Cathedral Pics"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}
	// Given that I am on the main page
	// When I click on the "Factorial" link
	// Then I should be redirected to the "Factorial" page
	@Test
	public void testFactLink() {
		
		// find the "factorial" link and click on it
		// The page you go to should include "factorial"
		// in the title
		driver.findElement(By.linkText("Factorial")).click();
		String str = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/h2")).getText();
		//System.out.println(str);
		assertTrue(str.contains("factorial"));
	}	
	
	// Given that I am on the main page
	// When I click on the "Fibonacci" link
	// Then I should be redirected to the "Factorial" page
	@Test
	public void testFibLink() {
		
		// find the "Fibonacci" link and click on it
		// The page you go to should include "Fibonacci"
		// in the title
		driver.findElement(By.linkText("Fibonacci")).click();
		String str = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/h2")).getText();
		//System.out.println(str);
		assertTrue(str.contains("Fibonacci"));
	}	
	
	// Given that I am on the main page
	// When I click on the "Hello" link
	// Then I should be redirected to the "Hello" page
	@Test
	public void testHelloLink() {
		
		// find the "Hello" link and click on it
		// The page you go to should include "Hello"
		// in the title
		driver.findElement(By.linkText("Hello")).click();
		String str = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/h2")).getText();
		//System.out.println(str);
		assertTrue(str.contains("Hello"));
	}	

	// Given that I am on the main page
	// When I click on the "Cathedral Pics" link
	// Then I should be redirected to the "Cathedral Pics" page
	@Test
	public void testCathyLink() {
		
		// find the "new" link and click on it
		// The page you go to should include "factorial"
		// in the title
		driver.findElement(By.linkText("Cathedral Pics")).click();
		String str = driver.findElement(By.xpath("//h2")).getText();
		//System.out.println(str);
		assertTrue(str.contains("Cathedral"));
	}	
		
		
	// Given that I am on the Factorial page
	// When I submit a number into the form
	// Then I should be redirected to a page that displays the factorial result
	@Test
	public void testFactWorks(){
		int test = 5;
		long fact = factorial(test);
		driver.get("https://cs1632ex.herokuapp.com/fact");
		WebElement submitButton = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/form/input[2]"));
		WebElement input = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/form/input[1]"));
		//System.out.println(submitButton.getAttribute("value"));
		input.sendKeys(Integer.toString(test));
		submitButton.click();
		//get results
		WebElement output = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/h2"));
		String result = output.getText();
		assertTrue(result.contains(Long.toString(fact)));
	}
	
	// Given that I am on the Fibonacci page
	// When I submit a number into the form
	// Then I should be redirected to a page that displays the Fibonacci result
	@Test
	public void testFibWorks(){
		int test = 5;
		int fib = fibonacci(test+1);
		driver.get("https://cs1632ex.herokuapp.com/fib");
		WebElement submitButton = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/form/input[2]"));
		WebElement input = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/form/input[1]"));
		input.sendKeys(Integer.toString(test));
		submitButton.click();
		//get results
		WebElement output = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/h2"));
		String result = output.getText();
		assertTrue(result.contains(Long.toString(fib)));
	}

	// Given that I am on the Factorial page
	// When I submit a negative number into the form
	// Then I should be redirected to a page that displays the factorial result as 1
	@Test
	public void testFactNegative(){
		int test = -1;
		//long fact = factorial(test);
		driver.get("https://cs1632ex.herokuapp.com/fact");
		WebElement submitButton = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/form/input[2]"));
		WebElement input = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/form/input[1]"));
		//System.out.println(submitButton.getAttribute("value"));
		input.sendKeys(Integer.toString(test));
		submitButton.click();
		//get results
		WebElement output = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/h2"));
		String result = output.getText();
		assertTrue(result.contains("is 1!"));
	}
	
	// Given that I am on the Fibonacci page
	// When I submit a negative number into the form
	// Then I should be redirected to a page that displays the Fibonacci result as 1
	@Test
	public void testFibNegative(){
		int test = -1;
		//int fib = fibonacci(test+1);
		driver.get("https://cs1632ex.herokuapp.com/fib");
		WebElement submitButton = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/form/input[2]"));
		WebElement input = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/form/input[1]"));
		input.sendKeys(Integer.toString(test));
		submitButton.click();
		//get results
		WebElement output = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/h2"));
		String result = output.getText();
		assertTrue(result.contains("is 1!"));
	}
	
	// Given that I am on the Factorial page
	// When I submit a letter into the form
	// Then I should be redirected to a page that displays the factorial result as 1
	@Test
	public void testFactInvalid(){
		try {
			driver.get("https://cs1632ex.herokuapp.com/fact");
			WebElement submitButton = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/form/input[2]"));
			WebElement input = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/form/input[1]"));
			//System.out.println(submitButton.getAttribute("value"));
			input.sendKeys("f");
			submitButton.click();
			//get results
			WebElement output = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/h2"));
			String result = output.getText();
			assertTrue(result.contains("is 1!"));
		} catch (Exception e) {
			fail();
		}
	}
	
	// Given that I am on the Fibonacci page
	// When I submit a letter into the form
	// Then I should be redirected to a page that displays the Fibonacci result as 1
	@Test
	public void testFibInvalid(){
		try {
			driver.get("https://cs1632ex.herokuapp.com/fact");
			WebElement submitButton = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/form/input[2]"));
			WebElement input = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/form/input[1]"));
			//System.out.println(submitButton.getAttribute("value"));
			input.sendKeys("f");
			submitButton.click();
			//get results
			WebElement output = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/h2"));
			String result = output.getText();
			assertTrue(result.contains("is 1!"));
		} catch (Exception e) {
			fail();
		}
	}
	
	// Given that I am on the "Hello" page without trailing values in the URL
	// The message on the page should read "Hello CS1632, from Prof. Laboon!"
	@Test
	public void testHelloWithoutTrail() {
		
		// start on the "Hello" page
		driver.get("https://cs1632ex.herokuapp.com/hello");
		// The message on the page hould include "Hello CS1632, from Prof. Laboon!
		String str = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/h2")).getText();
		//System.out.println(str);
		assertTrue(str.contains("Hello CS1632, from Prof. Laboon!"));
	}	
	
	// Given that I am on the "Hello" page with trailing values in the URL
	// The message on the page should read "Hello CS1632, from" whatever the value is
	@Test
	public void testHelloWithTrail() {
		String test = "the Batman";
		test = test.replaceAll(" ", "%20");
		// start on the "Hello" page
		driver.get("https://cs1632ex.herokuapp.com/hello/"+test);
		// The message on the page should include "Hello CS1632, from the Batman!"
		String str = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/h2")).getText();
		assertTrue(str.contains("Hello CS1632, from the Batman!"));
	}	
	
	// Given that I am on the Cathedral Pics page
	// The page should display three pictures of the cathedral
	@Test
	public void testHasCathyPics() {
		driver.get("https://cs1632ex.herokuapp.com/cathy");
		try {
			String pic1 = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/ol/li[1]/img")).getAttribute("alt");
			String pic2 = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/ol/li[2]/img")).getAttribute("alt");
			String pic3 = driver.findElement(By.xpath("//div[contains(@class,'jumbotron')]/ol/li[3]/img")).getAttribute("alt");
			assertTrue(pic1.contains("Cathedral"));
			assertTrue(pic2.contains("Cathedral"));
			assertTrue(pic3.contains("Cathedral"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}

	public int fibonacci(int n)  {
	    if(n == 0){
	        return 0;
	    }
	    else if(n == 1){
	      return 1;
	    }
	    else {
	      return fibonacci(n - 1) + fibonacci(n - 2);
	    }
	}
	
	public long factorial(int n) {
	      if (n <= 1){
	    	  return 1;
	      }
	      else{
	    	  return n * factorial(n - 1);
	      }
	   }


}