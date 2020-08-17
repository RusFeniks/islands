package xyz.pixelatedw.islands.helpers;

import java.util.Random;

public class WyHelper
{
	public static boolean isNullOrEmpty(String str)
	{
		if (str != null && !str.isEmpty() && !str.equalsIgnoreCase("n/a"))
			return false;
		return true;
	}
	
	public static double randomWithRange(int min, int max)
	{
		return new Random().nextInt(max + 1 - min) + min;
	}
}
