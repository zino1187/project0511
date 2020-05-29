package network.openapi;

/*이 클래스는 로직을 작성하기 위함이 아니라, 오직 산 1개의 정보를
담기 위한 VO(Value Object)이다!!
*/
public class Mountain {
	private int mntnid;
	private String mntnnm; //산이름 
	private String mntninfopoflc; //소재지
	private int mntninfohght;//산높이
	
	public int getMntnid() {
		return mntnid;
	}
	public void setMntnid(int mntnid) {
		this.mntnid = mntnid;
	}
	public String getMntnnm() {
		return mntnnm;
	}
	public void setMntnnm(String mntnnm) {
		this.mntnnm = mntnnm;
	}
	public String getMntninfopoflc() {
		return mntninfopoflc;
	}
	public void setMntninfopoflc(String mntninfopoflc) {
		this.mntninfopoflc = mntninfopoflc;
	}
	public int getMntninfohght() {
		return mntninfohght;
	}
	public void setMntninfohght(int mntninfohght) {
		this.mntninfohght = mntninfohght;
	}
	
	
}
