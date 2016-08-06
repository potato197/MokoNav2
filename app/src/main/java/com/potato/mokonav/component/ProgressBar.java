package com.potato.mokonav.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.potato.mokonav.R;

public class ProgressBar
  extends LinearLayout
{
  protected TextView mTextView;
  
  public ProgressBar(Context paramContext)
  {
    super(paramContext);
    init();
  }
  
  public ProgressBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }
  
  private void init()
  {
    LayoutInflater.from(getContext()).inflate(R.layout.progress_bar, this);
    this.mTextView = ((TextView)findViewById(R.id.ProgressTextView1));
  }
  
  public void setText(int paramInt)
  {
    this.mTextView.setText(paramInt);
  }
}



/* Location:           J:\android\tools\dex2jar-0.0.9.15\classes_dex2jar.jar

 * Qualified Name:     com.mchenxin.moko.widget.ProgressBar

 * JD-Core Version:    0.7.0.1

 */