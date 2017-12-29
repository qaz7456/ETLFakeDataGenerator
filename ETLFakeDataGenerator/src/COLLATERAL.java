import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import tw.com.fakedatagenerator.collateral.mode.Detail;
import tw.com.fakedatagenerator.mode.Footer;
import tw.com.fakedatagenerator.mode.Header;
import tw.com.fakedatagenerator.party.mode.DetailOwn;
import tw.com.fakedatagenerator.util.RandomValue;
import tw.com.fakedatagenerator.util.Utils;

public class COLLATERAL {
	
	/*
	 * 農漁會
	 * 新北市農會附設北區農會電腦共用中心 ：951 
	 * 財團法人農漁會南區資訊中心 ：952 
	 * 板橋區農會電腦共用中心 ：928
	 * 財團法人農漁會聯合資訊中心：910 
	 * 高雄市農會 ：605 
	 * 農漁會資訊共用系統 ：600
	 */
	private static String[] CENTRAL_NO_ARRAY = { "951", "952", "928", "910", "605", "600" };
	
	/*
	 * 本會代號 (配賦代號)
	 * [5070013]新竹市新竹區漁會信用部
	 * [5090015]苗栗縣南龍區漁會信用部
	 * [6200042]屏東縣恆春鎮農會信用部
	 * [6200204]屏東縣琉球鄉農會信用部
	 * [6200949]屏東縣佳冬鄉農會信用部
	 * [6210098]花蓮縣新秀地區農會信用部
	 * [6220066]臺東縣台東地區農會信用部
	 * [5250017]澎湖縣澎湖區漁會信用部
	 * [5260018]金門縣金門區漁會信用部
	 * [6080103]桃園市新屋區農會信用部
	 * [6100027]新竹縣新埔鎮農會信用部
	 * [6110051]苗栗縣苑裡鎮農會信用部
	 * [6110095]苗栗縣公館鄉農會信用部
	 * [6170862]嘉義市農會信用部
	 * [6180221]臺南市安定區農會信用部
	 */
	private static String[] DOMAIN_ID_ARRAY = {"5070013","5090015","6200042","6200204","6200949","6210098","6220066","5250017","5260018","6080103","6100027","6110051","6110095","6170862","6180221"};
	
	/*
	 * 幣別
	 * [CF]台幣
	 * [FR]外幣
	 */
	private static String[] CURRENCY_TYPE_ARRAY = { "CF", "FR" };

	/*
	 * 異動代號
	 * [N]新增
	 * [U]修改
	 */
	private static String[] CHANGE_CODE_ARRAY = {"N"};
	//private static String[] CHANGE_CODE_ARRAY = {"N","U"};

	/*
	 * 擔保品類別
	 */
	private static String[] COLLATERAL_TYPE_ARRAY = { "00","01","02","04","05","06","07","08","09","0A","10","11","12","13","14","15","16","17","18","19","1A","1B","1C","1D","1E","1F","1G","1H","1I","1J","1K","1X","1Y","1Z","20","21","22","23","24","25","26","27","28","29","2A","2B","2X","30","31","32","33","34","35","36","37","38","39","3A","3X","40","41","42","43","44","4X" };
	
	/*
	 * 與主債務人關係
	 */
	private static String[] RELATION_TYPE_CODE_ARRAY = {"1X","2X","3X","4X","5X","6X","7X","8X","9X","AX","BX","XX","XA","XB","XC","XD","XE","XF","XG","XH","XI","XJ","XK","XL","1A","1B","1C","1D","1E","1F","1G","1H","1I","1J","1K","1L","2A","2B","2C","2D","2E","2F","2G","2H","2I","2J","2K","2L","3A","3B","3C","3D","3E","3F","3G","3H","3I","3J","3K","3L","4A","4B","4C","4D","4E","4F","4G","4H","4I","4J","4K","4L","5A","5B","5C","5D","5E","5F","5G","5H","5I","5J","5K","5L","6A","6B","6C","6D","6E","6F","6G","6H","6I","6J","6K","6L","7A","7B","7C","7D","7E","7F","7G","7H","7I","7J","7K","7L","8A","8B","8C","8D","8E","8F","8G","8H","8I","8J","8K","8L","9A","9B","9C","9D","9E","9F","9G","9H","9I","9J","9K","9L","AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL","BA","BB","BC","BD","BE","BF","BG","BH","BI","BJ","BK","BL"};
	
	//檔案存放路徑
	private static String PATH = "D:\\PSC\\Projects\\全國農業金庫洗錢防制系統案\\假資料\\顧客放款擔保品資料\\";
	
	//主題
	private static String THEME = "COLLATERAL";
	
