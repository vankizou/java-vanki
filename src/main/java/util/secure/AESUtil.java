package util.secure;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/*
 * AES对称加密和解密
 */
public class AESUtil {
    private static final String ALGORITHM = "AES";
    private static final String CHARSET = "UTF-8";

    /**
     * AES加密
     *
     * @param password
     * @param content
     *
     * @return 加密好的Base64文本
     *
     * @throws Exception
     */
    public static String encryptStr(String password, String content) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);// 创建AES的Key生产者

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes());
        keyGen.init(128, random);

        SecretKey secretKey = keyGen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHM);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        byte[] byteContent = content.getBytes(CHARSET);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(byteContent);

        return Base64.getEncoder().encodeToString(result);
    }

    /**
     * AES解密
     *
     * @param password
     * @param contentBase64 需要解密的Base64
     *
     * @return
     *
     * @throws Exception
     */
    public static String decryptStr(String password, String contentBase64) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes());
        keyGen.init(128, random);

        SecretKey secretKey = keyGen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHM);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] result = cipher.doFinal(Base64.getDecoder().decode(contentBase64));

        return new String(result);
    }

    public static void main(String[] args) throws Exception {
        String a = AESUtil.encryptStr("123", "4353465tihkj我alsdjflsjdflksjdfklasjdklxjcv,.mn,.szdlokfjal;ksdjfiwoqpueropwqjeklfjskldvmsaklmvxzklcjfv;kladsjf啊善良的快放假了撒多级反馈拉萨解放路卡诗酒风流啦时间的法拉盛肯德基弗兰卡圣诞节法拉盛快递费枷加lskd流口水地方枯ghdfjhgo867y8i");

        System.out.println(a);

        System.out.println(AESUtil.decryptStr("123", a));
    }

}