package com.nindyahapsari.bubteatapp;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    public static final String PHONE_TO_BE_TYPED = "1111";
    public static final String PASS_TO_BE_TYPED = "1111";


    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivityRule =
            new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void typingPhoneNumber_loginActivityTest() {
        onView(withId(R.id.login_phone_input)).perform(typeText(PHONE_TO_BE_TYPED));
        onView(withId(R.id.login_phone_input)).check(matches(withText(PHONE_TO_BE_TYPED)));
    }


    @Test
    public void typePassword_checkPassword() {
        onView(withId(R.id.login_password)).perform(typeText(PASS_TO_BE_TYPED));
        onView(withId(R.id.login_password)).check(matches(withText(PASS_TO_BE_TYPED)));
    }


    @Test
    public void clickLoginButton_afterFillingForm(){

        onView(withId(R.id.login_phone_input)).perform(typeText(PHONE_TO_BE_TYPED));

        onView(withId(R.id.login_password)).perform(typeText(PASS_TO_BE_TYPED), closeSoftKeyboard());

        onView(withId(R.id.login_button_user)).perform(click());

    }


    // the next test:
    // check if user put the wrong id and password.
    // check if the check box function.









}
