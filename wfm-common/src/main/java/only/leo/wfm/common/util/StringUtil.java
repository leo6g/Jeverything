package only.leo.wfm.common.util;

import java.util.UUID;
import java.util.function.BinaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 */
public final class StringUtil {
	/** Private Constructor **/
	
	
	/**
	 * 判断字符串是否非null && 非空字符 
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isNotEmpty(String param) {
		return param != null && !"".equals(param.trim());
	}

	/**
	 * 判断字符串是否为null || 空字符串
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(String param) {
		return param == null || "".equals(param.trim());
	}
	
	/**
	 * 判断是否为数字类�?
	 * @param str
	 * @return True为数字类�?
	 */
	public static boolean isNum(String str) {
		String regex = "^(-?\\d+)(\\.\\d+)?$";
		return matchRegex(str, regex);
	}
	/**
	 * 生成uuid
	 * @return
	 */
	public static String getSquence() {
		String rtVal = null;
		rtVal = UUID.randomUUID().toString();
		rtVal = rtVal.replaceAll("-", "");
		return rtVal;
	}
				
	private static boolean matchRegex(String value, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	public static void main(String[] args) {
		String[] ss = {"1","2","3"};
		System.out.println(join(ss,","));
	}
	public static String addComma(String s) {
		return s+",";
	}
	public static String join(String[] items, String string) {
		String result = Stream.of(items).map(StringUtil::addComma).reduce(new BinaryOperator<String>() {
			
			@Override
			public String apply(String t, String u) {
				return t+u;
			}
		}).get();
		return result.substring(0, result.length()-1);
	}
}
