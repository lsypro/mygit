package testswing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;



public class TestIpPort {
	 /**
     * ٤�������̽��㱨��
     */
    private static String NUCLEAR_ALARM = "f000";
    //���ܺ˷������ݳ���
    private static int NUCLEAR_DATE_LENGTH = 55;
    //��¼��һ�α�����ʱ��
    private long lastAlarmTimestamp = 0;
	
	private Socket socket;
	private InputStream input;
	private OutputStream out;
	
	private String ip ="172.16.16.11"; // "127.0.0.1"
	private int port = 502;// 9906;
	
	private int dataLen = 0;
	private byte[] bytes = null;//ÿ�ζ�ȡ���ֽ���
	private byte[] norDatas = new byte[55];//��������
	private byte[] alarmDatas = new byte[8];//��������
	
	public String returnIpPort(String ip,int port){
		String ipandport = ip +":"+ port;
		return ipandport;
	}
	
	/**
	 * socket �ر�
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
	 * socket ����
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
	 * ��������
	 * @throws IOException 
	 */
	private String receiveDatas() throws IOException{
		NuclearModel model = new NuclearModel();
    	//�˷����豸��������ֵ����ֱ�ӽ����豸�ϵõ���ֵ��������
    	out.write(1);
	    //���������
	    if(input.available() <= 0) {
	    	return JsonUtil.pojoToJson(model);
	    }
	        
	    dataLen = input.available();
	    
        bytes = new byte[dataLen];
        input.read(bytes);
        
//        logger.debug("data: ", DataTransformUtil.bytesToHexString(bytes));
        
        //�����˼������,����Ϊ55
		if(dataLen == NUCLEAR_DATE_LENGTH) {
			System.arraycopy(this.bytes, 0, this.norDatas, 0, 55);
			receiveNormalDatas(model);
		} else {
			System.out.println("err");
		}
		
	    
	    return JsonUtil.pojoToJson(model);
	}
	/**
	 * ������������(23���ֽ�)
	 * @throws IOException 
	 */
	private void receiveNormalDatas(NuclearModel model) throws IOException{
		
		//��һ��٤��̽����
		byte[] gama01 = new byte[4];
		//��һ������̽����
		byte[] neutron01 = new byte[4];
		//٤����
		byte[] gammaAlarm = new byte[2];
		//���ӱ���
		byte[] neutronAlarm = new byte[2];
		
		System.arraycopy(this.bytes, 9, gama01, 0, 4);
		System.arraycopy(this.bytes, 17, neutron01, 0, 4);
		System.arraycopy(this.bytes, 51, gammaAlarm, 0, 2);
		System.arraycopy(this.bytes, 53, neutronAlarm, 0, 2);
		
		
        //����Ϊ���ﾻ��ֵ�������λ��ʾ��ǧ�ˣ���Ҫ(����ֵ/10)������1λС��
        //���磬����ֵΪ123����ʾ12.3ǧ��
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
	 * ���ֽ�����ת��Ϊ����
	 * 
	 * @param mybytes
	 * @return 
	 * @since 1.0.0
	 * 2014��12��17��-����7:37:55
	 */
	public static int Bytes4ToInt(byte[] mybytes) {
		return ((0xFF & mybytes[0]) << 24 | (0xFF & mybytes[1]) << 16 | (0xFF & mybytes[2]) << 8 | 0xFF & mybytes[3]);
//		return (0xFF & mybytes[0]) | 
//				(0xFF & mybytes[1]) << 8 | 
//				(0xFF & mybytes[2]) << 16 | 
//				(0xFF & mybytes[3]) << 24;
	}
}
