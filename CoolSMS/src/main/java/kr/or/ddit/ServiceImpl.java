package kr.or.ddit;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class ServiceImpl implements IService {

	@Override
	public void certifiedPhoneNumber(String phone, int randomNumber) {
		String api_key = "NCSQZ9MEWCUT85ET";
		String api_secret = "0SCXSM3SATH2EUO48KZJKL3EYVAEVZOA";
		Message coolsms = new Message(api_key, api_secret);

		// 4 params(to, from, type, text) are mandatory. must be filled
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("to", phone); // 수신전화번호
		params.put("from", "010-6360-8655"); // 등록한 발신전화번호. 
		params.put("type", "SMS");
		params.put("text", "[IT Cruit] 인증번호는" + "[" + randomNumber + "]" + "입니다."); // 문자 내용 입력
		params.put("app_version", "test app 1.2"); // application name and version

		try {
			JSONObject obj = (JSONObject) coolsms.send(params);
			System.out.println(obj.toString());
		} catch (CoolsmsException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCode());
		}
	}
}
