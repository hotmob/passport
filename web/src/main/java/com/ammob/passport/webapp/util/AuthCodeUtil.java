package com.ammob.passport.webapp.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import com.ammob.passport.Constants;

public class AuthCodeUtil {
	
    public enum Mode {
        Encode, Decode
    };

    public static String CutString(String str, int startIndex, int length){
        if (startIndex >= 0) {
            if (length < 0) {
                length = length * -1;
                if(startIndex - length < 0) {
                    length = startIndex;
                    startIndex = 0;
                } else {
                    startIndex = startIndex - length;
                }
            }
            if (startIndex > str.length()) {
                return "";
            }
        } else {
            if (length < 0) {
                return "";
            } else {
                if (length + startIndex > 0) {
                    length = length + startIndex;
                    startIndex = 0;
                } else {
                    return "";
                }
            }
        }
        if (str.length() - startIndex < length) {
            length = str.length() - startIndex;
        }
        return str.substring(startIndex, startIndex + length);
    }

    public static String CutString(String str, int startIndex) {
        return CutString(str, startIndex, str.length());
    }

    private static byte[] GetKey(byte[] pass, int kLen) {
        byte[] mBox = new byte[kLen];
        for (int i = 0; i < kLen; i++) {
            mBox[i] = (byte) i;
        }
        int j = 0;
        for (int i = 0; i < kLen; i++) {
            j = (j + (int) ((mBox[i] + 256) % 256) + pass[i % pass.length]) % kLen;
            byte temp = mBox[i];
            mBox[i] = mBox[j];
            mBox[j] = temp;
        }
        return mBox;
    }

    public static String RandomString(int lens) {
        String[] CharArray = {"a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1" , "2" , "3" , "4" , "5" , "6" , "7" , "8" , "9" };
        int clens = CharArray.length;
        String sCode = "";
        Random random = new Random();
        for (int i = 0; i < lens; i++) {
            sCode += CharArray[Math.abs(random.nextInt(clens))];
        }
        return sCode;
    }

    public static String authcodeEncode(String source, String key, int expiry) {
        return authcode(source, key, Mode.Encode, expiry);
    }

    public static String authcodeEncode(String source) {
    	if(!StringUtils.hasText(source)){
    		return null;
    	}
    	String key = DigestUtils.md5Hex(Constants.SECURITY_SUPERVISION_CODE); //加密解密验证串
        return authcode(source, key, Mode.Encode, 0);
    }

    public static String authcodeDecode(String source) {
    	if(!StringUtils.hasText(source)){
    		return null;
    	}
    	String key = DigestUtils.md5Hex(Constants.SECURITY_SUPERVISION_CODE); //解密解密验证串
        return authcode(source, key, Mode.Decode, 0);
    }

    private static String authcode(String source, String key, Mode operation, int expiry) {
        try {
            if (source == null || key == null) {
                return "";
            }
            int ckey_length = 4;
            String keya, keyb, keyc, cryptkey, result;
            key = DigestUtils.md5Hex(key);
            keya = DigestUtils.md5Hex(CutString(key, 0, 16));
            keyb = DigestUtils.md5Hex(CutString(key, 16, 16));
            
            keyc = ckey_length > 0 ? (operation == Mode.Decode ? CutString(
                    source, 0, ckey_length)
                    : RandomString(ckey_length))
                    : "";

            cryptkey = keya + DigestUtils.md5Hex(keya + keyc);

            if (operation == Mode.Decode) {
                byte[] temp;
                temp = Base64.decodeBase64(CutString(source, ckey_length));
                result = new String(RC4(temp, cryptkey));
                if (CutString(result, 10, 16).equals(CutString(DigestUtils.md5Hex(CutString(result, 26) + keyb), 0, 16))) {
                    return CutString(result, 26);
                } else {
                    temp = Base64.decodeBase64(CutString(source+"=", ckey_length));
                    result = new String(RC4(temp, cryptkey));
                    if (CutString(result, 10, 16).equals(CutString(DigestUtils.md5Hex(CutString(result, 26) + keyb), 0, 16))) {
                        return CutString(result, 26);
                    } else {
                        temp = Base64.decodeBase64(CutString(source+"==", ckey_length));
                        result = new String(RC4(temp, cryptkey));
                        if (CutString(result, 10, 16).equals(CutString(DigestUtils.md5Hex(CutString(result, 26) + keyb), 0, 16))) {
                            return CutString(result, 26);
                        }else{
                            return "";
                        }
                    }
                }
            } else {
                source = "0000000000" + CutString(DigestUtils.md5Hex(source + keyb), 0, 16) + source;
                byte[] temp = RC4(source.getBytes("UTF-8"), cryptkey);
                return keyc + (new String(Base64.encodeBase64(temp)));
            }
        } catch (Exception e) {
            return "";
        }

    }

    private static byte[] RC4(byte[] input, String pass) {
        if (input == null || pass == null)
            return null;

        byte[] output = new byte[input.length];
        byte[] mBox = GetKey(pass.getBytes(), 256);

        int i = 0;
        int j = 0;

        for (int offset = 0; offset < input.length; offset++) {
            i = (i + 1) % mBox.length;
            j = (j + (int) ((mBox[i] + 256) % 256)) % mBox.length;

            byte temp = mBox[i];
            mBox[i] = mBox[j];
            mBox[j] = temp;
            byte a = input[offset];

            byte b = mBox[(toInt(mBox[i]) + toInt(mBox[j])) % mBox.length];

            output[offset] = (byte) ((int) a^(int) toInt(b));
        }

        return output;
    }

    public static int toInt(byte b) {
        return (int) ((b + 256) % 256);
    }
    
    public static String encodeURL(String value) {
		try {
			return URLEncoder.encode(value, "UTF8");
		}catch (UnsupportedEncodingException e) {
			// Not supposed to happen
			throw new RuntimeException("Unexpected encoding exception", e);
		}
	}

    /**
     * 获取UNIX时间戳
     * @return
     */
    public static long getUnixTimestamp() {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis() / 1000;
    }

    /**
     * 封装验证字符串
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String wrap(String username) throws UnsupportedEncodingException {
    	String unixTimestamp = Long.toString(getUnixTimestamp());
		String authCode = authcodeEncode(username.concat(unixTimestamp));
    	return "username=" + username + "&timestamp=" + unixTimestamp + "&authcode=" + URLEncoder.encode(authCode, "UTF-8");
    }
}
