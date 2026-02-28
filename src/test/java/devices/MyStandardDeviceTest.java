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
    void givenNullPolicy_whenConstructed_thenThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StandardDevice(null));
    }

    @Test
    void givenNewDevice_whenChecked_thenDeviceIsOff() {
        assertFalse(device.isOn());
    }

    @Test
    void givenPolicySucceeds_whenTurnOn_thenDeviceIsOn() {
        when(stubFailingPolicy.attemptOn()).thenReturn(true);
        device.on();
        assertTrue(device.isOn());
    }

    @Test
    void givenDeviceIsOn_whenTurnOff_thenDeviceIsOff() {
        when(stubFailingPolicy.attemptOn()).thenReturn(true);
        device.on();
        assertTrue(device.isOn());
        device.off();
        assertFalse(device.isOn());
    }

    @Test
    void givenPolicyFails_whenTurnOn_thenThrowsIllegalStateExceptionAndRemainsOff() {
        when(stubFailingPolicy.attemptOn()).thenReturn(false);
        assertThrows(IllegalStateException.class, device::on);
        assertFalse(device.isOn()); // Ensure state hasn't changed
    }

    @Test
    void givenDeviceIsOn_whenReset_thenDeviceIsOff() {
        when(stubFailingPolicy.attemptOn()).thenReturn(true);
        device.on();
        device.reset();
        assertFalse(device.isOn());
    }

    @Test
    void givenDeviceIsOn_whenReset_thenPolicyIsReset() {
        when(stubFailingPolicy.attemptOn()).thenReturn(true);

        device.on();
        device.reset();

        verify(stubFailingPolicy).reset();
        verify(stubFailingPolicy).attemptOn();
    }

    @Test
    void givenMockedPolicy_whenToString_thenReturnsFormattedString() {
        when(stubFailingPolicy.policyName()).thenReturn("stubbed_policy");
        assertEquals("StandardDevice{policy=stubbed_policy, on=false}", device.toString());
    }
}
