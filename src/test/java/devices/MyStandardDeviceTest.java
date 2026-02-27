package devices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MyStandardDeviceTest {
    private Device device;
    private FailingPolicy stubFailingPolicy;

    @BeforeEach
    void init(){
        stubFailingPolicy = mock(FailingPolicy.class);
        device = new StandardDevice(stubFailingPolicy);
    }

    @Test
    void constructorThrowsOnNullPolicy() {
        assertThrows(NullPointerException.class, () -> new StandardDevice(null));
    }

    @Test
    void testDeviceIsInitiallyOff() {
        assertFalse(device.isOn());
    }

    @Test
    void testCanBeSwitchedOn() {
        when(stubFailingPolicy.attemptOn()).thenReturn(true);
        device.on();
        assertTrue(device.isOn());
    }

    @Test
    void testCanBeSwitchedOff() {
        when(stubFailingPolicy.attemptOn()).thenReturn(true);
        device.on();
        assertTrue(device.isOn());
        device.off();
        assertFalse(device.isOn());
    }

    @Test
    void testDeviceException() {
        when(stubFailingPolicy.attemptOn()).thenReturn(false);
        assertThrows(IllegalStateException.class, device::on);
        assertFalse(device.isOn()); // Ensure state hasn't changed
    }

    @Test
    void testDeviceReset(){
        when(stubFailingPolicy.attemptOn()).thenReturn(true);
        device.on();
        device.reset();
        assertFalse(device.isOn());
    }

    @Test
    void testResetInteraction() {
        when(stubFailingPolicy.attemptOn()).thenReturn(true);

        device.on();
        device.reset();

        verify(stubFailingPolicy).reset();
        verify(stubFailingPolicy).attemptOn();
    }

    @Test
    void testToString() {
        when(stubFailingPolicy.policyName()).thenReturn("stubbed_policy");
        assertEquals("StandardDevice{policy=stubbed_policy, on=false}", device.toString());
    }
}
