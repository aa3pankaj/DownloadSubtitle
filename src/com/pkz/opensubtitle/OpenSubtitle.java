package com.pkz.opensubtitle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

class OpenSubtitle {
	private static String OPEN_SUBTITLES_SERVER = "http://api.opensubtitles.org/xml-rpc";
	// private static String MOVIE_EXTENSIONS="mp4,mkv,avi,mov";

	XmlRpcClientConfigImpl xmlRpcClientConfig;
	XmlRpcClient xmlRpcClient;

	public OpenSubtitle() {

		xmlRpcClientConfig = new XmlRpcClientConfigImpl();
		xmlRpcClient = new XmlRpcClient();

		try {
			xmlRpcClientConfig.setServerURL(new URL(OPEN_SUBTITLES_SERVER));
			xmlRpcClient.setConfig(xmlRpcClientConfig);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	HashMap<?, ?> searchSubtitle(String object, String movieName) {

		HashMap<?, ?> map = null;
		HashMap<String, Object> pParams = new HashMap<>();
		pParams.put("query", movieName);
		pParams.put("sublanguageid", "eng");
		HashMap<String, Object> pParams2 = new HashMap<>();
		// list.put(", value)
		pParams2.put("limit", 10);
		Object[] obj = new Object[] { object, new Object[] { pParams },
				pParams2 };

		try {
			map = (HashMap) xmlRpcClient.execute("SearchSubtitles", obj);
		} catch (XmlRpcException e) {
		
			e.printStackTrace();
		}

		return map;

	}

	
	void downloadSubtitle(String link, String filename,String filePath) throws IOException {

		/*
		 * ArrayList<String> list=new ArrayList<>(); list.add(iDSubtitleFile);
		 * 
		 * Object[] obj=new Object[]{token,list};
		 * 
		 * HashMap<?,?> map=null; try { map=(HashMap)
		 * xmlRpcClient.execute("DownloadSubtitles",obj); } catch
		 * (XmlRpcException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * Object[] o=(Object[]) map.get("data"); Map m=(HashMap)o[0];
		 * 
		 * 
		 * return m.get("data");
		 */

		URL url = new URL(link);
		URLConnection yc = url.openConnection();
		System.out.println("----> Downloading subtitle file....Please Wait");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));
		String inputLine, total = "";
		while ((inputLine = in.readLine()) != null)
		{
			total += inputLine + "\n";
			
		}
		in.close();
		BufferedWriter output = null;
		try {
			File filSubtitleFile = new File(filename);
			output = new BufferedWriter(new FileWriter(filSubtitleFile));
			output.write(total);
			
			System.out.println("\n----> Subtitle file Downloaded successfully"+"\n\nThank you for downloading this application.... Enjoy!!");
			System.out.println("<Search 'aa3pankaj' on google to contact me>");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				output.close();
			}
		}

	}

	HashMap doLogIn() {
		HashMap<?, ?> val = null;
		List<String> param = new ArrayList<>();
		param.add("");
		param.add("");
		param.add("eng");
		param.add("moviejukebox 1.0.15");

		try {
			val = (HashMap) xmlRpcClient.execute("LogIn", param);
		} catch (XmlRpcException e) {
			
			e.printStackTrace();
		}
		return val;

	}

}
