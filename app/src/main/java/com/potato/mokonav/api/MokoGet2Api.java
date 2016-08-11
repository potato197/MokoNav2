package com.potato.mokonav.api;

import com.potato.mokonav.MokoApplication;
import com.potato.mokonav.api.Artist;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MokoGet2Api
{
  public ArrayList<Album> getAritstAlbums(String paramString)
    throws WSError
  {
    ArrayList localArrayList = new ArrayList();
    Matcher localMatcher1 = Pattern.compile("<div class=\"coverBox bd\">.*?</div>", Pattern.DOTALL).matcher(paramString);
    for (;;)
    {
      if (!localMatcher1.find()) {
        return localArrayList;
      }
      String str = localMatcher1.group();
      Album localAlbum = new Album();
      Matcher localMatcher2 = Pattern.compile("/post/.*?.html", Pattern.DOTALL).matcher(str);
      if (localMatcher2.find()) {
        localAlbum.setUrl("http://www.moko.cc" + localMatcher2.group());
      }
      Matcher localMatcher3 = Pattern.compile("http://img.*?.jpg", Pattern.DOTALL).matcher(str);
      if (localMatcher3.find()) {
        localAlbum.setCover(localMatcher3.group());
      }
      localArrayList.add(localAlbum);
    }
  }
  
  public Artist getArtist(String paramString)
    throws WSError
  {
    Artist localArtist = new Artist();
    String str1 = Caller.doGet(paramString);
    //Matcher localMatcher1 = Pattern.compile("http://img.*?.jpg", Pattern.DOTALL).matcher(str1);
    Matcher localLogoMatcher1 = Pattern.compile("<img id=\"imgUserLogo\" class=\"bd\" src=\"(http://img.*?.jpg)\"", Pattern.DOTALL).matcher(str1);
    if(localLogoMatcher1.find())
    {
      String str2 = localLogoMatcher1.group(1);
      localArtist.setLogo(str2);
    }

    Matcher localMatcher2 = Pattern.compile("<h1 class=\"name\">.*?</h1>", Pattern.DOTALL).matcher(str1);
    if (localMatcher2.find()) {
      String[] arrayOfString = localMatcher2.group().replaceAll("\\n|\\t", "").replaceAll("<.*?>", " ").trim().split("  ");
      localArtist.setName(arrayOfString[0]);
      localArtist.setRank(arrayOfString[1]);
    }

    Matcher localMatcher3 = Pattern.compile("<h2 class=\"duty\">.*?</h2>", Pattern.DOTALL).matcher(str1);
    if (localMatcher3.find()) {
      localArtist.setDuty(localMatcher3.group().replaceAll("\\n|\\t", "").replaceAll("<div.*?div>", "").replaceAll("<.*?>", " ").trim().split(" "));
    }

    Matcher localMatcher4 = Pattern.compile("<a id=\"a_post\".*?</a>", Pattern.DOTALL).matcher(str1);
    if (localMatcher4.find()) {
      localArtist.setAblumsSum(Integer.parseInt(localMatcher4.group().substring(1 + localMatcher4.group().indexOf("("), localMatcher4.group().indexOf(")"))));
    }

    localArtist.setAblums(getAritstAlbums(str1));
    return localArtist;
  }
  
  public ArrayList<Album> getClassAblums(String paramString1, int paramInt, String paramString2)
    throws WSError
  {
    if (paramInt != 1) {
      paramString1 = paramString1.replaceAll("1.html", paramInt + ".html");
    }
    String str1 = Caller.doGet(paramString1);
    ArrayList localArrayList = new ArrayList();
    String str2 = null;
    if (paramString2 == "big") {
      str2 = "<ul class=\"post big-post\">.*?</ul>";
    }
    if (paramString2 == "small") {
      str2 = "<ul class=\"post small-post\">.*?</ul>";
    }
    Matcher localMatcher1 = Pattern.compile(str2,  Pattern.DOTALL).matcher(str1);
    for (;;)
    {
      if (!localMatcher1.find()) {
        return localArrayList;
      }
      String str3 = localMatcher1.group();
      Album localAlbum = new Album();
      Matcher localMatcher2 = Pattern.compile("/post/.*?.html",  Pattern.DOTALL).matcher(str3);
      if (localMatcher2.find()) {
        localAlbum.setUrl("http://www.moko.cc" + localMatcher2.group());
      }
      Matcher localMatcher3 = Pattern.compile("http://img.*?.jpg",  Pattern.DOTALL).matcher(str3);
      if (localMatcher3.find()) {
        localAlbum.setCover(localMatcher3.group());
      }
      Matcher localMatcher4 = Pattern.compile("</label><a href=\"/.*?/\"",  Pattern.DOTALL).matcher(str3);
      if (localMatcher4.find()) {
        localAlbum.setAuthor(localMatcher4.group().split("/")[2]);
      }
      localArrayList.add(localAlbum);
    }
  }
  
  public ArrayList<String> getPhotos(String paramString)
    throws WSError
  {
   // String str1 = Caller.doGet(MokoApplication.login + paramString);
    String str1 = Caller.doGet(paramString);
    ArrayList localArrayList = new ArrayList();
    Matcher localMatcher = Pattern.compile(" src2=\"http://img[a-zA-Z_0-9]*.*?.jpg", Pattern.DOTALL).matcher(str1);
    for (;;)
    {
      if (!localMatcher.find()) {
        return localArrayList;
      }

      String str2 = localMatcher.group();
      Matcher localMatcher2 = Pattern.compile("http://img[a-zA-Z_0-9]*.*?.jpg", Pattern.DOTALL).matcher(str2);
      if (!localMatcher2.find()) {
        return localArrayList;
      }
      String str3 = localMatcher2.group();
      localArrayList.add(str3);
    }
  }
  
  public void updateAritstAlbums(String paramString, int paramInt, ArrayList<Album> paramArrayList)
    throws WSError
  {
/*    if (paramInt != 1) {
      paramString = paramString.replace("1/postsortid.html", paramInt + "/postsortid.html");
    }*/
    paramString = paramString.replaceAll("\\d+\\.html",paramInt+".html");
    paramArrayList.addAll(getAritstAlbums(Caller.doGet(paramString)));
  }
}



/* Location:           J:\android\tools\dex2jar-0.0.9.15\classes_dex2jar.jar

 * Qualified Name:     com.mchenxin.moko.api.util.MokoGet2Api

 * JD-Core Version:    0.7.0.1

 */