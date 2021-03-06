package com.hr.ui.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.constants.Constants;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

/**
 * 加密网络请求参数的工具类
 * 
 * @author 800hr：zhuhui
 * 
 *         2014-3-27
 */
public class EncryptUtils {
	public static String secret_key = Constants.SECRET_KEY;

	public static HashMap<String,String> encrypParams(Map<String, String> params2) {
		SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
		HashMap<String,String> requestMap=new HashMap<>();
		StringBuilder params = new StringBuilder();
		for (Map.Entry<String, String> entry : params2.entrySet()) {
            /*
             * 将字符串 遍历 前后加上 = &符号
			 */
			params.append(entry.getKey());
			params.append("=");
			params.append(entry.getValue());
			params.append("&");
		}

		if (params.length() > 0)
			params.deleteCharAt(params.length() - 1);
		try {

			String parameter = new String(params.toString().getBytes(
					Constants.ENCODE), "iso8859-1");

			String encodeParams = Rc4Md5Utils.AuthCode(parameter, "",
					"0");
			requestMap.put("s",Constants.SESSION_KEY == null ? "" : Constants.SESSION_KEY );
			requestMap.put("d",encodeParams);
			return requestMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	/**
	 * 请求数据的加解密函数
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String AuthCode(String str, String operation, String expiry)
			throws UnsupportedEncodingException {
		// 动态密匙长度，相同的明文会生成不同密文就是依靠动态密匙
		int ckey_length = 6;

		int keyLen = secret_key.length();
		while (keyLen < 16) {
			secret_key += "8hr";
			keyLen += 3;
			if (keyLen > 16) {
				secret_key = secret_key.substring(0, 16);
			}
		}

		// 密匙
		String secret_keyn = md5(secret_key);
		// 密匙a会参与加解密
		String secret_keya = md5(secret_keyn.substring(0, 16))
				+ secret_key.substring(0, 8);
		// 密匙b会用来做数据完整性验证
		String secret_keyb = md5(secret_keyn.substring(16, 32))
				+ secret_key.substring(8, 16);
		// 密匙c用于变化生成的密文
		long currentSecond = 0/* System.currentTimeMillis() / 1000 */;

		String secret_keyc = operation.equals("DECODE") ? str.substring(0,
				ckey_length) : md5(/* "0" */String.valueOf(currentSecond))
				.substring(32 - ckey_length);
		// 参与运算的密匙
		String cryptkey = secret_keya + md5(secret_keya + secret_keyc);
		int key_length = cryptkey.length();
		// 明文，前10位用来保存时间戳，解密时验证数据有效性，10到26位用来保存$keyb(密匙b)，解密时会通过这个密匙验证数据完整性
		// 如果是解码的话，会从第$ckey_length位开始，因为密文前$ckey_length位保存 动态密匙，以保证解密正确
		str = operation.equals("DECODE") ? new String(Base64.decode(
				str.substring(ckey_length), Base64.DEFAULT), "iso8859-1")
				: (!expiry.equals("0") ? expiry + currentSecond : "0000000000")
						+ md5(
								new String(str.getBytes("iso8859-1"),
										Constants.ENCODE) + secret_keyb).substring(
								0, 16) + str;
		Log.i("life", "str: " + str);
		int string_length = str.length();
		String result = "";
		int[] box = new int[256];
		for (int i = 0; i < 256; i++) {
			box[i] = i;
		}
		int[] rndkey = new int[256];
		// 产生密匙簿
		for (int i = 0; i < 256; i++) {
			rndkey[i] = (int) cryptkey.charAt(i % key_length);
		}
		// 用固定的算法，打乱密匙簿，增加随机性，好像很复杂，实际上对并不会增加密文的强度
		for (int j = 0, i = 0; i < 256; i++) {
			j = (j + box[i] + rndkey[i]) % 256;
			int temp = box[i];
			box[i] = box[j];
			box[j] = temp;
		}

		byte[] resultByte = new byte[string_length];
		// 核心加解密部分
		for (int a = 0, j = 0, i = 0; i < string_length; i++) {
			a = (a + 1) % 256;
			j = (j + box[a]) % 256;
			int temp = box[a];
			box[a] = box[j];
			box[j] = temp;
			// 从密匙簿得出密匙进行异或，再转成字符
			resultByte[i] = (byte) (str.charAt(i) ^ (box[(box[a] + box[j]) % 256]));
		}
		if (operation.equals("DECODE")) {
			// substr($result, 0, 10) == 0 验证数据有效性
			// substr($result, 0, 10) - time() > 0 验证数据有效性
			// substr($result, 10, 16) == substr(md5(substr($result, 26).$keyb),
			// 0, 16) 验证数据完整性
			// 验证数据有效性，请看未加密明文的格式
			result = new String(resultByte, "iso8859-1");
			if ((Integer.parseInt(result.substring(0, 10)) == 0 || Integer
					.parseInt(result.substring(0, 10)) - currentSecond > 0)
					&& result.substring(10, 26).equals(
							md5(result.substring(26) + secret_keyb).substring(
									0, 16))) {
				return new String(resultByte, Constants.ENCODE).substring(26);
			} else {
				return "";
			}
		} else {
			// 把动态密匙保存在密文里，这也是为什么同样的明文，生产不同密文后能解密的原因
			// 因为加密后的密文可能是一些特殊字符，复制过程可能会丢失，所以用base64编码
			return secret_keyc + Base64.encodeToString(resultByte, Base64.DEFAULT);
		}
	}

	/*
	 * md5加密内容
	 */
	public static String md5(String str) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// System.out.println("md5 : " + e.getMessage());
		}
		md.update(str.getBytes());
		byte[] byteArray = md.digest();

