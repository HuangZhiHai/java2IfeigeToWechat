package wechat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * ifeigeToWechat
 * 
 * @author hezhenfei
 *
 */
public class IfigeToWechat {

	/**
	 * ��������
	 * @param url ��Ϣ������ַ
	 * @param map ��Ϣ���͵��������
	 * @return
	 */
	public static String publicMethod(String url, Map<String, Object> map) {
		// ����һ��httpclient����
		CloseableHttpClient client = HttpClients.createDefault();
		// ����һ��post����
		HttpPost post = new HttpPost(url);
		// ����һ��Entity��ģ�������
		List<NameValuePair> formList = new ArrayList<NameValuePair>();
		// ��ӱ�����
		for (String key : map.keySet()) {
			formList.add(new BasicNameValuePair(key,map.get(key).toString()));
		}

		// ��װ��һ��Entity����
		StringEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(formList, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// �������������
		post.setEntity(entity);
		// ��������ı���ͷ���ı���
		post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
		// ������������˷��صı���
		post.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
		// ִ��post����
		CloseableHttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String resStr = null;
		// ��ȡ��Ӧ��
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			// ��ȡ����
			try {
				resStr = EntityUtils.toString(response.getEntity());
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// ���Ͳ��ɹ������״̬��
			System.out.println(statusCode);
		}
        System.out.println(resStr);
		return resStr;
	}

	
	/**
	 * ��ȡ��ǰ�����û�
	 * @param secret ��ѡ���ɸ����ϵͳ�����������Կ�����û����Ĳ鿴
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 */
	public static String findUsers(String secret) throws ClientProtocolException, IOException, JSONException {
		// url
		String url = "http://u.ifeige.cn/api/userlist";
		// map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("secret", secret==null?"":secret);
		String resStr = publicMethod(url, map);
		// ����JSONObject���󣬽���json��ʽ���ַ���
		JSONObject jsonObj = new JSONObject(resStr);
		// ��ȡ�û���Ϣ
		String users = jsonObj.getString("list");
		return users;
	}

	
	/**
	 * ��Ⱥ����ӽ�����Ϣ���û�
	 * @param secret ��ѡ���ɸ����ϵͳ�����������Կ�����û����Ĳ鿴
	 * @param token  ��ѡ���ɸ����Ⱥ��app_key������Ⱥ��������鿴
	 * @param uid ��ѡ���û��ڷɸ���ŵ�ID��Ψһ��ͨ���û��б�ӿڻ��
	 * @throws ParseException
	 * @throws IOException
	 */
	public static void addUsers(String secret, String token, String uid) throws ParseException, IOException {
		// url
		String url = "http://u.ifeige.cn/api/group_adduser";
		// map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("secret", secret==null?"":secret);
		map.put("token", token==null?"":token);
		map.put("uid", uid==null?"":uid);
		publicMethod(url, map);
	}

	
	/**
	 * ��Ⱥ��ɾ��������Ϣ���û�
	 * @param secret ��ѡ���ɸ����ϵͳ�����������Կ�����û����Ĳ鿴
	 * @param token  ��ѡ���ɸ����Ⱥ��app_key������Ⱥ��������鿴
	 * @param uid  ��ѡ���û��ڷɸ���ŵ�ID��Ψһ��ͨ���û��б�ӿڻ��
	 * @throws ParseException
	 * @throws IOException
	 */
	public static void delUsers(String secret, String token, String uid) throws ParseException, IOException {
		// url
		String url = "http://u.ifeige.cn/api/group_deleteuser";
		// map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("secret", secret==null?"":secret);
		map.put("token", token==null?"":token);
		map.put("uid", uid==null?"":uid);
		publicMethod(url, map);
	}

	
	/**
	 * �ɰ��Ⱥ����Ϣ
	 * @param secret ��ѡ���ɸ����ϵͳ�����������Կ�����û����Ĳ鿴����ȡ��ʽ�鿴README.md
	 * @param token ��ѡ��Ⱥ�鷢��Ϣ��ӦToken����ȡ��ʽ�鿴README.md
	 * @param key ��ѡ����Ϣģ��KEY����������Ϊ����ͨ��֪ͨ
	 * @param title ��ѡ�����ݱ���
	 * @param content ��ѡ��������������
	 * @param remark ��ѡ�����ݱ�ע
	 * @throws Exception
	 */
	public static void sendMsgOld(String secret, String token,String key, String title, String content, String remark)
			throws Exception {
		// url
		String url = "https://u.ifeige.cn/api/send_message";
		// map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("secret", secret==null?"":secret);
		map.put("token", token==null?"":token);
		map.put("key", key==null?"notice":key); //"notice"
		map.put("title", title==null?"":title);
		map.put("content", content==null?"":content);
		map.put("remark", remark==null?"":remark);
		publicMethod(url, map);
	}
	
	/**
	 * �°�Ⱥ��Ϣ������ʱ���ܴ���java��json�����ַ���
	 * @param secret
	 * @param app_key
	 * @param template_id
	 * @param data
	 */
//	public static void sendMsgNew(String secret,String app_key,String template_id,String data) {
//		String url = "https://u.ifeige.cn/api/message/send";
//		// map
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("secret", secret);
//		map.put("app_key", app_key);
//		map.put("template_id", template_id);
//		map.put("data", data);
//		publicMethod(url, map);
//	}
	
	
	/**
	 * ��ָ�����˷�����Ϣ���ϰ棩
	 * ���������ҷ����뵽README.md�в鿴
	 * @param secret  ��ѡ��ϵͳ�����������Կ�����û����Ĳ鿴
	 * @param uid  ��ѡ��������Ϣ��Ա��ID��ͨ���û��б���
	 * @param key  ��ѡ����Ϣģ��KEY����������Ϊ������֪ͨ
	 * @param title  ��ѡ����Ϣ���⣬��ʹ�����Լ��������滻
	 * @param content  ��ѡ����Ϣ���ݣ���ʹ�����Լ��������滻
	 * @param remark  ��ѡ����Ϣ��ϸ˵������ʹ�����Լ��������滻
	 */
	public static void sendMsgPersonOld(String secret,String uid,String key, String title, String content, String remark) {
		String url = "http://u.ifeige.cn/api/user_sendmsg";
		// map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("secret", secret==null?"":secret);
		map.put("uid", uid==null?"":uid);
		map.put("key", key==null?"":key);
		map.put("title", title==null?"":title);
		map.put("content", content==null?"":content);
		map.put("remark", remark==null?"":remark);
		publicMethod(url, map);
	}
}
