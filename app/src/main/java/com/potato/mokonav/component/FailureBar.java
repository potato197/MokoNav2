package com.potato.mokonav.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.potato.mokonav.R;

public class FailureBar
  extends LinearLayout
{
  protected Button mRetryButton;
  protected TextView mTextView;
  
  public FailureBar(Context paramContext)
  {
    super(paramContext);
    init();
  }
  
  public FailureBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }
  
  private void init()
  {
    LayoutInflater.from(getContext()).inflate(R.layout.failure_bar, this);
    this.mTextView = ((TextView)findViewById(R.id.ProgressTextView));
    this.mRetryButton = ((Button)findViewById(R.id.RetryButton));
  }
  
  public void setOnRetryListener(View.OnClickListener paramOnClickListener)
  {
    this.mRetryButton.setOnClickListener(paramOnClickListener);
  }
  
  public void setText(int paramInt)
  {
    this.mTextView.setText(paramInt);
  }
}



/* Location:           J:\android\tools\dex2jar-0.0.9.15\classes_dex2jar.jar

 * Qualified Name:     com.mchenxin.moko.widget.FailureBar

 * JD-Core Version:    0.7.0.1

 */