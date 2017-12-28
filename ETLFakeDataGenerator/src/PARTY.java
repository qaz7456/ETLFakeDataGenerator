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

import tw.com.fakedatagenerator.mode.Footer;
import tw.com.fakedatagenerator.mode.Header;
import tw.com.fakedatagenerator.party.mode.DetailOwn;
import tw.com.fakedatagenerator.util.RandomValue;
import tw.com.fakedatagenerator.util.Utils;

public class PARTY {
	
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
	 * [I]停止往來
	 */
	private static String[] CHANGE_CODE_ARRAY = {"N"};
	//private static String[] CHANGE_CODE_ARRAY = {"N","U","I"};

	/*
	 * 是否為本行客戶
	 * [Y]是(有開立帳戶的客戶)
	 * [N]否
	 */
	private static String[] MY_CUSTOMER_FLAG_ARRAY = { "Y"};
	//private static String[] MY_CUSTOMER_FLAG_ARRAY = { "Y", "N" };
	
	/*
	 * 歸屬本/分會代號
	 * [5080014]苗栗縣通苑區漁會
	 * [5100019]苗栗縣南龍區漁會
	 * [5110010]彰化縣彰化區漁會
	 * [5420010]臺南市麻豆區農會
	 * [5490017]臺南市下營區農會
	 * [5510012]臺南市官田區農會
	 * [5520013]臺南市大內區農會
	 * [5570018]臺南市新市區農會
	 * [5580019]臺南市安定區農會
	 * [5620016]臺南市仁德區農會
	 * [5670011]臺南市南化區農會
	 * [5680012]臺南市七股區農會
	 * [5700017]南投縣南投市農會
	 * [5750012]南投縣中寮鄉農會
	 * [5770014]南投縣魚池鄉農會
	 * [5780015]南投縣水里鄉農會
	 * [5800010]南投縣鹿谷鄉農會
	 * [5810011]南投縣信義鄉農會
	 * [5820012]南投縣仁愛鄉農會
	 * [6310013]彰化縣溪湖鎮農會
	 * [6320014]彰化縣田中鎮農會
	 * [6330015]彰化縣北斗鎮農會
	 * [6350017]彰化縣線西鄉農會
	 * [6360018]彰化縣伸港鄉農會
	 * [6380010]彰化縣花壇鄉農會
	 * [6390011]彰化縣大村鄉農會
	 * [6420017]彰化縣社頭鄉農會
	 * [6430018]彰化縣二水鄉農會
	 * [6460011]彰化縣大城鄉農會
	 * [6470012]彰化縣溪州鄉農會
	 * [6490014]彰化縣埔鹽鄉農會
	 * [6500018]彰化縣福興鄉農會
	 * [6510019]彰化縣彰化市農會
	 * [6980018]雲林縣麥寮鄉農會
	 * [6990019]雲林縣林內鄉農會
	 * [8600015]南投縣中埔鄉農會
	 * [8680013]台中市東勢區農會
	 * [8700018]臺中市梧棲區農會
	 * [8710019]台中市大甲區農會
	 * [8720010]台中市沙鹿區農會
	 * [8750013]臺中市太平區農會
	 * [8760014]臺中市烏日區農會
	 * [8770015]臺中市后里區農會
	 * [8780016]臺中市大雅區農會
	 * [8800011]台中市石岡區農會
	 * [8810012]台中市新社區農會
	 * [8820013]臺中市大肚區農會
	 * [8830014]台中市外埔區農會
	 * [8840015]台中市大安區農會
	 * [8860017]台中市和平區農會
	 * [9070011]苗栗縣竹南鎮農會
	 * [9150012]苗栗縣西湖鄉農會
	 * [9170014]苗栗縣公館鄉農會
	 * [9180015]苗栗縣銅鑼鄉農會
	 * [9190016]苗栗縣三義鄉農會
	 * [9200010]苗栗縣造橋鄉農會
	 * [9210011]苗栗縣南庄鄉農會
	 * [9250015]苗栗縣三灣鄉農會
	 * [9260016]苗栗縣大湖地區農會
	 * [9530012]彰化縣田尾鄉農會
	 */
	private static String[] BRANCH_CODE_ARRAY={"5080014","5100019","5110010","5420010","5490017","5510012","5520013","5570018","5580019","5620016","5670011","5680012","5700017","5750012","5770014","5780015","5800010","5810011","5820012","6310013","6320014","6330015","6350017","6360018","6380010","6390011","6420017","6430018","6460011","6470012","6490014","6500018","6510019","6980018","6990019","8600015","8680013","8700018","8710019","8720010","8750013","8760014","8770015","8780016","8800011","8810012","8820013","8830014","8840015","8860017","9070011","9150012","9170014","9180015","9190016","9200010","9210011","9250015","9260016","9530012"};
	
	/*
	 * 顧客類型
	 * [100]自然人-個人
	 * [211]中小企業-無記名股票可發行
	 * [212]中小企業-無記名股票不可發行
	 * [221]上市公司-無記名股票可發行
	 * [222]上市公司-無記名股票不可發行
	 * [231]上櫃公司-無記名股票可發行
	 * [232]上櫃公司-無記名股票不可發行
	 * [241]興櫃公司-無記名股票可發行
	 * [242]興櫃公司-無記名股票不可發行
	 * [260]外國公司
	 * [270]境外公司
	 * [280]一般公司-其他
	 * [310]財團法人
	 * [320]社團法人
	 * [330]公法人
	 * [390]非營利法人-其他
	 * [410]獨資
	 * [420]合夥
	 * [490]非法人-其他
	 * [900]其他
	 */
	private static String[] ENTITY_TYPE_ARRAY = { "100","211","212","221","222","231","232","241","242","260","270","280","310","320","330","390","410","420","490","900" };
	
