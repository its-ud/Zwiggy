package com.example.zwiggy.Data;

public class ResDetail {
    private static String Resname;
    private static String Resownerid;
    private static int ResminAmnt;
    public static void setResname(String resname)
    {
        Resname=resname;
    }
    public static String getResname()
    {
        return Resname;
    }
    public static String getResownerid()
    {
        return Resownerid;
    }
    public static void setResownerid(String resownerid)
    {
        Resownerid=resownerid;
    }
    public static int getResminAmnt()
    {
        return ResminAmnt;
    }
    public static void setResminAmnt(int x)
    {
        ResminAmnt=x;
    }
}
