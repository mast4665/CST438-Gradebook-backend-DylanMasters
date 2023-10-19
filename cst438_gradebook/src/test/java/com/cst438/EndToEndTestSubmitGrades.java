package com.cst438;

import static org.assertj.core.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * This example shows how to use selenium testing using the web driver 
 * with Chrome browser.
 * 
 *  - Buttons, input, and anchor elements are located using XPATH expression.
 *  - onClick( ) method is used with buttons and anchor tags.
 *  - Input fields are located and sendKeys( ) method is used to enter test data.
 *  - Spring Boot JPA is used to initialize, verify and reset the database before
 *      and after testing.
 *      
 *  In SpringBootTest environment, the test program may use Spring repositories to 
 *  setup the database for the test and to verify the result.
 */

@SpringBootTest
public class EndToEndTestSubmitGrades {

	public static final String CHROME_DRIVER_FILE_LOCATION = "C:/chromedriver_win32/chromedriver.exe";

	public static final String URL = "http://localhost:3000";
	public static final int SLEEP_DURATION = 1000; // 1 second.
	public static final String TEST_ASSIGNMENT_NAME = "db design";
	public static final String NEW_GRADE = "99";
	

	@Test
	public void addAssignmentTest() throws Exception {
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();
        driver.get(URL);
        Thread.sleep(SLEEP_DURATION);

        try {
            // Perform steps to add a new assignment
            // Locate and click the button to add an assignment
            WebElement addAssignmentButton = driver.findElement(By.id("add-assignment-button"));
            addAssignmentButton.click();
            Thread.sleep(SLEEP_DURATION);

            // Fill out the assignment details in the form (e.g., name, due date)
            WebElement nameInput = driver.findElement(By.id("assignment-name-input"));
            WebElement dueDateInput = driver.findElement(By.id("due-date-input"));

            nameInput.sendKeys("New Assignment");
            dueDateInput.sendKeys("2023-12-31");

            // Submit the form
            WebElement submitButton = driver.findElement(By.id("submit-assignment-button"));
            submitButton.click();
            Thread.sleep(SLEEP_DURATION);

            // Verify that the new assignment is displayed on the page
            WebElement newAssignmentElement = driver.findElement(By.xpath("//td[contains(text(),'New Assignment')]"));
            assertThat(newAssignmentElement).isNotNull();

        } finally {
            driver.quit();
        }
	}

	@Test
	public void updateAssignmentTest() throws Exception {
        // Initialize the WebDriver and navigate to the assignment you want to update
        // This test assumes that an assignment with a specific name is already present on the page
        // You should adapt this to your specific scenario
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();
        driver.get(URL);
        Thread.sleep(SLEEP_DURATION);

        try {
            // Locate and click the assignment you want to update (e.g., based on assignment name)
            WebElement assignmentToUpdate = driver.findElement(By.xpath("//td[contains(text(),'Assignment Name')]"));
            assignmentToUpdate.click();
            Thread.sleep(SLEEP_DURATION);

            // Update the assignment details in the form (e.g., name, due date)
            WebElement nameInput = driver.findElement(By.id("assignment-name-input"));
            WebElement dueDateInput = driver.findElement(By.id("due-date-input"));

            nameInput.clear();
            nameInput.sendKeys("Updated Assignment Name");
            dueDateInput.clear();
            dueDateInput.sendKeys("2023-12-31");

            // Submit the form
            WebElement submitButton = driver.findElement(By.id("submit-assignment-button"));
            submitButton.click();
            Thread.sleep(SLEEP_DURATION);

            // Verify that the assignment is updated
            WebElement updatedAssignmentElement = driver.findElement(By.xpath("//td[contains(text(),'Updated Assignment Name')]"));
            assertThat(updatedAssignmentElement).isNotNull();

        } finally {
            driver.quit();
        }
	}

