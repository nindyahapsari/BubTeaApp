package com.nindyahapsari.bubteatapp;


import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertNotNull;


public class MainActivityTest {


    private MainActivity mMainActivity;

    @Before
    public void setUp(){

        mMainActivity = new MainActivity();
    }


    @Test
    public void checkMainActivity_isNotNull() throws Exception{

        assertNotNull(mMainActivity);

    }


}
