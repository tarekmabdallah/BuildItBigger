package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class EndPointAsyncTaskTest {

        @Test
    public void testGetJokeTask() throws ExecutionException, InterruptedException {
            EndpointsAsyncTask testJoke = new EndpointsAsyncTask();
            testJoke.execute();
            String joke = testJoke.get();
            assertNotNull(joke);
        }
}
