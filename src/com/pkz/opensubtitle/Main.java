package com.pkz.opensubtitle;

import java.io.IOException;

import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {

		OpenSubtitle openSubtitle = new OpenSubtitle();
		HashMap<?, ?> loginMap;
		HashMap<?, ?> outputMap;
		loginMap = openSubtitle.doLogIn();

		StringTokenizer st = new StringTokenizer(args[0], "\\");

		String filePath = "";
		while (st.hasMoreTokens()) {

			filePath = st.nextToken();

		}
		// Search for subtitles on opensubtitle.com using name of the movie file.
		outputMap = openSubtitle.searchSubtitle((String) loginMap.get("token"),
				filePath.substring(0, filePath.lastIndexOf(".")));
		Object[] list;

		list = (Object[]) outputMap.get("data");
		// get the subtitle download link from the object array.
		HashMap m = (HashMap) list[0];
		// downloads subtitle and saves it at the movie file location, with the same name.
		openSubtitle.downloadSubtitle(m.get("SubDownloadLink").toString()
				.replaceAll(".gz", ""),
				filePath.substring(0, filePath.lastIndexOf(".")) + ".srt",filePath);

		// encoded=subs.downloadSubtitle((String)map.get("token"),(String)m.get("IDSubtitleFile"));
		// System.out.println(m.get("IDSubtitleFile"));

		// System.out.println((String)encoded);

		// byte[] decoded =Base64.getDecoder().decode((String)encoded);

		/*
		 * FileOutputStream fos = new FileOutputStream("D:\\t.txt");
		 * fos.write(decoded); //fos.write(new String(decoded)); fos.close();
		 */

	}
}
