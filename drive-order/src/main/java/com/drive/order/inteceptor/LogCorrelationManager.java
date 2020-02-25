package com.drive.order.inteceptor;

public class LogCorrelationManager {
	public static final String CORRELATION_ID_KEY = "X-Correlation-ID";
	private static ThreadLocal<LogCorrelationSequencer> lastCorrelationSequencer = new ThreadLocal();
	private static LogCorrelationSequencer defaultSequencer = new LogCorrelationSequencer();

	public LogCorrelationManager() {
	}

	public static String nextCorrelationId() {
		LogCorrelationSequencer seq = (LogCorrelationSequencer)lastCorrelationSequencer.get();
		return seq == null ? defaultSequencer.incAndGetId() : seq.incAndGetId();
	}

	public static String lastCorrelationId() {
		LogCorrelationSequencer seq = (LogCorrelationSequencer)lastCorrelationSequencer.get();
		return seq == null ? defaultSequencer.getId() : seq.getId();
	}

	public static void bindThreadNewCorrelationId() {
		lastCorrelationSequencer.set(new LogCorrelationSequencer());
	}

	public static void bindThreadSubCorrelationId(String parentCorrelationId) {
		lastCorrelationSequencer.set(new LogCorrelationSequencer(parentCorrelationId, true));
	}

	public static void bindThreadLastCorrelationId(String lastCorrelationId) {
		lastCorrelationSequencer.set(new LogCorrelationSequencer(lastCorrelationId, false));
	}

	public static boolean isThreadBinded() {
		return lastCorrelationSequencer.get() != null;
	}

	public static void unbindThread() {
		lastCorrelationSequencer.remove();
	}
}
