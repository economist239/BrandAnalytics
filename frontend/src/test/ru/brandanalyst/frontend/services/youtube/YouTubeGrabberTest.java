package ru.brandanalyst.frontend.services.youtube;

import java.io.*;
import java.util.ArrayList;

public class YouTubeGrabberTest {
	public static void main(String[] args) {
		try {
			ArrayList<YouTubeEntry> result = YouTubeGrabber.searchYouTubeVideos("microsoft", 10);

			File output = new File("result.html");
			try (FileWriter outFile = new FileWriter(output);
				 PrintWriter out = new PrintWriter(outFile)) {

				println(out, "<html>");
				println(out, "<head>");
				println(out, "<title>YouTube grabber test</title>");
				println(out, "</head>");
				println(out, "<body>");

				for (YouTubeEntry e : result) {
					println(out, "<a href=\"" + e.getHref() + "\">" + e.getTitle() + "</a><br>");
					println(out, e.getEmbeddedPlayerHtmlCode());
					println(out, "<br><br>");
				}

				println(out, "</body>");
				println(out, "</html>");

				out.flush();
				out.close();
			} catch (IOException e) {
				System.err.println("Error: " + e.toString());
			}
		} catch (YouTubeGrabberException e) {
			System.err.println("Error: " + e.toString());
		}
	}

	private static void println(PrintWriter out, String text) throws IOException {
		out.println(text);
		if (out.checkError()) {
			throw new IOException("Error while writing output file.");
		}
	}
}

