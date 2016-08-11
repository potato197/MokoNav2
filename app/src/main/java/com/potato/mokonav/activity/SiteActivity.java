package com.potato.mokonav.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.potato.mokonav.MokoApplication;
import com.potato.mokonav.R;
import com.potato.mokonav.adapter.GridImageAdapter;
import com.potato.mokonav.api.Album;
import com.potato.mokonav.api.Artist;
import com.potato.mokonav.api.WSError;
import com.potato.mokonav.api.MokoGet2Api;
import com.potato.mokonav.component.ArtistBar;
import com.potato.mokonav.component.FailureBar;
import com.potato.mokonav.component.ProgressBar;
import java.util.ArrayList;

public class SiteActivity
  extends Activity
{
  private Artist mArtist;
  private ArtistBar mArtistBar;
  private FailureBar mFailureBar;
  private GridView mGridView;
  private ProgressBar mProgressBar;
  private ViewFlipper mViewFlipper;
  private int page = 1;
  MokoGet2Api server = new MokoGet2Api();
  private String url;
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.site);
    this.url = getIntent().getExtras().getString("url");
    this.mViewFlipper = ((ViewFlipper)findViewById(R.id.siteViewFlipper));
    this.mProgressBar = ((ProgressBar)findViewById(R.id.siteProgressBar));
    this.mFailureBar = ((FailureBar)findViewById(R.id.siteFailureBar));
    this.mGridView = ((GridView)findViewById(R.id.siteGridView));
    this.mArtistBar = ((ArtistBar)findViewById(R.id.siteArtistBar));
    new NewsTask().execute();
  }
  
  private class NewsTask
    extends AsyncTask<Void, WSError, Artist>
  {
    private NewsTask() {}
    
    public Artist doInBackground(Void... paramVarArgs)
    {
      for (;;) {
        try {
          SiteActivity.this.mArtist = SiteActivity.this.server.getArtist(SiteActivity.this.url);
          return SiteActivity.this.mArtist;
        } catch (WSError localWSError) {
            publishProgress();
        }
      }
    }
    
    public void onPostExecute(final Artist paramArtist)
    {
      if (paramArtist != null)
      {
        SiteActivity.this.mViewFlipper.setDisplayedChild(1);
        SiteActivity.this.mArtistBar.setArtist(paramArtist);
        final GridImageAdapter localGridImageAdapter = new GridImageAdapter(SiteActivity.this);
        localGridImageAdapter.setList(paramArtist.getAblums());
        SiteActivity.this.mGridView.setAdapter(localGridImageAdapter);
        SiteActivity.this.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
          public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
          {
            Intent localIntent = new Intent();
            localIntent.setClass(SiteActivity.this, PhotoActivity.class);
            Bundle localBundle = new Bundle();
            localBundle.putString("url", (paramArtist.getAblums().get(paramAnonymousInt)).getUrl());
            localIntent.putExtras(localBundle);
            SiteActivity.this.startActivity(localIntent);
          }
        });
/*        SiteActivity.this.mGridView.setOnScrollListener(new AbsListView.OnScrollListener()
        {
          public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
          {
            int i = -1 + (paramAnonymousInt1 + paramAnonymousInt2);
            if ((paramAnonymousInt1 + paramAnonymousInt2 == paramAnonymousInt3) && (paramAnonymousInt3 < paramArtist.getAblumsSum()))
            {
              SiteActivity localSiteActivity = SiteActivity.this;
              localSiteActivity.page = (1 + localSiteActivity.page);
              for (;;) {
                try {
                  SiteActivity.this.server.updateAritstAlbums(SiteActivity.this.url, SiteActivity.this.page, paramArtist.getAblums());
                  localGridImageAdapter.setList(paramArtist.getAblums());
                  localGridImageAdapter.notifyDataSetChanged();
                  SiteActivity.this.mGridView.setSelection(i);
                  return;
                } catch (WSError localWSError) {
                  localWSError.printStackTrace();
                }
              }
            }

          }
          
          public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt) {}
        });*/
      }
      else {
        SiteActivity.this.mViewFlipper.setDisplayedChild(2);
        SiteActivity.this.mFailureBar.setOnRetryListener(new View.OnClickListener() {
          public void onClick(View paramAnonymousView) {
            new SiteActivity.NewsTask().execute();
          }
        });
        SiteActivity.this.mFailureBar.setText(R.string.connection_fail);
      }
      super.onPostExecute(paramArtist);
    }
    
    public void onPreExecute()
    {
      SiteActivity.this.mViewFlipper.setDisplayedChild(0);
      SiteActivity.this.mProgressBar.setText(R.string.loading);
      super.onPreExecute();
    }
    
    protected void onProgressUpdate(WSError... paramVarArgs)
    {
      Toast.makeText(SiteActivity.this, paramVarArgs[0].getMessage(), Toast.LENGTH_SHORT).show();
      super.onProgressUpdate(paramVarArgs);
    }
  }
}



/* Location:           J:\android\tools\dex2jar-0.0.9.15\classes_dex2jar.jar

 * Qualified Name:     com.mchenxin.moko.activity.SiteActivity

 * JD-Core Version:    0.7.0.1

 */