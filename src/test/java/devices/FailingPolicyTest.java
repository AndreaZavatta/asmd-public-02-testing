package devices;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FailingPolicyTest {

    @Test
    void givenEmptyStream_whenAttemptOn_thenThrowsIllegalStateException() {
        FailingPolicy failingPolicy = new FailingPolicyImpl(Stream.empty());
        assertThrows(IllegalStateException.class, failingPolicy::attemptOn);
    }

    @Test
    void givenExhaustedStream_whenAttemptOn_thenThrowsIllegalStateException() {
        FailingPolicy failingPolicy = new FailingPolicyImpl(Stream.of(true));
        failingPolicy.attemptOn();
        assertThrows(IllegalStateException.class, failingPolicy::attemptOn);
    }

    @Test
    void givenStreamWithFalse_whenAttemptOn_thenReturnsTrue() {
        FailingPolicy failingPolicy = new FailingPolicyImpl(Stream.of(false));
        assertTrue(failingPolicy.attemptOn());
    }

    @Test
    void givenMixedStream_whenAttemptOnMultipleTimes_thenReturnsExpectedResults() {
        FailingPolicy failingPolicy = new FailingPolicyImpl(Stream.of(false, false, true, false));
        assertTrue(failingPolicy.attemptOn());
        assertTrue(failingPolicy.attemptOn());
        assertFalse(failingPolicy.attemptOn());
        assertFalse(failingPolicy.attemptOn());
    }

    @Test
    void givenFailedState_whenReset_thenFailureStateIsCleared() {
        FailingPolicy failingPolicy = new FailingPolicyImpl(Stream.of(true, false, false));
        assertFalse(failingPolicy.attemptOn());
        failingPolicy.reset();
        assertTrue(failingPolicy.attemptOn());
    }

    @Test
    void givenInitializedPolicy_whenGetPolicyName_thenReturnsGenericFailingPolicy() {
        FailingPolicy failingPolicy = new FailingPolicyImpl(Stream.of(true));
        assertEquals("GenericFailingPolicy", failingPolicy.policyName());
    }

}
