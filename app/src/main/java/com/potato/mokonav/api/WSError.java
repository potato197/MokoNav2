package com.potato.mokonav.api;

public class WSError
  extends Throwable
{
  private static final long serialVersionUID = 1L;
  private String message;
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String paramString)
  {
    this.message = paramString;
  }
}



/* Location:           J:\android\tools\dex2jar-0.0.9.15\classes_dex2jar.jar

 * Qualified Name:     com.mchenxin.moko.api.WSError

 * JD-Core Version:    0.7.0.1

 */