	/*
	 * 客戶子類型
	 * [1]金融同業註記 
	 * [2]預付或儲值卡公司
	 * [空白]其他
	 */
	private static String[] ENTITY_SUB_TYPE_ARRAY = { "1","2"," " };
	
	/*
	 * 國籍
	 * [AD]安道爾	ANDORRA	
	 * [AE]阿拉伯聯合大公國	UNITED ARAB EMIRATES	
	 * [AF]阿富汗	AFGHANISTAN	
	 * [AG]安地卡及巴布達	ANTIGUA AND BARBUDA	
	 * [AI]英屬安圭拉	ANGUILLA	
	 * [AL]阿爾巴尼亞	ALBANIA	
	 * [AM]亞美尼亞	ARMENIA	
	 * [AO]安哥拉	ANGOLA	
	 * [AQ]南極洲	ANTARCTICA	
	 * [AR]阿根廷	ARGENTINA	
	 * [AS]美屬薩摩亞	AMERICAN SAMOA	
	 * [AT]奧地利	AUSTRIA	
	 * [AU]澳大利亞	AUSTRALIA	
	 * [AW]阿魯巴	ARUBA	
	 * [AX]奧蘭群島	ALAND ISLANDS	
	 * [AZ]亞塞拜然	AZERBAIJAN	
	 * [BA]波士尼亞	BOSNIA AND HERZEGOVINA	
	 * [BB]巴貝多	BARBADOS	
	 * [BD]孟加拉	BANGLADESH	
	 * [BE]比利時	BELGIUM	
	 * [BF]上伏塔(布吉納法索)	BURKINA FASO	
	 * [BG]保加利亞	BULGARIA	
	 * [BH]巴林	BAHRAIN	
	 * [BI]蒲隆地	BURUNDI	
	 * [BJ]貝南(達荷美)	BENIN	
	 * [BL]聖巴瑟米	SAINT BARTHÉLEMY	
	 * [BM]百慕達	BERMUDA	
	 * [BN]汶萊	BRUNEI DARUSSALAM	
	 * [BO]玻利維亞	BOLIVIA	
	 * [BQ]波奈、聖佑達修斯及沙巴	BONAIRE, SINT EUSTATIUS AND SABA	
	 * [BR]巴西	BRAZIL	
	 * [BS]巴哈馬	BAHAMAS	
	 * [BT]不丹	BHUTAN	
	 * [BV]波維特島	BOUVET ISLAND	
	 * [BW]波扎那	BOTSWANA	
	 * [BY]白俄羅斯	BELARUS	
	 * [BZ]貝里斯	BELIZE	
	 * [CA]加拿大	CANADA	
	 * [CC]可可斯群島	COCOS (KEELING) ISLANDS	
	 * [CD]剛果民主共和國	CONGO, THE DEMOCRATIC REP. OF THE	
	 * [CF]中非共和國	CENTRAL AFRICAN REPUBLIC	
	 * [CG]剛果	CONGO	
	 * [CH]瑞士	SWITZERLAND	
	 * [CI]象牙海岸	COTE D'IOVIRE	
	 * [CK]科克群島	COOK ISLANDS	
	 * [CL]智利	CHILE	
	 * [CM]喀麥隆	CAMEROON	
	 * [CN]中國大陸	CHINA	
	 * [CO]哥倫比亞	COLOMBIA	
	 * [CR]哥斯大黎加	COSTA RICA	
	 * [CU]古巴	CUBA	
	 * [CV]佛德角	CAPE VERDE	
	 * [CW]古拉索	CURAÇAO	
	 * [CX]聖誕島	CHRISTMAS ISLAND	
	 * [CY]塞普路斯	CYPRUS	
	 * [CZ]捷克	CZECH REPUBLIC	
	 * [DE]德國	GERMANY	
	 * [DJ]吉布地	DJIBOUTI	
	 * [DK]丹麥	DENMARK	
	 * [DM]多米尼克	DOMINICA	
	 * [DO]多明尼加	DOMINICAN REPUBLIC	
	 * [DZ]阿爾及利亞	ALGERIA	
	 * [EC]厄瓜多爾	ECUADOR	
	 * [EE]愛沙尼亞	ESTONIA	
	 * [EG]埃及	EGYPT	
	 * [EH]西撒哈拉	WESTERN SAHARA	
	 * [ER]厄利垂亞	ERITREA	
	 * [ES]西班牙	SPAIN	
	 * [ET]衣索比亞	ETHIOPIA	
	 * [FI]芬蘭	FINLAND	
	 * [FJ]斐濟	FIJI	
	 * [FK]福克蘭群島	FALKLAND ISLANDS (MALVINAS)	
	 * [FM]密克羅尼西亞	MICRONESIA (FEDERATED STATES OF)	
	 * [FO]法羅群島	FAEROE ISLANDS	
	 * [FR]法國	FRANCE	
	 * [GA]加彭	GABON	
	 * [GB]英國	UNITED KINGDOM	
	 * [GD]格瑞那達	GRENADA	
	 * [GE]喬治亞	GEORGIA	
	 * [GF]法屬圭亞那	FRENCH GUIANA	
	 * [GG]格恩西島 	GUERNSEY, C.I.	
	 * [GH]迦納	GHANA	
	 * [GI]直布羅陀	GIBRALTAR	
	 * [GL]格陵蘭	GREENLAND	
	 * [GM]甘比亞	GAMBIA	
	 * [GN]幾內亞	GUINEA	
	 * [GP]瓜德魯普島	GUADELOUPE	
	 * [GQ]赤道幾內亞	EQUATORIAL GUINEA	
	 * [GR]希臘	GREECE	
	 * [GS]南喬治亞及南三明治群島	SOUTH GEORGIA AND SOUTH SANDWICH ISLANDS	
	 * [GT]瓜地馬拉	GUATEMALA	
	 * [GU]關島	GUAM	
	 * [GW]幾內亞比索	GUINEA-BISSAU	
	 * [GY]蓋亞那	GUYANA	
	 * [HK]香港	HONG KONG	
	 * [HM]赫德及麥當勞群島	HEARD AND McDonald ISLANDS	
	 * [HN]宏都拉斯	HONDURAS	
	 * [HR]克羅埃西亞	CROATIA	
	 * [HT]海地	HAITI	
	 * [HU]匈牙利	HUNGARY 	
	 * [ID]印尼	INDONESIA	
	 * [IE]愛爾蘭	IRELAND	
	 * [IL]以色列	ISRAEL	
	 * [IM]曼島	ISLE OF MAN	
	 * [IN]印度	INDIA	
	 * [IO]英屬印度洋地區	BRITISH INDIAN OCEAN TERRITORY	
	 * [IQ]伊拉克	IRAQ	
	 * [IR]伊朗	IRAN (ISLAMIC REPUBLIC OF)	
	 * [IS]冰島	ICELAND	
	 * [IT]義大利	ITALY	
	 * [JE]澤西島	JERSEY, C.I.	
	 * [JM]牙買加	JAMAICA	
	 * [JO]約旦	JORDAN	
	 * [JP]日本	JAPAN	
	 * [KE]肯亞	KENYA	
	 * [KG]吉爾吉斯	KYRGYZSTAN	
	 * [KH]高棉	CAMBODIA	
	 * [KI]吉里巴斯	KIRIBATI	
	 * [KM]葛摩	COMOROS	
	 * [KN]聖克里斯多福	SAINT KITTS AND NEVIS	
	 * [KP]北韓	KOREA, DEMOCRATIC PEOPLE'S OF	
	 * [KR]大韓民國	KOREA, REPUBLIC OF	
	 * [KW]科威特	KUWAIT	
	 * []開曼群島	CAYMAN ISLANDS	KY
	 * []哈薩克	KAZAKHSTAN	KZ
	 * []寮國	LAO PEOPLE'S DEMOCRATIC REPUBLIC	LA
	 * []黎巴嫩	LEBANON	LB
	 * []聖露西亞	SAINT LUCIA	LC
	 * []列支敦斯堡	LIECHTENSTEIN	LI
	 * []斯里蘭卡	SRI LANKA	LK
	 * []賴比瑞亞	LIBERIA	LR
	 * []賴索托	LESOTHO	LS
	 * [LT]立陶宛	LITHUANIA	
	 * [LU]盧森堡	LUXEMBOURG	
	 * [LV]拉脫維亞	LATVIA	
	 * [LY]利比亞	LIBYAN ARAB JAMAHIRIYA	
	 * [MA]摩洛哥	MOROCCO	
	 * [MC]摩納哥	MONACO	
	 * [MD]摩爾多瓦	MOLDOVA, REPUBLIC OF	
	 * [ME]蒙特尼哥羅	MONTENEGRO	
	 * [MF]聖馬丁(法屬)	SAINT MARTIN (FRENCH PART)	
	 * [MG]馬達加斯加	MADAGASCAR	
	 * [MH]馬紹爾群島	MARSHALL ISLANDS	
	 * [MK]馬其頓	MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF 	
	 * [ML]馬利	MALI	
	 * [MM]緬甸	MYANMAR	
	 * [MN]蒙古	MONGOLIA	
	 * [MO]澳門	MACAO	
	 * [MP]北馬里亞納群島	NORTHERN MARIANA ISLANDS	
	 * [MQ]法屬馬丁尼克	MARTINIQUE	
	 * [MR]茅利塔尼亞	MAURITANIA	
	 * [MS]蒙瑟拉特島	MONTSERRAT	
	 * [MT]馬爾他	MALTA	
	 * [MU]模里西斯	MAURITIUS	
	 * [MV]馬爾地夫	MALDIVES	
	 * [MW]馬拉威	MALAWI	
	 * [MX]墨西哥	MEXICO	
	 * [MY]馬來西亞	MALAYSIA	
	 * [MZ]莫三鼻給	MOZAMBIQUE	
	 * [NA]納米比亞	NAMIBIA	
	 * [NC]新喀里多尼亞	NEW CALEDONIA	
	 * [NE]尼日	NIGER	
	 * [NF]諾福克群島	NORFOLK ISLAND	
	 * [NG]奈及利亞	NIGERIA	
	 * [NI]尼加拉瓜	NICARAGUA	
	 * [NL]荷蘭	NETHERLANDS	
	 * [NO]挪威	NORWAY	
	 * [NP]尼伯爾	NEPAL	
	 * [NR]諾魯	NAURU	
	 * [NU]紐威島	NIUE	
	 * [NZ]紐西蘭	NEW ZEALAND	
	 * [OM]阿曼	OMAN	
	 * [PA]巴拿馬	PANAMA	
	 * [PE]秘魯	PERU	
	 * [PF]法屬玻里尼西亞	FRENCH POLYNESIA	
	 * [PG]巴布亞新幾內亞	PAPUA NEW GUINEA	
	 * [PH]菲律賓	PHILIPPINES	
	 * [PK]巴基斯坦	PAKISTAN	
	 * [PL]波蘭	POLAND	
	 * [PM]聖匹及密啟倫群島	SAINT PIERRE AND MIQUELON	
	 * [PN]皮特康島	PITCAIRN	
	 * [PR]波多黎各	PUERTO RICO	
	 * [PT]葡萄牙	PORTUGAL	
	 * [PW]帛琉	PALAU	
	 * [PY]巴拉圭	PARAGUAY	
	 * [PZ]巴拿馬運河區	PANAMA CANAL ZONE	
	 * [QA]庫達(卡達)	QATAR	
	 * [RE]留尼旺	REUNION	
	 * [RO]羅馬尼亞	ROMANIA	
	 * [RS]塞爾維亞	SERBIA 	
	 * [RU]俄羅斯聯邦	RUSSIAN FEDERATION	
	 * [RW]盧安達	RWANDA	
	 * [SA]沙烏地阿拉伯	SAUDI ARABIA	
	 * [SB]索羅門群島	SOLOMON ISLANDS	
	 * [SC]塞席爾	SEYCHELLES	
	 * [SD]蘇丹	SUDAN	
	 * [SE]瑞典	SWEDEN	
	 * [SG]新加坡	SINGAPORE	
	 * [SH]聖赫勒拿島	SAINT HELENA	
	 * [SI]斯洛凡尼亞	SLOVENIA	
	 * [SJ]斯瓦巴及尖棉島	SVALBARD AND JAN MAYEN ISLANDS	
	 * [SK]斯洛伐克	SLOVAKIA	
	 * [SL]獅子山	SIERRA LEONE	
	 * [SM]聖馬利諾	SAN MARINO	
	 * [SN]塞內加爾	SENEGAL	
	 * [SO]索馬利亞	SOMALIA	
	 * [SR]蘇利南	SURINAME	
	 * [SS]南蘇丹	SOUTH SUDAN	
	 * [ST]聖托馬-普林斯浦	SAO TOME AND PRINCIPE	
	 * [SV]薩爾瓦多	EL SALVADOR	
	 * [SX]聖馬丁(荷屬)	SINT MAARTEN (DUTCH PART)	
	 * [SY]敘利亞	SYRIAN ARAB REPUBLIC	
	 * [SZ]史瓦濟蘭	SWAZILAND	
	 * [TC]土克斯及開科斯群島	TURKS AND CAICOS ISLANDS	
	 * [TD]查德	CHAD	
	 * [TF]法屬南部屬地	FRENCH SOUTHERN TERRITORIES	
	 * [TG]多哥	TOGO	
	 * [TH]泰國	THAILAND	
	 * [TJ]塔吉克	TAJIKISTAN	
	 * [TK]托克勞群島	TOKELAU	
	 * [TL]東帝汶	TIMOR-LESTE 	
	 * [TM]土庫曼	TURKMENISTAN	
	 * [TN]突尼西亞	TUNISIA	
	 * [TO]東加	TONGA	
	 * [TR]土耳其	TURKEY	
	 * [TT]千里達-托貝哥	TRINIDAD AND TOBAGO 	
	 * [TV]吐瓦魯	TUVALU	
	 * [TW]中華民國	TAIWAN	
	 * [TZ]坦尚尼亞	TANZANIA, UNITED REPUBLIC OF	
	 * [UA]烏克蘭	UKRAINE	
	 * [UG]烏干達	UGANDA	
	 * [UM]美屬邊疆群島	UNITED STATES MINOR OUTLYING ISLANDS	
	 * [US]美國	UNITED STATES 	
	 * [UY]烏拉圭	URUGUAY	
	 * [UZ]烏玆別克	UZBEKISTAN	
	 * [VA]教廷	HOLY SEE (VATICAN CITY STATE)	
	 * [VC]聖文森	SAINT VINCENT AND THE GRENADINES	
	 * [VE]委內瑞拉	VENEZUELA	
	 * [VG]英屬維爾京群島	VIRGIN ISLANDS, BRITISH	
	 * [VI]美屬維爾京群島	VIRGIN ISLANDS, U.S.	
	 * [VN]越南	VIET NAM	
	 * [VU]萬那杜	VANUATU	
	 * [WF]沃里斯及伏塔那島	WALLIS AND FUTUNA ISLANDS	
	 * [WS]薩摩亞群島	SAMOA	
	 * [XA]中華民國(OBU)	TAIWAN(OBU)	
	 * [XD]其他亞洲地區	　	
	 * [XE]其他中東及近東地區	　	
	 * [XF]其他歐洲地區	　	
	 * [XG]其他非洲國家	　	
	 * [XH]其他北美洲地區	　	
	 * [XI]其他中美洲地區	　	
	 * [XJ]其他南美洲地區	　	
	 * [XK]其他大洋洲地區	　	
	 * [YE]北葉門	YEMEN	
	 * [YT]美亞特	MAYOTTE	
	 * [ZA]南非共和國	SOUTH AFRICA	
	 * [ZM]尚比亞	ZAMBIA	
	 * [ZW]辛巴威(羅德西亞)	ZIMBABWE	
	 */
	private static String[] NATIONALITY_CODE_ARRAY ={"AD","AE","AF","AG","AI","AL","AM","AO","AQ","AR","AS","AT","AU","AW","AX","AZ","BA","BB","BD","BE","BF","BG","BH","BI","BJ","BL","BM","BN","BO","BQ","BR","BS","BT","BV","BW","BY","BZ","CA","CC","CD","CF","CG","CH","CI","CK","CL","CM","CN","CO","CR","CU","CV","CW","CX","CY","CZ","DE","DJ","DK","DM","DO","DZ","EC","EE","EG","EH","ER","ES","ET","FI","FJ","FK","FM","FO","FR","GA","GB","GD","GE","GF","GG","GH","GI","GL","GM","GN","GP","GQ","GR","GS","GT","GU","GW","GY","HK","HM","HN","HR","HT","HU","ID","IE","IL","IM","IN","IO","IQ","IR","IS","IT","JE","JM","JO","JP","KE","KG","KH","KI","KM","KN","KP","KR","KW","KY","KZ","LA","LB","LC","LI","LK","LR","LS","LT","LU","LV","LY","MA","MC","MD","ME","MF","MG","MH","MK","ML","MM","MN","MO","MP","MQ","MR","MS","MT","MU","MV","MW","MX","MY","MZ","NA","NC","NE","NF","NG","NI","NL","NO","NP","NR","NU","NZ","OM","PA","PE","PF","PG","PH","PK","PL","PM","PN","PR","PT","PW","PY","PZ","QA","RE","RO","RS","RU","RW","SA","SB","SC","SD","SE","SG","SH","SI","SJ","SK","SL","SM","SN","SO","SR","SS","ST","SV","SX","SY","SZ","TC","TD","TF","TG","TH","TJ","TK","TL","TM","TN","TO","TR","TT","TV","TW","TZ","UA","UG","UM","US","UY","UZ","VA","VC","VE","VG","VI","VN","VU","WF","WS","XA","XD","XE","XF","XG","XH","XI","XJ","XK","YE","YT","ZA","ZM","ZW"};
	
	
	/*
	 * 性別
	 * [F]女
	 * [M]男
	 * [空白]法人
	 */
	private static String[] GENDER_ARRAY = { "F","M"," " };
	
