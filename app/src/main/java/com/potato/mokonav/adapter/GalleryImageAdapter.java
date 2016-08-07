package com.potato.mokonav.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;

import com.github.droidfu.adapters.WebGalleryAdapter;
import com.github.droidfu.widgets.WebImageView;

import java.util.List;

import com.potato.mokonav.R;

public class GalleryImageAdapter
  extends WebGalleryAdapter
{
  private Context context;
  private List<String> imageUrls;
  
  public GalleryImageAdapter(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
  }
  
  public int getCount()
  {
    return this.imageUrls.size();
  }
  
  public List<String> getImageUrls()
  {
    return this.imageUrls;
  }
  
  public Object getItem(int paramInt)
  {
    return Integer.valueOf(paramInt);
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    WebImageView localWebImageView;
    if (paramView == null)
    {
      localWebImageView = new WebImageView(this.context, null, true);
      localWebImageView.setLayoutParams(new Gallery.LayoutParams(164, 175));
      paramView = localWebImageView;
      ViewHolder localViewHolder = new ViewHolder();
      localViewHolder.webImageView = localWebImageView;
      paramView.setTag(localViewHolder);
      localWebImageView.reset();
      localWebImageView.setNoImageDrawable(R.drawable.logo);
    }
    else
    {
      localWebImageView = ((ViewHolder)paramView.getTag()).webImageView;
    }

    localWebImageView.setImageUrl(this.imageUrls.get(paramInt));
    localWebImageView.loadImage();
    onGetView(paramInt, paramView, paramViewGroup);
    return paramView;
  }
  
  protected void onGetView(int paramInt, View paramView, ViewGroup paramViewGroup) {}
  
  public void setImageUrls(List<String> paramList)
  {
    this.imageUrls = paramList;
  }
  
  private static final class ViewHolder
  {
    private WebImageView webImageView;
  }
}



/* Location:           J:\android\tools\dex2jar-0.0.9.15\classes_dex2jar.jar

 * Qualified Name:     com.mchenxin.moko.adapter.GalleryImageAdapter

 * JD-Core Version:    0.7.0.1

 */