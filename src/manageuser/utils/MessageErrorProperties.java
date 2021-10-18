/**
 * Copyright(C) 2020  Luvina SoftWare
 * MessageErrorProperties.java, Jul 8, 2020 tiepnd
 */
package manageuser.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/**
 * là class để đọc dữ liệu từ file message_error_ja.properties
 * 
 * @author tiepnd
 */
public class MessageErrorProperties {
	// lưu các cặp <key, value> trong file properties
	private static Map<String, String> mapMassageErrorProperties = new HashMap<String, String>();
	static {
		try {
			// tạo đối tượng kiểu Properties
			Properties properties = new Properties(); 
			// đọc file properties
			properties.load(new InputStreamReader(MessageErrorProperties.class.getClassLoader()
					.getResourceAsStream(Constant.PROPERTIES_MESSAGE_ERROR_PATH), Constant.UTF_8));
			// lưu các giá trị key trong file properties					
			Enumeration<?> enumeration = properties.propertyNames(); 
			// true nếu vẫn còn phần tử, false nếu tất cả phần tử đã được lấy ra
			while (enumeration.hasMoreElements()) { 
				// key = key tiếp theo
				String key = (String) enumeration.nextElement(); 
				// lấy value tương ứng với key
				String value = properties.getProperty(key); 
				// thêm vào list
				mapMassageErrorProperties.put(key, value); 
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * lấy value tương ứng với key trong file properties
	 * 
	 * @param key tên key trong file properties
	 * @return trả về value tương ứng với key
	 */
	public static String getValueByKey(String key) {
		String value = mapMassageErrorProperties.get(key);
		return value;
	}
}