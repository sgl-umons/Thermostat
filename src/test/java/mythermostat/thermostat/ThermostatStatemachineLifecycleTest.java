package mythermostat.thermostat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mythermostat.ITimer;
import mythermostat.ITimerCallback;
import mythermostat.thermostat.IThermostatStatemachine.SCInterfaceOperationCallback;

/**
 * Tests for {@link ThermostatStatemachine} lifecycle ordering (init/enter
 * preconditions) and the default configuration values set by init() before
 * enter() has run. Kept separate from {@link ThermostatStatemachineTest} so
 * that class's setUp() can safely call init()+enter() for every test without
 * invalidating these pre-init/pre-enter checks.
 */
class ThermostatStatemachineLifecycleTest {

	/** ITimer double that never actually schedules anything. */
	private static class NoOpTimer implements ITimer {
		@Override
		public void setTimer(ITimerCallback callback, int eventID, long time, boolean isPeriodic) {
			// intentionally does nothing: tests drive transitions explicitly
		}

		@Override
		public void unsetTimer(ITimerCallback callback, int eventID) {
			// intentionally does nothing
		}
	}

	private ThermostatStatemachine therm;

	@BeforeEach
	void setUp() {
		therm = new ThermostatStatemachine();
		therm.setTimer(new NoOpTimer());
		SCInterfaceOperationCallback outTempCallback = () -> 5L;
		therm.getSCInterface().setSCInterfaceOperationCallback(outTempCallback);
	}

	// Verifies that init() refuses to run when no ITimer has been supplied,
	// since the statemachine depends on it to drive its internal cycles.
	@Test
	void initThrowsWithoutTimer() {
		ThermostatStatemachine noTimer = new ThermostatStatemachine();
		assertThrows(IllegalStateException.class, noTimer::init);
	}

	// Verifies that enter() cannot be called before init(), enforcing the
	// expected lifecycle order of the generated statemachine.
	@Test
	void enterThrowsBeforeInit() {
		assertThrows(IllegalStateException.class, therm::enter);
	}

	// Verifies that init() populates every configuration field (time, day/night
	// hours, temperature bounds, indoor temperature, window state) with the
	// expected default values before the machine has been entered.
	@Test
	void initSetsDefaultConfigurationValues() {
		therm.init();

		assertEquals(0, therm.getTime());
		assertEquals(0, therm.getHour());
		assertEquals(8, therm.getDay());
		assertEquals(22, therm.getNight());
		assertEquals(10, therm.getMinT());
		assertEquals(27, therm.getMaxT());
		assertEquals(21, therm.getDayT());
		assertEquals(16, therm.getNightT());
		assertEquals(15, therm.getInT());
		assertFalse(therm.getIsOpen());
	}
}
