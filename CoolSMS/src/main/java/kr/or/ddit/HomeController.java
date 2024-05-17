package kr.or.ddit;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Inject
	private IService Service;
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		log.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	
	@GetMapping(value = "form.do")
	public String form() {
		
		return "form";
	}
	
	@RequestMapping(value = "/phoneCheck", method = RequestMethod.GET)
	@ResponseBody
	public String sendSMS(@RequestParam("phone") String userPhoneNumber) {
		int randomNumber = (int)((Math.random()* (9999 - 1000 + 1)) + 1000);//난수 생성
		log.info(userPhoneNumber + "<<<<<<<<<<<<<"); 
		System.out.println(userPhoneNumber + "<<<<<<<<<<<<<<");
		Service.certifiedPhoneNumber(userPhoneNumber,randomNumber);
		
		return Integer.toString(randomNumber);
	}
	
	@PostMapping("/phoneCheck.do")
	public ResponseEntity<String> sendSMS(@RequestBody Map<String, String> map , Model model){
	
		String phone = map.get("phone"); // 맵에서 회원이 입력한 전화번호를 꺼낸다
		int randomNumber = (int) ((Math.random()*(99999 - 10000 + 1)) + 10000);//난수 생성
		log.info(randomNumber + "<<<<<<<<<<");
		Service.certifiedPhoneNumber(phone,randomNumber); // 번호와 랜덤 숫자를 서비스로 전달
		String random = Integer.toString(randomNumber);
		log.info(phone + "<<<<<<<<<<"); 
		
		return new ResponseEntity<String>(random,HttpStatus.OK);
	}
	
	
	
}
