package network.openapi;

/*�� Ŭ������ ������ �ۼ��ϱ� ������ �ƴ϶�, ���� �� 1���� ������
��� ���� VO(Value Object)�̴�!!
*/
public class Mountain {
	private int mntnid;
	private String mntnnm; //���̸� 
	private String mntninfopoflc; //������
	private int mntninfohght;//�����
	
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
