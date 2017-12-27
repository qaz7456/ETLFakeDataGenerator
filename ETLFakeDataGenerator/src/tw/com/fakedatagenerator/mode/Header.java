package tw.com.fakedatagenerator.mode;

public class Header {

	private String record_type; // 區別碼
	private String central_no; // 報送單位
	private String record_date; // 檔案日期
	private String reserve_field; // 保留欄

	public String getRecord_type() {
		return record_type;
	}

	public void setRecord_type(String record_type) {
		this.record_type = record_type;
	}

	public String getCentral_no() {
		return central_no;
	}

	public void setCentral_no(String central_no) {
		this.central_no = central_no;
	}

	public String getRecord_date() {
		return record_date;
	}

	public void setRecord_date(String record_date) {
		this.record_date = record_date;
	}

	public String getReserve_field() {
		return reserve_field;
	}

	public void setReserve_field(String reserve_field) {
		this.reserve_field = reserve_field;
	}
}