	/*
	 * 職業/行業
	 */
	private static String [] OCCUPATION_CODE_ARRAY = {"999","001","021","022","029","031","032","039","041","042","049","051","052","059","061","062","069","071","072","079","081","082","089","091","092","099","101","102","109","111","112","119","121","122","129"};
	
	
	/*
	 * 婚姻狀況
	 * [1]已婚 
	 * [2]離婚 
	 * [3]未婚
	 * [空白]法人戶
	 */
	private static String [] MARITAL_STATUS_CODE_ARRAY = {"1","2"," "};
	
	/*
	 * 行內員工註記
	 * [Y]是
	 * [N]否
	 */
	private static String [] EMPLOYEE_FLAG_ARRAY= {"Y","N"};
	
	/*
	 * 是否具多重國籍(自然人)
	 * [Y]是 
	 * [N]否(含無法辨識)
	 */
	private static String [] MULTIPLE_NATIONALITY_FLAG_ARRAY= {"Y","N"};
	
	/*
	 * 金融卡約定服務
	 * [Y]是 
	 * [N]否(含無法辨識)
	 */
	private static String [] REGISTERED_SERVICE_ATM_ARRAY= {"Y","N"};
	
	/*
	 * 電話約定服務
	 * [Y]是 
	 * [N]否(含無法辨識)
	 */
	private static String [] REGISTERED_SERVICE_TELEPHONE_ARRAY= {"Y","N"};
	
