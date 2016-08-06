package com.potato.mokonav.api;

import android.util.Log;

import com.potato.mokonav.MokoApplication;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Caller
{
  private static RequestCache requestCache = null;
  
  /* Error */
  private static String convertStreamToString(java.io.InputStream paramInputStream)
  {
    BufferedReader reader = new BufferedReader(new InputStreamReader(paramInputStream));
    StringBuilder sb = new StringBuilder();

    String line = null;

    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        paramInputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return sb.toString();
    // Byte code:
    //   0: new 19	java/io/BufferedReader
    //   3: dup
    //   4: new 21	java/io/InputStreamReader
    //   7: dup
    //   8: aload_0
    //   9: invokespecial 24	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   12: invokespecial 27	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   15: astore_1
    //   16: new 29	java/lang/StringBuilder
    //   19: dup
    //   20: invokespecial 30	java/lang/StringBuilder:<init>	()V
    //   23: astore_2
    //   24: aload_1
    //   25: invokevirtual 34	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   28: astore 7
    //   30: aload 7
    //   32: ifnonnull +12 -> 44
    //   35: aload_0
    //   36: invokevirtual 39	java/io/InputStream:close	()V
    //   39: aload_2
    //   40: invokevirtual 42	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   43: areturn
    //   44: aload_2
    //   45: new 29	java/lang/StringBuilder
    //   48: dup
    //   49: aload 7
    //   51: invokestatic 48	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   54: invokespecial 51	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   57: ldc 53
    //   59: invokevirtual 57	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   62: invokevirtual 42	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   65: invokevirtual 57	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   68: pop
    //   69: goto -45 -> 24
    //   72: astore 5
    //   74: aload 5
    //   76: invokevirtual 60	java/io/IOException:printStackTrace	()V
    //   79: aload_0
    //   80: invokevirtual 39	java/io/InputStream:close	()V
    //   83: goto -44 -> 39
    //   86: astore 6
    //   88: aload 6
    //   90: invokevirtual 60	java/io/IOException:printStackTrace	()V
    //   93: goto -54 -> 39
    //   96: astore_3
    //   97: aload_0
    //   98: invokevirtual 39	java/io/InputStream:close	()V
    //   101: aload_3
    //   102: athrow
    //   103: astore 4
    //   105: aload 4
    //   107: invokevirtual 60	java/io/IOException:printStackTrace	()V
    //   110: goto -9 -> 101
    //   113: astore 9
    //   115: aload 9
    //   117: invokevirtual 60	java/io/IOException:printStackTrace	()V
    //   120: goto -81 -> 39
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	123	0	paramInputStream	java.io.InputStream
    //   15	10	1	localBufferedReader	java.io.BufferedReader
    //   23	22	2	localStringBuilder	java.lang.StringBuilder
    //   96	6	3	localObject	Object
    //   103	3	4	localIOException1	IOException
    //   72	3	5	localIOException2	IOException
    //   86	3	6	localIOException3	IOException
    //   28	22	7	str	String
    //   113	3	9	localIOException4	IOException
    // Exception table:
    //   from	to	target	type
    //   24	30	72	java/io/IOException
    //   44	69	72	java/io/IOException
    //   79	83	86	java/io/IOException
    //   24	30	96	finally
    //   44	69	96	finally
    //   74	79	96	finally
    //   97	101	103	java/io/IOException
    //   35	39	113	java/io/IOException
  }
  
  public static String createStringFromIds(int[] paramArrayOfInt)
  {
    String str="";
    if (paramArrayOfInt == null) {
      str = "";
    }
    for (;;)
    {
      str = "";
      int i = paramArrayOfInt.length;
      for (int j = 0; j < i; j++)
      {
        int k = paramArrayOfInt[j];
        str = str + k + "+";
      }
      return str;
    }
  }
  
  public static String doGet(String paramString)
    throws WSError
  {
    RequestCache localRequestCache = requestCache;
    String str = null;
    if (localRequestCache != null)
    {
      str = requestCache.get(paramString);
      if (str != null)
      {
        Log.d(MokoApplication.TAG, "Caller.doGet [cached] " + paramString);
        return str;
      }
    }
    Log.d(MokoApplication.TAG, "Caller.doGet [begin]" + paramString);
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
    HttpGet localHttpGet = new HttpGet(paramString);
    try
    {
      HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpGet);
      HttpEntity localHttpEntity = localHttpResponse.getEntity();
      if (localHttpEntity != null)
      {
        str = convertStreamToString(localHttpEntity.getContent());
        if (requestCache != null) {
          requestCache.put(paramString, str);
        }
      }
    }
    catch (UnknownHostException localUnknownHostException)
    {
      WSError localWSError2 = new WSError();
      localWSError2.setMessage(localUnknownHostException.getLocalizedMessage());
      throw localWSError2;
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      for (;;)
      {
        localClientProtocolException.printStackTrace();
      }
    }
    catch (SocketException localSocketException)
    {
      WSError localWSError1 = new WSError();
      localWSError1.setMessage(localSocketException.getLocalizedMessage());
      throw localWSError1;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        localIOException.printStackTrace();
      }
    }
    Log.d(MokoApplication.TAG, "Caller.doGet [end] " + paramString);
    return str;
  }
  
  public static void setRequestCache(RequestCache paramRequestCache)
  {
    requestCache = paramRequestCache;
  }
}



/* Location:           J:\android\tools\dex2jar-0.0.9.15\classes_dex2jar.jar

 * Qualified Name:     com.mchenxin.moko.api.util.Caller

 * JD-Core Version:    0.7.0.1

 */