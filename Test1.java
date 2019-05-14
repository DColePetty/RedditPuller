
// TestingWebPage
// Cole Petty
// May 11, 2019
// CS 165
// colecole@rams.colostate.edu

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.net.*;


public class Test1 {
	
	public static String grabH2(String content)
	{
		// getting the h2's
		String oldContent = content;
		String newContent = "";
		
		while(oldContent.indexOf("<h2") > 0)
		{
			int first = oldContent.indexOf("<h2");
			String middle = oldContent.substring(first);
			int second = middle.indexOf("/h2>");
			
			if(first > -1 && second > -1)
			{
				System.out.println("First: " + first + " Second: " + second + " totalSize: " + oldContent.length());
				newContent += oldContent.substring(first, first + second) + " \n";
				oldContent = oldContent.substring(0, first) + oldContent.substring(first + second -1);
			}
		}
		return newContent;
	}
	
	
	public static String grabHref(String content)
	{
		// getting the h2's
		String oldContent = content;
		ArrayList<String> newContent = new ArrayList<String>();
		
		while(oldContent.indexOf("<h2") > 0)
		{
			int first = oldContent.indexOf("<h2");
			String middle = oldContent.substring(first);
			int second = middle.indexOf("/h2>");
			
			if(first > -1 && second > -1)
			{
				//System.out.println("First: " + first + " Second: " + second + " totalSize: " + oldContent.length());
				newContent.add(new String(oldContent.substring(first, first + second)));
				oldContent = oldContent.substring(0, first) + oldContent.substring(first + second -1);
			}
		}
		
		String oldContent2 = content;
		String newContent2 = "";
		
		for(String curr: newContent)
		{
			int second = oldContent2.indexOf(curr);
			String looking = oldContent2.substring(0, second);
			
			int one = looking.lastIndexOf("href=\"");
			int two = looking.lastIndexOf(">");
			
			newContent2 += (new String(oldContent2.substring(one, two)) + "\n");
		}
		return newContent2;
	}
	
	public static void writeHtml(String url1, String fileName1)
	{
		try 
		{
			URL url = new URL(url1);
			URLConnection con = url.openConnection();
	        InputStream is = con.getInputStream();
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        String line = null;
	        PrintWriter writer = new PrintWriter(fileName1 + ".txt");
	        while ((line = br.readLine()) != null) 
	        {
	            writer.println(line);
	        }
	        writer.close();
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args)
	{
		String totalContent = "";
		String urlStr1 = "https://www.reddit.com/r/wow/" ;
		String urlStr2 = "https://www.reddit.com/search?q=classicwow";
		String urlStr3 = "https://www.reddit.com/";
		String urlStr4 = "https://www.reddit.com/r/AskReddit/";
		String urlStr5 = "https://pace.math.colostate.edu/courses.html";
		String chosenString = urlStr1;
		try
		{
			/* THIS CODE IS FROM  
			 * https://stackoverflow.com/questions/6159118/using-java-to-pull-data-from-a-webpage
			*/
			{
		        // Make a URL to the web page
		        URL url = new URL(chosenString);
		        // Get the input stream through URL Connection
		        URLConnection con = url.openConnection();
		        InputStream is = con.getInputStream();
		        // Once you have the Input Stream, it's just plain old Java IO stuff.
		        // For this case, since you are interested in getting plain-text web page
		        // I'll use a reader and output the text content to System.out.
		        // For binary content, it's better to directly read the bytes from stream and write
		        // to the target file.
		        BufferedReader br = new BufferedReader(new InputStreamReader(is));
		        String line = null;
		        PrintWriter writer = new PrintWriter("htmlSource.txt");
		        // read each line and write to System.out
		        while ((line = br.readLine()) != null) {
		            //System.out.println(line);
		            totalContent += (line + "/n");
		            writer.println(line);
		        }
		        writer.close();
		        //System.out.println("TOTAL CONTENT" + totalContent);
			}		
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			// System.out.println("Yo that's not a webpage");
		}
		
		
		/*
		 * Testing the grabMethods below 
		 */
		System.out.println(grabH2(totalContent));
		File page = new File("askRedditPage");
		String pageStr = "";
		
		Scanner sc = null;
		try {
			sc = new Scanner(new File("askRedditPage"));
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		while(sc.hasNextLine())
		{
			pageStr += (sc.nextLine() + "/n");
		}
		
		String h2 = grabH2(pageStr);
		System.out.println(h2);
		String links = grabHref(pageStr);
		System.out.println(links);	
		
		
		ArrayList<ArrayList<String>> both = new ArrayList<ArrayList<String>>();
		Scanner sc2 = new Scanner(h2);
		Scanner sc3 = new Scanner(links);
		while(sc2.hasNext() && sc3.hasNext())
		{
			ArrayList<String> curr = new ArrayList<String>();
			curr.add(sc2.nextLine());
			curr.add(sc3.nextLine());
			both.add(curr);
		}
		System.out.println(both);
		
		writeHtml(urlStr5, "paceMathHomeHTML");
	}
}
