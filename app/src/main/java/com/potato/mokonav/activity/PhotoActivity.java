package com.potato.mokonav.activity;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.github.droidfu.adapters.WebGalleryAdapter;
import com.potato.mokonav.MokoApplication;
import com.potato.mokonav.R;
import com.potato.mokonav.api.WSError;
import com.potato.mokonav.api.MokoGet2Api;
import com.potato.mokonav.component.FailureBar;
import com.potato.mokonav.component.PhotoGallery;

import java.io.IOException;
import java.util.ArrayList;

public class PhotoActivity
  extends Activity
{
  private FailureBar mFailureBar;
  private PhotoGallery mGallery;
  int mPosition;
  private ViewFlipper mViewFlipper;
  private ArrayList<String> photos = null;
  private String url = null;

  public boolean onContextItemSelected(MenuItem paramMenuItem)
  {
    AdapterView.AdapterContextMenuInfo localAdapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)paramMenuItem.getMenuInfo();
    if (localAdapterContextMenuInfo == null) {
      return super.onContextItemSelected(paramMenuItem);
    }
    switch (paramMenuItem.getItemId())
    {
      case 0:
        try
        {
          WallpaperManager localWallpaperManager = WallpaperManager.getInstance(this);
          int i = localWallpaperManager.getDesiredMinimumWidth();
          int j = localWallpaperManager.getDesiredMinimumHeight();
          localWallpaperManager.clear();
          localWallpaperManager.setBitmap(MokoApplication.resizeBmp(this.photos.get(localAdapterContextMenuInfo.position % this.photos.size()), i, j));
          Toast.makeText(this, getString(R.string.setsuccess), Toast.LENGTH_SHORT).show();
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
          Toast.makeText(this, getString(R.string.setfailed), Toast.LENGTH_SHORT).show();
        }
        break;
      case 1:
        if (MokoApplication.saveToFile(getString(R.string.app_folder), this.photos.get(localAdapterContextMenuInfo.position % this.photos.size()))) {
          Toast.makeText(this, getString(R.string.savesuccess), Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(this, getString(R.string.savefailed), Toast.LENGTH_SHORT).show();
        }
        break;
    }
    return super.onContextItemSelected(paramMenuItem);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.photo);
    this.url = getIntent().getExtras().getString("url");
    this.mViewFlipper = ((ViewFlipper)findViewById(R.id.photoViewFlipper));
    this.mFailureBar = ((FailureBar)findViewById(R.id.photoFailureBar));
    this.mGallery = ((PhotoGallery)findViewById(R.id.photoGallery));
    registerForContextMenu(this.mGallery);
    new NewsTask().execute();
  }

  public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo)
  {
    if (Environment.getExternalStorageState().equals("mounted"))
    {
      paramContextMenu.setHeaderTitle("");
      paramContextMenu.add(0, 0, 0, R.string.setbackgroud);
      paramContextMenu.add(0, 1, 1, R.string.savepic);
      return;
    }
    paramContextMenu.setHeaderTitle("");
    paramContextMenu.add(0, 0, 0, R.string.setbackgroud);
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(R.menu.phonemenu, paramMenu);
    return true;
  }

/*
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    }
    for (;;)
    {
      return true;
      if (this.mPosition == 0)
      {
        Toast.makeText(this, getString(R.string.firstpage), Toast.LENGTH_SHORT).show();
      }
      else
      {
        PhotoGallery localPhotoGallery2 = this.mGallery;
        int m = -1 + this.mPosition;
        this.mPosition = m;
        localPhotoGallery2.setSelection(m);
        continue;
        if (MokoApplication.saveToFile(getString(R.string.app_folder), (String)this.photos.get(this.mPosition % this.photos.size())))
        {
          Toast.makeText(this, getString(R.string.savesuccess), Toast.LENGTH_SHORT).show();
        }
        else
        {
          Toast.makeText(this, getString(R.string.savefailed), Toast.LENGTH_SHORT).show();
          continue;
          try
          {
            WallpaperManager localWallpaperManager = WallpaperManager.getInstance(this);
            int j = localWallpaperManager.getDesiredMinimumWidth();
            int k = localWallpaperManager.getDesiredMinimumHeight();
            localWallpaperManager.clear();
            localWallpaperManager.setBitmap(MokoApplication.resizeBmp(this.photos.get(this.mPosition % this.photos.size()), j, k));
            Toast.makeText(this, getString(R.string.setsuccess), Toast.LENGTH_SHORT).show();
          }
          catch (IOException localIOException)
          {
            localIOException.printStackTrace();
            Toast.makeText(this, getString(R.string.setfailed), Toast.LENGTH_SHORT).show();
          }
          continue;
          if (this.mPosition == -1 + this.photos.size())
          {
            Toast.makeText(this, getString(R.string.lastpage), Toast.LENGTH_SHORT).show();
          }
          else
          {
            PhotoGallery localPhotoGallery1 = this.mGallery;
            int i = 1 + this.mPosition;
            this.mPosition = i;
            localPhotoGallery1.setSelection(i);
          }
        }
      }
    }
  }
*/

  private class NewsTask
    extends AsyncTask<Void, WSError, ArrayList<String>>
  {
    private NewsTask() {}

    public ArrayList<String> doInBackground(Void... paramVarArgs)
    {
      MokoGet2Api localMokoGet2Api = new MokoGet2Api();
      for (;;) {
        try {
          PhotoActivity.this.photos = localMokoGet2Api.getPhotos(PhotoActivity.this.url);
          return PhotoActivity.this.photos;
        } catch (WSError localWSError) {
            publishProgress(localWSError);
        }
      }
    }

    public void onPostExecute(final ArrayList<String> paramArrayList)
    {
      if ((paramArrayList != null) && (paramArrayList.size() > 0))
      {
        PhotoActivity.this.mViewFlipper.setDisplayedChild(1);
        WebGalleryAdapter localWebGalleryAdapter = new WebGalleryAdapter(PhotoActivity.this);
        localWebGalleryAdapter.setImageUrls(paramArrayList);
        PhotoActivity.this.mGallery.setAdapter(localWebGalleryAdapter);
        PhotoActivity.this.mGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
          public void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
          {
            PhotoActivity.this.mPosition = paramAnonymousInt;
            PhotoActivity.this.setTitle(PhotoActivity.this.getString(R.string.app_name) + "(" + (1 + paramAnonymousInt % paramArrayList.size()) + "/" + paramArrayList.size() + ")");
          }
          public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView) {}
        });
      }
      else
      {
        PhotoActivity.this.mViewFlipper.setDisplayedChild(2);
        PhotoActivity.this.mFailureBar.setOnRetryListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            new PhotoActivity.NewsTask().execute();
          }
        });
        PhotoActivity.this.mFailureBar.setText(R.string.connection_fail);
      }
      super.onPostExecute(paramArrayList);
    }

    public void onPreExecute()
    {
      PhotoActivity.this.mViewFlipper.setDisplayedChild(0);
      super.onPreExecute();
    }

    protected void onProgressUpdate(WSError... paramVarArgs)
    {
      Toast.makeText(PhotoActivity.this, paramVarArgs[0].getMessage(), Toast.LENGTH_SHORT).show();
      super.onProgressUpdate(paramVarArgs);
    }
  }
}



/* Location:           J:\android\tools\dex2jar-0.0.9.15\classes_dex2jar.jar

 * Qualified Name:     com.mchenxin.moko.activity.PhotoActivity

 * JD-Core Version:    0.7.0.1

 */