package devices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MyStandardDeviceTest {
    private Device device;
    FailingPolicy stubFailingPolicy;

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
    void testCanBeSwitchedOn() {
        when(this.stubFailingPolicy.attemptOn()).thenReturn(true);
        device.on();
        assertTrue(device.isOn());
    }

    @Test
    void testCanBeSwitchedOff() {
        when(this.stubFailingPolicy.attemptOn()).thenReturn(true);
        device.on();
        assertTrue(device.isOn());
        device.off();
        assertFalse(device.isOn());
    }

    @Test
    void testDeviceException() {
        when(this.stubFailingPolicy.attemptOn()).thenReturn(false);
        assertThrows(IllegalStateException.class, () -> device.on());
    }

    @Test
    void testDeviceReset(){
        when(this.stubFailingPolicy.attemptOn()).thenReturn(true);
        device.on();
        device.reset();
        assertFalse(device.isOn());
    }

    @Test
    void testResetInteraction() {
        when(this.stubFailingPolicy.attemptOn()).thenReturn(true);

        device.on();
        device.reset();

        verify(this.stubFailingPolicy, times(1)).reset();
        verify(this.stubFailingPolicy, times(1)).attemptOn();
    }
}
