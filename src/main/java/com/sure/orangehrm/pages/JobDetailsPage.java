package com.sure.orangehrm.pages;

import com.sure.orangehrm.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class JobDetailsPage extends BasePage {

    public JobDetailsPage(WebDriver driver) {
        super(driver);
    }

    // ================= Locators =================
    private final By jobTab = By.xpath("//a[text()='Job']");
    private final By joinedDateInput = By.xpath("//label[text()='Joined Date']/following::input[@placeholder='yyyy-dd-mm']");
    private final By jobTitleDropdown = By.cssSelector("div.oxd-select-text");
    private final By employmentStatusDropdown = By.xpath(
            "//label[text()='Employment Status']/following::div[contains(@class,'oxd-select-text')][1]"
    );
    private final By subUnitDropdown = By.xpath(
            "//label[text()='Sub Unit']/following::div[contains(@class,'oxd-select-text')][1]"
    );
    private final By locationDropdown = By.xpath(
            "//label[text()='Location']/following::div[contains(@class,'oxd-select-text')][1]"
    );
    private final By contractToggle = By.cssSelector("span.oxd-switch-input.oxd-switch-input--active.--label-right");
    private final By contractStartInput = By.xpath("//label[text()='Contract Start Date']/following::input[@placeholder='yyyy-dd-mm'][1]");
    private final By contractEndInput = By.xpath("//label[text()='Contract End Date']/following::input[@placeholder='yyyy-dd-mm'][1]");
    private final By saveButton = By.cssSelector("button[type='submit'].oxd-button");
    private final By successToast = By.cssSelector("p.oxd-toast-content-text");
    //    private final By dropdownOptions = By.cssSelector("div[role='option'] span");
    private final By listBox = By.cssSelector("div[role='listbox']");
    private final By optionsLocator = By.cssSelector("div[role='listbox'] div[role='option']");

    // ================= Page Actions =================

    public void open() {
        click(jobTab);
    }

    public void setJoinedDate(String date) {
        type(joinedDateInput, date);
    }

    public void selectJobTitle(String title) throws InterruptedException {
        click(jobTitleDropdown);

        // Wait for dropdown animation and options to load
        Thread.sleep(1000);
        WaitHelper.waitForElementVisible(driver, listBox);

        By option = By.xpath("//div[@role='option' and normalize-space()='" + title + "']");
        WebElement jobOption = WaitHelper.waitForElementPresent(driver, option);
        WaitHelper.waitForElementClickable(driver, option);
        jobOption.click();
    }

    public void selectEmploymentStatus(String status) {
        click(employmentStatusDropdown);
        WaitHelper.waitForElementVisible(driver, optionsLocator);

        By option = By.xpath("//div[@role='option' and normalize-space()='" + status + "']");
        WebElement statusOption = WaitHelper.waitForElementClickable(driver, option);
        statusOption.click();
    }

    public void selectSubUnit(String subUnit) {
        click(subUnitDropdown);
        WaitHelper.waitForElementVisible(driver, optionsLocator);

        By option = By.xpath("//div[@role='option' and normalize-space()='" + subUnit + "']");
        WebElement subUnitOption = WaitHelper.waitForElementClickable(driver, option);
        subUnitOption.click();
    }

    public void selectLocationRandomly() {
        WebElement dropdown = WaitHelper.waitForElementClickable(driver, locationDropdown);
        dropdown.click();
        WaitHelper.waitForElementVisible(driver, optionsLocator);

        List<WebElement> options = driver.findElements(optionsLocator);
        Random rand = new Random();
        int index = rand.nextInt(options.size());
        WebElement randomOption = options.get(index);

        WaitHelper.waitForElementClickable(driver, randomOption).click();
    }

    public void includeContract() {
        click(contractToggle);
    }

    public void setContractDatesTodayPlusYear() {
        includeContract();

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        type(contractStartInput, startDate.format(formatter));
        type(contractEndInput, endDate.format(formatter));
    }

    public void save() {
        click(saveButton);
    }

    public boolean isSuccessShown() {
        return isDisplayed(successToast);
    }
}