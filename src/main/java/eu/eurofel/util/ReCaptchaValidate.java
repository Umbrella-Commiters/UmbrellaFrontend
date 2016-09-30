package eu.eurofel.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.tapestry5.json.JSONObject;

public class ReCaptchaValidate {

	public static void main(String[] args) {
		System.out.println(ReCaptchaValidate.validate("",
				"gpsdfLKUJjFSADddsfew", "145.33.12.99"));

	}

	public static boolean validate(String secret, String response, String ip) {
		boolean ret = false;

		try {
			String out = ReCaptchaValidate.googleValidate(secret, response, ip);
			JSONObject obj = new JSONObject(out);
			if (obj.getString("success").equals("true")) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}

	private static String googleValidate(String secret, String response,
			String ip) throws Exception {
		String urlParameters = "secret=	6LdgaCgTAAAAAEMwLiDCxH4XI5mwyvFHhEpbki_C&response="
				+ response + "&remoteip=" + ip;

		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;

		String request = "https://www.google.com/recaptcha/api/siteverify";
		URL url = new URL(request);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("charset", "utf-8");
		conn.setRequestProperty("Content-Length",
				Integer.toString(postDataLength));
		conn.setUseCaches(false);

		try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
			wr.write(postData);
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));
		String message = new String();
		String line = null;

		while ((line = in.readLine()) != null) {
			message += line;
		}
		return message;

	}
}
