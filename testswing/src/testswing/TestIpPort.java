package testswing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;



public class TestIpPort {
	 /**
     * 伽马或中子探测点报警
     */
    private static String NUCLEAR_ALARM = "f000";
    //接受核辐射数据长度
    private static int NUCLEAR_DATE_LENGTH = 55;
    //记录上一次报警的时间
    private long lastAlarmTimestamp = 0;
	
	private Socket socket;
	private InputStream input;
	private OutputStream out;
	
	private String ip ="172.16.16.11"; // "127.0.0.1"
	private int port = 502;// 9906;
	
	private int dataLen = 0;
	private byte[] bytes = null;//每次读取的字节数
	private byte[] norDatas = new byte[55];//正常数据
	private byte[] alarmDatas = new byte[8];//报警数据
	
	public String returnIpPort(String ip,int port){
		String ipandport = ip +":"+ port;
		return ipandport;
	}
	
	/**
	 * socket 关闭
	 * @param e
	 */
	private void socketClose(Exception e) {
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e1) {
				e.printStackTrace();
			}
			
			if(input != null) {
				try {
					input.close();
				} catch (IOException e2) {
					e.printStackTrace();
				}
			}
			if(out != null) {
				try {
					out.close();
				} catch (IOException e2) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * socket 连接
	 * @param e
	 */
	private boolean connect(){
		try {
			this.socket = new Socket(ip, port);
			this.input = this.socket.getInputStream();
			this.out = socket.getOutputStream();
			return true;
		} catch (Exception e) {
			socketClose(e);
			return false;
		}
	}
	
	/**
	 * 接收数据
	 * @throws IOException 
	 */
	private String receiveDatas() throws IOException{
		NuclearModel model = new NuclearModel();
    	//核辐射设备发送任意值，它直接将从设备上得到的值反馈过来
    	out.write(1);
	    //无数据情况
	    if(input.available() <= 0) {
	    	return JsonUtil.pojoToJson(model);
	    }
	        
	    dataLen = input.available();
	    
        bytes = new byte[dataLen];
        input.read(bytes);
        
//        logger.debug("data: ", DataTransformUtil.bytesToHexString(bytes));
        
        //正常核检测数据,长度为55
		if(dataLen == NUCLEAR_DATE_LENGTH) {
			System.arraycopy(this.bytes, 0, this.norDatas, 0, 55);
			receiveNormalDatas(model);
		} else {
			System.out.println("err");
		}
		
	    
	    return JsonUtil.pojoToJson(model);
	}
	/**
	 * 接收正常数据(23个字节)
	 * @throws IOException 
	 */
	private void receiveNormalDatas(NuclearModel model) throws IOException{
		
		//第一个伽马探测器
		byte[] gama01 = new byte[4];
		//第一个中子探测器
		byte[] neutron01 = new byte[4];
		//伽马报警
		byte[] gammaAlarm = new byte[2];
		//中子报警
		byte[] neutronAlarm = new byte[2];
		
		System.arraycopy(this.bytes, 9, gama01, 0, 4);
		System.arraycopy(this.bytes, 17, neutron01, 0, 4);
		System.arraycopy(this.bytes, 51, gammaAlarm, 0, 2);
		System.arraycopy(this.bytes, 53, neutronAlarm, 0, 2);
		
		
        //称重为货物净重值、如果单位表示成千克，需要(称重值/10)，保留1位小数
        //比如，称重值为123，表示12.3千克
        int gamaValue = Bytes4ToInt(gama01);
        int neutron01Value = Bytes4ToInt(neutron01);
        //int gammaAlarmValue = Bytes4ToInt(gammaAlarm);
        //int neutronAlarmValue = Bytes4ToInt(neutronAlarm);
        
        model.setGamma01(gamaValue);
        model.setGammaAlarm(1);
        
        model.setNeutron01(neutron01Value);
        model.setNeutronAlarm(2);
        
//        logger.debug("gamaValue: " + gamaValue + " neutron01Value: " + neutron01Value + " gammaAlarmValue: " + 
//        		DataTransformUtil.bytesToHexString(gammaAlarm) + " neutronAlarmValue: " + DataTransformUtil.bytesToHexString(neutronAlarm));
        
	}
	
	/**
	 * 把字节数组转换为整形
	 * 
	 * @param mybytes
	 * @return 
	 * @since 1.0.0
	 * 2014年12月17日-下午7:37:55
	 */
	public static int Bytes4ToInt(byte[] mybytes) {
		return ((0xFF & mybytes[0]) << 24 | (0xFF & mybytes[1]) << 16 | (0xFF & mybytes[2]) << 8 | 0xFF & mybytes[3]);
//		return (0xFF & mybytes[0]) | 
//				(0xFF & mybytes[1]) << 8 | 
//				(0xFF & mybytes[2]) << 16 | 
//				(0xFF & mybytes[3]) << 24;
	}
}
