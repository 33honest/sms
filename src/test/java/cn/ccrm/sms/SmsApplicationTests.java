package cn.ccrm.sms;

import java.io.InputStream;
import java.util.Hashtable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.ccrm.sms.config.SmsConfig;
import cn.ccrm.sms.utils.Utils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsApplicationTests {
	
	@Autowired
	private SmsConfig smsConfig;

	@Test
	public void contextLoads() {

	}

	@Test
	public void queryBalance() {

		try {
			String UserName = smsConfig.getUsername();// 用户名
			String Password = smsConfig.getPassword();// 密码
			String Timestemp = Utils.getTimestemp();// 时间戳
			String Key = Utils.getKey(UserName, Password, Timestemp);// 加密
			String serverAddress = "http://www.youxinyun.com:3070/Platform_Http_Service/servlet/GetBalance";// 请求的URL
			StringBuffer _StringBuffer = new StringBuffer();
			_StringBuffer.append("UserName=" + UserName + "&");
			_StringBuffer.append("Key=" + Key + "&");
			_StringBuffer.append("Timestemp=" + Timestemp + "&");
			_StringBuffer.append("ServiceType=01&");
			_StringBuffer.append("AccountType=01");
			Hashtable _Header = new Hashtable();
			_Header.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			System.out.println(_StringBuffer.toString());
			// post请求
			InputStream _InputStream = Utils.SendMessage(_StringBuffer.toString().getBytes("utf-8"), _Header, serverAddress);
			String response = Utils.GetResponseString(_InputStream, "utf-8");
			// 响应的包体
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
