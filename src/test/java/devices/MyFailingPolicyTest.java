package devices;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MyFailingPolicyTest {

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
    void testFailsOnFirstAttemptWhenPolicyDictatesFailure() {
        FailingPolicy failingPolicy = new FailingPolicyImpl(Stream.of(true));
        assertFalse(failingPolicy.attemptOn());
    }

    @Test
    void testMaintainsFailureStateWithoutReset() {
        FailingPolicy failingPolicy = new FailingPolicyImpl(Stream.of(true, false, false));
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

}
