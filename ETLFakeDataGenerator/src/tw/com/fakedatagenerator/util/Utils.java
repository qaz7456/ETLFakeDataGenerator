package tw.com.fakedatagenerator.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Utils {
	public static String getSliceBytesText(byte[] source, int start, int end, String charset) {
		String text = "";
		try {
			text = new String(Arrays.copyOfRange(source, start, end), charset);
		} catch (Exception e) {
			return text;
		}
		return text;
	}

	public static <T> T getRandomValFromArray(T[] array) {
		Random random = new Random();

		return array[random.nextInt(array.length)];
	}

	public static byte[] getAllByteArray(byte[]... bs) {

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
	
	public static String getRandomTimeBetweenTwoDates(long beginTime, long endTime) {
		long diff = endTime - beginTime + 1;
		long date = beginTime + (long) (Math.random() * diff);
		Date randomDate = new Date(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		return dateFormat.format(randomDate);
	}
	
	public static String fileNameFormat(String... strs) throws UnsupportedEncodingException {
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
	public static String totalCntFormat(int val, int range) {
		String tmp = String.valueOf(val);
		StringBuffer sBuffer = new StringBuffer();
		if (range > tmp.length()) {
			for (int i = 0; i < (range - tmp.length()); i++) {
				sBuffer.append("0");
			}
		}
		sBuffer.append(tmp);
		return sBuffer.toString();
	}

	public static byte[] byteFormat(byte[] source, int range) {

		byte[] a = new byte[range];
		byte[] b = Arrays.copyOf(source, range);

		for (int i = 0; i < b.length; i++) {
			byte now = b[i];
			if (now == 0)
				now = 32;
			a[i] = now;
		}
		return a;

	}
}
