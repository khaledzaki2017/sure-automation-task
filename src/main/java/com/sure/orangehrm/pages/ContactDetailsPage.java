package com.sure.orangehrm.pages;

import com.sure.orangehrm.utils.ElementHelper;
import com.sure.orangehrm.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class ContactDetailsPage extends BasePage {

    public ContactDetailsPage(WebDriver driver) {
        super(driver);
    }

    // ================= Locators =================
    private final By contactDetailsTab = By.cssSelector("a[href*='contactDetails']");
    private final By street1Input = By.xpath("//label[text()='Street 1']/following::input[1]");
    private final By street2Input = By.xpath("//label[text()='Street 2']/following::input[1]");
    private final By cityInput = By.xpath("//label[text()='City']/following::input[1]");
    private final By stateInput = By.xpath("//label[text()='State/Province']/following::input[1]");
    private final By postalCodeInput = By.xpath("//label[text()='Zip/Postal Code']/following::input");
    private final By countryDropdown = By.xpath("//label[text()='Country']/following::div[contains(@class, 'oxd-select-text-input')]");
    private final By saveButton = By.cssSelector("button[type='submit']");
    private final By toastMessage = By.cssSelector("p.oxd-text.oxd-text--p.oxd-toast-content-text");
    private final By addButton = By.xpath("//button[.//i[contains(@class, 'bi-plus')] and contains(., 'Add')]");
    private final By fileInput = By.cssSelector("input.oxd-file-input[type='file']");
    private final By saveContactDetailsBtn = By.xpath("//div[@class='orangehrm-attachment']//button[@type='submit'][normalize-space()='Save']");
    private final By attachmentsRows = By.cssSelector("div.orangehrm-attachment div[role='row']");

    // ================= Page Actions =================

    public void open() {
        click(contactDetailsTab);
        WaitHelper.waitForElementVisible(driver, street1Input);
    }

    public void fillContactDetails(Map<String, String> data) {
        type(street1Input, data.getOrDefault("street1", "Street-" + new Random().nextInt(9000)));
        type(street2Input, data.getOrDefault("street2", "Apt-" + new Random().nextInt(9000)));
        type(cityInput, data.getOrDefault("city", "Cairo"));
        type(stateInput, data.getOrDefault("state", "Giza"));
        type(postalCodeInput, data.getOrDefault("postalCode", "12345"));

        click(countryDropdown);
        click(By.xpath("//span[text()='" + data.getOrDefault("country", "Egypt") + "']"));
    }

    public void save() {
        click(saveButton);
        WaitHelper.waitForElementVisible(driver, toastMessage);
    }

    public boolean isSuccessShown() {
        try {
            return ElementHelper.isDisplayed(driver, toastMessage);
        } catch (Exception e) {
            return false;
        }
    }

    public void addAttachment(String filePath) {
        click(addButton);

        try {
            WebElement upload = WaitHelper.waitForElementPresent(driver, fileInput);
            upload.sendKeys(filePath);

            // Wait for upload to complete and verify
            Thread.sleep(2000);

            // Verify upload success
            WebElement fileInputDiv = driver.findElement(fileInput);
            String fileNameText = fileInputDiv.getText();

            if (!fileNameText.equals("No file selected")) {
                System.out.println("File uploaded successfully: " + fileNameText);
            } else {
                System.out.println("File upload failed");
            }
        } catch (Exception e) {
            System.out.println("File upload failed: " + e.getMessage());
        }
    }

    public int attachmentsCount() {
        List<WebElement> rows = ElementHelper.getVisibleElements(driver, attachmentsRows);
        return rows.size();
    }

    public void saveContactDetailsBtn() {
        click(saveContactDetailsBtn);
        WaitHelper.waitForElementVisible(driver, toastMessage);
    }
}