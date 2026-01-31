package com.makemytrip;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MakeMyTripAutomation {

	public static void main(String[] args) {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--start-maximized");
		WebDriver wd = new ChromeDriver(chromeOptions);
		wd.get("https://www.makemytrip.com/");
		
		WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(30));
		By closeModalButtonLocator = By.xpath("//span[@data-cy='closeModal']");
		WebElement closeModalButton = wait.until(ExpectedConditions.elementToBeClickable(closeModalButtonLocator));
		closeModalButton.click();
		
		By tp_dtMinimizeLocator =By.xpath("//img[@alt='minimize']");
		WebElement tp_dtMinimize = wait.until(ExpectedConditions.elementToBeClickable(tp_dtMinimizeLocator));
		tp_dtMinimize.click();
		
		By mmtworktooltipLocator = By.xpath("//*[text()='MMT WORK']");
		List<WebElement> tips = wd.findElements(mmtworktooltipLocator);
		if(!tips.isEmpty() && tips.get(0).isDisplayed()) {
			 new Actions(wd).moveByOffset(5, 5).click().perform();
		}
 		
		By fromCityLabelLocator = By.xpath("//label[@for='fromCity']");
		WebElement forCityLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(fromCityLabelLocator));
		forCityLabel.click();
		
		By fromCityInputTextBoxLocator = By.xpath("//input[@placeholder='From']");
		WebElement fromCityInputTextBox = wait.until(ExpectedConditions.visibilityOfElementLocated(fromCityInputTextBoxLocator));
		fromCityInputTextBox.sendKeys("Pune");
		
		By fromCitySuggestionListLocator = By.xpath("//p[contains(text(), 'SUGGESTIONS')]/ancestor::div[contains(@class, 'react-autosuggest')]/ul/li");
		boolean state = wait.until(ExpectedConditions.and(ExpectedConditions.visibilityOfElementLocated(fromCitySuggestionListLocator),
				ExpectedConditions.numberOfElementsToBeLessThan(fromCitySuggestionListLocator, 12)));
		
		List<WebElement> fromCitySuggestionList = null;
		
		if(state) {
			fromCitySuggestionList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(fromCitySuggestionListLocator));
		}
		
		System.out.println(fromCitySuggestionList.size());  
		
		for(WebElement suggestion : fromCitySuggestionList) {
			System.out.println(suggestion.getText());
		}
		
		fromCitySuggestionList.get(0).click();
		
		
		By toCityLabelLocator = By.xpath("//label[@for='toCity']");
		WebElement toCityLabe = wait.until(ExpectedConditions.elementToBeClickable(toCityLabelLocator));
		toCityLabe.click();
		
		By toCityInputTextBoxLocator = By.xpath("//input[@placeholder='To']");
		WebElement toCityInputTextBox = wait.until(ExpectedConditions.visibilityOfElementLocated(toCityInputTextBoxLocator));
		toCityInputTextBox.sendKeys("Hyderabad");
		
		By toCitySuggestionListLocator = By.xpath("//*[contains(text(), '*This visa information applies to Indian passport holders')]/ancestor::div[contains(@class, 'react-autosuggest')]/ul/li");
		state = wait.until(ExpectedConditions.and(ExpectedConditions.visibilityOfElementLocated(toCitySuggestionListLocator),
				ExpectedConditions.numberOfElementsToBeMoreThan(toCitySuggestionListLocator, 10)));
		
		List<WebElement> toCitySuggestionList = null;
		if(state) {
			toCitySuggestionList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(toCitySuggestionListLocator));
		}
		
		toCitySuggestionList.get(0).click();
		
		LocalDate targetDate = LocalDate.now();
		targetDate = targetDate.plusDays(5);
		String targetMonth = targetDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
		int targetYear = targetDate.getYear();
		int targetDay = targetDate.getDayOfMonth();
		
		By calendarMonthLocator = By.xpath("//div[text()='"+targetMonth+" "+targetYear+"']/ancestor::div[@class='DayPicker-Month']");
		WebElement calendarMonth = wait.until(ExpectedConditions.visibilityOfElementLocated(calendarMonthLocator));
		
		By dateLocator = By.xpath(".//p[text()='"+targetDay+"']/ancestor::div[contains(@class, 'DayPicker-Day')]");
		WebElement date = calendarMonth.findElement(dateLocator);
		date.click();
	}

}
