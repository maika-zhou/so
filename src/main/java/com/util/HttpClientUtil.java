package com.util;



import com.google.gson.Gson;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 编写HttpClient发送请求
 */
public class HttpClientUtil {

	public static final Integer HTTP_POST = 1;
	public static final Integer HTTP_GET = 2;
	public static final Integer HTTP_JSON = 3;

	public static final String UTF8 = "UTF-8";
	public static final String GBK = "gbk";
	public static final String GB2312 = "gb2312";
	public static final String ISO_8859_1 = "ISO-8859-1";


	/**
	 * @param url       发送请求地址
	 * @param param     发送请求的参数信息
	 * @param httpType  发送请求的类型，post为HttpClient.HTTP_POST;get为HttpClient.HTTP_GET
	 * @param uriEncode 发送请求的字符集编码，HttpClient.UTF8；HttpClient.GBK；HttpClient.GB2312；HttpClient.ISO_8859_1；
	 * @param timeOut   发送请求的相应时间
	 * @return 发送请求的返回值或者null
	 * @throws RuntimeException
	 * @throws Exception        发送异常信息
	 */
	public static String sendHttpUrl(String url, Map<String, String> param, Map<String, String> header, Integer httpType, String uriEncode, Integer timeOut) throws RuntimeException, Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		RequestConfig config = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
		CloseableHttpResponse response = null;
		if (HttpClientUtil.HTTP_GET.compareTo(httpType) == 0) {
			StringBuilder sb = new StringBuilder();
			String responseUrl;
			sb.append(url);
			if (param != null && param.size() > 0) {
				sb.append("?");
				for (String key : param.keySet()) {
					sb.append(key).append("=").append(param.get(key)).append("&");
				}
				responseUrl = sb.toString().trim().substring(0, sb.toString().trim().length() - 1);
			} else {
				responseUrl = url;
			}
			HttpGet httpGet = new HttpGet(responseUrl);
			httpGet.setConfig(config);
			httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.6) Gecko/20100625Firefox/3.6.6 Greatwqs");
			httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml,application/json");
			httpGet.setHeader("Accept-Language", "zh-cn,zh");
			
