package com.springboot.test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


/**
 * 实时查询请求Demo
 * @author Administrator
 *
 */
public class SynQueryDemo {

	public static void main(String[] args) {
		String key = "vmCnLlqk1753";				//贵司的授权key
		String customer = "83FA4024C2E287D7CF616C6247E7B043";			//贵司的查询公司编号
		String com = "shentong";			//快递公司编码
		String num = "773010929925035";	//快递单号
		String phone = "";				//手机号码后四位
		String from = "";				//出发地
		String to = "";					//目的地
		int resultv2 = 0;				//开启行政规划解析

		SynQueryDemo demo = new SynQueryDemo(key, customer);
		String result = demo.synQueryData(com, num, phone, from, to, resultv2);
		System.out.println(result);
	}
	
	/**
	 * 实时查询请求地址
	 */
	private static final String SYNQUERY_URL = "http://poll.kuaidi100.com/poll/query.do";
	
	private String key;			//授权key
	private String customer;	//实时查询公司编号

	public SynQueryDemo(String key, String customer) {
		this.key = key;
		this.customer = customer;
	}
	
	/**
	 * 实时查询快递单号
	 * @param com			快递公司编码
	 * @param num			快递单号
	 * @param phone			手机号
	 * @param from			出发地城市
	 * @param to			目的地城市
	 * @param resultv2		开通区域解析功能：0-关闭；1-开通
	 * @return
	 */
	public String synQueryData(String com, String num, String phone, String from, String to, int resultv2) {

		StringBuilder param = new StringBuilder("{");
		param.append("\"com\":\"").append(com).append("\"");
		param.append(",\"num\":\"").append(num).append("\"");
		param.append(",\"phone\":\"").append(phone).append("\"");
		param.append(",\"from\":\"").append(from).append("\"");
		param.append(",\"to\":\"").append(to).append("\"");
		if(1 == resultv2) {
			param.append(",\"resultv2\":1");
		} else {
			param.append(",\"resultv2\":0");
		}
		param.append("}");
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("customer", this.customer);
		String sign = MD5Utils.encode(param + this.key + this.customer);
		params.put("sign", sign);
		params.put("param", param.toString());
		
		return this.post(params);
	}
	
	/**
	 * 发送post请求
	 */
	public String post(Map<String, String> params) {
		StringBuffer response = new StringBuffer("");
		
		BufferedReader reader = null;
		try {
			StringBuilder builder = new StringBuilder();
			for (Map.Entry<String, String> param : params.entrySet()) {
				if (builder.length() > 0) {
					builder.append('&');
				}
				builder.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				builder.append('=');
				builder.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			}
			byte[] bytes = builder.toString().getBytes("UTF-8");

			URL url = new URL(SYNQUERY_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(bytes);

			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			
			String line = "";
            while ((line = reader.readLine()) != null) {
            	response.append(line);
            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return response.toString();
	}
}

/**
 * md5加密
 */
class MD5Utils {
	private static MessageDigest mdigest = null;
	private static char digits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	private static MessageDigest getMdInst() {
		if (null == mdigest) {
			try {
				mdigest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return mdigest;
	}

	public static String encode(String s) {
		if(null == s) {
			return "";
		}
		
		try {
			byte[] bytes = s.getBytes();
			getMdInst().update(bytes);
			byte[] md = getMdInst().digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = digits[byte0 >>> 4 & 0xf];
				str[k++] = digits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
