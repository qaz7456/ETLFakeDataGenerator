import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import tw.com.fakedatagenerator.party.phone.mode.Detail;
import tw.com.fakedatagenerator.util.RandomValue;
import tw.com.fakedatagenerator.mode.Footer;
import tw.com.fakedatagenerator.mode.Header;

public class PARTY_PHONE {

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
	 * [D]刪除
	 */
	private static String[] CHANGE_CODE_ARRAY = {"N"};
	//private static String[] CHANGE_CODE_ARRAY = {"N","U","D"};
	
	/*
	 * [P01]公司 
	 * [P02]住家 
	 * [P03]手機 
	 * [P04]聯絡人(公司戶)
	 * [P05]法定代理人
	 * [P06]外匯聯絡人其他
	 * [P07]外匯聯絡人手機
	 */
	private static String[] PHONE_TYPE_ARRAY = {"P03"};
	//private static String[] PHONE_TYPE_ARRAY = {"P01","P02","P03","P04","P05","P06","P07"};
	
	//檔案存放路徑
	private static String PATH = "D:\\PSC\\Projects\\全國農業金庫洗錢防制系統案\\假資料\\顧客電話資料\\";
	
	//主題
	private static String THEME = "PARTY_PHONE";
	
	//隨機日期 - 起日
	private static long BEGINTIME = Timestamp.valueOf("2013-01-01 00:00:00").getTime();
	
	//隨機日期 - 訖日
	private static long ENDTIME = Timestamp.valueOf("2018-12-31 00:58:00").getTime();

