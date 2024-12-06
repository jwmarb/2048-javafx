package org.csc335.interfaces;

import java.time.Duration;

public interface TimerListener {
  public void timerChanged(Duration timeLeft);
}
