/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulo.inventario.config;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import static java.lang.System.out;
import static java.lang.System.runFinalization;
import java.net.HttpURLConnection;
import java.net.URL;

//import javax.net.ssl.HttpsURLConnection;

public class DB {

	private final String USER_AGENT = "Mozilla/5.0";

	/*public static void main(String[] args) throws Exception {

		DB http = new DB();

		System.out.println("Testing 1 - Send Http GET request");
		http.sendGet("");
		
		System.out.println("\nTesting 2 - Send Http POST request");
		http.sendPost("", "");

	}*/

	// HTTP GET request
	public String sendGet(String url) throws Exception {
                runFinalization();
		//String url = "http://www.google.com/search?q=mkyong";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		out.println("\nSending 'GET' request to URL : " + url);
		out.println("Response Code : " + responseCode);

                StringBuilder response = null;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String inputLine = null;
                response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

		//print result
		out.println(response.toString());
                return response.toString();

	}
	
	// HTTP POST request
	public String sendPost(String url, String urlParameters) throws Exception {

		//String url = "https://selfsolve.apple.com/wcResults.do";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		//String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
		
		// Send post request
		con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(urlParameters);
                wr.flush();
            }

		int responseCode = con.getResponseCode();
		out.println("\nSending 'POST' request to URL : " + url);
		out.println("Post parameters : " + urlParameters);
		out.println("Response Code : " + responseCode);

                StringBuilder response;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
		
		//print result
		out.println(response.toString());
                return response.toString();

	}

}