package com.baidu.push;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

import com.baidu.push.auth.PushCredentials;
import com.baidu.push.model.DeleteMessageResponseBean;
import com.baidu.push.model.DeviceType;
import com.baidu.push.model.Empty;
import com.baidu.push.model.FetchTagBean;
import com.baidu.push.model.IosCert;
import com.baidu.push.model.Message;
import com.baidu.push.model.MessageType;
import com.baidu.push.model.NotifyMessage;
import com.baidu.push.model.OpenType;
import com.baidu.push.model.PushType;
import com.baidu.push.model.QueryBindListResponseBean;
import com.baidu.push.reqeust.DeleteMsgRequest;
import com.baidu.push.reqeust.DeleteTagRequest;
import com.baidu.push.reqeust.FetchMessageRequest;
import com.baidu.push.reqeust.FetchMsgCountRequest;
import com.baidu.push.reqeust.FetchTagRequest;
import com.baidu.push.reqeust.IosCertRequest;
import com.baidu.push.reqeust.PushMessageRequest;
import com.baidu.push.reqeust.QueryBindRequest;
import com.baidu.push.reqeust.QueryDeviceTypeRequest;
import com.baidu.push.reqeust.QueryUserTagsRequest;
import com.baidu.push.reqeust.SetTagRequest;
import com.baidu.push.reqeust.VerifyBindRequest;
import com.baidu.push.response.PushResponse;
import com.baidu.push.util.PushServiceException;

/**
 * 示例程序
 * @author liyazhou@baidu.com
 *
 */
public class PushClientTest {
	private final static String API_KEY = "XjgeS87hlub6Lv8CoBQncdiC";
	private final static String SECRIT_KEY = "FCa4gufIt9T2cH2Xp3p02seqG6DTVS0o";