	/*
	 * 傳真約定服務
	 * [Y]是
	 * [N]否(含無法辨識)，僅外幣有此欄位
	 */
	private static String [] REGISTERED_SERVICE_FAX_ARRAY= {"Y","N"};
	
	/*
	 * 網銀約定服務
	 * [Y]是 
	 * [N]否(含無法辨識)
	 */
	private static String [] REGISTERED_SERVICE_INTERNET_ARRAY= {"Y","N"};
	
	/*
	 * 行動銀行約定服務
	 * [Y]是 
	 * [N]否(含無法辨識)
	 */
	private static String [] REGISTERED_SERVICE_MOBILE_ARRAY= {"Y","N"};
	
	/*
	 * 是否得發行無記名股票 (法人)
	 * [Y]是 
	 * [N]否
	 */
	private static String [] BEARER_STOCK_FLAG_ARRAY= {"Y","N"};

	
	//檔案存放路徑
	private static String PATH = "D:\\PSC\\Projects\\全國農業金庫洗錢防制系統案\\假資料\\顧客基本資料\\";
	
	//主題
	private static String THEME = "PARTY";
	
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
		int detailCount = 1000000;
		try {
			System.out.println("顧客基本資料創建開始\n");
			
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
					DetailOwn detail = new DetailOwn();
					detail.setRecord_type("2");
					detail.setDomain_id( String.valueOf(domain_id) );
					detail.setParty_number(RandomValue.getUnicde());
					detail.setChange_code(Utils.getRandomValFromArray(CHANGE_CODE_ARRAY));
					detail.setMy_customer_flag(Utils.getRandomValFromArray(MY_CUSTOMER_FLAG_ARRAY));
					detail.setBranch_code(Utils.getRandomValFromArray(BRANCH_CODE_ARRAY));
					detail.setEntity_type(Utils.getRandomValFromArray(ENTITY_TYPE_ARRAY));
					detail.setEntity_sub_type(Utils.getRandomValFromArray(ENTITY_SUB_TYPE_ARRAY));
					detail.setParty_first_name_1("");
					detail.setParty_last_name_1(RandomValue.getChineseName());
					detail.setDate_of_birth("");
					detail.setDeceased_date("");
					detail.setNationality_code(Utils.getRandomValFromArray(NATIONALITY_CODE_ARRAY));
					detail.setParty_first_name_2("");
					detail.setParty_last_name_2("");
					detail.setOpen_date("");
					detail.setClose_date("");
					
					String gender = Utils.getRandomValFromArray(GENDER_ARRAY);
					detail.setGender(gender);
					
					if(" ".equals(gender)){
				        Random rand=new Random(); 
				        
				        int annual_income=(rand.nextInt(10)+1)*40000; 
				        
						detail.setAnnual_income(String.valueOf(annual_income));
						detail.setMarital_status_code(" ");
					}else{
						detail.setAnnual_income("");
						detail.setMarital_status_code(Utils.getRandomValFromArray(MARITAL_STATUS_CODE_ARRAY));
					}

					detail.setOccupation_code(Utils.getRandomValFromArray(OCCUPATION_CODE_ARRAY));
					detail.setEmployer_name("");
					detail.setEmployer("");
					detail.setEmployee_flag(Utils.getRandomValFromArray(EMPLOYEE_FLAG_ARRAY));
					detail.setPlace_of_birth("");
					
					String multiple_nationality_flag = Utils.getRandomValFromArray(MULTIPLE_NATIONALITY_FLAG_ARRAY);
					detail.setMultiple_nationality_flag(multiple_nationality_flag);
					if ("N".equals(multiple_nationality_flag)){
						detail.setNationality_code_2(Utils.getRandomValFromArray(NATIONALITY_CODE_ARRAY));
					}else {
						detail.setNationality_code_2("");
					}
					detail.setEmail_address(RandomValue.getEmail(5, 12));
					detail.setRegistered_service_atm(Utils.getRandomValFromArray(REGISTERED_SERVICE_ATM_ARRAY));
					
					detail.setRegistered_service_telephone(Utils.getRandomValFromArray(REGISTERED_SERVICE_TELEPHONE_ARRAY));
					detail.setRegistered_service_fax(Utils.getRandomValFromArray(REGISTERED_SERVICE_FAX_ARRAY));
					detail.setRegistered_service_internet(Utils.getRandomValFromArray(REGISTERED_SERVICE_INTERNET_ARRAY));
					detail.setRegistered_service_mobile(Utils.getRandomValFromArray(REGISTERED_SERVICE_MOBILE_ARRAY));
					detail.setBearer_stock_flag(Utils.getRandomValFromArray(BEARER_STOCK_FLAG_ARRAY));
					
					detail.setBearer_stock_description("");
					detail.setForeign_transaction_purpose("");
			        Random rand=new Random();
					detail.setTotal_asset(String.valueOf((rand.nextInt(10)*123456)*(rand.nextInt(10)*654321)));
					detail.setTrust_total_asset(String.valueOf((rand.nextInt(10)*123456)*(rand.nextInt(10)*654321)));
					byte[] detail_byte = getOwnDetail(detail);
					stringBuffer.append(new String(detail_byte, "BIG5")).append("\r\n");
					domain_id++;
			
				}
				
