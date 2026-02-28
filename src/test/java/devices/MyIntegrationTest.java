package devices;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MyIntegrationTest {

    @Test
    void givenNewDeviceWithEmptyPolicy_whenChecked_thenDeviceIsOff() {
        FailingPolicy policy = new FailingPolicyImpl(Stream.empty());
        Device device = new StandardDevice(policy);
        assertFalse(device.isOn());
    }

    @Test
    void givenPolicyWithFalse_whenTurnOn_thenDeviceIsOn() {
        FailingPolicy policy = new FailingPolicyImpl(Stream.of(false));
        Device device = new StandardDevice(policy);
        device.on();
        assertTrue(device.isOn());
    }

    @Test
    void givenPolicyWithTrue_whenTurnOn_thenThrowsExceptionAndRemainsOff() {
        FailingPolicy policy = new FailingPolicyImpl(Stream.of(true));
        Device device = new StandardDevice(policy);
        assertThrows(IllegalStateException.class, device::on);
        assertFalse(device.isOn());
    }

    @Test
    void givenPolicyWithMultipleFalse_whenTurnOnAndOff_thenDeviceStateChangesAccordingly() {
        FailingPolicy policy = new FailingPolicyImpl(Stream.of(false, false));
        Device device = new StandardDevice(policy);
        device.on();
        assertTrue(device.isOn());
        device.off();
        assertFalse(device.isOn());
        device.on();
        assertTrue(device.isOn());
    }

    @Test
    void givenDeviceIsOn_whenReset_thenDeviceIsOff() {
        FailingPolicy policy = new FailingPolicyImpl(Stream.of(false));
        Device device = new StandardDevice(policy);
        device.on();
        assertTrue(device.isOn());
        device.reset();
        assertFalse(device.isOn());
    }

    @Test
    void givenFailedDevice_whenResetAndTurnOn_thenDeviceTurnsOnSuccessfully() {
        FailingPolicy policy = new FailingPolicyImpl(Stream.of(true, false, false));
        Device device = new StandardDevice(policy);
        assertThrows(IllegalStateException.class, device::on);
        assertFalse(device.isOn());
        device.reset();
        device.on();
        assertTrue(device.isOn());
    }
}
