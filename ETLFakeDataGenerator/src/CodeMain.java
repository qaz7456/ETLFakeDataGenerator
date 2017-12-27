import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class CodeMain {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String to = "測試中";
		to =new String(to.getBytes("big5"),"big5");

		FormatString(to, 8);
		byte[] byteArr = byteFormat(to.getBytes(), 11);
		StringBuffer stringBuffer = new StringBuffer();
		for (byte b : byteArr) {
			stringBuffer.append(b).append(" ");
		}
		System.out.println(stringBuffer.toString());
	}

	public static String FormatString(String str, int size) {

		byte[] byteArr = str.getBytes();
		byte[] formByteArr = new byte[size];

		for (int i = 0; i < size; i++) {
			formByteArr[i] = 32;
		}

		System.arraycopy(byteArr, 0, formByteArr, 0, byteArr.length);
		StringBuffer stringBuffer = new StringBuffer();
		for (byte b : formByteArr) {
			stringBuffer.append(b).append(" ");
		}
		System.out.println(stringBuffer.toString());
		String formStr = new String(formByteArr);

		return formStr;
	}

	private static byte[] byteFormat(byte[] source, int range) {

		return Arrays.copyOf(source, range);

	}
}
