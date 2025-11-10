package com.sure.orangehrm.tests;

import com.sure.orangehrm.api.EmployeeApi;
import com.sure.orangehrm.api.PersonalDetailsApi;
import com.sure.orangehrm.driver.DriverFactory;
import com.sure.orangehrm.pages.*;
import com.sure.orangehrm.utils.ConfigReader;
import com.sure.orangehrm.utils.JsonUtils;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class E2ECompleteTest extends BaseTest {

    private String empId;
    private String empNumber;

    @BeforeClass
    public void setupAndLogin() {
        driver = DriverFactory.getDriver();

        // UI Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));

        DashboardPage dashboard = new DashboardPage(driver);
        assertTrue(dashboard.isAtDashboard(), "Dashboard should be visible after login");
    }

    @Test(priority = 1)
    public void createEmployeeViaApi() {
        Map<String, Object> employeePayload = new HashMap<>();
        employeePayload.put("firstName", "Auto");
        employeePayload.put("middleName", "API");
        employeePayload.put("lastName", "User");

        Response resp = EmployeeApi.createEmployee(employeePayload);
        assertEquals(resp.getStatusCode(), 201, "Employee creation should return 201");

        empId = resp.jsonPath().getString("data.employeeId");
        empNumber = resp.jsonPath().getString("data.empNumber");
        System.out.println("[API] empId=" + empId + " empNumber=" + empNumber);
    }

    @Test(priority = 2, dependsOnMethods = "createEmployeeViaApi")
    public void addPersonalDetailsViaApi() {
        Map<String, Object> personalPayload = JsonUtils.readJsonToMap("testdata/personalDetails.json");
        personalPayload.put("employeeId", empId);

        Response resp = PersonalDetailsApi.updatePersonalDetails(empNumber, personalPayload);
        assertTrue(resp.getStatusCode() == 200 || resp.getStatusCode() == 204,
                "Personal details update should return 200/204");
    }

    @Test(priority = 3, dependsOnMethods = "addPersonalDetailsViaApi")
    public void uiEditContactAndJobDetails() {
        // PIM search
        PIMPage pim = new PIMPage(driver);
        pim.goTo();
        pim.searchByNameOrId(empId);
        assertTrue(pim.isEmployeePresent(), "Employee must appear in search results");
        pim.openFirstResultEdit();

        // Contact details
        ContactDetailsPage contact = new ContactDetailsPage(driver);
        Map<String, String> contactData = JsonUtils.readJsonToMap("testdata/contactDetails.json");
        // randomize streets
        contactData.put("street1", "Street1 " + System.currentTimeMillis() % 10000);
        contactData.put("street2", "Street2 " + ((int) (Math.random() * 1000)));

        contact.fillContactDetails(contactData);
        contact.save();
        assertTrue(contact.isSuccessShown(), "Contact save success toast should appear");

        String filePath = System.getProperty("user.dir") + "/src/test/resources/files/sample_attachment.pdf";
        contact.addAttachment(filePath);
        assertTrue(contact.isSuccessShown(), "Attachment upload success toast should appear");
        assertEquals(contact.attachmentsCount(), 1, "One attachment should be present");

        // Job details
        JobDetailsPage job = new JobDetailsPage(driver);
        job.open();
        job.setJoinedDate("2015-06-15");   // yyyy-MM-dd expected by page's inputs
        job.selectJobTitle("Software Engineer");
        job.selectEmploymentStatus("Part-Time Internship");
        job.selectSubUnit("Quality Assurance");
        job.selectLocationRandomly();
        job.includeContract();
        job.setContractDatesTodayPlusYear();
        job.save();
        assertTrue(job.isSuccessShown(), "Job details save success toast should appear");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) driver.quit();
    }
}