		StringBuilder md5Str = new StringBuilder();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5Str.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5Str.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5Str.toString();
	}

	/**
	 * 将base字符串转换为图片
	 * @param string
	 * @return
	 */
	public static Bitmap stringtoBitmap(String string){
		//将字符串转换成Bitmap类型
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray = com.hr.ui.utils.Base64.decode(string.split(",")[1], com.hr.ui.utils.Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	/**
	 * 获取公司手机链接
	 *
	 * @param e_id
	 * @return
	 */
	public static String getCompanyUrl(String e_id,String industryId) {
		StringBuilder builder = new StringBuilder("http://m.");
		if ("11".equals(industryId)/*.equals("11")*/) {// 建筑行业
			builder.append("buildhr");
		} else if ("12".equals(industryId)/*.equals("12")*/) {// 金融行业
			builder.append("bankhr");
		} else if ("13".equals(industryId)/*.equals("13")*/) {// 传媒行业
			builder.append("media.800hr");
		} else if ("14".equals(industryId)/*.equals("14")*/) {// 医药行业
			builder.append("healthr");
		} else if ("15".equals(industryId)/*.equals("15")*/) {// 教培行业
			builder.append("edu.800hr");
		} else if ("19".equals(industryId)/*.equals("19")*/) {// 电子行业
			builder.append("ele.800hr");
		} else if ("22".equals(industryId)/*.equals("22")*/) {// 机械行业
			builder.append("michr");
		} else if ("26".equals(industryId)/*.equals("26")*/) {// 服装行业
			builder.append("clothr");
		} else if ("29".equals(industryId)/*.equals("29")*/) {// 化工行业
			builder.append("chenhr");
		}else if ("23".equals(industryId)/*.equals("23")*/) {// IT行业
			builder.append("it.800hr");
		}
		else if ("40".equals(industryId)/*.equals("40")*/) {// 酒店行业
			builder.append("hotel.800hr");
		}
		else if ("16".equals(industryId)/*.equals("16")*/) {// 运输行业
			builder.append("56.800hr");
		}
		else if ("21".equals(industryId)/*.equals("21")*/) {// 通信行业
			builder.append("telecom.800hr");
		}
		else if ("20".equals(industryId)/*.equals("20")*/) {// 电力行业
			builder.append("ep.800hr");
		}
		else if ("30".equals(industryId)/*.equals("30")*/) {// 旅游行业
			builder.append("tour.800hr");
		}
		else if ("6".equals(industryId)/*.equals("6")*/) {// 旅游行业
			builder.append("tour.800hr");
		}
		builder.append(".com/company/");
		builder.append(e_id);

		return builder.toString();
	}

	public static String getJobUrl(String j_id,String industryId) {

		StringBuilder builder = new StringBuilder("http://m.");
		if ("11".equals(industryId)/*.equals("11")*/) {// 建筑行业
			builder.append("buildhr");
		} else if ("12".equals(industryId)/*.equals("12")*/) {// 金融行业
			builder.append("bankhr");
		} else if ("13".equals(industryId)/*.equals("13")*/) {// 传媒行业
			builder.append("media.800hr");
		} else if ("14".equals(industryId)/*.equals("14")*/) {// 医药行业
			builder.append("healthr");
		} else if ("15".equals(industryId)/*.equals("15")*/) {// 教培行业
			builder.append("edu.800hr");
		} else if ("19".equals(industryId)/*.equals("19")*/) {// 电子行业
			builder.append("ele.800hr");
		} else if ("22".equals(industryId)/*.equals("22")*/) {// 机械行业
			builder.append("michr");
		} else if ("26".equals(industryId)/*.equals("26")*/) {// 服装行业
			builder.append("clothr");
		} else if ("29".equals(industryId)/*.equals("29")*/) {// 化工行业
			builder.append("chenhr");
		}else if ("23".equals(industryId)/*.equals("23")*/) {// IT行业
			builder.append("it.800hr");
		}
		else if ("40".equals(industryId)/*.equals("40")*/) {// 酒店行业
			builder.append("hotel.800hr");
		}
		else if ("16".equals(industryId)/*.equals("16")*/) {// 运输行业
			builder.append("56.800hr");
		}
		else if ("21".equals(industryId)/*.equals("21")*/) {// 通信行业
			builder.append("telecom.800hr");
		}
		else if ("20".equals(industryId)/*.equals("20")*/) {// 电力行业
			builder.append("ep.800hr");
		}
		else if ("30".equals(industryId)/*.equals("30")*/) {// 旅游行业
			builder.append("tour.800hr");
		}
      /*  else if ("6".equals(MyUtils.industryId)*//*.equals("6")*//*) {// 旅游行业
            builder.append("tour.800hr");
        }*/
       /* if (MyUtils.industryId.equals("11")) {// 建筑行业
            builder.append("buildhr");
        } else if (MyUtils.industryId.equals("12")) {// 金融行业
            builder.append("bankhr");
        } else if (MyUtils.industryId.equals("13")) {// 传媒行业
            builder.append("media.800hr");
        } else if (MyUtils.industryId.equals("14")) {// 医药行业
            builder.append("healthr");
        } else if (MyUtils.industryId.equals("15")) {// 教培行业
            builder.append("edu.800hr");
        } else if (MyUtils.industryId.equals("19")) {// 电子行业
            builder.append("ele.800hr");
        } else if (MyUtils.industryId.equals("22")) {// 机械行业
            builder.append("michr");
        } else if (MyUtils.industryId.equals("26")) {// 服装行业
            builder.append("clothr");
        } else if (MyUtils.industryId.equals("29")) {// 化工行业
            builder.append("chenhr");
        } else if (MyUtils.industryId.equals("23")) {// IT行业
            builder.append("it.800hr");
        }
        else if (MyUtils.industryId.equals("40")) {// 酒店行业
            builder.append("hotel.800hr");
        }
        else if (MyUtils.industryId.equals("16")) {// 运输行业
            builder.append("56.800hr");
        }
        else if (MyUtils.industryId.equals("21")) {// 通信行业
            builder.append("telecom.800hr");
        }
        else if (MyUtils.industryId.equals("20")) {// 电力行业
            builder.append("ep.800hr");
        }
        else if (MyUtils.industryId.equals("30")) {// 旅游行业
            builder.append("tour.800hr");
        }*/
		builder.append(".com/job/");
		builder.append(j_id);
		builder.append(".html");

		return builder.toString();

	}
	public static  String getBenDiPhoto(Context context){
		Resources res = context.getResources();
		BitmapDrawable d = (BitmapDrawable) res.getDrawable(R.drawable.app);
		Bitmap img = d.getBitmap();
		int width = img.getWidth();
		int height = img.getHeight();
		// 设置想要的大小
		int newWidth = 30;
		int newHeight = 30;
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(img, 0, 0, width, height, matrix,
				true);
		String fn = "image_test.png";
		String path = context.getFilesDir() + File.separator + fn;
		try{
			OutputStream os = new FileOutputStream(path);
			img.compress(Bitmap.CompressFormat.PNG, 100, os);
			os.close();
		}catch(Exception e){
			Log.e("TAG", "", e);
		}
		return path;
	}
}
