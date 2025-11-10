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
    private final By jobTab = By.cssSelector("a[href*='job']");
    private final By joinedDateInput = By.cssSelector("label:has-text('Joined Date') + div input");
    private final By jobTitleDropdown = By.cssSelector("label:has-text('Job Title') + div .oxd-select-text");
    private final By employmentStatusDropdown = By.cssSelector("label:has-text('Employment Status') + div .oxd-select-text");
    private final By subUnitDropdown = By.cssSelector("label:has-text('Sub Unit') + div .oxd-select-text");
    private final By locationDropdown = By.cssSelector("label:has-text('Location') + div .oxd-select-text");
    private final By contractCheckbox = By.cssSelector("label:has-text('Include Contract Details') input[type='checkbox']");
    private final By contractStartInput = By.cssSelector("label:has-text('Contract Start Date') + div input");
    private final By contractEndInput = By.cssSelector("label:has-text('Contract End Date') + div input");
    private final By saveButton = By.cssSelector("button[type='submit']");
    private final By successToast = By.cssSelector("p.oxd-toast-content-text");
    private final By dropdownOptions = By.cssSelector("div[role='option'] span");

    // ================= Actions =================

    public void open() {
        click(jobTab);
    }

    public void setJoinedDate(String date) {
        type(joinedDateInput, date);
    }

    public void selectJobTitle(String title) {
        click(jobTitleDropdown);
        click(optionWithText(title));
    }

    public void selectEmploymentStatus(String status) {
        click(employmentStatusDropdown);
        click(optionWithText(status));
    }

    public void selectSubUnit(String unit) {
        click(subUnitDropdown);
        click(optionWithText(unit));
    }

    public void selectLocationRandomly() {
        click(locationDropdown);
        List<WebElement> options = getVisibleElements(dropdownOptions);
        if (options.size() > 1) {
            int randomIndex = new Random().nextInt(options.size());
            options.get(randomIndex).click();
        } else {
            options.get(0).click();
        }
    }

    public void includeContract() {
        WebElement checkbox = WaitHelper.waitForElementVisible(driver, contractCheckbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void setContractDatesTodayPlusYear() {
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

    private By optionWithText(String text) {
        return By.xpath("//span[normalize-space()='" + text + "']");
    }
}
