package cn.net.health.user.util;//package cn.net.health.user.util;
//
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
///**
// * 根据IP地址获取详细的地域信息
// * 淘宝API : http://ip.taobao.com/service/getIpInfo.php?ip=106.239.255.250
// * @since 2018-10-01
// * @author 邪客
// */
//@Slf4j
//public class AddressUtils {
//	/**
//	 *
//	 * @param content
//	 *            请求的参数 格式为：name=xxx&pwd=xxx
//	 * @param encodingString
//	 *            服务器端请求编码。如GBK,UTF-8等
//	 * @return
//	 * @throws UnsupportedEncodingException
//	 */
//	private static String getAddresses(String content, String encodingString) {
//		// 调用淘宝API
//		String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
//		// 取得IP所在的省市区信息
//		String returnStr = getResult(urlStr, content, encodingString);
//		if (returnStr != null) {
//			String[] temp = returnStr.split(",");
//			if(temp.length < 3){
//				return "0";//无效IP，局域网测试
//			}
//			return returnStr;
//		}
//		return null;
//	}
//	/**
//	 * @param urlStr
//	 *            请求的地址
//	 * @param content
//	 *            请求的参数 格式为：name=xxx&pwd=xxx
//	 * @param encoding
//	 *            服务器端请求编码 如GBK,UTF-8等
//	 * @return
//	 */
//	private static String getResult(String urlStr, String content, String encoding) {
//		HttpURLConnection connection = null;
//		try {
//			URL url = new URL(urlStr);
//			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
//			connection.setConnectTimeout(3000);// 设置连接超时时间，单位毫秒
//			connection.setReadTimeout(5000);// 设置读取数据超时时间，单位毫秒
//			connection.setDoOutput(true);// 是否打开输出流 true|false
//			connection.setDoInput(true);// 是否打开输入流true|false
//			connection.setRequestMethod("POST");// 提交方法POST|GET
//			connection.setUseCaches(false);// 是否缓存true|false
//			connection.connect();// 打开连接端口
//			DataOutputStream out = new DataOutputStream(connection
//					.getOutputStream());// 打开输出流往对端服务器写数据
//			out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
//			out.flush();// 刷新
//			out.close();// 关闭输出流
//			BufferedReader reader = new BufferedReader(new InputStreamReader(
//					connection.getInputStream(), encoding));
//			StringBuffer buffer = new StringBuffer();
//			String line = "";
//			while ((line = reader.readLine()) != null) {
//				buffer.append(line);
//			}
//			reader.close();
//			return buffer.toString();
//		} catch (IOException e) {
//			e.printStackTrace();
//			log.info("----------------------最后的3");
//		} finally {
//			log.info("----------------------最后的2");
//			if (connection != null) {
//				connection.disconnect();// 关闭连接
//			}
//		}
//		log.info("----------------------最后的");
//		return null;
//	}
//
//	public static String getAddressByIp(String ipAddress){
//		String json_result = getAddresses("ip=" + ipAddress, "utf-8");
//
//		String address ="";
//		if("0".equals(json_result)){
//			log.info("局域网测试");
//			return "0";
//		}else {
//			JSONObject dataJson = JSONObject.parseObject(json_result).getJSONObject("data");
//			log.info("dataJson： " + dataJson);
//			String country = dataJson.getString("country");
//			String region = dataJson.getString("region");
//			String city = dataJson.getString("city");
//			String county = dataJson.getString("county");
//			String isp = dataJson.getString("isp");
//			String area = dataJson.getString("area");
//			log.info("国家： " + country);
//			log.info("地区： " + area);
//			log.info("省份: " + region);
//			log.info("城市： " + city);
//			log.info("区/县： " + county);
//			log.info("互联网服务提供商： " + isp);
//			  address = country + " ";
//			address += region + " ";
//			address += city + " ";
//			//address += county;
//			log.info(address);
//		}
//		return address;
//	}
//
//	// 测试
//	public static void main(String[] args) {
//		String ip = "101.200.55.12";
//		getAddressByIp(ip);
//	}
//
//}
