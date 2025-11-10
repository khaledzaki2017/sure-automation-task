package com.sure.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PIMPage extends BasePage {
    public PIMPage(WebDriver driver) {
        super(driver);
    }


    // ================= Locators =================

//    private final By pimMenu = By.xpath("//a[@class='oxd-main-menu-item active']");
//    private final By searchInput = By.cssSelector("input[placeholder='Type for hints...']");
//    private final By searchBtn = By.cssSelector("button[type='submit']");
//    private final By firstResult = By.cssSelector(".oxd-table-body .oxd-table-card");
    private final By pimMenu = By.xpath("//span[normalize-space()='PIM']");
//    private final By searchInput = By.xpath("//label[text()='Employee Name']/following::input");
//    private final By searchBtn = By.xpath("//button[@type='submit']");
//    private final By firstResult = By.xpath("//div[@role='rowgroup']//div[1]//div[1]//div[5]");
//    private final By editBtnInRow = By.xpath("//div[@role='table']//div[1]//div[1]//div[9]//div[1]//button[1]//i[1]");

    // Search Form Fields
    private final By employeeNameInput = By.xpath("//label[text()='Employee Name']/following::input[@placeholder='Type for hints...']");
    private final By employeeIdInput = By.xpath("//label[text()='Employee Id']/following::input");
    private final By searchBtn = By.xpath("//button[@type='submit' and text()=' Search ']");

    // Table Results
    private final By firstResultRow = By.xpath("(//div[@class='oxd-table-card'])[1]");
    private final By firstResultName = By.xpath("(//div[@class='oxd-table-card'])[1]//div[@class='oxd-table-cell' and @role='cell'][3]/div");
    private final By firstResultId = By.xpath("(//div[@class='oxd-table-card'])[1]//div[@class='oxd-table-cell' and @role='cell'][2]/div");

    private final By editBtnInRow = By.xpath("(//div[@class='oxd-table-card'])[1]//button/i[contains(@class, 'bi-pencil-fill')]");


    // ================= Actions =================

    public void goTo() {
        click(pimMenu);
    }

    public void searchByNameOrId(String text) {
        type(employeeNameInput, text);
        click(searchBtn);

    }

    public boolean isEmployeePresent() {
        return isDisplayed(firstResultRow);
    }

    public void openFirstResultEdit() {
        click(firstResultRow);
    }
}
