package com.potato.mokonav.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import com.github.droidfu.widgets.WebImageView;
import com.potato.mokonav.MokoApplication;
import com.potato.mokonav.R;
import com.potato.mokonav.api.Album;
import java.util.ArrayList;

public class GridImageAdapter
  extends BaseAdapter
{
  private ArrayList<Album> albums;
  private Context context;
  
  public GridImageAdapter(Context paramContext)
  {
    this.context = paramContext;
  }
  
  public int getCount()
  {
    return this.albums.size();
  }
  
  public Object getItem(int paramInt)
  {
    return null;
  }
  
  public long getItemId(int paramInt)
  {
    return 0L;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    WebImageView localWebImageView;
    if (paramView == null)
    {
      localWebImageView = new WebImageView(this.context, null, false);
      localWebImageView.setLayoutParams(new AbsListView.LayoutParams(150, 160));
      paramView = localWebImageView;
      ViewHolder localViewHolder = new ViewHolder();
      localViewHolder.webImageView = localWebImageView;
      paramView.setTag(localViewHolder);
      localWebImageView.reset();
      localWebImageView.setNoImageDrawable(R.drawable.logo);
    }
    else
    {
      Log.d(MokoApplication.TAG, "==========" + paramInt + ", paramView==null?? " + (paramView == null) );
      localWebImageView = ((ViewHolder)paramView.getTag()).webImageView;
/*      String imageUrl= localWebImageView.getImageUrl();
      String coverUrl = this.albums.get(paramInt).getCover();
      Boolean UrlMatchCover=imageUrl.equals(coverUrl);
      Log.d(MokoApplication.TAG, "paramView==null, ImageMatchCover= " + UrlMatchCover);
      if(UrlMatchCover!=true) {
        Log.d(MokoApplication.TAG, "imageUrl= " + imageUrl);
        Log.d(MokoApplication.TAG, "coverUrl= " + coverUrl);
      }*/
    }

      localWebImageView.setImageUrl(this.albums.get(paramInt).getCover());
      localWebImageView.loadImage();
      onGetView(paramInt, paramView, paramViewGroup);
      return paramView;
  }
  
  protected void onGetView(int paramInt, View paramView, ViewGroup paramViewGroup) {}
  
  public void setList(ArrayList<Album> paramArrayList)
  {
    this.albums = paramArrayList;
  }
  
  private static final class ViewHolder
  {
    private WebImageView webImageView;
  }
}



/* Location:           J:\android\tools\dex2jar-0.0.9.15\classes_dex2jar.jar

 * Qualified Name:     com.mchenxin.moko.adapter.GridImageAdapter

 * JD-Core Version:    0.7.0.1

 */