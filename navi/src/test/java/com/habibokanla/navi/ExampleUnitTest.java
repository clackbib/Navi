package com.habibokanla.navi;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    Navi navi = new Navi();

    class MockListener {

        public int invocationCount;

        public MockListener() {
            invocationCount = 0;
        }

        @Listen
        void testMethod(String str) {
            invocationCount++;
        }
    }


    @Test
    public void testUnregister() {
        String mockEvent = "Habib";
        MockListener listener = new MockListener();
        navi.call(listener);
        navi.swat(listener);

        navi.hey(mockEvent);

        assertTrue(listener.invocationCount == 0);
    }

    @Test(expected = IllegalStateException.class)
    public void testUnregister2() {
        MockListener listener = new MockListener();
        navi.call(listener);
        navi.swat(listener);
        navi.swat(listener);
    }

    @Test(expected = IllegalStateException.class)
    public void testUnregister3() {
        MockListener listener = new MockListener();
        navi.swat(listener);
    }

    @Test(expected = IllegalStateException.class)
    public void testRegister() {
        MockListener listener = new MockListener();
        navi.call(listener);
        navi.call(listener);
    }

    @Test
    public void invocationTest() throws Exception {
        String mockEvent = "Habib";
        MockListener listener = new MockListener();
        navi.call(listener);

        navi.hey(mockEvent);
        navi.hey(mockEvent);

        navi.swat(listener);

        assertTrue(listener.invocationCount == 2);

    }

    @Test
    public void invocationTest2() throws Exception {
        String mockEvent = "Habib";
        MockListener listener = new MockListener();
        navi.call(listener);

        navi.hey(mockEvent);

        navi.swat(listener);

        navi.hey(mockEvent);

        assertTrue(listener.invocationCount == 1);

    }
}