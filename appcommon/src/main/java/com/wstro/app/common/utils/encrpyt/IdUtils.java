package com.wstro.app.common.utils.encrpyt;

public class IdUtils
{
    public static String createId()
    {
       /* String id = UUID.randomUUID().toString();
         
        id = DEKHash(id) + "";
         
        int diff = 12 - id.length();*/
        String randStr = RandomStringUtils.randomNumeric(12);
      /*  for (int i = 0; i < diff; i++)
        {
            int randIndex = (int) (Math.random() * randStr.length());
            int index = (int) (Math.random() * id.length());
            id = id.substring(0, index) + randStr.charAt(randIndex) + id.substring(index, id.length());
        }
        return id;*/
        return randStr;
    }
     
    private static int DEKHash(String str)
    {
        int hash = str.length();
 
        for (int i = 0; i < str.length(); i++)
        {
            hash = ((hash << 5) ^ (hash >> 27)) ^ str.charAt(i);
        }
 
        return (hash & 0x7FFFFFFF);
    }
}