				//創建尾錄
				Footer footer = new Footer();
				footer.setRecord_type("3");
				footer.setCentral_no(central_no);
				footer.setRecord_date(record_date);
				footer.setTotal_cnt(Utils.totalCntFormat(detailCount, 7));
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
			System.out.println("顧客基本資料創建完成，此次作業創建" + fileCount + "個檔案");
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
	 * 保留欄	R	X(602)
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
		byte[] reserve_field_byte = Utils.byteFormat(reserve_field.getBytes("BIG5"), 602);

		return Utils.getAllByteArray(record_type_byte, central_no_byte, record_date_byte, reserve_field_byte);
	}
	
	
	/* 本行顧客-明細錄
	 * 區別碼						R	X(01)
	 * 本會代號						R	X(07)
	 * 客戶統編						R	X(11)
	 * 異動代號						R	X(01)
	 * 是否為本行客戶				R	X(01)
	 * 歸屬本/分會代號				R	X(07)
	 * 顧客類型						R	X(03)
	 * 客戶子類型					O	X(01)
	 * 中文名字						O	X(40)
	 * 中文姓氏						R	X(80)
	 * 出生年月日/創立日期			R	X(08)
	 * 亡故日期						O	X(08)
	 * 國籍							R	X(02)
	 * 英文名字						O	X(40)
	 * 英文姓氏						O	X(80)
	 * 顧客開戶日期					R	X(08)
	 * 顧客結清日期					O	X(08)
	 * 性別							O	X(01)
	 * 年收入(法人)					O	9(10)
	 * 職業/行業						R	X(06)
	 * 婚姻狀況						O	X(01)
	 * 服務機構						O	X(30)
	 * 服務機構統編					O	X(08)
	 * 行內員工註記					O	X(01)
	 * 出生地						O	X(18)
	 * 是否具多重國籍(自然人)			R	X(01)
	 * 第二國籍						O	X(02)
	 * 顧客電子郵件					O	X(80)
	 * 金融卡約定服務				R	X(01)
	 * 電話約定服務					R	X(01)
	 * 傳真約定服務					R	X(01)
	 * 網銀約定服務					R	X(01)
	 * 行動銀行約定服務				R	X(01)
	 * 是否得發行無記名股票 (法人)	R	X(01)
	 * 無記名股票 (法人)資訊說明		O	X(40)
	 * 外國人士居留或交易目的			O	X(80)
	 * 顧客AUM餘額					O	9(12)V99
	 * 信託客戶AUM餘額				O	9(12)V99
	 */
	private static byte[] getOwnDetail(DetailOwn detail) throws UnsupportedEncodingException {
		
		//區別碼
		String record_type = detail.getRecord_type();
		byte[] record_type_byte = Utils.byteFormat(record_type.getBytes("BIG5"), 1);

		//本會代號
		String domain_id = detail.getDomain_id();
		byte[] domain_id_byte = Utils.byteFormat(domain_id.getBytes("BIG5"), 7);

		//客戶統編
		String party_number = detail.getParty_number();
		byte[] party_number_byte = Utils.byteFormat(party_number.getBytes("BIG5"), 11);

		//異動代號
		String change_code = detail.getChange_code();
		byte[] change_code_byte = Utils.byteFormat(change_code.getBytes("BIG5"), 1);
		
		//是否為本行客戶
		String my_customer_flag = detail.getMy_customer_flag();
		byte[] my_customer_flag_byte = Utils.byteFormat(my_customer_flag.getBytes("BIG5"), 1);

		//歸屬本/分會代號
		String branch_code = detail.getBranch_code();
		byte[] branch_code_byte = Utils.byteFormat(branch_code.getBytes("BIG5"), 7);

		//顧客類型
		String entity_type = detail.getEntity_type();
		byte[] entity_type_byte = Utils.byteFormat(entity_type.getBytes("BIG5"), 3);

		//客戶子類型
		String entity_sub_type = detail.getEntity_sub_type();
		byte[] entity_sub_type_byte = Utils.byteFormat(entity_sub_type.getBytes("BIG5"), 1);

		//中文名字
		String party_first_name_1 = detail.getParty_first_name_1();
		byte[] party_first_name_1_byte = Utils.byteFormat(party_first_name_1.getBytes("BIG5"), 40);

		//中文姓氏
		String party_last_name_1 = detail.getParty_last_name_1();
		byte[] party_last_name_1_byte = Utils.byteFormat(party_last_name_1.getBytes("BIG5"), 80);

		//出生年月日/創立日期
		String date_of_birth = detail.getDate_of_birth();
		byte[] date_of_birth_byte = Utils.byteFormat(date_of_birth.getBytes("BIG5"), 8);

		//亡故日期
		String deceased_date = detail.getDeceased_date();
		byte[] deceased_date_byte = Utils.byteFormat(deceased_date.getBytes("BIG5"), 8);

		//國籍
		String nationality_code = detail.getNationality_code();
		byte[] nationality_code_byte = Utils.byteFormat(nationality_code.getBytes("BIG5"), 2);

		//英文名字
		String party_first_name_2 = detail.getParty_first_name_2();
		byte[] party_first_name_2_byte = Utils.byteFormat(party_first_name_2.getBytes("BIG5"), 40);

		//英文姓氏
		String party_last_name_2 = detail.getParty_first_name_2();
		byte[] party_last_name_2_byte = Utils.byteFormat(party_last_name_2.getBytes("BIG5"), 80);

		//顧客開戶日期
		String open_date = detail.getOpen_date();
		byte[] open_date_byte = Utils.byteFormat(open_date.getBytes("BIG5"), 8);
		
		//顧客結清日期
		String close_date = detail.getClose_date();
		byte[] close_date_byte = Utils.byteFormat(close_date.getBytes("BIG5"), 8);

		//性別
		String gender = detail.getGender();
		byte[] gender_byte = Utils.byteFormat(gender.getBytes("BIG5"), 1);
		
		//年收入
		String annual_income = detail.getAnnual_income();
		byte[] annual_income_byte = Utils.byteFormat(annual_income.getBytes("BIG5"), 10);
		
		//職業/行業
		String occupation_code = detail.getOccupation_code();
		byte[] occupation_code_byte = Utils.byteFormat(occupation_code.getBytes("BIG5"), 6);

		//婚姻狀況
		String marital_status_code = detail.getMarital_status_code();
		byte[] marital_status_code_byte = Utils.byteFormat(marital_status_code.getBytes("BIG5"), 1);
		
		//服務機構
		String employer_name = detail.getEmployer_name();
		byte[] employer_name_byte = Utils.byteFormat(employer_name.getBytes("BIG5"), 30);
		
		//服務機構統編
		String employer = detail.getEmployer();
		byte[] employer_byte = Utils.byteFormat(employer.getBytes("BIG5"), 8);
		
		//行內員工註記
		String employee_flag = detail.getEmployee_flag();
		byte[] employee_flag_byte = Utils.byteFormat(employee_flag.getBytes("BIG5"), 1);
		
		//出生地
		String place_of_birth = detail.getPlace_of_birth();
		byte[] place_of_birthmployer_byte = Utils.byteFormat(place_of_birth.getBytes("BIG5"), 18);
		
		//是否具多重國籍(自然人)
		String multiple_nationality_flag = detail.getMultiple_nationality_flag();
		byte[] multiple_nationality_flag_byte = Utils.byteFormat(multiple_nationality_flag.getBytes("BIG5"), 1);
		
		//第二國籍
		String nationality_code_2 = detail.getNationality_code_2();
		byte[] nationality_code_2_byte = Utils.byteFormat(nationality_code_2.getBytes("BIG5"), 2);
		
		//顧客電子郵件
		String email_address = detail.getEmail_address();
		byte[] email_address_byte = Utils.byteFormat(email_address.getBytes("BIG5"), 80);
		
		//金融卡約定服務
		String registered_service_atm = detail.getRegistered_service_atm();
		byte[] registered_service_atm_byte = Utils.byteFormat(registered_service_atm.getBytes("BIG5"), 1);
		
		//電話約定服務
		String registered_service_telephone = detail.getRegistered_service_telephone();
		byte[] registered_service_telephone_byte = Utils.byteFormat(registered_service_telephone.getBytes("BIG5"), 1);
		
		//傳真約定服務
		String registered_service_fax = detail.getRegistered_service_fax();
		byte[] registered_service_fax_byte = Utils.byteFormat(registered_service_fax.getBytes("BIG5"), 1);
		
		//網銀約定服務
		String registered_service_internet = detail.getRegistered_service_internet();
		byte[] registered_service_internet_byte = Utils.byteFormat(registered_service_internet.getBytes("BIG5"), 1);
		
		//行動銀行約定服務
		String registered_service_mobile = detail.getRegistered_service_mobile();
		byte[] registered_service_mobile_byte = Utils.byteFormat(registered_service_mobile.getBytes("BIG5"), 1);
		
		//是否得發行無記名股票 (法人)
		String bearer_stock_flag = detail.getBearer_stock_flag();
		byte[] bearer_stock_flag_byte = Utils.byteFormat(bearer_stock_flag.getBytes("BIG5"), 1);
		
		//無記名股票 (法人)資訊說明
		String bearer_stock_description = detail.getBearer_stock_description();
		byte[] bearer_stock_description_byte = Utils.byteFormat(bearer_stock_description.getBytes("BIG5"), 40);
		
		//外國人士居留或交易目的
		String foreign_transaction_purpose = detail.getForeign_transaction_purpose();
		byte[] foreign_transaction_purpose_byte = Utils.byteFormat(foreign_transaction_purpose.getBytes("BIG5"), 80);
		
		//顧客aum餘額
		String total_asset = detail.getTotal_asset();
		byte[] total_asset_byte = Utils.byteFormat(total_asset.getBytes("BIG5"), 14);
		
		//信託客戶aum餘額
		String trust_total_asset = detail.getTrust_total_asset();
		byte[] trust_total_asset_byte = Utils.byteFormat(trust_total_asset.getBytes("BIG5"), 14);
				
		
		return Utils.getAllByteArray(record_type_byte, domain_id_byte, party_number_byte, change_code_byte, my_customer_flag_byte, branch_code_byte, entity_type_byte,
				entity_sub_type_byte, party_first_name_1_byte, party_last_name_1_byte, date_of_birth_byte, deceased_date_byte, nationality_code_byte, party_first_name_2_byte,
				party_last_name_2_byte, open_date_byte, close_date_byte, gender_byte, annual_income_byte, occupation_code_byte, marital_status_code_byte, employer_name_byte,
				employer_byte, employee_flag_byte, place_of_birthmployer_byte, multiple_nationality_flag_byte, nationality_code_2_byte, email_address_byte, registered_service_atm_byte,
				registered_service_telephone_byte, registered_service_fax_byte, registered_service_internet_byte, registered_service_mobile_byte, bearer_stock_flag_byte,
				bearer_stock_description_byte, foreign_transaction_purpose_byte, total_asset_byte, trust_total_asset_byte);
	}
	
	/*
	 * 尾錄
	 * 區別碼	R	X(01)
	 * 報送單位	R	X(07)
	 * 檔案日期	R	X(08)
	 * 總筆數	R	9(07)
	 * 保留欄	R	X(595)
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
		byte[] reserve_field_byte = Utils.byteFormat(reserve_field.getBytes("BIG5"), 595);
		
		return Utils.getAllByteArray(record_type_byte, central_no_byte, record_date_byte, total_cnt_byte, reserve_field_byte);
	}
}
