package com.potato.mokonav.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.potato.mokonav.component.FailureBar;
import com.potato.mokonav.component.PhotoGallery;
import com.potato.mokonav.component.ProgressBar;
import com.potato.mokonav.MokoApplication;
import com.potato.mokonav.api.WSError;
import com.potato.mokonav.api.Album;
import com.potato.mokonav.api.MokoGet2Api;
import com.potato.mokonav.adapter.GalleryImageAdapter;

import com.potato.mokonav.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private Button mButton;
    private ListView mChannelsListView;
    private FailureBar mFailureBar;
    private PhotoGallery mGallery;
    private ProgressBar mProgressBar;
    private ViewFlipper mViewFlipper;
    private List<HashMap<String, Object>> getListData()
    {
        ArrayList localArrayList = new ArrayList();
        for (int i = 1;; i++)
        {
            if (i >= MokoApplication.channelsTitle.length) {
                return localArrayList;
            }
            HashMap localHashMap = new HashMap();
            localHashMap.put("channelTitle", getString(MokoApplication.channelsTitle[i]));
            localArrayList.add(localHashMap);
        }
    }
    private class NewsTask
            extends AsyncTask<Void, WSError, ArrayList<Album>>
    {
        private NewsTask() {}

        @Override
        public ArrayList<Album> doInBackground(Void... paramVarArgs)
        {
            MokoGet2Api localMokoGet2Api = new MokoGet2Api();
            try
            {
                ArrayList localArrayList = localMokoGet2Api.getClassAblums(MokoApplication.channelsURL[0], 1, "big");
                return localArrayList;
            }
            catch (WSError localWSError)
            {
                publishProgress(localWSError);
            }
            return null;
        }

        @Override
        public void onPostExecute(final ArrayList<Album> paramArrayList)
        {
            ArrayList localArrayList;
            int i;
            if ((paramArrayList != null) && (paramArrayList.size() > 0))
            {
                localArrayList = new ArrayList();
                i = 0;
                for (;;) {
                    if (i >= paramArrayList.size()) {
                        HomeActivity.this.mViewFlipper.setDisplayedChild(1);
                        GalleryImageAdapter localGalleryImageAdapter = new GalleryImageAdapter(HomeActivity.this);
                        localGalleryImageAdapter.setImageUrls(localArrayList);
                        HomeActivity.this.mGallery.setAdapter(localGalleryImageAdapter);
                        HomeActivity.this.mGallery.setSelection(1, true);/*
                        HomeActivity.this.mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
                            Intent localIntent = new Intent();
                            localIntent.setClass(HomeActivity.this, PhotoActivity.class);
                            Bundle localBundle = new Bundle();
                            localBundle.putString("url", ((Album)paramArrayList.get(paramAnonymousInt)).getUrl());
                            localIntent.putExtras(localBundle);
                            HomeActivity.this.startActivity(localIntent);
                            }
                        });*/
                        super.onPostExecute(paramArrayList);
                        return;
                    }
                    localArrayList.add(paramArrayList.get(i).getCover());
                    i++;
                }
            }
            else
            {
                HomeActivity.this.mViewFlipper.setDisplayedChild(2);
                HomeActivity.this.mFailureBar.setOnRetryListener(new View.OnClickListener()
                {
                    public void onClick(View paramAnonymousView)
                    {
                        new NewsTask().execute();
                    }
                });
                HomeActivity.this.mFailureBar.setText(R.string.connection_fail);
            }
        }

        @Override
        public void onPreExecute()
        {
            HomeActivity.this.mViewFlipper.setDisplayedChild(0);
            HomeActivity.this.mProgressBar.setText(R.string.loading);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(WSError... paramVarArgs)
        {
            Toast.makeText(HomeActivity.this, paramVarArgs[0].getMessage(), Toast.LENGTH_LONG).show();
           super.onProgressUpdate(paramVarArgs);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.mChannelsListView = ((ListView)findViewById(R.id.channelsListView));
        this.mGallery = ((PhotoGallery)findViewById(R.id.homeGallery));
        this.mProgressBar = ((ProgressBar)findViewById(R.id.homeProgressBar));
        this.mFailureBar = ((FailureBar)findViewById(R.id.homeFailureBar));
        this.mViewFlipper = ((ViewFlipper)findViewById(R.id.homeViewFlipper));
        this.mButton = ((Button)findViewById(R.id.chosenButton));
        ListAdapter localListAdapter = new ListAdapter(this, getListData(), R.layout.channel_row, new String[] { "channelTitle" }, new int[] { R.id.channelTitle });
       this.mChannelsListView.setAdapter(localListAdapter);
         this.mChannelsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
            {
                Intent localIntent = new Intent();
                localIntent.setClass(HomeActivity.this, ChannelActivity.class);
                Bundle localBundle = new Bundle();
                localBundle.putInt("channel", paramAnonymousInt + 1);
                localIntent.putExtras(localBundle);
                HomeActivity.this.startActivity(localIntent);
            }
        });
        this.mButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                Intent localIntent = new Intent();
                localIntent.setClass(HomeActivity.this, ChannelActivity.class);
                Bundle localBundle = new Bundle();
                localBundle.putInt("channel", 0);
                localIntent.putExtras(localBundle);
                HomeActivity.this.startActivity(localIntent);
            }
        });
        if (MokoApplication.isWiFiActive(this))
        {
            new NewsTask().execute();

            //new NewsTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            return;
        }
    }
    protected void onDestroy()
    {
        super.onDestroy();
        System.exit(0);
    }
    public class ListAdapter
            extends SimpleAdapter
    {
        public ListAdapter(Context context,List<HashMap<String, Object>> paramList, int paramInt, String[] paramArrayOfString, int[] paramArrayOfInt)
        {
            super(context, paramList, paramInt, paramArrayOfString, paramArrayOfInt);
        }
        public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
        {
            View localView = super.getView(paramInt, paramView, paramViewGroup);
            localView.setBackgroundColor(MokoApplication.channelsColor[(paramInt + 1)]);
            return localView;
        }
    }
}

