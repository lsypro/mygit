package testswing;

public class NuclearModel {
    private int gamma01;//1ºÅÙ¤ÂíÌ½²âÆ÷
    private int gamma02;//2ºÅÙ¤ÂíÌ½²âÆ÷
    private int neutron01;//1ºÅÖĞ×ÓÌ½²âÆ÷
    private int neutron02;//2ºÅÖĞ×ÓÌ½²âÆ÷
    private int gamma03;//3ºÅÙ¤ÂíÌ½²âÆ÷
    private int gamma04;//4ºÅÙ¤ÂíÌ½²âÆ÷
    private int gamma05;//5ºÅÙ¤ÂíÌ½²âÆ÷
    private int gamma06;//6ºÅÙ¤ÂíÌ½²âÆ÷

    private int gammaAlarm = 0;//Ù¤ÂíÌ½²âÆ÷±¨¾¯ 1:±¨¾¯ 0£ºÕı³£
    private int neutronAlarm = 0;//ÖĞ×ÓÌ½²âÆ÷±¨¾¯ 1:±¨¾¯ 0£ºÕı³£
    
    public int getGamma01() {
        return gamma01;
    }
    public void setGamma01(int gamma01) {
        this.gamma01 = gamma01;
    }
    public int getGamma02() {
        return gamma02;
    }
    public void setGamma02(int gamma02) {
        this.gamma02 = gamma02;
    }
    public int getNeutron01() {
        return neutron01;
    }
    public void setNeutron01(int neutron01) {
        this.neutron01 = neutron01;
    }
    public int getNeutron02() {
        return neutron02;
    }
    public void setNeutron02(int neutron02) {
        this.neutron02 = neutron02;
    }
    public int getGamma03() {
        return gamma03;
    }
    public void setGamma03(int gamma03) {
        this.gamma03 = gamma03;
    }
    public int getGamma04() {
        return gamma04;
    }
    public void setGamma04(int gamma04) {
        this.gamma04 = gamma04;
    }
    public int getGamma05() {
        return gamma05;
    }
    public void setGamma05(int gamma05) {
        this.gamma05 = gamma05;
    }
    public int getGamma06() {
        return gamma06;
    }
    public void setGamma06(int gamma06) {
        this.gamma06 = gamma06;
    }
    public int getGammaAlarm() {
        return gammaAlarm;
    }
    public void setGammaAlarm(int gammaAlarm) {
        this.gammaAlarm = gammaAlarm;
    }
    public int getNeutronAlarm() {
        return neutronAlarm;
    }
    public void setNeutronAlarm(int neutronAlarm) {
        this.neutronAlarm = neutronAlarm;
    }
    
	@Override
	public String toString() {
		return "NuclearModel [gamma01=" + gamma01 + ", gamma02=" + gamma02
				+ ", neutron01=" + neutron01 + ", neutron02=" + neutron02
				+ ", gamma03=" + gamma03 + ", gamma04=" + gamma04
				+ ", gamma05=" + gamma05 + ", gamma06=" + gamma06
				+ ", gammaAlarm=" + gammaAlarm + ", neutronAlarm="
				+ neutronAlarm + "]";
	}
    
}