	//隨機日期 - 起日
	private static long BEGINTIME = Timestamp.valueOf("2013-01-01 00:00:00").getTime();
	
	//隨機日期 - 訖日
	private static long ENDTIME = Timestamp.valueOf("2018-12-31 00:58:00").getTime();
	
	public static void main(String[] args) {
		//要創建文件的數目
		int fileCount = 1;
		
		//起始本會代號
		int domain_id = 5030019;
		
		//要產生的明細錄筆數範圍
		int detailCount = 1;
		try {
			System.out.println("顧客放款擔保品資料創建開始\n");
			
			StringBuilder executiveContent = new StringBuilder();
			
			for (int i = 0; i < fileCount; i++) {
				String central_no = Utils.getRandomValFromArray(CENTRAL_NO_ARRAY);
				String record_date = Utils.getRandomTimeBetweenTwoDates(BEGINTIME, ENDTIME);
				
				String fileName = Utils.fileNameFormat(central_no,
						Utils.getRandomValFromArray(CURRENCY_TYPE_ARRAY), THEME,
						record_date);

				StringBuffer stringBuffer = new StringBuffer();
				
				//創建首錄
				Header header = new Header();
				header.setRecord_type("1");
				header.setCentral_no(central_no);
				header.setRecord_date(record_date);
				header.setReserve_field("測試資料");
				byte[] header_byte = getHeader(header);
				
				stringBuffer.append(new String(header_byte, "BIG5")).append("\r\n");

				for(int j=0 ; j<detailCount ; j++){
					//創建明細錄
					Detail detail = new Detail();
					detail.setRecord_type("2");
					detail.setDomain_id( String.valueOf(domain_id) );
					detail.setCollateral_id(RandomValue.getRandomNumToStr(20));
					detail.setChange_code(Utils.getRandomValFromArray(CHANGE_CODE_ARRAY));
					String loan_master_number =RandomValue.getRandomNumToStr(20);
					detail.setLoan_master_number(loan_master_number);
					detail.setLoan_detail_number(loan_master_number);
					detail.setCollateral_type(Utils.getRandomValFromArray(COLLATERAL_TYPE_ARRAY));
					detail.setCollateral_value(RandomValue.getRandomNumToStr(14));
					detail.setGuarantee_amount(RandomValue.getRandomNumToStr(14));
					detail.setCollateral_desc("擔保品描述");
					detail.setRelation_id(RandomValue.getRandomNumToStr(11));
					detail.setRelation_name(RandomValue.getChineseName());
					detail.setRelation_type_code(Utils.getRandomValFromArray(RELATION_TYPE_CODE_ARRAY));
					detail.setParty_number(RandomValue.getRandomNumToStr(11));
					
					byte[] detail_byte = getDetail(detail);
					stringBuffer.append(new String(detail_byte, "BIG5")).append("\r\n");
					domain_id++;
			
				}
				
				//創建尾錄
				Footer footer = new Footer();
				footer.setRecord_type("3");
				footer.setCentral_no(central_no);
				footer.setRecord_date(record_date);
				footer.setTotal_cnt(Utils.DigitalFillBit(detailCount, 7));
				footer.setReserve_field("測試資料");
				byte[] footer_byte = getFooter(footer);
				
				stringBuffer.append(new String(footer_byte, "BIG5"));
				

				Path path = Paths.get(PATH + fileName);
				
				try (BufferedWriter writer = Files.newBufferedWriter(path)) {

					Files.write(path, stringBuffer.toString().getBytes("BIG5"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
					
					executiveContent.append("檔名: ").append(fileName).append("\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(executiveContent.toString());
			System.out.println("顧客放款擔保品資料創建完成，此次作業創建" + fileCount + "個檔案");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/*
	 * 首錄
	 * 區別碼	R	X(01)
	 * 報送單位	R	X(07)
	 * 檔案日期	R	X(08)
	 * 保留欄	R	X(187)
	 */
	private static byte[] getHeader(Header header) throws UnsupportedEncodingException {

		//區別碼
		String record_type = header.getRecord_type();
		byte[] record_type_byte = Utils.byteFormat(record_type.getBytes("BIG5"), 1);

		//報送單位
		String central_no = header.getCentral_no();
		byte[] central_no_byte = Utils.byteFormat(central_no.getBytes("BIG5"), 7);

		//檔案日期
		String record_date = header.getRecord_date();
		byte[] record_date_byte = Utils.byteFormat(record_date.getBytes("BIG5"), 8);

		//保留欄
		String reserve_field = header.getReserve_field();
		byte[] reserve_field_byte = Utils.byteFormat(reserve_field.getBytes("BIG5"), 187);

		return Utils.getAllByteArray(record_type_byte, central_no_byte, record_date_byte, reserve_field_byte);
	}
	
	
	/* 
	 * 明細錄
	 */
	private static byte[] getDetail(Detail detail) throws UnsupportedEncodingException {
		
		//區別碼
		String record_type = detail.getRecord_type();
		byte[] record_type_byte = Utils.byteFormat(record_type.getBytes("BIG5"), 1);

		//本會代號
		String domain_id = detail.getDomain_id();
		byte[] domain_id_byte = Utils.byteFormat(domain_id.getBytes("BIG5"), 7);

		//擔保品編號
		String collateral_id = detail.getCollateral_id();
		byte[] collateral_id_byte = Utils.byteFormat(collateral_id.getBytes("BIG5"), 20);

		//異動代號
		String change_code = detail.getChange_code();
		byte[] change_code_byte = Utils.byteFormat(change_code.getBytes("BIG5"), 1);

		//批覆書編號/申請書編號
		String loan_master_number = detail.getLoan_master_number();
		byte[] loan_master_number_byte = Utils.byteFormat(loan_master_number.getBytes("BIG5"), 20);

		//額度編號
		String loan_detail_number = detail.getLoan_detail_number();
		byte[] loan_detail_number_byte = Utils.byteFormat(loan_detail_number.getBytes("BIG5"), 20);
		
		//額度編號
		String collateral_type = detail.getCollateral_type();
		byte[] collateral_type_byte = Utils.byteFormat(collateral_type.getBytes("BIG5"), 2);
		
		//鑑價金額
		String collateral_value = detail.getCollateral_value();
		byte[] collateral_value_byte = Utils.byteFormat(collateral_value.getBytes("BIG5"), 14);
		
		//擔保金額
		String guarantee_amount = detail.getGuarantee_amount();
		byte[] guarantee_amount_byte = Utils.byteFormat(guarantee_amount.getBytes("BIG5"), 14);
		
		//擔保品描述
		String collateral_desc = detail.getCollateral_desc();
		byte[] collateral_desc_byte = Utils.byteFormat(collateral_desc.getBytes("BIG5"), 40);
		
		//所有權人統編
		String relation_id = detail.getRelation_id();
		byte[] relation_id_byte = Utils.byteFormat(relation_id.getBytes("BIG5"), 11);
		
		//所有權人姓名
		String relation_name = detail.getRelation_name();
		byte[] relation_name_byte = Utils.byteFormat(relation_name.getBytes("BIG5"), 40);
		
		//與主債務人關係
		String relation_type_code = detail.getRelation_type_code();
		byte[] relation_type_code_byte = Utils.byteFormat(relation_type_code.getBytes("BIG5"), 2);
		
		//客戶(主債務人)統編
		String party_number = detail.getParty_number();
		byte[] party_number_byte = Utils.byteFormat(party_number.getBytes("BIG5"), 11);
		
		
		return Utils.getAllByteArray(record_type_byte,domain_id_byte,collateral_id_byte,change_code_byte,loan_master_number_byte,loan_detail_number_byte,collateral_type_byte,collateral_value_byte,guarantee_amount_byte,collateral_desc_byte,relation_id_byte,relation_name_byte,relation_type_code_byte,party_number_byte);
	}
	
	/*
	 * 尾錄
	 * 區別碼	R	X(01)
	 * 報送單位	R	X(07)
	 * 檔案日期	R	X(08)
	 * 總筆數	R	9(07)
	 * 保留欄	R	X(180)
	 */
	private static byte[] getFooter(Footer footer) throws UnsupportedEncodingException {
		// 區別碼
		String record_type =footer.getRecord_type();
		byte[] record_type_byte = Utils.byteFormat(record_type.getBytes("BIG5"), 1);
		
		// 報送單位
		String central_no =footer.getCentral_no();
		byte[] central_no_byte = Utils.byteFormat(central_no.getBytes("BIG5"), 7);
		
		// 檔案日期
		String record_date =footer.getRecord_date();
		byte[] record_date_byte = Utils.byteFormat(record_date.getBytes("BIG5"), 8);
		
		// 總筆數
		String total_cnt =footer.getTotal_cnt();
		byte[] total_cnt_byte = Utils.byteFormat(total_cnt.getBytes("BIG5"), 7);
		
		// 保留欄
		String reserve_field =footer.getReserve_field();
		byte[] reserve_field_byte = Utils.byteFormat(reserve_field.getBytes("BIG5"), 180);
		
		return Utils.getAllByteArray(record_type_byte, central_no_byte, record_date_byte, total_cnt_byte, reserve_field_byte);
	}

}
