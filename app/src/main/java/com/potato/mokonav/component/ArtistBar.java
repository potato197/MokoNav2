package com.potato.mokonav.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.droidfu.widgets.WebImageView;
import com.potato.mokonav.api.Artist;
import com.potato.mokonav.R;

public class ArtistBar
  extends LinearLayout
{
  private TextView mAlbumSumTextView;
  private TextView mArtistDutyTextView;
  private WebImageView mArtistImageView;
  private TextView mArtistTextView;
  private Context mContext;
  
  public ArtistBar(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    init();
  }
  
  public ArtistBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    init();
  }
  
  public void init()
  {
    LayoutInflater.from(getContext()).inflate(R.layout.artist_bar, this);
    this.mArtistTextView = ((TextView)findViewById(R.id.ArtistTextView));
    this.mAlbumSumTextView = ((TextView)findViewById(R.id.AlbumSumTextView));
    this.mArtistImageView = ((WebImageView)findViewById(R.id.ArtistImageView));
    this.mArtistDutyTextView = ((TextView)findViewById(R.id.ArtistDutyTextView));
  }
  
  public void setArtist(Artist paramArtist)
  {
    this.mArtistTextView.setText(paramArtist.getName() + "  (" + paramArtist.getRank() + ")");
    this.mAlbumSumTextView.setText(this.mContext.getString(R.string.post) + "(" + paramArtist.getAblumsSum() + ")");
    this.mArtistImageView.reset();
    this.mArtistImageView.setImageUrl(paramArtist.getLogo());
    this.mArtistImageView.loadImage();
    for (int i = 0;; i++)
    {
      if (i >= paramArtist.getDuty().length) {
        return;
      }
      this.mArtistDutyTextView.setText(this.mArtistDutyTextView.getText() + " " + paramArtist.getDuty()[i]);
    }
  }
}



/* Location:           J:\android\tools\dex2jar-0.0.9.15\classes_dex2jar.jar

 * Qualified Name:     com.mchenxin.moko.widget.ArtistBar

 * JD-Core Version:    0.7.0.1

 */