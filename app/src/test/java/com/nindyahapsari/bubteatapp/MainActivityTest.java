package com.nindyahapsari.bubteatapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import org.apache.tools.ant.Main;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.robolectric.Robolectric.buildActivity;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class MainActivityTest {

    private MainActivity activity;



    @Before
    public void setUp(){

        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
    }


    @Test
    public void checkMainActivity_notNull(){

        assertNotNull(activity);
    }


    @Test
    public void checkButton_loginFunction_shouldStartNextActivity(){

        Button button = (Button) activity.findViewById(R.id.login_button);
        button.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertEquals(LoginActivity.class.getCanonicalName(), intent.getComponent().getClassName());

    }


}
