package testswing;

import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * <P>Title: csc_sh</P>
 * <P>Description: Json转换工具</P>
 * <P>Copyright: Copyright(c) 2016</P>
 * <P>Company: yunkouan.com</P>
 * <P>Date:2016年8月17日-上午9:37:35</P>
 * @author liusy
 * @version 1.0.0
 */
public class JsonUtil {
	
	private static Gson gson;
	//未JSON处理类注入适配器
	static{
		if(gson == null){
			GsonBuilder builder = new GsonBuilder();
			builder.excludeFieldsWithoutExposeAnnotation()
//			.registerTypeAdapter(Date.class, new GsonDateTypeAdapter())
//			.registerTypeAdapter(Integer.class, new GsonIntegerTypeAdapter())
			.serializeNulls();	
			gson = builder.create();
		}
	}
	
	public static Gson getInstance() {
		return gson;
	}
	/**
	 * 转成json数据为java对象
	 * @param json 需要转换的json字符串
	 * @param clasz 期望转换的对象类
	 * @return Object
	 */
	public static <T> T jsonToPojo(String json,Class<T> clasz)  {
		return (T)gson.fromJson(json, clasz);
	}
	
	/**
	 * 将java对象装换成JSON字符串
	 * @param obj 需要转换的对象
	 * @return JSON字符串
	 */
	public static String pojoToJson(Object obj){
		String json = gson.toJson(obj);
		json = StringUtils.replace(json, "null", "\"\"");
		return json;
	}
	
	/**
	 * 为了前台区分返回的json是图片的信息或者是坐标的信息，对生成的json进行转化。
	 * 添加一个type进行标识,当type=coord时，标识返回的结果是坐标信息；当type=image时，标识返回的结果是图片信息。
	 * value表示返回的结果。
	 * 
	 * @param json
	 * @return 
	 * @since 1.0.0
	 * 2014年12月22日-下午3:04:30
	 */
	public static String transformJson(String type,String json){
		return "{\"type\":\""+type+"\",\"value\":" + json + "}";
	}
}
