package com.iotek.util;

public class DVDUtil {
        //ÅĞ¶Ï×Ö·û´®ÊÇ·ñÊÇÊı×Ö
	public static boolean isNumber(String str) {
        	for(int i=str.length();i>0;i--) {
        		if(!Character.isDigit(str.charAt(i-1))) {
        			return false;
        		}
        	}
        	return true;
        }
}
