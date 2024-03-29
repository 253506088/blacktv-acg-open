package blacktv.tvacg.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证邮箱与手机是否正确
 * 
 * @author hp
 *
 */
public class Check {
	/**
	 * 验证邮箱
	 *
	 * @param email
	 * @return
	 */

	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号码，11位数字，1开通，第二位数必须是3456789这些数字之一 *
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public static boolean checkPhoneNumber(String phoneNumber) {
		boolean flag = false;
		try { 
			// Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Pattern regex = Pattern.compile("^1[345789]\\d{9}$");
			Matcher matcher = regex.matcher(phoneNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
}
