package com.potato.mokonav.api;

import java.util.ArrayList;

public class Artist
{
  private ArrayList<Album> ablums;
  private int ablumsSum = 0;
  private String[] duty;
  private String logo;
  private String name;
  private String rank;
  
  public ArrayList<Album> getAblums()
  {
    return this.ablums;
  }
  
  public int getAblumsSum()
  {
    return this.ablumsSum;
  }
  
  public String[] getDuty()
  {
    return this.duty;
  }
  
  public String getLogo()
  {
    return this.logo;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getRank()
  {
    return this.rank;
  }
  
  public void setAblums(ArrayList<Album> paramArrayList)
  {
    this.ablums = paramArrayList;
  }
  
  public void setAblumsSum(int paramInt)
  {
    this.ablumsSum = paramInt;
  }
  
  public void setDuty(String[] paramArrayOfString)
  {
    this.duty = paramArrayOfString;
  }
  
  public void setLogo(String paramString)
  {
    this.logo = paramString;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public void setRank(String paramString)
  {
    this.rank = paramString;
  }
}



/* Location:           J:\android\tools\dex2jar-0.0.9.15\classes_dex2jar.jar

 * Qualified Name:     com.mchenxin.moko.api.Artist

 * JD-Core Version:    0.7.0.1

 */