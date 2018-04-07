package com.edroplet.rent.clients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.edroplet.rent.BaseApplication;
import com.edroplet.rent.beans.ActivitiesData;
import com.edroplet.rent.beans.HandPickedData;
import com.edroplet.rent.beans.HandPickedDetailData;
import com.edroplet.rent.beans.UserInfo;
import com.edroplet.rent.beans.TopicsData;

/**
 * @name ClientApi
 * @Descripation 这是一个用来访问网络的类<br>
 *               1、<br>
 *               2、<br>
 *               3、<br>
 * @author edroplet
 * @date 2018-04-06
 * @version 1.0
 */
public class ClientApi {
	private static String startId;

	public ClientApi() {
		// TODO Auto-generated constructor stub
	}

	public static JSONObject ParseJson(final String path, final String encode) {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		HttpParams httpParams = httpClient.getParams();
		// HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
		// HttpConnectionParams.setSoTimeout(httpParams, 10000);
		HttpPost httpPost = new HttpPost(path);
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(httpResponse.getEntity(),
						encode);
				JSONObject jsonObject = new JSONObject(result);
				return jsonObject;
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return null;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			if (httpClient != null)
				httpClient.getConnectionManager().shutdown();
		}
		return null;

	}

	/**
	 * @param Url
	 *            下载的Url
	 * @return
	 */
	public static ArrayList<HandPickedData> getJingXuanData(String Url) {
		ArrayList<HandPickedData> list = new ArrayList<HandPickedData>();
		JSONObject json = ParseJson(Url, "utf-8");

		if (json == null) {
			return null;
		} else {
			try {

				JSONArray Data = json.getJSONObject("obj").getJSONArray("list");
				
				for (int i = 0; i < Data.length(); i++) {
					System.out.println("------->"+i);
					JSONObject data = Data.getJSONObject(i);
					HandPickedData handPickedData = new HandPickedData();
					handPickedData.setId(data.optString("id"));
					JSONObject element = data.getJSONObject("tour");
					handPickedData.setTitle(element.optString("title"));
					handPickedData.setPubdate(element.optString("startdate"));
					handPickedData.setPictureCount(element.optString("cntP"));
					handPickedData.setImage(element.optString("coverpic"));
					handPickedData.setViewCount(element.optString("pcolor"));
					handPickedData.setFavoriteCount(element.getString("likeCnt"));
					handPickedData.setViewCount(element.optString("viewCnt"));
					handPickedData.setForeword(element.optString("foreword"));
					UserInfo userInfo = new UserInfo();
					JSONObject owner = element.optJSONObject("owner");
					userInfo.setUsername(owner.optString("username"));
					userInfo.setNickname(owner.optString("nickname"));
					userInfo.setUserId(owner.optString("userid"));
					userInfo.setAvatar(owner.getString("avatar"));
					handPickedData.setUserInfo(userInfo);
					JSONArray dispcitys = element.getJSONArray("dispCities");
					String[] citys = new String[dispcitys.length()];
					for (int j = 0; j < dispcitys.length(); j++) {

						citys[j] = dispcitys.optString(j);
					}
					handPickedData.setDispCities(citys);
					handPickedData.setCmtCount(element.getString("cntcmt"));
					// System.out.println("----->"+handPickedData.getDispCities().length);
					/*JSONArray cmt = element.optJSONArray("cmt");
					Comment[] comments = new Comment[cmt.length()];
					if (cmt!=null) {
						
						for (int j = 0; j < cmt.length(); j++) {
							JSONObject cmtdata = cmt.getJSONObject(i);
							Comment comment = new Comment();
							UserInfo uInfo = new UserInfo();
							JSONObject user = cmtdata.getJSONObject("user");
							uInfo.setAvatar(user.optString("avatar"));
							uInfo.setNickname(user.optString("username"));
							uInfo.setNickname(user.optString("nickname"));
							comment.setUserInfo(uInfo);
							comment.setContent(cmtdata.optString("words"));
							comment.setLike(cmtdata.optBoolean("isLiked"));
							comments[j] = comment;
						}
					}
					handPickedData.setComments(comments);*/
					handPickedData.setTourId(element.optString("id"));
					list.add(handPickedData);
					if (i == Data.length() - 1) {
						startId = handPickedData.getId();
					}

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("------->"+list.size());
			return list;

		}

	}

	public static String getStartId() {

		return startId;
	}

	/**
	 * 解析实体的Json数据
	 * 
	 * @param tourId
	 * @return
	 */
	public static ArrayList<HandPickedDetailData> getJingxuanDetailDatas(
			String tourId) {
		ArrayList<HandPickedDetailData> list = new ArrayList<HandPickedDetailData>();
		String jingXuanDetailUrl = BaseApplication.webServiceConfig.getHost() +
				"/demo27/php/formAction.php?submit=getATour2&tourid="
				+ tourId
				+ "&recType=1&refer=PlazaHome&ID2=1&token=3a79c4024f682aee74723a419f6605f9&v=a5.0.4&vc=anzhi&vd=f2e4ee47505f6fba";
		System.out.println("------>"+jingXuanDetailUrl);
		JSONObject json = ParseJson(jingXuanDetailUrl, "utf-8");
		if (json == null) {
			return null;
		} else {

			try {
				JSONArray Data = json.getJSONObject("obj").getJSONArray(
						"records");
				for (int i = 0; i < Data.length(); i++) {
					JSONObject data = Data.getJSONObject(i);
					HandPickedDetailData handPickedDetailData = new HandPickedDetailData();
					/*String location = data.getJSONObject("location")
							.optJSONObject("city").optString("city");
					handPickedDetailData.setPoi(location);*/
					handPickedDetailData.setText(data.getString("words"));
					handPickedDetailData
							.setImage("http://img.117go.com/timg/p308/"
									+ data.getString("picfile"));
					list.add(handPickedDetailData);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}

	}
	
	/**
	 * 解析实体的Json数据
	 * 
	 * @param
	 * @return
	 */
	public static ArrayList<ArrayList<TopicsData>> getzhuantiDatas() {
		ArrayList<ArrayList<TopicsData>> list_all =new ArrayList<ArrayList<TopicsData>>();
		String jingXuanDetailUrl = BaseApplication.webServiceConfig.getHost() +
				"/demo27/php/gloryAction.php?submit=getGlory&vc=wandoujia&vd=80f117eb4244b778&v=a5.0.6";
		JSONObject json = ParseJson(jingXuanDetailUrl, "utf-8");
		if (json == null) {
			return null;
		} else {
			try {
				JSONArray Data = json.getJSONObject("obj").getJSONArray(
						"trip_list");
				
				ArrayList<TopicsData> list1 = new ArrayList<TopicsData>();
				System.out.println(">>>>>>>>>>"+Data);
				for (int i = 0; i < Data.length(); i++) {
					TopicsData topicsData =new TopicsData();
					JSONObject data =Data.getJSONObject(i);
					topicsData.setId(data.getString("id"));
					topicsData.setName(data.getString("name"));
					topicsData.setIamge("http://img.117go.com/timg/p616/"+data.getString("coverpic"));
					list1.add(topicsData);
					System.out.println(">>>>>>>>>>"+ topicsData.getIamge());
					
				}
				
				JSONArray Data2 = json.getJSONObject("obj").getJSONArray(
						"pic_list");
				ArrayList<TopicsData> list2 = new ArrayList<TopicsData>();
				for (int i = 0; i < Data2.length(); i++) {
					TopicsData topicsData =new TopicsData();
					JSONObject data =Data2.getJSONObject(i);
					topicsData.setId(data.getString("id"));
					topicsData.setName(data.getString("name"));
					topicsData.setIamge("http://img.117go.com/timg/p616/"+data.getString("coverpic"));
					list2.add(topicsData);
				}
				
				list_all.add(list1);
				list_all.add(list2);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list_all;
		}

	}
	
	public static ArrayList<HandPickedData> getTourZhuanti(String searchId){
		ArrayList<HandPickedData> list = new ArrayList<HandPickedData>();
		String Url = BaseApplication.webServiceConfig.getHost() +
				"/demo27/php/searchAction.php?submit=getSearchTours&searchid="
				+searchId+
				"&startId=0&length=20&fetchNewer=1&vc=wandoujia&vd=80f117eb4244b778&v=a5.0.6";
		JSONObject json = ParseJson(Url, "utf-8");
		if (json == null) {
			return null;
		} else {
			try {

				JSONArray Data = json.getJSONObject("obj").getJSONArray("items");
				
				for (int i = 0; i < Data.length(); i++) {
					System.out.println("------->"+i);
					JSONObject data = Data.getJSONObject(i);
					HandPickedData handPickedData = new HandPickedData();
					handPickedData.setId(data.optString("id"));
					JSONObject element = data.getJSONObject("tour");
					handPickedData.setTitle(element.optString("title"));
					handPickedData.setPubdate(element.optString("startdate"));
					handPickedData.setPictureCount(element.optString("cntP"));
					handPickedData.setImage(element.optString("coverpic"));
					handPickedData.setViewCount(element.optString("pcolor"));
					handPickedData.setFavoriteCount(element.getString("likeCnt"));
					handPickedData.setViewCount(element.optString("viewCnt"));
					handPickedData.setForeword(element.optString("foreword"));
					UserInfo userInfo = new UserInfo();
					JSONObject owner = element.optJSONObject("owner");
					userInfo.setUsername(owner.optString("username"));
					userInfo.setNickname(owner.optString("nickname"));
					userInfo.setUserId(owner.optString("userid"));
					userInfo.setAvatar(owner.getString("avatar"));
					handPickedData.setUserInfo(userInfo);
					JSONArray dispcitys = element.getJSONArray("dispCities");
					String[] citys = new String[dispcitys.length()];
					for (int j = 0; j < dispcitys.length(); j++) {

						citys[j] = dispcitys.optString(j);
					}
					handPickedData.setDispCities(citys);
					handPickedData.setCmtCount(element.getString("cntcmt"));
					// System.out.println("----->"+handPickedData.getDispCities().length);
					/*JSONArray cmt = element.optJSONArray("cmt");
					Comment[] comments = new Comment[cmt.length()];
					if (cmt!=null) {
						
						for (int j = 0; j < cmt.length(); j++) {
							JSONObject cmtdata = cmt.getJSONObject(i);
							Comment comment = new Comment();
							UserInfo uInfo = new UserInfo();
							JSONObject user = cmtdata.getJSONObject("user");
							uInfo.setAvatar(user.optString("avatar"));
							uInfo.setNickname(user.optString("username"));
							uInfo.setNickname(user.optString("nickname"));
							comment.setUserInfo(uInfo);
							comment.setContent(cmtdata.optString("words"));
							comment.setLike(cmtdata.optBoolean("isLiked"));
							comments[j] = comment;
						}
					}
					handPickedData.setComments(comments);*/
					handPickedData.setTourId(element.optString("id"));
					list.add(handPickedData);
					if (i == Data.length() - 1) {
						startId = handPickedData.getId();
					}

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("------->"+list.size());
			return list;

		}

		
	}
	
	public static ArrayList<ActivitiesData> getHuoDongList(){
		String jingXuanDetailUrl = BaseApplication.webServiceConfig.getHost() +
		"/demo27/php/eventAction.php?submit=getEventList&startId=0&length=20&vc=wandoujia&vd=80f117eb4244b778&v=a5.0.6";
		ArrayList<ActivitiesData> list = new ArrayList<ActivitiesData>();
		JSONObject json = ParseJson(jingXuanDetailUrl, "utf-8");
		if (json == null) {
			return null;
		} else {

			try {
				JSONArray Data = json.getJSONArray("obj");
				
				
				for (int i = 0; i < Data.length(); i++) {
					ActivitiesData activitiesData =new ActivitiesData();
					JSONObject data =Data.getJSONObject(i);
					activitiesData.setId(data.getString("id"));
					activitiesData.setName(data.getString("name"));
					activitiesData.setIamge("http://img.117go.com/timg/p616/"+data.getString("coverpic"));
					activitiesData.setUrlS(data.getString("url"));
					list.add(activitiesData);
				}
					
				}catch (Exception e) {
					// TODO: handle exception
				}
		}
		return list;
	}
}
