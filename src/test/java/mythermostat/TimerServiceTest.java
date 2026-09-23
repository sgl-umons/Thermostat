package mythermostat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Tests for the real {@link TimerService}, which schedules callbacks on a
 * background {@link java.util.Timer}. These are timing-based, so generous
 * timeouts are used to avoid flakiness while still keeping the suite fast.
 */
class TimerServiceTest {

	private TimerService timerService;

	@BeforeEach
	void setUp() {
		timerService = new TimerService();
	}

	@AfterEach
	void tearDown() {
		timerService.cancel();
	}

	// Verifies that a callback registered via setTimer() actually fires after the
	// configured delay and receives the event ID it was scheduled with.
	@Test
	@Timeout(2)
	void setTimerInvokesCallbackWithEventIdAfterDelay() throws InterruptedException {
		CountDownLatch fired = new CountDownLatch(1);
		CopyOnWriteArrayList<Integer> receivedEventIds = new CopyOnWriteArrayList<>();
		ITimerCallback callback = eventID -> {
			receivedEventIds.add(eventID);
			fired.countDown();
		};

		timerService.setTimer(callback, 42, 50, false);

		assertTrue(fired.await(1, TimeUnit.SECONDS), "callback should fire before the timeout");
		assertEquals(1, receivedEventIds.size());
		assertEquals(42, receivedEventIds.get(0));
	}

	// Verifies that calling unsetTimer() before the delay elapses cancels the
	// timer, so the callback never fires.
	@Test
	@Timeout(2)
	void unsetTimerPreventsCallbackFromFiring() throws InterruptedException {
		CountDownLatch fired = new CountDownLatch(1);
		ITimerCallback callback = eventID -> fired.countDown();

		timerService.setTimer(callback, 1, 200, false);
		timerService.unsetTimer(callback, 1);

		boolean firedInTime = fired.await(400, TimeUnit.MILLISECONDS);
		assertFalse(firedInTime, "callback should not fire once its timer has been unset");
	}
}
