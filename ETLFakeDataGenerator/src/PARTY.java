import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
	 */
	private static String[] DOMAIN_ID_ARRAY = {"5030019","5040010","5040021","5040032","5040054","5050011","5050022","5050033","5050066","5050077","5060012","5070013","5080014","5080025","5100019","5100020","5110010","5110021","5110032","5110043","5110054","5110065","5110076","5110087","5120011","5120022","5120033","5120044","5120055","5150014","5150025","5150036","5150047","5150058","5150069","5150070","5150081","5170016","5170027","5170038","5180017","5180028","5180039","5180040","5180051","5180073","5190018","5190029","5190030","5200012","5200023","5200034","5200056","5210024","5210035","5210068","5210079","5210091","5210105","5210116","5210149","5230015","5230037","5230048","5230059","5230082","5230093","5230118","5230129","5230130","5240016","5250017","5250028","5250039","5260018","5410019","5410020","5410031","5410042","5410053","5410064","5420010","5420021","5420032","5420043","5470015","5470026","5470037","5470048","5470059","5470060","5490017","5490028","5490039","5490040","5510012","5510023","5510034","5510045","5510056","5510067","5520013","5520024","5520035","5520046","5560017","5560028","5560039","5560040","5560051","5560062","5570018","5570029","5580019","5580020","5580031","5580042","5580053","5590010","5610015","5620016","5620027","5620038","5620049","5620050","5620061","5640018","5640029","5640030","5650019","5650020","5670011","5680012","5700017","5700028","5700039","5700040","5700051","5700062","5700073","5700084","5730010","5730021","5730032","5730043","5740011","5740022","5740033","5740044","5740055","5750012","5750023","5750034","5750045","5770014","5770025","5770036","5770047","5770058","5770069","5780015","5780026","5780037","5780048","5790016","5790027","5790038","5790049","5790050","5790061","5790072","5800010","5800021","5800032","5800043","5800054","5800065","5800076","5800087","5810011","5810022","5810033","5820012","5830013","5830024","5830035","5830046","5830057","5870017","5870028","5870039","5870040","6030016","6030027","6030038","6030049","6050018","6050029","6050030","6050041","6050052","6050063","6050074","6050085","6050096","6050100","6050111","6050122","6050133","6051336","6060019","6060020","6060031","6060042","6060053","6060064","6060075","6060097","6060101","6060112","6060123","6060134","6060145","6060156","6060167","6060178","6060189","6060190","6060204","6060215","6060226","6060237","6060248","6060259","6060260","6060271","6060282","6060293","6060318","6060329","6060330","6060341","6060352","6060363","6060385","6060396","6060400","6060411","6060422","6060433","6060444","6060455","6060466","6060477","6060488","6060499","6060503","6060514","6060547","6060558","6060569","6060570","6060581","6060592","6060606","6060639","6060640","6060651","6060662","6060673","6060684","6060695","6060709","6060710","6060721","6060732","6060743","6060754","6060765","6060776","6060787","6060798","6060802","6060813","6060824","6060835","6060846","6060857","6060868","6060891","6060905","6060916","6060927","6060938","6060949","6060950","6060961","6060983","6060994","6061005","6061016","6061027","6061038","6061061","6061072","6061083","6061094","6061108","6061119","6061120","6061131","6061142","6061153","6061175","6061186","6061197","6061201","6061212","6061223","6061245","6061267","6061278","6061289","6061290","6061315","6061326","6061337","6061348","6061359","6061360","6070010","6070021","6070032","6070054","6070065","6070076","6070087","6070098","6070102","6070113","6070135","6070146","6070157","6070168","6070179","6070180","6070191","6070205","6070216","6070227","6070238","6070249","6070250","6070272","6070283","6070308","6070319","6070331","6070342","6070353","6070364","6070375","6070386","6070412","6070423","6070434","6070445","6070467","6070489","6070515","6070526","6080011","6080033","6080044","6080055","6080066","6080077","6080088","6080099","6080103","6080114","6080136","6080147","6080158","6080169","6080170","6080181","6080192","6080206","6080217","6080228","6080239","6080240","6080251","6080262","6080273","6080284","6080295","6080309","6080310","6080321","6080365","6080376","6080387","6080398","6080402","6080413","6080424","6080435","6080446","6080457","6080468","6080479","6080480","6080491","6080505","6080516","6080527","6080538","6080549","6080550","6080561","6080572","6080583","6080594","6080619","6080620","6080631","6080642","6080653","6080664","6080686","6080697","6080712","6080723","6080745","6080756","6080767","6080778","6080790","6080804","6080826","6080837","6080848","6080859","6090089","6090090","6090104","6100016","6100027","6100038","6100050","6100061","6100072","6100083","6100094","6100108","6100119","6100120","6100131","6100142","6100153","6100186","6100197","6100201","6100212","6100223","6100234","6100256","6100267","6100289","6100290","6100304","6100315","6100326","6100337","6100348","6100359","6100360","6100371","6100382","6100393","6100429","6100430","6100441","6100452","6100463","6100474","6100485","6110062","6110501","6120085","6120096","6120236","6120247","6120258","6120269","6120270","6120281","6120292","6120443","6120557","6120568","6120834","6120867","6120890","6121015","6121093","6121107","6121118","6130020","6130031","6130075","6130189","6130260","6130271","6130282","6130293","6130466","6130488","6130499","6130606","6130617","6130673","6130684","6130754","6140043","6140087","6140124","6140179","6140180","6140227","6140261","6140320","6140401","6140412","6140423","6140478","6140515","6140526","6140559","6140593","6140696","6140700","6140711","6140733","6140799","6140858","6140870","6141017","6141028","6141291","6141327","6141338","6160012","6160023","6160034","6160056","6160078","6160089","6160090","6160115","6160126","6160137","6160148","6160160","6160171","6160218","6160230","6160241","6160252","6160263","6160274","6160285","6160296","6160300","6160311","6160322","6160333","6160403","6160414","6160436","6160447","6160458","6160469","6160492","6160506","6160517","6160528","6160539","6160540","6160562","6160573","6160595","6160609","6160610","6160621","6160643","6160665","6160698","6160702","6160713","6160735","6160746","6160757","6160779","6170013","6170024","6170035","6170046","6170057","6170068","6170079","6170080","6170091","6170105","6170116","6170138","6170149","6170150","6170172","6170194","6170208","6170219","6170220","6170231","6170242","6170253","6170275","6170286","6170297","6170301","6170312","6170323","6170334","6170345","6170356","6170367","6170378","6170389","6170390","6170404","6170415","6170426","6170437","6170448","6170459","6170460","6170471","6170482","6170493","6170507","6170518","6170563","6170574","6170585","6170596","6170600","6170611","6170622","6170633","6170644","6170655","6170666","6170677","6170688","6170699","6170703","6170714","6170725","6170758","6170769","6170781","6170806","6170817","6170862","6171261","6171272","6171283","6171320","6171331","6171353","6171364","6171375","6171386","6171397","6171401","6171412","6171423","6171434","6171467","6171478","6171489","6171490","6180025","6180036","6180069","6180081","6180092","6180139","6180162","6180184","6180195","6180243","6180298","6180324","6180335","6180346","6180357","6180368","6180416","6180427","6180438","6180449","6180450","6180461","6180472","6180483","6180494","6180508","6180612","6180689","6180715","6180771","6180782","6180793","6180807","6180818","6180829","6180966","6180977","6180988","6181011","6181066","6181158","6181181","6181206","6181217","6181239","6181240","6181262","6181343","6181365","6181402","6190015","6190026","6190037","6190048","6190059","6190060","6190071","6190082","6190093","6190107","6190118","6190129","6190130","6190141","6190185","6190196","6190211","6190222","6190244","6190255","6190266","6190277","6190288","6190299","6190303","6190314","6190325","6190336","6190347","6190358","6190369","6190370","6190381","6190392","6190406","6190417","6190428","6190439","6190440","6190451","6190462","6190473","6190484","6190495","6190565","6190576","6190587","6190598","6190602","6190624","6190646","6190657","6190679","6190680","6190716","6190727","6190738","6190749","6190750","6190761","6190772","6190783","6190794","6190808","6190819","6190831","6190853","6190864","6190886","6190897","6190901","6190923","6190934","6190967","6190978","6190989","6190990","6191001","6191012","6191023","6191045","6191067","6191078","6191089","6191090","6191104","6191115","6200031","6200042","6200075","6200086","6200112","6200167","6200189","6200204","6200215","6200259","6200330","6200341","6200547","6200628","6200709","6200710","6200721","6200743","6200754","6200787","6200813","6200868","6200879","6200880","6200891","6200905","6200916","6200927","6200938","6200949","6200950","6200961","6200972","6200983","6200994","6210021","6210032","6210043","6210098","6210102","6210124","6210135","6210146","6210191","6210205","6210216","6210227","6210238","6210331","6220011","6220022","6220033","6220044","6220055","6220066","6220077","6220088","6220099","6220103","6220114","6220125","6220136","6220147","6220158","6220169","6220170","6220181","6220192","6220206","6220217","6220228","6220239","6220240","6220251","6220284","6230012","6230023","6230034","6230045","6230056","6230067","6230078","6230089","6230090","6230104","6230115","6230126","6230137","6230148","6230159","6230171","6230182","6230207","6230229","6230241","6230252","6230274","6240013","6240024","6240035","6240046","6240057","6240068","6250014","6250025","6250036","6250047","6250058","6250069","6250070","6250092","6250117","6250139","6250140","6270016","6280017","6280028","6280039","6280040","6280051","6290018","6290029","6290030","6290041","6310013","6320014","6330015","6350017","6360018","6380010","6380021","6380032","6380043","6380054","6390011","6390022","6390033","6390044","6390055","6420017","6420028","6420039","6420040","6430018","6460011","6460022","6470012","6470023","6470034","6490014","6490025","6500018","6500029","6500030","6510019","6830010","6830021","6850012","6850023","6850034","6850045","6850056","6930013","6930024","6960016","6960027","6960038","6960049","6960050","6970017","6970028","6970039","6980018","6980029","6990019","7490011","7490022","7490033","7490044","7490055","7490066","8600015","8600026","8600037","8600048","8600059","8600060","8600071","8600082","8600093","8660011","8660022","8660033","8680013","8690014","8690025","8690036","8700018","8700029","8700030","8700041","8700052","8710019","8710020","8710031","8710042","8710053","8710064","8720010","8720021","8720032","8720043","8720054","8750013","8750024","8750035","8750046","8750057","8750068","8750079","8750080","8760014","8760025","8760036","8760047","8770015","8770026","8770037","8770048","8780016","8780027","8780038","8780049","8780050","8790017","8790028","8790039","8790040","8790051","8800011","8800022","8810012","8810023","8810034","8820013","8820024","8820035","8820046","8820057","8830014","8830025","8830036","8840015","8840026","8840037","8850016","8850027","8850038","8850049","8850050","8860017","8860028","8860039","8910015","8910026","8910037","8910048","8950019","8950020","8960010","8960021","8970011","8970022","8970033","8980012","8980023","8980034","9020016","9020027","9020038","9020049","9030017","9030028","9030039","9030040","9030051","9030062","9030073","9030084","9030095","9060010","9060021","9060032","9060043","9060054","9070011","9070022","9070033","9070044","9080012","9080023","9090013","9090024","9090035","9140011","9150012","9150023","9170014","9170025","9170036","9170047","9180015","9180026","9180037","9190016","9190027","9200010","9200021","9200032","9200043","9200054","9210011","9210022","9210033","9220012","9220023","9220034","9220056","9220067","9220078","9220089","9230013","9230024","9240014","9240025","9250015","9260016","9260027","9260038","9260049","9260050","9530012","9530023","9530034","9530045"};
	
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
		
		List<String> data = new ArrayList<>();
		
		try {
			System.out.println("顧客基本資料創建開始\n");
			long time1, time2;
			time1 = System.currentTimeMillis();
			
			StringBuilder executiveContent = new StringBuilder();
			
			for (int i = 0; i < fileCount; i++) {
				String central_no = Utils.getRandomValFromArray(CENTRAL_NO_ARRAY);
				String record_date = Utils.getRandomTimeBetweenTwoDates(BEGINTIME, ENDTIME);
				
				String fileName = Utils.fileNameFormat(central_no,
						Utils.getRandomValFromArray(CURRENCY_TYPE_ARRAY), THEME,
						record_date);

				Path path = Paths.get(PATH + fileName);
				
		        Files.createDirectories(path.getParent());
		        
				//創建首錄
				Header header = new Header();
				header.setRecord_type("1");
				header.setCentral_no(central_no);
				header.setRecord_date(record_date);
				header.setReserve_field("測試資料");
				byte[] header_byte = getHeader(header);
				
				data.add(new String(header_byte, "BIG5"));
				
				for(int j=0 ; j<detailCount ; j++){
					//創建明細錄
					DetailOwn detail = new DetailOwn();
					detail.setRecord_type("2");
					detail.setDomain_id( Utils.getRandomValFromArray(DOMAIN_ID_ARRAY));
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
				        
						detail.setAnnual_income(Utils.DigitalFillBit(annual_income,10));
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
					byte[] detail_byte =  getOwnDetail(detail);
					
					data.add(new String(detail_byte, "BIG5"));
					
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

				try (BufferedWriter writer = Files.newBufferedWriter(path)) {
					
					Files.write(path, data, Charset.forName("Big5"),  StandardOpenOption.CREATE, StandardOpenOption.WRITE);
					Files.write(path, footer_byte, StandardOpenOption.APPEND);
					
					executiveContent.append("檔名: ").append(fileName).append("\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println(executiveContent.toString());
			System.out.println("顧客基本資料創建完成，此次作業創建" + fileCount + "個檔案\n");
			
			time2 = System.currentTimeMillis();
			System.out.println("花了：" + (time2 - time1) + "豪秒");
		}catch (Exception e) {
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