	public static void main1(String[] args) throws Exception {
		byte[] tmp =Files.readAllBytes(Paths.get(PATH+"910_FR_PARTY_PHONE_20140225.txt"));
		System.out.println(tmp.length);
	}
	public static void main(String[] args) throws Exception {

		//要創建文件的數目
		int fileCount = 1;
		
		//起始本會代號
		int domain_id = 5070013;
		
		//要產生的明細錄筆數範圍
		int detailCount = 100;
		//detailCount = (int)((Math.random()*detailCount)+1);
		
		//要產生的明細錄筆數範圍
		//int detailCount = 50;
		//detailCount = (int)((Math.random()*detailCount)+1);
		
		try {
			System.out.println("資料顧客電話資料創建開始\n");
			
			StringBuilder executiveContent = new StringBuilder();
			
			for (int i = 0; i < fileCount; i++) {

				String fileName = fileNameFormat(getRandomValFromArray(CENTRAL_NO_ARRAY),
						getRandomValFromArray(CURRENCY_TYPE_ARRAY), THEME,
						getRandomTimeBetweenTwoDates(BEGINTIME, ENDTIME));

				StringBuffer stringBuffer = new StringBuffer();

				String central_no = getRandomValFromArray(CENTRAL_NO_ARRAY);
				String record_date = getRandomTimeBetweenTwoDates(BEGINTIME, ENDTIME);
				
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
					//detail.setDomain_id(getRandomValFromArray(DOMAIN_ID_ARRAY));
					detail.setParty_number(RandomValue.getUnicde());
					detail.setChange_code(getRandomValFromArray(CHANGE_CODE_ARRAY));
					detail.setPhone_type(getRandomValFromArray(PHONE_TYPE_ARRAY));
					detail.setPhone_number(RandomValue.getTel());
					byte[] detail_byte = getDetail(detail);
					stringBuffer.append(new String(detail_byte, "BIG5")).append("\r\n");
					domain_id++;
				}
				
				//創建尾錄
				Footer footer = new Footer();
				footer.setRecord_type("3");
				footer.setCentral_no(central_no);
				footer.setRecord_date(record_date);
				footer.setTotal_cnt(String.valueOf(detailCount));
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
			System.out.println("資料顧客電話資料創建完成，此次作業創建" + fileCount + "個檔案");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * 尾錄
	 * 區別碼	R	X(01)
	 * 報送單位	R	X(07)
	 * 檔案日期	R	X(08)
	 * 總筆數	R	9(07)
	 * 保留欄	R	X(20)
	 */
	private static byte[] getFooter(Footer footer) throws UnsupportedEncodingException {
		// 區別碼
		String record_type =footer.getRecord_type();
		byte[] record_type_byte = byteFormat(record_type.getBytes("BIG5"), 1);
		
		// 報送單位
		String central_no =footer.getCentral_no();
		byte[] central_no_byte = byteFormat(central_no.getBytes("BIG5"), 7);
		
		// 檔案日期
		String record_date =footer.getRecord_date();
		byte[] record_date_byte = byteFormat(record_date.getBytes("BIG5"), 8);
		
		// 總筆數
		String total_cnt =footer.getTotal_cnt();
		byte[] total_cnt_byte = byteFormat(total_cnt.getBytes("BIG5"), 7);
		
		// 保留欄
		String reserve_field =footer.getReserve_field();
		byte[] reserve_field_byte = byteFormat(reserve_field.getBytes("BIG5"), 20);
		
		return getAllByteArray(record_type_byte, central_no_byte, record_date_byte, total_cnt_byte, reserve_field_byte);
	}
	
	/*
	 * 明細錄
	 * 區別碼	R X(01) 
	 * 本會代號 	R X(07) 
	 * 客戶統編 	R X(11) 
	 * 異動代號 	R X(01) 
	 * 電話類別 	O X(03) 
	 * 電話號碼	R X(20)
	 */
	private static byte[] getDetail(Detail detail) throws UnsupportedEncodingException {
		
		//區別碼
		String record_type = detail.getRecord_type();
		byte[] record_type_byte = byteFormat(record_type.getBytes("BIG5"), 1);

		//本會代號
		String domain_id = detail.getDomain_id();
		byte[] domain_id_byte = byteFormat(domain_id.getBytes("BIG5"), 7);

		//客戶統編
		String party_number = detail.getParty_number();
		byte[] party_number_byte = byteFormat(party_number.getBytes("BIG5"), 11);

		//異動代號
		String change_code = detail.getChange_code();
		byte[] change_code_byte = byteFormat(change_code.getBytes("BIG5"), 1);

		//電話類別
		String phone_type = detail.getPhone_type();
		byte[] phone_type_byte = byteFormat(phone_type.getBytes("BIG5"), 3);

		//電話號碼
		String phone_number = detail.getPhone_number();
		byte[] phone_number_byte = byteFormat(phone_number.getBytes("BIG5"), 20);

		return getAllByteArray(record_type_byte, domain_id_byte, party_number_byte, change_code_byte, phone_type_byte, phone_number_byte);
	}

	/*
	 * 首錄
	 * 區別碼	R	X(01)
	 * 報送單位	R	X(07)
	 * 檔案日期	R	X(08)
	 * 保留欄	R	X(27)
	 */
	private static byte[] getHeader(Header header) throws UnsupportedEncodingException {

		//區別碼
		String record_type = header.getRecord_type();
		byte[] record_type_byte = byteFormat(record_type.getBytes("BIG5"), 1);

		//報送單位
		String central_no = header.getCentral_no();
		byte[] central_no_byte = byteFormat(central_no.getBytes("BIG5"), 7);

		//檔案日期
		String record_date = header.getRecord_date();
		byte[] record_date_byte = byteFormat(record_date.getBytes("BIG5"), 8);

		//保留欄
		String reserve_field = header.getReserve_field();
		byte[] reserve_field_byte = byteFormat(reserve_field.getBytes("BIG5"), 27);

		return getAllByteArray(record_type_byte, central_no_byte, record_date_byte, reserve_field_byte);
	}

	private static String getSliceBytesText(byte[] source, int start, int end, String charset) {
		String text = "";
		try {
			text = new String(Arrays.copyOfRange(source, start, end), charset);
		} catch (Exception e) {
			return text;
		}
		return text;
	}

	private static byte[] getAllByteArray(byte[]... bs) {

		int length = 0;
		for (int i = 0; i < bs.length; i++) {
			length += bs[i].length;
		}
		byte[] all = new byte[length];

		int start = 0;
		int last_start = 0;
		int copy_start = 0;

		for (int i = 0; i < bs.length; i++) {
			byte[] now = bs[i];

			int nowByteLength = now.length;

			StringBuffer stringBuffer = new StringBuffer();
			for (int j = 0; j < nowByteLength; j++) {
				stringBuffer.append(now[j] + " ");
			}

			start += last_start;

			if (start != 0) {
				start += 1;
				nowByteLength -= 1;
			}

			System.arraycopy(now, 0, all, copy_start, now.length);
			last_start = nowByteLength;
			copy_start += now.length;
		}
		return all;
	}

	private static String fileNameFormat(String... strs) {
		String fileName = "";

		for (int i = 0; i < strs.length; i++) {
			fileName += strs[i];
			if (i < strs.length - 1)
				fileName += "_";
		}
		if (!fileName.equals(""))
			fileName += ".txt";
		return fileName;
	}

	private static <T> T getRandomValFromArray(T[] array) {
		Random random = new Random();

		return array[random.nextInt(array.length)];
	}

	private static String getRandomTimeBetweenTwoDates(long beginTime, long endTime) {
		long diff = endTime - beginTime + 1;
		long date = beginTime + (long) (Math.random() * diff);
		Date randomDate = new Date(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		return dateFormat.format(randomDate);
	}

	private static byte[] byteFormat(byte[] source, int range) {

		return Arrays.copyOf(source, range);

	}
}
