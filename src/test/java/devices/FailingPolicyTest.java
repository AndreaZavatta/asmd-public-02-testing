package devices;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FailingPolicyTest {
    FailingPolicy failingPolicy;

    @Test
    void testAttemptOn() {
        failingPolicy = new FailingPolicyImpl(Stream.of(true));
        assertFalse(failingPolicy.attemptOn());
    }

    @Test
    void testReset() {
        failingPolicy = new FailingPolicyImpl(Stream.of(true, false));
        assertFalse(failingPolicy.attemptOn());
        failingPolicy.reset();
        assertTrue(failingPolicy.attemptOn());
    }


}
