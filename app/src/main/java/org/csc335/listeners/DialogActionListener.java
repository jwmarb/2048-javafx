package org.csc335.listeners;

public interface DialogActionListener {
  public void dialogHidden();

  public void dialogShown();

  public void dialogAction(int actionIdx);
}
