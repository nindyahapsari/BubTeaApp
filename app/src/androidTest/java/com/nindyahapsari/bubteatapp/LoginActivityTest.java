package com.nindyahapsari.bubteatapp;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    public static final String NAME_TO_BE_TYPED = "MarieCurie";
    public static final String PASS_TO_BE_TYPED = "1234";


    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivityRule =
            new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void writingName_loginActivityTest() {
        onView(withId(R.id.login_name)).perform(typeText(NAME_TO_BE_TYPED));
        onView(withId(R.id.login_name)).check(matches(withText(NAME_TO_BE_TYPED)));
    }


    @Test
    public void typePass_loginActivityTest() {
        onView(withId(R.id.login_password)).perform(typeText(PASS_TO_BE_TYPED));
        onView(withId(R.id.login_password)).check(matches(withText(PASS_TO_BE_TYPED)));
    }


}
