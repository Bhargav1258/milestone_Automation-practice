package pages;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AutomationPracticePage {

    WebDriver driver;
    public void enterFieldWithAutoScroll(By locator, String value) {
        WebElement element = driver.findElement(locator);
        
        // 🚀 The JavaScript Command to scroll up or down automatically right to the element
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        
        // Brief pause to let the smooth scrolling complete before typing
        try { Thread.sleep(300); } catch (Exception e) {}
        
        element.clear();
        element.sendKeys(value);
    }

    public AutomationPracticePage(WebDriver driver) {
        this.driver = driver;
    }
    

    // ==================================
    // UNIT 1 - GUI ELEMENTS
    // ==================================

    By txtName = By.id("name");
    By txtEmail = By.id("email");
    By txtPhone = By.id("phone");
    By txtAddress = By.id("textarea");

    By maleRadio = By.id("male");
    By femaleRadio = By.id("female");

    By sunday = By.id("sunday");
    By monday = By.id("monday");
    By tuesday = By.id("tuesday");

    By countryDropdown = By.id("country");
    By colorDropdown = By.id("colors");
    By animalDropdown = By.id("animals");

    // ==================================
    // UNIT 2 - DATE PICKERS
    // ==================================

    By datePicker1 = By.id("datepicker");

    By startDate = By.id("start-date");
    By endDate = By.id("end-date");

    By submitDateBtn =
            By.xpath("//button[text()='Submit']");

    // ==================================
    // UNIT 3 - FILE UPLOAD
    // ==================================

    By singleFile =
            By.id("singleFileInput");

    By multipleFiles =
            By.id("multipleFilesInput");

    // ==================================
    // UNIT 1 METHODS
    // ==================================

    public void enterName(String name) {
        driver.findElement(txtName).clear();
        driver.findElement(txtName).sendKeys(name);
    }

    public void enterEmail(String email) {
        driver.findElement(txtEmail).clear();
        driver.findElement(txtEmail).sendKeys(email);
    }

    public void enterPhone(String phone) {
        driver.findElement(txtPhone).clear();
        driver.findElement(txtPhone).sendKeys(phone);
    }

    public void enterAddress(String address) {
        driver.findElement(txtAddress).clear();
        driver.findElement(txtAddress).sendKeys(address);
    }

    public void selectGender(String gender) {

        if(gender.equalsIgnoreCase("Male"))
            driver.findElement(maleRadio).click();
        else
            driver.findElement(femaleRadio).click();
    }

    public void selectDays() {

        driver.findElement(sunday).click();
        driver.findElement(monday).click();
        driver.findElement(tuesday).click();
    }

    public void selectCountry(String country) {

        Select select =
                new Select(driver.findElement(countryDropdown));

        select.selectByVisibleText(country);
    }

    public void selectColor(String color) {

        Select select =
                new Select(driver.findElement(colorDropdown));

        select.selectByVisibleText(color);
    }

    public void selectAnimal(String animal) {

        Select select =
                new Select(driver.findElement(animalDropdown));

        select.selectByVisibleText(animal);
    }

    // ==================================
    // UNIT 2 METHODS
    // ==================================

    public void selectDatePicker1(String date) {

        driver.findElement(datePicker1).clear();

        driver.findElement(datePicker1).sendKeys(date);

        // close calendar popup
        driver.findElement(By.tagName("body")).click();
    }

    public void selectDatePicker2(String date) {

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "document.getElementById('txtDate').value='" +
                date +
                "';");
    }

    public void selectDateRange(
            String start,
            String end) {

        driver.findElement(startDate).clear();
        driver.findElement(startDate).sendKeys(start);

        driver.findElement(endDate).clear();
        driver.findElement(endDate).sendKeys(end);
    }

    public void submitDateRange() {

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].click();",
                driver.findElement(submitDateBtn));
    }
    // ==================================
    // UNIT 3 METHODS
    // ==================================

    public void uploadSingleFile(String path) {

        driver.findElement(singleFile)
                .sendKeys(path);
    }

    public void uploadMultipleFiles(
            String file1,
            String file2) {

        driver.findElement(multipleFiles)
                .sendKeys(file1 + "\n" + file2);
    }
 // ==================================
 // UNIT 4 - PAGINATION WEB TABLE
 // ==================================

    By pages =
            By.xpath("//ul[@id='pagination']/li");

   By tableRows =
            By.xpath("//table[@id='productTable']/tbody/tr");

   public int getTotalPages() {

       return driver.findElements(pages).size();
   }

   public void selectAllProductsInAllPages() throws InterruptedException {

       List<WebElement> totalPages =
               driver.findElements(pages);

       int pageCount = totalPages.size();

       JavascriptExecutor js =
               (JavascriptExecutor) driver;

       for(int p=1; p<=pageCount; p++) {

           if(p>1) {

               driver.findElement(
               By.xpath("//ul[@id='pagination']/li["+p+"]"))
               .click();

           
           }

           List<WebElement> rows =
                   driver.findElements(tableRows);

           for(int r=1; r<=rows.size(); r++) {

               String product =
               driver.findElement(
               By.xpath("//table[@id='productTable']/tbody/tr["+r+"]/td[2]"))
               .getText();

               String price =
               driver.findElement(
               By.xpath("//table[@id='productTable']/tbody/tr["+r+"]/td[3]"))
               .getText();

               System.out.println(product +
                       " --> " + price);

               Thread.sleep(500); // wait before selecting checkbox

               WebElement checkbox =
               driver.findElement(
               By.xpath("//table[@id='productTable']/tbody/tr["+r+"]/td[4]/input"));

               if(!checkbox.isSelected()) {

                   js.executeScript(
                       "arguments[0].scrollIntoView(true);",
                       checkbox);

                   Thread.sleep(500); // wait after scroll

                   js.executeScript(
                       "arguments[0].click();",
                       checkbox);

               }
           }

           Thread.sleep(1000); // wait before next page
       }
   }
	// ==================================
	// UNIT 5 - FORM
	// ==================================

	By section1Textbox =
	        By.xpath("//input[@id='input1']");

	By section1Submit =
	        By.xpath("//button[@id='btn1']");

	By section2Textbox =
	        By.xpath("//input[@id='input2']");

	By section2Submit =
	        By.xpath("//button[@id='btn2']");

	By section3Textbox =
	        By.xpath("//input[@id='input3']");

	By section3Submit =
	        By.xpath("//button[@id='btn3']");
	
	public void fillSection1(String text)
	        throws InterruptedException {

	    driver.findElement(section1Textbox)
	            .sendKeys(text);

	    Thread.sleep(1000);

	    driver.findElement(section1Submit)
	            .click();
	}

	public void fillSection2(String text)
	        throws InterruptedException {

	    driver.findElement(section2Textbox)
	            .sendKeys(text);

	    Thread.sleep(1000);

	    driver.findElement(section2Submit)
	            .click();
	}

	public void fillSection3(String text)
	        throws InterruptedException {

	    driver.findElement(section3Textbox)
	            .sendKeys(text);

	    Thread.sleep(1000);

	    driver.findElement(section3Submit)
	            .click();
	}
	// ==================================
	// UNIT 6 - LINKS
	// ==================================

	By allLinks = By.tagName("a");
	public int getTotalLinks() {

	    return driver.findElements(allLinks).size();
	}
	public void printValidLinks() {

	    int totalLinks =
	            driver.findElements(allLinks).size();

	    for(int i=0;i<totalLinks;i++) {

	        String url =
	        driver.findElements(allLinks)
	                .get(i)
	                .getAttribute("href");

	        if(url != null &&
	           !url.isEmpty()) {

	            System.out.println(url);
	        }
	    }
	}
	// ==================================
		// UNIT 7 - TABS & WIKIPEDIA
		// ==================================

		By wikiBox = By.id("Wikipedia1_wikipedia-search-input");
		By wikiBtn = By.className("wikipedia-search-button");
		By googleLink = By.xpath("//div[@id='Wikipedia1_wikipedia-search-results']//a[text()='Google']");
		By startBtn = By.xpath("//button[text()='START']");
		By stopBtn = By.xpath("//button[text()='STOP']");

		public void searchWikipediaAndNavigateTab() {
			
			// 1. Search for Google
			driver.findElement(wikiBox).sendKeys("Google");
			driver.findElement(wikiBtn).click();

			// Save the current window handle
			String currentTab = driver.getWindowHandle();

			// Wait for search results to load
			try { Thread.sleep(2000); } catch (Exception e) {}

			// 2. Click on the exact text link "Google"
			driver.findElement(googleLink).click();

			// 3. Switch to new tab, print URL, close it, and come back
			for (String handle : driver.getWindowHandles()) {
				if (!handle.equals(currentTab)) {
					driver.switchTo().window(handle);
					
					// Print the website URL as requested
					System.out.println("Opened Tab URL: " + driver.getCurrentUrl());
					
					driver.close();
					break;
				}
			}
			
			// Switch back to original page
			driver.switchTo().window(currentTab);
			
			driver.findElement(startBtn).click();
			System.out.println("Clicked START button successfully.");
			
			try { Thread.sleep(2000); } catch (Exception e) {}
			
			driver.findElement(stopBtn).click();
			System.out.println("Clicked STOP button successfully.");
		}
		// ==================================
		// UNIT 8 - ALERTS, POPUPS & WINDOWS
		// ==================================

		By alertButton = By.id("alertBtn");
		By confirmButton = By.id("confirmBtn");
		
		By promptButton = By.id("promptBtn");

		// Fixed Locators
		By tabButton = By.xpath("//button[text()='New Tab']");
		By popUpWindowButton = By.id("PopUp");
		public void executeUnit8_AlertsAndPopups() throws InterruptedException {
		    String parentWindow = driver.getWindowHandle();
		    JavascriptExecutor js = (JavascriptExecutor) driver;

		    // ---- Part 1: Alerts ----
		    driver.findElement(alertButton).click();
		    Thread.sleep(1000);
		    
		    driver.switchTo().alert().accept();

		    driver.findElement(confirmButton).click();
		    Thread.sleep(1000);
		    driver.switchTo().alert().dismiss();

		    driver.findElement(promptButton).click();
		    Thread.sleep(3000);
		    driver.switchTo().alert().sendKeys("Bhargav Automation");
		    Thread.sleep(1000);
		    driver.switchTo().alert().accept();

		    // ---- Part 2: New Tab ----
		    js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(tabButton));
		    driver.findElement(tabButton).click();
		    Thread.sleep(2000);

		    for (String handle : driver.getWindowHandles()) {
		        if (!handle.equals(parentWindow)) {
		            driver.switchTo().window(handle);
		            System.out.println("New Tab URL: " + driver.getCurrentUrl());
		            driver.close();
		            break;
		        }
		    }
		    driver.switchTo().window(parentWindow);
		    // ---- Part 3: Popup Window ----
			
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		    WebElement popupBtn = wait.until(ExpectedConditions.elementToBeClickable(popUpWindowButton));
		    popupBtn.click();
		    
		    Thread.sleep(2000);

		    Set<String> allWindows = driver.getWindowHandles();
		    for (String handle : allWindows) {
		        if (!handle.equals(parentWindow)) {
		            driver.switchTo().window(handle);
		            System.out.println("Popup Window URL: " + driver.getCurrentUrl());
		            driver.close(); // Close popup window
		            break;
		        }
		    }
		    driver.switchTo().window(parentWindow);
		    System.out.println("Popup Window handled successfully, returned to parent.");
		
		}
		// ==================================
		// UNIT 9 - ADVANCED MOUSE INTERACTIONS
		// ==================================

		// Existing Locators
		By field1Box = By.id("field1");
		By copyTextBtn = By.xpath("//button[text()='Copy Text']");
		By dragSource = By.id("draggable");
		By dropTarget = By.id("droppable");
		
		// FIXED LOCATORS: Exact structural matches for the Hover and Slider components
		By pointMeBtn = By.className("dropbtn");
		By subMenuItemLaptops = By.xpath("//div[@class='dropdown-content']//a[text()='Laptops']");
		By sliderHandle = By.cssSelector("#slider span.ui-slider-handle");

		public void executeUnit9_AdvancedMouseActions() {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);

			try {
				// ---- 1. FIXED: POINT ME (Mouse Hover Action) ----
				js.executeScript("arguments[0].scrollIntoView({block: 'center'});", driver.findElement(pointMeBtn));
				Thread.sleep(1000); // Critical delay to clear previous drag focus layer
				
				// Move to the element, wait a second to let the layout render, then move and click the target link
				actions.moveToElement(driver.findElement(pointMeBtn)).perform();
				System.out.println("Hovered over Point Me button successfully.");
				Thread.sleep(1000); 
				
				driver.findElement(subMenuItemLaptops).click();
				System.out.println("Clicked sub-menu item 'Laptops' successfully.");
				Thread.sleep(1000);

				// ---- 2. COPY TEXT (Double Click Action) ----
				js.executeScript("arguments[0].scrollIntoView({block: 'center'});", driver.findElement(copyTextBtn));
				Thread.sleep(800);
				
				driver.findElement(field1Box).clear();
				driver.findElement(field1Box).sendKeys("Bhargav Automation");
				
				actions.doubleClick(driver.findElement(copyTextBtn)).perform();
				System.out.println("Double Clicked 'Copy Text' successfully.");
				Thread.sleep(1000);

				// ---- 3. DRAG AND DROP ACTION ----
				js.executeScript("arguments[0].scrollIntoView({block: 'center'});", driver.findElement(dropTarget));
				Thread.sleep(800);
				
				actions.dragAndDrop(driver.findElement(dragSource), driver.findElement(dropTarget)).perform();
				System.out.println("Drag and Drop operation completed successfully.");
				Thread.sleep(1000);
				
				

				// ---- 5. SLIDER CONTROL ACTION (PHYSICAL DRAG FROM LEFT TO RIGHT) ----
				// TARGET: The actual handle knob element (span) right inside the slider container
				WebElement sr1 = driver.findElement(By.xpath("(//span[@class=\"ui-slider-handle ui-corner-all ui-state-default\"])[1]"));
				
				// Scroll to the slider handle to ensure viewport focus
				js.executeScript("arguments[0].scrollIntoView({block: 'center'});", sr1);
				Thread.sleep(1000);
				
				org.openqa.selenium.interactions.Actions ac3 = new org.openqa.selenium.interactions.Actions(driver);
				ac3.dragAndDropBy(sr1, 50, 0).perform(); // move right
				
				System.out.println("Slider handle dragged 50px right successfully.");
				Thread.sleep(1000);
			
				
				// ---- 6. SCROLLING DROPDOWN SELECTION (comboBox) ----
				WebElement comboBoxElement = driver.findElement(By.id("comboBox")); 
				
				// Scroll to the local combobox element safely
				js.executeScript("arguments[0].scrollIntoView({block: 'center'});", comboBoxElement);
				Thread.sleep(1000);
				
				// Click to expand the layout list open
				comboBoxElement.click();
				Thread.sleep(500);
				
				// Target the specific option element by class and text ("Item 1")
				WebElement targetOption = driver.findElement(By.xpath("//div[@class='option' and text()='Item 1']"));
				
				// Scroll slightly inside the container to bring it cleanly into view before clicking
				js.executeScript("arguments[0].scrollIntoView({block: 'nearest'});", targetOption);
				Thread.sleep(500);
				
				// Click the item to select it
				targetOption.click();
				System.out.println("ComboBox option 'Item 1' selected successfully using class properties.");
				Thread.sleep(1000);} 
			catch (Exception e) {
				System.out.println("Advanced Mouse operations exception: " + e.getMessage());
			}
		}
		 // ==================================
	    // UNIT 10 - LINK TRAVERSAL VALIDATION
	    // ==================================

		public void executeUnit10_LinkTraversal() {
		    JavascriptExecutor js = (JavascriptExecutor) driver;
		    
		    // Capture the exact main dashboard window handle layout and base homepage URL right now
		    String mainWindowHandle = driver.getWindowHandle();
		    String basePageUrl = driver.getCurrentUrl();

		    String[] targetLinks = {
		        "Apple", "Lenovo", "Dell", 
		        "Errorcode 400", "Errorcode 401", "Errorcode 403", 
		        "Errorcode 404", "Errorcode 408", "Errorcode 500", 
		        "Errorcode 502", "Errorcode 503"
		    };

		    System.out.println("🚀 Processing " + targetLinks.length + " links dynamically via Page Load Waits...");

		    // Set up a clean 15-second explicit wait engine
		    org.openqa.selenium.support.ui.WebDriverWait wait = 
		        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15));

		    for (String linkText : targetLinks) {
		        try {
		            // 1. Ensure the driver context is explicitly focused back onto the main window layout
		            driver.switchTo().window(mainWindowHandle);

		            // 🚀 STRATEGY FIX: If we are not on the base homepage, force-return to it immediately
		            if (!driver.getCurrentUrl().equals(basePageUrl)) {
		                driver.get(basePageUrl);
		                wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
		            }

		            // 2. 🚀 XPATH STRATEGY: Locate using a normalized text locator to counter DOM differences
		            By linkLocator = By.xpath("//a[normalize-space()='" + linkText + "']");
		            
		            // Wait explicitly until the link is present and visible
		            WebElement currentLink = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(linkLocator));
		            
		            // 3. Scroll it into layout view smoothly
		            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", currentLink);
		            Thread.sleep(500); // Give scroll physics a split second to settle

		            // 4. 🚀 CLICKABILITY GUARD: Wait explicitly until the element is truly ready to receive clicks
		            wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(currentLink));
		            currentLink.click();

		            // 5. THE LOAD WAIT FIX: Wait explicitly for the target page to finish loading its assets
		            wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
		            
		            // 🌟 PRINT STATEMENT: Instantly confirms the link opened successfully
		            System.out.println("🔗 link opened: " + linkText);
		            Thread.sleep(1000); // Keep open for 1 second so you can see it visually load

		            // 6. Handle Isolation & Return Context
		            java.util.Set<String> allWindows = driver.getWindowHandles();
		            if (allWindows.size() > 1) {
		                // Case A: If it opened in a fresh tab, destroy it and switch back
		                for (String windowHandle : allWindows) {
		                    if (!windowHandle.equals(mainWindowHandle)) {
		                        driver.switchTo().window(windowHandle);
		                        driver.close(); 
		                    }
		                }
		                driver.switchTo().window(mainWindowHandle);
		            } else {
		                // Case B: If it opened inside the same tab frame layout, go back
		                driver.navigate().back();
		                
		                // Wait until the dashboard returns and settles completely
		                wait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlToBe(basePageUrl));
		                wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
		                Thread.sleep(500); // Stabilization cushion
		            }

		        } catch (Exception e) {
		            System.out.println("⚠️ Skipped or timed out processing link: [" + linkText + "]. Recovering page state...");
		            try {
		                // Emergency recovery fallback path to get back home
		                driver.switchTo().window(mainWindowHandle);
		                driver.get(basePageUrl); 
		                wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
		            } catch (Exception windowEx) {
		                // Fallback catch block
		            }
		        }
		    }
		    System.out.println("=== UNIT 10 COMPLETE: ALL LINKS TRAVERSED SILENTLY ===");
		}
		
		// ==================================
		// UNIT 11 - SHADOW DOM
		// ==================================

	
		public void executeUnit11_ShadowDOM() throws InterruptedException {

		    JavascriptExecutor js =(JavascriptExecutor) driver;

		    // Scroll to Shadow DOM section
		    WebElement shadowHost = driver.findElement(By.id("shadow_host"));

		    js.executeScript( "arguments[0].scrollIntoView({block:'center'});", shadowHost);

		    Thread.sleep(2000);

		    SearchContext shadowRoot = shadowHost.getShadowRoot();

		    System.out.println("===== UNIT 11 =====");

		    // Print Mobile & Laptop
		    String shadowText = shadowRoot.findElement(By.cssSelector(".info")).getText();

		    System.out.println(shadowText);

		    Thread.sleep(1000);

		    // Blog Link
		    WebElement blogLink = shadowRoot.findElement(By.linkText("Blog"));

		    String blogUrl =blogLink.getAttribute("href");

		    String mainWindow = driver.getWindowHandle();

		    // Open Blog in new tab using JS
		    js.executeScript( "window.open(arguments[0]);", blogUrl);

		    Thread.sleep(3000);

		    String blogTitle = "";

		    for(String win : driver.getWindowHandles()) {

		        if(!win.equals(mainWindow)) {
		        	driver.switchTo().window(win);
		        	blogTitle =driver.getTitle();

		            System.out.println( "Blog Title : " + blogTitle);

		            Thread.sleep(2000);

		            driver.close();

		            break;
		        }
		    }

		    driver.switchTo().window(mainWindow);

		    Thread.sleep(2000);

		    // Re-acquire Shadow DOM
		    shadowHost =
		            driver.findElement(By.id("shadow_host"));

		    shadowRoot =
		            shadowHost.getShadowRoot();

		    // Textbox
		    WebElement txtBox =
		            shadowRoot.findElement(
		            By.cssSelector("input[type='text']"));

		    txtBox.clear();

		    txtBox.sendKeys(blogTitle);

		    Thread.sleep(1000);

		    // Checkbox
		    WebElement checkbox =
		            shadowRoot.findElement(
		            By.cssSelector("input[type='checkbox']"));

		    if(!checkbox.isSelected()) {

		        checkbox.click();
		    }

		    Thread.sleep(1000);

		    // Upload File
		    WebElement upload =
		            shadowRoot.findElement(
		            By.cssSelector("input[type='file']"));

		    upload.sendKeys(
		    "C:\\Users\\Admin\\OneDrive\\Pictures\\133907427220636723.jpg");

		    Thread.sleep(2000);

		    System.out.println(
		            "===== UNIT 11 COMPLETED =====");
		}}