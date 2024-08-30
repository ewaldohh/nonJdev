/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webviewer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author DonnyAM
 */
class PathUtil {
    
	/*private static String[] sufixes = new String[]{"", ".vision", ".phm", ".main"};
	
	public static String processPath(String path)
	{
		if(!path.contains("[USERNAME]"))
			return path;
		
		String username = System.getProperty("user.name");
		String resultPath = path;
		int count = -1;
		
		for(String sufix : sufixes)
		{
			String curPath = path.replaceAll("\\[USERNAME\\]", username + sufix);
			String[] paths = curPath.split("\\/|\\\\");
			String testPath = "";
			int curCount = 0;
			for(String subPath : paths)
			{
				testPath += subPath + "\\";
				if(!(new File(testPath).isDirectory()))
					break;
				curCount++;
			}
			if(curCount > count)
			{
				count = curCount;
				resultPath = curPath;
			}
		}
		
		return resultPath;
	}*/
	
	private static Pattern VAR_PATTERN = Pattern.compile("%([^%]*)%");
	
	public static String processPath(String path)
	{
            try{
		String username = System.getProperty("user.name");
		path = path.replaceAll("\\[USERNAME\\]", username);
		
		Matcher matcher = VAR_PATTERN.matcher(path);
		while(matcher.find())
		{
			path = path.replace(matcher.group(0), System.getenv(matcher.group(1)));
		}
		
		path = path.replaceAll("\\\\", "/");
            } catch (Exception ex ){
            }
		
		return path;
	}
}
