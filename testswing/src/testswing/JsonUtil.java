package testswing;

import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * <P>Title: csc_sh</P>
 * <P>Description: Jsonת������</P>
 * <P>Copyright: Copyright(c) 2016</P>
 * <P>Company: yunkouan.com</P>
 * <P>Date:2016��8��17��-����9:37:35</P>
 * @author liusy
 * @version 1.0.0
 */
public class JsonUtil {
	
	private static Gson gson;
	//δJSON������ע��������
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
	 * ת��json����Ϊjava����
	 * @param json ��Ҫת����json�ַ���
	 * @param clasz ����ת���Ķ�����
	 * @return Object
	 */
	public static <T> T jsonToPojo(String json,Class<T> clasz)  {
		return (T)gson.fromJson(json, clasz);
	}
	
	/**
	 * ��java����װ����JSON�ַ���
	 * @param obj ��Ҫת���Ķ���
	 * @return JSON�ַ���
	 */
	public static String pojoToJson(Object obj){
		String json = gson.toJson(obj);
		json = StringUtils.replace(json, "null", "\"\"");
		return json;
	}
	
	/**
	 * Ϊ��ǰ̨���ַ��ص�json��ͼƬ����Ϣ�������������Ϣ�������ɵ�json����ת����
	 * ���һ��type���б�ʶ,��type=coordʱ����ʶ���صĽ����������Ϣ����type=imageʱ����ʶ���صĽ����ͼƬ��Ϣ��
	 * value��ʾ���صĽ����
	 * 
	 * @param json
	 * @return 
	 * @since 1.0.0
	 * 2014��12��22��-����3:04:30
	 */
	public static String transformJson(String type,String json){
		return "{\"type\":\""+type+"\",\"value\":" + json + "}";
	}
}
