package devices;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MyFailingPolicyTest {

    @Test
    void testEmptyStream() {
        FailingPolicy failingPolicy = new FailingPolicyImpl(Stream.empty());
        assertThrows(IllegalStateException.class, failingPolicy::attemptOn);
    }

    @Test
    void testStreamExhaustion() {
        FailingPolicy failingPolicy = new FailingPolicyImpl(Stream.of(true));
        failingPolicy.attemptOn();
        assertThrows(IllegalStateException.class, failingPolicy::attemptOn);
    }

    @Test
    void testSuccessOnFirstAttempt() {
        FailingPolicy failingPolicy = new FailingPolicyImpl(Stream.of(false));
        assertTrue(failingPolicy.attemptOn());
    }

    @Test
    void testStateTransitions() {
        FailingPolicy failingPolicy = new FailingPolicyImpl(Stream.of(false, false, true, false));
        assertTrue(failingPolicy.attemptOn());
        assertTrue(failingPolicy.attemptOn());
        assertFalse(failingPolicy.attemptOn());
        assertFalse(failingPolicy.attemptOn());
    }

    @Test
    void testResetClearsFailureState() {
        FailingPolicy failingPolicy = new FailingPolicyImpl(Stream.of(true, false, false));
        assertFalse(failingPolicy.attemptOn());
        failingPolicy.reset();
        assertTrue(failingPolicy.attemptOn());
    }

    @Test
    void testPolicyName() {
        FailingPolicy failingPolicy = new FailingPolicyImpl(Stream.of(true));
        assertEquals("GenericFailingPolicy", failingPolicy.policyName());
    }

}
