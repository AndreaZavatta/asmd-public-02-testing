package devices;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MyIntegrationTest {

    @Test
    void testDeviceIsInitiallyOff() {
        FailingPolicy policy = new FailingPolicyImpl(Stream.empty());
        Device device = new StandardDevice(policy);
        
        assertFalse(device.isOn());
    }

    @Test
    void testDeviceCanBeSwitchedOnWhenPolicyIsSuccess() {
        FailingPolicy policy = new FailingPolicyImpl(Stream.of(false));
        Device device = new StandardDevice(policy);
        
        device.on();
        assertTrue(device.isOn());
    }

    @Test
    void testDeviceFailsWhenPolicyIsFailure() {
        FailingPolicy policy = new FailingPolicyImpl(Stream.of(true));
        Device device = new StandardDevice(policy);
        
        assertThrows(IllegalStateException.class, device::on);
        assertFalse(device.isOn());
    }

    @Test
    void testDeviceCanBeSwitchedOnAndOff() {
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
    void testDeviceTurnsOffOnReset() {
        FailingPolicy policy = new FailingPolicyImpl(Stream.of(false));
        Device device = new StandardDevice(policy);
        
        device.on();
        assertTrue(device.isOn());
        
        device.reset();
        
        assertFalse(device.isOn());
    }

    @Test
    void testDeviceResetClearsFailure() {
        FailingPolicy policy = new FailingPolicyImpl(Stream.of(true, false, false));
        Device device = new StandardDevice(policy);
        
        assertThrows(IllegalStateException.class, device::on);
        assertFalse(device.isOn());
        
        device.reset();
        
        device.on();
        assertTrue(device.isOn());
    }
}
