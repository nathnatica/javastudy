package study.urlencode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlEncodeDecode {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String encoded = java.net.URLEncoder.encode("âÊçH","Windows-31j");
		String decoded = new String(java.net.URLDecoder.decode(encoded, "iso-8859-1").getBytes("iso-8859-1"), "Windows-31j");
		System.out.println(decoded);

		String target = "âÊçH";
		encoded =  URLEncoder.encode(target, "UTF-8");
		decoded = URLDecoder.decode(encoded, "UTF-8");
		System.out.println(target.equals(decoded));
	}
}