	@Test
	public void queryBindList() {
		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));
		QueryBindRequest request = new QueryBindRequest();

		PushResponse<QueryBindListResponseBean> response = client
				.queryBind(request);
		System.out.println(response);
	}

	@Test
	public void pushMessageOne() {
		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));
		PushMessageRequest request = new PushMessageRequest();
		request.setMessageType(MessageType.message);
		request.setMessages("北京百度网讯科技有限公司");
		request.setPushType(PushType.all_user);
		request.setMessageKeys("aaa");

		PushResponse<Integer> response = client.pushMessage(request);

		System.out.println(response);
	}
	
	@Test
	public void pushMessageOneOnlyUser() {
		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));
		String userId = "1019224349965463533";
		String channelId =  "4662362348223953953";
		
		PushMessageRequest request = new PushMessageRequest();
		request.setUserId(userId);
		request.setChannelId(channelId);
		
		request.setMessageType(MessageType.notify);
		String msg = String.format("{'title':'%s','description':'%s','notification_builder_id':1,'notification_basic_style':1,'open_type':2,'custom_content':{'test':'test'}}", "aaaa", 
				"北京百度网讯科技有限公司北京百度网讯科技有限公司北京百度网讯科技有限公司北京百度网讯科技有限公司!");
		
		request.setMessages(msg);
		request.setPushType(PushType.single_user);
		request.setMessageKeys("aaa");

		PushResponse<Integer> response = client.pushMessage(request);

		System.out.println(response);
		
		
	}
	
	/**
	 * 如果使用https的话，需要在本地安装证书，关于安装证书的方法，请参考下面链接:
	 * 
	 *  @see <a href="http://asialee.iteye.com/blog/360352">官方文档</a>
	 * 
	 */
	@Test
	public void pushMessageOneNeedSsl() {
		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));
		PushMessageRequest request = new PushMessageRequest();
		request.setNeedSsl(true);
		String user = "1019224349965463533";
		String channelId = "4662362348223953953";
		
		request.setChannelId(channelId);
		request.setUserId(user);
		request.setMessageType(MessageType.message);
		request.setMessages("中国");
		request.setPushType(PushType.single_user);
		request.setDeviceTypes(Arrays.asList(DeviceType.iso));
		request.setMessageKeys("aaa");

		PushResponse<Integer> response = client.pushMessage(request);

		System.out.println(response);
	}
	
	@Test
	public void pushMessageMulti() {
		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));
		PushMessageRequest request = new PushMessageRequest();
		request.setMessageType(MessageType.message);
		request.setMessages(Arrays.asList("百度","云推送"));
		request.setPushType(PushType.all_user);
		request.setMessageKeys(Arrays.asList("baidu","push"));

		PushResponse<Integer> response = client.pushMessage(request);

		System.out.println(response);
	}
	
	@Test
	public void pushNotify() {
		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));
		PushMessageRequest request = new PushMessageRequest();
		request.setMessageType(MessageType.notify);
		String msg = String.format("{'title':'%s','description':'%s','notification_builder_id':1,'notification_basic_style':1,'open_type':2,'custom_content':{'test':'test'}}", "aaaa", 
				"北京百度网讯科技有限公司北京百度网讯科技有限公司北京百度网讯科技有限公司北京百度网讯科技有限公司!");
		
		request.setMessages(msg);
		request.setPushType(PushType.all_user);
		request.setMessageKeys("msgkeys");

		PushResponse<Integer> response = client.pushMessage(request);

		System.out.println(response);
	}
	
	@Test
	public void pushNotifyOnlyOne() {
		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));
		String user = "1019224349965463533";
		//String user = "823447191115133917";
		PushMessageRequest request = new PushMessageRequest();
		request.setMessageType(MessageType.notify);
		String msg = String.format("{'title':'%s','description':'%s','notification_builder_id':1,'notification_basic_style':1,'open_type':2,'custom_content':{'test':'test'}}", "aaaa", 
				"北京百度网讯科技有限公司北京百度网讯科技有限公司北京百度网讯科技有限公司北京百度网讯科技有限公司!");
		request.setUserId(user);
		request.setMessages(msg);
		request.setPushType(PushType.single_user);
		request.setDeviceTypes(Arrays.asList(DeviceType.iso));
		request.setMessageKeys("msgkeys");

		PushResponse<Integer> response = client.pushMessage(request);

		System.out.println(response);
	}
	
	@Test
	public void pushNotifyWithObject() {
		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));
		PushMessageRequest request = new PushMessageRequest();
		request.setMessageType(MessageType.notify);
		
		String desc = "百度云推送挺好用的!";
		NotifyMessage msg = new NotifyMessage(desc);
		msg.setNotification_builder_id(1);
		msg.setNotification_basic_style(1);
		msg.setOpenType(OpenType.user_define);
		msg.setTitle("百度");
		
		request.setMessages(msg);
		request.setPushType(PushType.all_user);
		request.setMessageKeys("msgkeys");

		PushResponse<Integer> response = client.pushMessage(request);

		System.out.println(response);
	}

	@Test
	public void verifyBind() {
		//String userId = "714190140740382023";
		String userId = "1019224349965463533";

		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));
		VerifyBindRequest request = new VerifyBindRequest(userId);

		PushResponse<Empty> response = client.veryBind(request);

		System.out.println(response);
	}

	@Test
	public void fetchMessage() {
		String userId = "714190140740382023";

		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));
		FetchMessageRequest request = new FetchMessageRequest(userId);

		PushResponse<List<Message>> response = client.fetchMessage(request);
		System.out.println(response);
	}

	@Test
	public void fetchMessageCount() {
		String userId = "714190140740382023";

		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));
		FetchMsgCountRequest request = new FetchMsgCountRequest(userId);

		PushResponse<Integer> response = client.fetchMessageCount(request);
		System.out.println(response);
	}

	@Test
	public void deleteMsg() {
		String userId = "714190140740382023";

		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));
		List<String> msgIds = Arrays.asList("1234", "2345");
		DeleteMsgRequest request = new DeleteMsgRequest(userId, msgIds);

		PushResponse<DeleteMessageResponseBean> response = client
				.deleteMessage(request);
		System.out.println(response);
	}

	@Test
	public void setTag() {
		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));

		String tag = "tag_" + new Random().nextInt();
		SetTagRequest request = new SetTagRequest(tag);

		PushResponse<Empty> response = client.setTag(request);
		System.out.println(response);
	}

	@Test
	public void fetchTag() {
		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));

		FetchTagRequest request = new FetchTagRequest();

		PushResponse<FetchTagBean> response = client.fetchTag(request);
		System.out.println(response);
	}

	@Test
	public void deleteTag() {
		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));

		String tag = "tag_1784262953";
		DeleteTagRequest request = new DeleteTagRequest(tag);

		try {
			PushResponse<Empty> response = client.deleteTag(request);
			System.out.println(response);
		} catch (PushServiceException pse) {
			System.out.println(pse);
		}
	}

	@Test
	public void queryUserTags() {
		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));

		String userId = "714190140740382023";
		QueryUserTagsRequest request = new QueryUserTagsRequest(userId);

		PushResponse<FetchTagBean> response = client.queryUserTags(request);
		System.out.println(response);
	}

	@Test
	public void queryDeviceType() {
		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));

		String channelId = "4662362348223953953";
		QueryDeviceTypeRequest request = new QueryDeviceTypeRequest(channelId);

		PushResponse<DeviceType> response = client.queryDeviceType(request);

		System.out.println(response);
	}

	@Test
	public void initIosCert() {
		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));

		String channelId = "4662362348223953953";
		
		InputStream devIn = ClassLoader.getSystemResourceAsStream("dev.pem");
		InputStream relIn =  ClassLoader.getSystemResourceAsStream("prod.pem");
		
		IosCert iosCert = new IosCert();
		iosCert.setName("name");
		iosCert.setDescription("description");
		iosCert.setReleaseCert(relIn);
		iosCert.setDevCert(devIn);

		IosCertRequest request = new IosCertRequest(iosCert);
		request.setChannelId(channelId);

		PushResponse<Empty> response = client.initIosCert(request);

		System.out.println(response);
	}
	
	@Test
	public void queryIosCert() {
		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));

		IosCertRequest request = new IosCertRequest();
		String channelId = "4662362348223953953";
		request.setChannelId(channelId);

		PushResponse<IosCert> response = client.queryIosCert(request);
		System.out.println(response);
	}
	
	@Ignore
	@Test
	public void updateIosCert() {
		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));

		String channelId = "4209939674169155738";

		IosCert iosCert = new IosCert();
		
		InputStream devIn = ClassLoader.getSystemResourceAsStream("dev.pem");
		InputStream relIn =  ClassLoader.getSystemResourceAsStream("prod.pem");
		
		
		iosCert.setName("name");
		iosCert.setDescription("description");
		iosCert.setReleaseCert(devIn);
		iosCert.setDevCert(relIn);

		IosCertRequest request = new IosCertRequest(iosCert);
		request.setChannelId(channelId);

		PushResponse<Empty> response = client.updateIosCert(request);

		System.out.println(response);
	}

	@Ignore
	@Test
	public void deleteIosCert() {
		PushClient client = new DefaultPushClient(new PushCredentials(API_KEY,
				SECRIT_KEY));

		IosCertRequest request = new IosCertRequest();
		String channelId = "4209939674169155738";
		request.setChannelId(channelId);

		PushResponse<Empty> response = client.deleteIosCert(request);
		System.out.println(response);
	}

	
}
