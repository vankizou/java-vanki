package util.secure;

/**
 * Created by vanki on 2017/8/1.
 */
public class SecureUtil {
    public static void main(String[] args) {

    }

    public static String getParameters(String aesParams, String rsaKey) throws Exception {
        if (aesParams == null || rsaKey == null) return null;

        String key = RSAUtil.decrypt(rsaKey);
        return AESUtil.decryptStr(key, aesParams);
    }


    public static boolean verifySign(String text, String saltRSA, String sign) throws Exception {
        if (saltRSA == null || sign == null) return false;

        String salt = RSAUtil.decrypt(saltRSA);

        if (text == null) {
            text = salt;
        } else {
            text += salt;
        }
        // TODO text MD5
//        if (sign.equals(text))

        return true;
    }
}
