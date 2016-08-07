package com.potato.mokonav;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.potato.mokonav.api.Caller;
import com.potato.mokonav.api.RequestCache;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.potato.mokonav.R;

public class MokoApplication
  extends Application
{
  public static String TAG = "mokocc";
  public static int[] channelsColor = { -11776948, -16724737, -65382, -78586, -6697983, -9223788,   -39424,-3407872,-10144922,  -14859163,   -3684409 };
  public static int[] channelsTitle = { R.string.chosen,
          R.string.photographer,
          R.string.models,
          R.string.makeup,
          R.string.ebusiness,
          R.string.video,
          R.string.design,
          R.string.wedding,
          R.string.arts,
          R.string.cosplay,
          R.string.more };
  public static String[] channelsURL;
  private static MokoApplication instance;
  public static String login = "http://www.moko.cc/jsps/common/login.action?usermingzi=lightingmoon@sina.com&userkey=qq1234&tourl=";
//  static String month = "2015/1";
  private RequestCache mRequestCache;
  
  static
  {
    String[] arrayOfString = new String[11];
//    arrayOfString[0] = ("http://www.moko.cc/" + month + "/1/postMonthList.html");
    arrayOfString[0] = ("http://www.moko.cc/moko/post/1.html");
    arrayOfString[1] = "http://www.moko.cc/channels/Post/28/1.html";
    arrayOfString[2] = "http://www.moko.cc/channels/Post/23/1.html";
    arrayOfString[3] = "http://www.moko.cc/channels/Post/151/1.html";
    arrayOfString[4] = "http://www.moko.cc/channels/Post/152/1.html";
    arrayOfString[5] = "http://www.moko.cc/channels/Post/154/1.html";
    arrayOfString[6] = "http://www.moko.cc/channels/Post/41/1.html";
    arrayOfString[7] = "http://www.moko.cc/channels/Post/153/1.html";
    arrayOfString[8] = "http://www.moko.cc/channels/Post/150/1.html";
    arrayOfString[9] = "http://www.moko.cc/channels/Post/148/1.html";
    arrayOfString[10] = "http://www.moko.cc/channels/Post/94/1.html";
    channelsURL = arrayOfString;
  }
  
  public static MokoApplication getInstance()
  {
    return instance;
  }
  
  public static boolean isWiFiActive(Context paramContext)
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo;
    if (localConnectivityManager != null)
    {
      activeNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
      if (activeNetworkInfo != null && activeNetworkInfo.getState()== NetworkInfo.State.CONNECTED) {
        return true;
      }
    }
    return false;
  }
  
  public static Bitmap resizeBmp(String paramString, int paramInt1, int paramInt2)
  {
    Bitmap localBitmap = returnBitMap(paramString);
    int i = localBitmap.getWidth();
    int j = localBitmap.getHeight();
    Matrix localMatrix = new Matrix();
    localMatrix.postScale(paramInt1 / i, paramInt2 / j);
    return Bitmap.createBitmap(localBitmap, 0, 0, i, j, localMatrix, true);
  }
  
  public static Bitmap returnBitMap(String paramString)
  {
    Bitmap localBitmap = null;
    URL localURL1 = null;
    URL localURL2 = null;
    try
    {
      localURL1 = new URL(paramString);
      localURL2 = localURL1;
//      InputStream localInputStream;
      return localBitmap;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      for (;;)
      {
        try
        {
          InputStream localInputStream = localURL2.openStream();
          localBitmap = BitmapFactory.decodeStream(localInputStream);
          localInputStream.close();
          return localBitmap;
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
        }
        localMalformedURLException.printStackTrace();
        localURL2 = null;
      }
    }
  }
  
  /* Error */
  public static boolean saveToFile(String paramString1, String paramString2)
  {
    return false;
    // Byte code:
    //   0: invokestatic 199	android/os/Environment:getExternalStorageState	()Ljava/lang/String;
    //   3: ldc 201
    //   5: invokevirtual 132	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   8: ifne +5 -> 13
    //   11: iconst_0
    //   12: ireturn
    //   13: new 35	java/lang/StringBuilder
    //   16: dup
    //   17: ldc 203
    //   19: invokestatic 209	java/lang/System:currentTimeMillis	()J
    //   22: invokestatic 215	android/text/format/DateFormat:format	(Ljava/lang/CharSequence;J)Ljava/lang/CharSequence;
    //   25: invokeinterface 218 1 0
    //   30: invokestatic 222	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   33: invokespecial 41	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   36: ldc 224
    //   38: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   41: invokevirtual 51	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   44: astore_2
    //   45: new 35	java/lang/StringBuilder
    //   48: dup
    //   49: invokestatic 228	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
    //   52: invokevirtual 231	java/io/File:toString	()Ljava/lang/String;
    //   55: invokestatic 222	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   58: invokespecial 41	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   61: ldc 233
    //   63: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: aload_0
    //   67: invokevirtual 45	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   70: invokevirtual 51	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   73: astore_3
    //   74: aconst_null
    //   75: astore 4
    //   77: new 230	java/io/File
    //   80: dup
    //   81: aload_3
    //   82: invokespecial 234	java/io/File:<init>	(Ljava/lang/String;)V
    //   85: astore 5
    //   87: aload 5
    //   89: invokevirtual 237	java/io/File:exists	()Z
    //   92: istore 12
    //   94: aconst_null
    //   95: astore 4
    //   97: iload 12
    //   99: ifne +9 -> 108
    //   102: aload 5
    //   104: invokevirtual 240	java/io/File:mkdirs	()Z
    //   107: pop
    //   108: new 230	java/io/File
    //   111: dup
    //   112: aload_3
    //   113: aload_2
    //   114: invokespecial 243	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   117: astore 14
    //   119: aload 14
    //   121: invokevirtual 246	java/io/File:createNewFile	()Z
    //   124: istore 15
    //   126: aconst_null
    //   127: astore 4
    //   129: iload 15
    //   131: ifeq +42 -> 173
    //   134: new 248	java/io/FileOutputStream
    //   137: dup
    //   138: aload 14
    //   140: invokespecial 251	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   143: astore 16
    //   145: aload_1
    //   146: invokestatic 142	com/mchenxin/moko/MokoApplication:returnBitMap	(Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   149: astore 17
    //   151: aload 17
    //   153: ifnull +32 -> 185
    //   156: aload 17
    //   158: getstatic 257	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   161: bipush 100
    //   163: aload 16
    //   165: invokevirtual 261	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   168: pop
    //   169: aload 16
    //   171: astore 4
    //   173: aload 4
    //   175: ifnull +82 -> 257
    //   178: aload 4
    //   180: invokevirtual 264	java/io/OutputStream:close	()V
    //   183: iconst_1
    //   184: ireturn
    //   185: aload 16
    //   187: aconst_null
    //   188: invokevirtual 268	java/io/OutputStream:write	([B)V
    //   191: aload 16
    //   193: astore 4
    //   195: goto -22 -> 173
    //   198: astore 10
    //   200: aload 10
    //   202: invokevirtual 269	java/io/FileNotFoundException:printStackTrace	()V
    //   205: aload 4
    //   207: ifnull +50 -> 257
    //   210: aload 4
    //   212: invokevirtual 264	java/io/OutputStream:close	()V
    //   215: iconst_1
    //   216: ireturn
    //   217: astore 8
    //   219: aload 8
    //   221: invokevirtual 188	java/io/IOException:printStackTrace	()V
    //   224: aload 4
    //   226: ifnull +31 -> 257
    //   229: aload 4
    //   231: invokevirtual 264	java/io/OutputStream:close	()V
    //   234: iconst_1
    //   235: ireturn
    //   236: astore 6
    //   238: aload 4
    //   240: ifnull +12 -> 252
    //   243: aload 4
    //   245: invokevirtual 264	java/io/OutputStream:close	()V
    //   248: iconst_1
    //   249: ireturn
    //   250: astore 7
    //   252: aload 6
    //   254: athrow
    //   255: astore 11
    //   257: iconst_0
    //   258: ireturn
    //   259: astore 9
    //   261: goto -4 -> 257
    //   264: astore 19
    //   266: goto -9 -> 257
    //   269: astore 6
    //   271: aload 16
    //   273: astore 4
    //   275: goto -37 -> 238
    //   278: astore 8
    //   280: aload 16
    //   282: astore 4
    //   284: goto -65 -> 219
    //   287: astore 10
    //   289: aload 16
    //   291: astore 4
    //   293: goto -93 -> 200
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	296	0	paramString1	String
    //   0	296	1	paramString2	String
    //   44	70	2	str1	String
    //   73	40	3	str2	String
    //   75	217	4	localObject1	java.lang.Object
    //   85	18	5	localFile1	java.io.File
    //   236	17	6	localObject2	java.lang.Object
    //   269	1	6	localObject3	java.lang.Object
    //   250	1	7	localThrowable1	java.lang.Throwable
    //   217	3	8	localIOException1	IOException
    //   278	1	8	localIOException2	IOException
    //   259	1	9	localThrowable2	java.lang.Throwable
    //   198	3	10	localFileNotFoundException1	java.io.FileNotFoundException
    //   287	1	10	localFileNotFoundException2	java.io.FileNotFoundException
    //   255	1	11	localThrowable3	java.lang.Throwable
    //   92	6	12	bool1	boolean
    //   117	22	14	localFile2	java.io.File
    //   124	6	15	bool2	boolean
    //   143	147	16	localFileOutputStream	java.io.FileOutputStream
    //   149	8	17	localBitmap	Bitmap
    //   264	1	19	localThrowable4	java.lang.Throwable
    // Exception table:
    //   from	to	target	type
    //   77	94	198	java/io/FileNotFoundException
    //   102	108	198	java/io/FileNotFoundException
    //   108	126	198	java/io/FileNotFoundException
    //   134	145	198	java/io/FileNotFoundException
    //   77	94	217	java/io/IOException
    //   102	108	217	java/io/IOException
    //   108	126	217	java/io/IOException
    //   134	145	217	java/io/IOException
    //   77	94	236	finally
    //   102	108	236	finally
    //   108	126	236	finally
    //   134	145	236	finally
    //   200	205	236	finally
    //   219	224	236	finally
    //   243	248	250	java/lang/Throwable
    //   210	215	255	java/lang/Throwable
    //   229	234	259	java/lang/Throwable
    //   178	183	264	java/lang/Throwable
    //   145	151	269	finally
    //   156	169	269	finally
    //   185	191	269	finally
    //   145	151	278	java/io/IOException
    //   156	169	278	java/io/IOException
    //   185	191	278	java/io/IOException
    //   145	151	287	java/io/FileNotFoundException
    //   156	169	287	java/io/FileNotFoundException
    //   185	191	287	java/io/FileNotFoundException
  }
  
  public String getVersion()
  {
    PackageManager localPackageManager = getPackageManager();
    try
    {
      String str = localPackageManager.getPackageInfo(getPackageName(), 0).versionName;
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
    }
    return "0.0.0";
  }
  
  public void onCreate()
  {
    super.onCreate();
    this.mRequestCache = new RequestCache();
    Caller.setRequestCache(this.mRequestCache);
    instance = this;
  }
}



/* Location:           J:\android\tools\dex2jar-0.0.9.15\classes_dex2jar.jar

 * Qualified Name:     com.mchenxin.moko.MokoApplication

 * JD-Core Version:    0.7.0.1

 */