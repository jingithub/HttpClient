import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

/**
 * HttpClient类
 * @author chenrj
 * @date 2016年8月29日下午7:01:46
 */
public class HttpClientHelp {
	
	private static final Log LOG = LogFactory.getLog(HttpClientHelp.class);

    /**
     * get 请求
     * @param url 请求url
     * @param paraMap 请求参数
     * @return
     * @throws IOException
     */
	public static HttpGet getMethod(String url, Map<String, String> paraMap) throws IOException {
		StringBuffer paraStr = new StringBuffer();
		String requestUrl = url;
		if(paraMap != null){
			for (Iterator<Entry<String,String>> it = paraMap.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = (Entry<String, String>) it.next();
				paraStr.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
			requestUrl = url + "?" + paraStr.toString().substring(0, paraStr.length() - 1);
		}
		
		HttpGet get = new HttpGet(requestUrl);
		get.setHeader("Content-Type", "application/json; charset=utf-8");
		get.setHeader("Accept", "application/json");
		LOG.info("查询接口请求url=" + requestUrl);
		return get;
	}
	/**
	 * post 请求
	 * @param url 请求url
	 * @param paraMap 请求参数
	 * @return
	 * @throws IOException
	 */
	public static HttpPost postMethod(String url, Map<String, String> paraMap) throws IOException {
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		List<NameValuePair> params = new ArrayList<NameValuePair>();  
		for (Iterator<Entry<String, String>> it = paraMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = (Entry<String, String>) it.next();
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			LOG.info("paraMap: key :"+entry.getKey()+" , value:"+entry.getValue());
		}
		LOG.info("post请求url=" + url + " data=" + paraMap);
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params);
		post.setEntity(uefEntity);
		return post;
	}
	
	/**
	 * post 请求
	 * @param url 请求url
	 * @param paraMap 请求参数
	 * @return
	 * @throws IOException
	 */
	public static HttpPost postMethodByStr(String url, String jsonStr) throws IOException {
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/json;charset=UTF-8");
		LOG.info("post请求url="+url+" data=" + jsonStr);
		StringEntity entity = new StringEntity(jsonStr);
		post.setEntity(entity);
		return post;
	}
    
}
