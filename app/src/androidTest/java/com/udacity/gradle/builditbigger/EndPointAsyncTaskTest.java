package com.udacity.gradle.builditbigger;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class EndPointAsyncTaskTest {

    private Context instrumentationContext;

    @Before
    public void setup() {
        instrumentationContext = InstrumentationRegistry.getTargetContext(); // to get the target context to start intent to open DisplayJokeActivity
    }

    @Test
    public void testGetJokeTask() throws ExecutionException, InterruptedException {
        EndpointsAsyncTask testJoke = new EndpointsAsyncTask();
        testJoke.execute(instrumentationContext);

        assertNotNull(testJoke.get());
        String joke = testJoke.get().second;
        assertFalse(joke.isEmpty());
    }
}
