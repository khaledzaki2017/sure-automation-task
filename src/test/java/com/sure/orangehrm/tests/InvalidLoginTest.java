package com.sure.orangehrm.tests;

import com.sure.orangehrm.pages.LoginPage;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

public class InvalidLoginTest extends BaseTest {

    @Test
    public void invalidLoginShowsError() {
        LoginPage login = new LoginPage(driver);
        login.open();
        login.login("wronguser","wrongpass");
        String err = login.getLoginError();
        assertTrue(err.contains("Invalid credentials") || err.length() > 0, "Expect login failure message");
    }
}
