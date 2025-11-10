
package com.sure.orangehrm.tests;

import com.sure.orangehrm.api.ApiClient;
import com.sure.orangehrm.api.EmployeeApi;
import com.sure.orangehrm.api.PersonalDetailsApi;
import com.sure.orangehrm.pages.*;
import com.sure.orangehrm.utils.ConfigReader;
import com.sure.orangehrm.utils.JsonUtils;
import io.restassured.response.Response;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class E2ETest extends BaseTest {

    private EmployeeApi employeeApi;
    private PersonalDetailsApi personalDetailsApi;
    private String empId;
    private String empNumber;

    @BeforeClass(alwaysRun = true)
    public void setupClass() throws Exception {
        // Step 1: UI Login (needed to get session cookie)
        initDriverForUI();

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        loginPage.open();
        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        assertTrue(dashboardPage.isAtDashboard(), "Dashboard should be visible after login");

        // Extract session cookie for API
        String cookieValue = driver.manage().getCookieNamed("orangehrm").getValue();
        ApiClient.setSessionCookie("orangehrm=" + cookieValue);
        System.out.println("[E2ETest] Cookie transferred from UI to API: orangehrm=" + cookieValue);

        // Initialize API helpers
        employeeApi = new EmployeeApi();
        personalDetailsApi = new PersonalDetailsApi();
    }

    @Test(priority = 1)
    public void createEmployeeViaApi() {
        // Step 2: Create Employee via API
        Map<String, Object> employeePayload = new HashMap<>();
        employeePayload.put("firstName", "Khaled");
        employeePayload.put("middleName", "Abdelhameed");
        employeePayload.put("lastName", "Zaki");
        employeePayload.put("empPicture", null);
        employeePayload.put("employeeId", "EMP" + System.currentTimeMillis() % 100000);

        Response resp = employeeApi.createEmployee(employeePayload);
        assertTrue(resp.getStatusCode() == 200 || resp.getStatusCode() == 201,
                "Employee creation failed, status: " + resp.getStatusCode());

        empId = resp.jsonPath().getString("data.id");
        if (empId == null) empId = resp.jsonPath().getString("data.employeeId");
        empNumber = resp.jsonPath().getString("data.empNumber");

        assertNotNull(empId, "Employee ID should not be null");
        assertNotNull(empNumber, "Employee Number should not be null");

        System.out.println("[API] Employee Created , empId=" + empId + ", empNumber=" + empNumber);
    }

    @Test(priority = 2, dependsOnMethods = "createEmployeeViaApi")
    public void addPersonalDetailsViaApi() throws Exception {
        // Step 3: Add Personal Details via API
        Map<String, Object> personalPayload = JsonUtils.readJsonToMap(
                "src/test/resources/testdata/personalDetails.json"
        );

        personalPayload.put("employeeId", empId);

        Response resp = personalDetailsApi.updatePersonalDetails(empNumber, personalPayload);
        assertTrue(resp.getStatusCode() == 200 || resp.getStatusCode() == 204,
                "Personal details update failed, status: " + resp.getStatusCode());

        System.out.println("[API] Personal details updated for empNumber=" + empNumber);
    }

    @Test(priority = 3, dependsOnMethods = "addPersonalDetailsViaApi")
    public void uiEditContactAndJobDetails() throws Exception {
        // Step 4: UI - Contact and Job Details
        PIMPage pim = new PIMPage(driver);
        pim.goTo();
        pim.searchByNameOrId(empId);
        assertTrue(pim.isEmployeePresent(), "Employee must appear in search results");
        pim.openFirstResultEdit();

        ContactDetailsPage contact = new ContactDetailsPage(driver);
        Map<String, String> contactData = JsonUtils.getData(
                "src/test/resources/testdata/contactDetails.json"
        );

        // Auto-generate random streets
        contactData.put("street1", "Street_" + System.currentTimeMillis() % 10000);
        contactData.put("street2", "Street_" + (int) (Math.random() * 1000));

        contact.open();
        contact.fillContactDetails(contactData);
        contact.save();
        assertTrue(contact.isSuccessShown(), "Contact details success toast must appear");

        // Add attachment
        String filePath = System.getProperty("user.dir") + "/src/test/resources/files/sample_attachment.pdf";
        contact.addAttachment(filePath);
        assertTrue(contact.isSuccessShown(), "Attachment success toast must appear");
        assertEquals(contact.attachmentsCount(), 1, "Exactly 1 attachment should be present");

        // Job Details
        JobDetailsPage job = new JobDetailsPage(driver);
        job.open();
        job.setJoinedDate("2015-06-15");
        job.selectJobTitle("Software Engineer");
        job.selectEmploymentStatus("Part-Time Internship");
        job.selectSubUnit("Quality Assurance");
        job.selectLocationRandomly();
        job.includeContract();
        job.setContractDatesTodayPlusYear();
        job.save();
        assertTrue(job.isSuccessShown(), "Job details success toast must appear");
    }
}