	@Test
	public void deleteAssignmentTest() throws Exception {
		// Initialize the WebDriver and navigate to the assignment you want to delete
        // This test assumes that an assignment with a specific name is already present on the page
        // You should adapt this to your specific scenario
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        WebDriver driver = new ChromeDriver();
        driver.get(URL);
        Thread.sleep(SLEEP_DURATION);

        try {
            // Locate and click the assignment you want to delete (e.g., based on assignment name)
            WebElement assignmentToDelete = driver.findElement(By.xpath("//td[contains(text(),'Assignment Name')]"));
            assignmentToDelete.click();
            Thread.sleep(SLEEP_DURATION);

            // Locate and click the button to delete the assignment
            WebElement deleteButton = driver.findElement(By.id("delete-assignment-button"));
            deleteButton.click();
            Thread.sleep(SLEEP_DURATION);

            // Verify that the assignment is deleted
            boolean isAssignmentPresent = driver.findElements(By.xpath("//td[contains(text(),'Assignment Name')]")).isEmpty();
            assertThat(isAssignmentPresent).isTrue();

        } finally {
            driver.quit();
        }
	}

	@Test
	public void addCourseTest() throws Exception {



		// set the driver location and start driver
		//@formatter:off
		// browser	property name 				Java Driver Class
		// edge 	webdriver.edge.driver 		EdgeDriver
		// FireFox 	webdriver.firefox.driver 	FirefoxDriver
		// IE 		webdriver.ie.driver 		InternetExplorerDriver
		//@formatter:on
		
		/*
		 * initialize the WebDriver and get the home page. 
		 */

		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
		WebDriver driver = new ChromeDriver();
		// Puts an Implicit wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get(URL);
		Thread.sleep(SLEEP_DURATION);
		
		WebElement w;
		

		try {
			/*
			* locate the <td> element for assignment title 'db design'
			* 
			*/
			
			List<WebElement> elements  = driver.findElements(By.xpath("//td"));
			boolean found = false;
			for (WebElement we : elements) {
				if (we.getText().equals(TEST_ASSIGNMENT_NAME)) {
					found=true;
					we.findElement(By.xpath("..//a")).click();
					break;
				}
			}
			assertThat(found).withFailMessage("The test assignment was not found.").isTrue();

			/*
			 *  Locate and click Grade button to indicate to grade this assignment.
			 */
			
			Thread.sleep(SLEEP_DURATION);

			/*
			 *  enter grades for all students, then click save.
			 */
			ArrayList<String> originalGrades = new ArrayList<>();
			elements  = driver.findElements(By.xpath("//input"));
			for (WebElement element : elements) {
				originalGrades.add(element.getAttribute("value"));
				element.clear();
				element.sendKeys(NEW_GRADE);
				Thread.sleep(SLEEP_DURATION);
			}
			
			for (String s : originalGrades) {
				System.out.println("'"+s+"'");
			}

			/*
			 *  Locate submit button and click
			 */
			driver.findElement(By.id("sgrade")).click();
			Thread.sleep(SLEEP_DURATION);
			
			w = driver.findElement(By.id("gmessage"));
			assertThat(w.getText()).withFailMessage("After saving grades, message should be \"Grades saved.\"").startsWith("Grades saved");
			
			driver.navigate().back();  // back button to last page
			Thread.sleep(SLEEP_DURATION);
			
			// find the assignment 'db design' again.
			elements  = driver.findElements(By.xpath("//td"));
			found = false;
			for (WebElement we : elements) {
				if (we.getText().equals(TEST_ASSIGNMENT_NAME)) {
					found=true;
					we.findElement(By.xpath("..//a")).click();
					break;
				}
			}
			Thread.sleep(SLEEP_DURATION);
			assertThat(found).withFailMessage("The test assignment was not found.").isTrue();
			
			// verify the grades. Change grades back to original values

			elements  = driver.findElements(By.xpath("//input"));
			for (int idx=0; idx < elements.size(); idx++) {
				WebElement element = elements.get(idx);
				assertThat(element.getAttribute("value")).withFailMessage("Incorrect grade value.").isEqualTo(NEW_GRADE);
				
				// clear the input value by backspacing over the value
				while(!element.getAttribute("value").equals("")){
			        element.sendKeys(Keys.BACK_SPACE);
			    }
				if (!originalGrades.get(idx).equals("")) element.sendKeys(originalGrades.get(idx));
				Thread.sleep(SLEEP_DURATION);
			}
			driver.findElement(By.id("sgrade")).click();
			Thread.sleep(SLEEP_DURATION);
			
			w = driver.findElement(By.id("gmessage"));
			assertThat(w.getText()).withFailMessage("After saving grades, message should be \"Grades saved.\"").startsWith("Grades saved");


		} catch (Exception ex) {
			throw ex;
		} finally {

			driver.quit();
		}

	}
}
