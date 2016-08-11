package com.potato.mokonav.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.potato.mokonav.MokoApplication;
import com.potato.mokonav.R;
import com.potato.mokonav.adapter.GridImageAdapter;
import com.potato.mokonav.api.Album;
import com.potato.mokonav.api.WSError;
import com.potato.mokonav.api.MokoGet2Api;
import com.potato.mokonav.component.FailureBar;
import com.potato.mokonav.component.ProgressBar;
import java.util.ArrayList;

public class ChannelActivity
  extends Activity
{
  private ArrayList<Album> albums = null;
  private GridView gridView;
  private int id = 0;
  private FailureBar mFailureBar;
  private ProgressBar mProgressBar;
  private ViewFlipper mViewFlipper;
  private Button nextButton;
  private int page = 1;
  private TextView pageTextView;
  private Button preButton;
  private RelativeLayout relativeLayout;
  private Spinner spinner;
  private ArrayAdapter<String> spinnerAdapter;
  private String[] spinnerData = new String[MokoApplication.channelsTitle.length];
  
  public boolean onContextItemSelected(MenuItem paramMenuItem)
  {
    AdapterView.AdapterContextMenuInfo localAdapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)paramMenuItem.getMenuInfo();
    switch (paramMenuItem.getItemId())
    {
      case 0:
        if (localAdapterContextMenuInfo == null) {
          break;
        }
        String str = "http://www.moko.cc/post/" + this.albums.get(localAdapterContextMenuInfo.position).getAuthor() + "/new/1.html";
        Intent localIntent = new Intent();
        localIntent.setClass(this, SiteActivity.class);
        Bundle localBundle = new Bundle();
        localBundle.putString("url", str);
        localIntent.putExtras(localBundle);
        startActivity(localIntent);
        break;
    }
    return super.onContextItemSelected(paramMenuItem);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.channel);
    this.id = getIntent().getExtras().getInt("channel");
    this.spinner = ((Spinner)findViewById(R.id.spinner));
    this.mViewFlipper = ((ViewFlipper)findViewById(R.id.channelViewFlipper));
    this.mProgressBar = ((ProgressBar)findViewById(R.id.channelProgressBar));
    this.mFailureBar = ((FailureBar)findViewById(R.id.channelFailureBar));
    this.gridView = ((GridView)findViewById(R.id.channelGridView));
    this.relativeLayout = ((RelativeLayout)findViewById(R.id.relativeLayout));
    this.relativeLayout.setBackgroundColor(MokoApplication.channelsColor[this.id]);
    this.preButton = ((Button)findViewById(R.id.pre));
    this.nextButton = ((Button)findViewById(R.id.next));
    this.pageTextView = ((TextView)findViewById(R.id.page));
    registerForContextMenu(this.gridView);
    for (int i = 0;; i++)
    {
      if (i >= MokoApplication.channelsTitle.length)
      {
        this.spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.spinnerData);
        this.spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        this.spinner.setAdapter(this.spinnerAdapter);
        this.spinner.setSelection(this.id);
       // new NewsTask().execute(new Void[] { null });
        this.pageTextView.setText(this.page + "/20");
        this.preButton.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (ChannelActivity.this.page == 1)
            {
              Toast.makeText(ChannelActivity.this, ChannelActivity.this.getString(R.string.firstpage), Toast.LENGTH_SHORT).show();
              return;
            }
            ChannelActivity localChannelActivity = ChannelActivity.this;
            localChannelActivity.page = (-1 + localChannelActivity.page);
            new ChannelActivity.NewsTask().execute(new Void[] { null });
            ChannelActivity.this.pageTextView.setText(ChannelActivity.this.page + "/20");
          }
        });
        this.nextButton.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (ChannelActivity.this.page == 20)
            {
              Toast.makeText(ChannelActivity.this, ChannelActivity.this.getString(R.string.lastpage),  Toast.LENGTH_SHORT).show();
              return;
            }
            ChannelActivity localChannelActivity = ChannelActivity.this;
            localChannelActivity.page = (1 + localChannelActivity.page);
            new ChannelActivity.NewsTask().execute(new Void[] { null });
            ChannelActivity.this.pageTextView.setText(ChannelActivity.this.page + "/20");
          }
        });
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
          public void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
          {
            ChannelActivity.this.id = paramAnonymousInt;
            ChannelActivity.this.relativeLayout.setBackgroundColor(MokoApplication.channelsColor[ChannelActivity.this.id]);
            ChannelActivity.this.page = 1;
            ChannelActivity.this.pageTextView.setText(ChannelActivity.this.page + "/20");
            new ChannelActivity.NewsTask().execute(new Void[] { null });
          }
          
          public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView) {}
        });
        return;
      }
      this.spinnerData[i] = getString(MokoApplication.channelsTitle[i]);
    }
  }
  
  public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo)
  {
    AdapterView.AdapterContextMenuInfo localAdapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)paramContextMenuInfo;
    if (this.albums.get(localAdapterContextMenuInfo.position).getAuthor() != null)
    {
      paramContextMenu.setHeaderIcon(R.drawable.logo1);
      paramContextMenu.setHeaderTitle(R.string.site);
      paramContextMenu.add(0, 0, 0, this.albums.get(localAdapterContextMenuInfo.position).getAuthor());
    }
  }
  
  private class NewsTask
    extends AsyncTask<Void, WSError, ArrayList<Album>>
  {
    private NewsTask() {}
    
    public ArrayList<Album> doInBackground(Void... paramVarArgs)
    {
      MokoGet2Api localMokoGet2Api = new MokoGet2Api();
      ChannelActivity.this.albums = null;
     for(;;)
      {
        try
        {
          if (ChannelActivity.this.id == 0)
          {
            ChannelActivity.this.albums = localMokoGet2Api.getClassAblums(MokoApplication.channelsURL[0], ChannelActivity.this.page, "big");
          }
          else {
            ChannelActivity.this.albums = localMokoGet2Api.getClassAblums(MokoApplication.channelsURL[ChannelActivity.this.id], ChannelActivity.this.page, "small");
          }
          return ChannelActivity.this.albums;
        }
        catch (WSError localWSError)
        {
            publishProgress(localWSError);
        }
     }
    }
    
    public void onPostExecute(final ArrayList<Album> paramArrayList)
    {
      if ((paramArrayList != null) && (paramArrayList.size() > 0))
      {
        ChannelActivity.this.mViewFlipper.setDisplayedChild(1);
        GridImageAdapter localGridImageAdapter = new GridImageAdapter(ChannelActivity.this);
        localGridImageAdapter.setList(paramArrayList);
        ChannelActivity.this.gridView.setAdapter(localGridImageAdapter);
        ChannelActivity.this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
          public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
          {
            Intent localIntent = new Intent();
            localIntent.setClass(ChannelActivity.this, PhotoActivity.class);
            Bundle localBundle = new Bundle();
            localBundle.putString("url", paramArrayList.get(paramAnonymousInt).getUrl());
            localIntent.putExtras(localBundle);
            ChannelActivity.this.startActivity(localIntent);
          }
        });
      }
      else
      {
        ChannelActivity.this.mViewFlipper.setDisplayedChild(2);
        ChannelActivity.this.mFailureBar.setOnRetryListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            new ChannelActivity.NewsTask().execute(new Void[] { null });
          }
        });
        ChannelActivity.this.mFailureBar.setText(R.string.connection_fail);
      }
      super.onPostExecute(paramArrayList);
    }
    
    public void onPreExecute()
    {
      ChannelActivity.this.mViewFlipper.setDisplayedChild(0);
      ChannelActivity.this.mProgressBar.setText(R.string.loading);
      super.onPreExecute();
    }
    
    protected void onProgressUpdate(WSError... paramVarArgs)
    {
      Toast.makeText(ChannelActivity.this, paramVarArgs[0].getMessage(),Toast.LENGTH_SHORT).show();
      super.onProgressUpdate(paramVarArgs);
    }
  }
}



/* Location:           J:\android\tools\dex2jar-0.0.9.15\classes_dex2jar.jar

 * Qualified Name:     com.mchenxin.moko.activity.ChannelActivity

 * JD-Core Version:    0.7.0.1

 */