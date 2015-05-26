package com.avi.ejjabberd.utils;

import android.app.Application;
import android.os.Handler;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AbstractListeningExecutorService;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class UiThreadExecutor extends AbstractListeningExecutorService
{
  private final Handler uiHandler;

  public UiThreadExecutor(Application paramApplication)
  {
    this.uiHandler = new Handler(paramApplication.getMainLooper());
  }

  public boolean awaitTermination(long paramLong, TimeUnit paramTimeUnit)
      throws InterruptedException
  {
    return false;
  }

  public void execute(Runnable paramRunnable)
  {
    this.uiHandler.post(paramRunnable);
  }

  public boolean isShutdown()
  {
    return false;
  }

  public boolean isTerminated()
  {
    return false;
  }

  public void shutdown()
  {
  }

  public List<Runnable> shutdownNow()
  {
    return Lists.newArrayList();
  }
}