			if (header != null && header.size() > 0) {
				for (String key : header.keySet()) {
					System.out.println(key+":"+header.get(key));
					httpGet.setHeader(key, header.get(key));
				}
			}
			
			
			switch (uriEncode) {
				case HttpClientUtil.UTF8:
					httpGet.setHeader("Accept-Charset", "utf-8");
					break;
				case HttpClientUtil.GBK:
					httpGet.setHeader("Accept-Charset", "gbk");
					break;
				case HttpClientUtil.GB2312:
					httpGet.setHeader("Accept-Charset", "gb2312");
					break;
				case HttpClientUtil.ISO_8859_1:
					httpGet.setHeader("Accept-Charset", "ISO-8859-1");
					break;
				default:
					httpGet.setHeader("Accept-Charset", "utf-8");
					break;
			}
			response = client.execute(httpGet);
		} else if (HttpClientUtil.HTTP_POST.compareTo(httpType) == 0) {
			HttpPost post = new HttpPost(url);
			if (param != null && param.size() > 0) {
				List<NameValuePair> nvpList = new ArrayList<>();
				for (String key : param.keySet()) {
					BasicNameValuePair bnvpObject = new BasicNameValuePair(key, param.get(key));
					nvpList.add(bnvpObject);
				}
				if (uriEncode != null) {
					switch (uriEncode) {
						case HttpClientUtil.UTF8:
							post.setHeader("Accept-Charset", "utf-8");
							post.setEntity(new UrlEncodedFormEntity(nvpList, UTF8));
							break;
						case HttpClientUtil.GBK:
							post.setHeader("Accept-Charset", "gbk");
							post.setEntity(new UrlEncodedFormEntity(nvpList, GBK));
							break;
						case HttpClientUtil.GB2312:
							post.setHeader("Accept-Charset", "gb2312");
							post.setEntity(new UrlEncodedFormEntity(nvpList, GB2312));
							break;
						case HttpClientUtil.ISO_8859_1:
							post.setHeader("Accept-Charset", "ISO-8859-1");
							post.setEntity(new UrlEncodedFormEntity(nvpList, ISO_8859_1));
							break;
						default:
							post.setHeader("Accept-Charset", "utf-8");
							post.setEntity(new UrlEncodedFormEntity(nvpList));
							break;
					}
				} else {
					post.setHeader("Accept-Charset", "utf-8");
					post.setEntity(new UrlEncodedFormEntity(nvpList));
				}
			}
			post.setConfig(config);
			post.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.6) Gecko/20100625Firefox/3.6.6 Greatwqs");
			post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml,application/json");
			post.setHeader("Accept-Language", "zh-cn,zh");
			if (header != null && header.size() > 0) {
				for (String key : header.keySet()) {
					System.out.println(key+":"+header.get(key));
					post.setHeader(key, header.get(key));
				}
			}
			
			
			response = client.execute(post);
		} else if (HttpClientUtil.HTTP_JSON.compareTo(httpType) == 0) {
			HttpPost post = new HttpPost(url);
			if (param != null && param.size() > 0) {
				Gson gson = new Gson();
				if (uriEncode != null) {
					switch (uriEncode) {
						case HttpClientUtil.UTF8:
							post.setHeader("Accept-Charset", "utf-8");
							post.setEntity(new StringEntity(gson.toJson(param), UTF8));
							break;
						case HttpClientUtil.GBK:
							post.setHeader("Accept-Charset", "gbk");
							post.setEntity(new StringEntity(gson.toJson(param), GBK));
							break;
						case HttpClientUtil.GB2312:
							post.setHeader("Accept-Charset", "gb2312");
							post.setEntity(new StringEntity(gson.toJson(param), GB2312));
							break;
						case HttpClientUtil.ISO_8859_1:
							post.setHeader("Accept-Charset", "ISO-8859-1");
							post.setEntity(new StringEntity(gson.toJson(param), ISO_8859_1));
							break;
						default:
							post.setHeader("Accept-Charset", "utf-8");
							post.setEntity(new StringEntity(gson.toJson(param), UTF8));
							break;
					}
				} else {
					post.setHeader("Accept-Charset", "utf-8");
					post.setEntity(new StringEntity(gson.toJson(param), UTF8));
				}
			}
			post.setConfig(config);
			post.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.6) Gecko/20100625Firefox/3.6.6 Greatwqs");
			post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml,application/json");
			post.setHeader("Accept-Language", "zh-cn,zh");
			post.setHeader("Content-Type", "application/json");
			response = client.execute(post);
		}
		if (response != null) {
			String responseValue;
			if (uriEncode != null) {
				responseValue = EntityUtils.toString(response.getEntity(), uriEncode);
			} else {
				responseValue = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
			if (response.getStatusLine().getStatusCode() == 200) {
				/** 请求正常*/
				response.close();
				client.close();
				return responseValue;
			} else {
				throw new Exception(responseValue);
			}
		} else {
			if (client != null) {
				client.close();
			}
			return null;
		}

	}

	private static TrustManager manager = new X509TrustManager() {
		@Override
		public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

		}

		@Override
		public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

		}

		@Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};

	private static void enableSSL() {
		try {
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[]{manager}, null);
			socketFactory = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}

	private static SSLConnectionSocketFactory socketFactory;

	public static String doHttpsPost(String url, Map<String, String> param) throws Exception {
		enableSSL();
		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
				.setExpectContinueEnabled(true).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig).build();

		HttpPost post = new HttpPost(url);
		List<NameValuePair> nvpList = new ArrayList<NameValuePair>();
		if (param != null && param.size() > 0) {
			for (String key : param.keySet()) {
				BasicNameValuePair bnvpObject = new BasicNameValuePair(key, param.get(key));
				nvpList.add(bnvpObject);
			}
		}

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvpList, Consts.UTF_8);
		post.setEntity(entity);
		CloseableHttpResponse response = httpClient.execute(post);

		if (response != null) {
			String responseValue;
			responseValue = EntityUtils.toString(response.getEntity(), "UTF-8");
			if (response.getStatusLine().getStatusCode() == 200) {
				return responseValue;
			} else {
				throw new Exception(responseValue);
			}
		} else {
			return null;
		}
	}
}