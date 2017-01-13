import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


/**
 * API调用助手
 * @author chenrj
 * @date 2016年8月29日下午7:01:35
 */
public class CallAPIHelper {
	private static final Log LOG = LogFactory.getLog(CallAPIHelper.class);

	public CallAPIHelper() {
	}

	/**
	 * 调用 GET方式
	 * @param apiUrl
	 * @param paraMap
	 * @return
	 * @throws IfosException
	 */
	public static String get(String apiUrl, Map<String, String> paraMap) {
		CloseableHttpClient httpclient = HttpClients.createDefault();  
		String rs = null;
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = HttpClientHelp.getMethod(apiUrl, paraMap);
			//设置请求和传输超时
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
			httpGet.setConfig(requestConfig);
			//执行请求
			response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			rs = EntityUtils.toString(entity);
			if(LOG.isDebugEnabled()){
				LOG.debug("接口返回结果:\n" + rs);
			}
			LOG.info("调接口完成");
		} catch (Exception e) {
			System.out.println(e);
			LOG.error("调用api错误", e);
		} finally {
			if(response != null) {
				try {
					response.close();
				} catch (IOException e) {
					LOG.error("IOException: ", e);
				}
			}
		}
		return rs;
	}

	/**
	 * 调用 POST方式--MAP
	 * @param apiUrl
	 * @param paraMap
	 * @return
	 * @throws IfosException
	 */
	public static String post(String apiUrl, Map<String, String> paraMap) {
		Logger log = Logger.getLogger("post");
		String rs = null;
		CloseableHttpResponse response = null;
		if(paraMap == null){
			LOG.info("CallAPIHelper的post方法map参数为空");
			return rs;
		}
		try {
			//创建默认的httpClient实例
			CloseableHttpClient httpclient = HttpClients.createDefault();
			//创建httppost
			HttpPost httpPost = HttpClientHelp.postMethod(apiUrl,paraMap);
			//设置请求和传输超时
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
			httpPost.setConfig(requestConfig);
			//执行请求
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			rs = EntityUtils.toString(entity);
			httpPost.releaseConnection();
			log.warning("接口返回结果:\n" + rs);
			if(LOG.isDebugEnabled()){
				LOG.debug("接口返回结果:\n" + rs);
			}
			LOG.info("调接口完成");
		} catch (Exception e) {
			log.warning("调用api错误"+e);
			LOG.error("调用api错误", e);
		} finally {
			if(response != null) {
				try {
					response.close();
				} catch (IOException e) {
					LOG.error("IOException: ", e);
				}
			}
		}
		return rs;
	}

	/**
	 * 调用 POST方式--json字符串
	 * @param apiUrl
	 * @param jsonStr
	 * @return
	 * @throws IfosException
	 */
	public static String postByStr(String apiUrl, String jsonStr) {
		String rs = null;
		CloseableHttpResponse response = null;
		if(jsonStr == null){
			LOG.info("CallAPIHelper的postByStr方法jsonStr参数为空");
			throw new NullPointerException("请求参数为空！");
		}
		try {
			//创建默认的httpClient实例
			CloseableHttpClient httpclient = HttpClients.createDefault();
			//创建httppost
			HttpPost httpPost = HttpClientHelp.postMethodByStr(apiUrl, jsonStr);
			//设置请求和传输超时
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
			httpPost.setConfig(requestConfig);
			//执行请求
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			rs = EntityUtils.toString(entity);
			httpPost.releaseConnection();
			if(LOG.isDebugEnabled()){
				LOG.debug("接口返回结果:\n" + rs);
			}
			LOG.info("调接口完成");
		} catch (Exception e) {
			LOG.error("调用api错误", e);
			throw new RuntimeException("远程请求失败");
		} finally {
			if(response != null) {
				try {
					response.close();
				} catch (IOException e) {
					LOG.error("IOException: ", e);
				}
			}
		}
		return rs;
	}

}
