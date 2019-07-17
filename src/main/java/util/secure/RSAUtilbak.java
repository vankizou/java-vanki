package util.secure;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Provider;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vanki on 2017/8/1.
 */
public class RSAUtilbak {
    private static final String ALGORITHM = "RSA";
    private static final int KEY_SIZE = 1024;
    private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();    // 安全服务提供者

    private static KeyFactory keyFactory;

    private static RSAPrivateKey serverPrivateKey;
    private static RSAPublicKey clientPublicKey;

    static {
        try {
            keyFactory = KeyFactory.getInstance(ALGORITHM, DEFAULT_PROVIDER);

            serverPrivateKey = getPrivateKey(SecureConfig.serverPrivateKeyBase64);
            clientPublicKey = getPublicKey(SecureConfig.clientPublicKeyBase64);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(getKeyPair());
// abcdefghijklmnopqrstuvwxyz1234567890123456789012345678901234567800abcdefghijklmnopqrstuvwxyz1234567890123456789012345678901234567800
        String clientEn = encryptStr("你好啊啊世纪东方拉斯科两地分居");
        System.out.println(clientEn);
        System.out.println(decryptStr(SecureConfig.clientPrivateKeyBase64, clientEn));


        /*String serverEn = encryptStr(SecureConfig.serverPublicKeyBase64, "我是邹凡奇, number1");
        System.out.println(serverEn);
        System.out.println(decryptStr(serverEn));*/

        System.out.println(decryptStr("nr/LqTDFBemQhQqIJJYVpW8C+HwkZRfExrePeYIDAA07k4YwhzD7xfCWWir/AZZnw10Mg8N3fjYlVoYUS5V17Q=="));
    }


    /**
     * 获取一对rsa公钥和私钥
     *
     * @return
     *
     * @throws Exception
     */
    private static Map<String, String> getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGenerator.genKeyPair();

        Map<String, String> keyPairBase64Map = new HashMap<>();
        keyPairBase64Map.put("privateKey", Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
        keyPairBase64Map.put("publicKey", Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));

        return keyPairBase64Map;
    }


    /**
     * 用客户端公钥加密文本
     *
     * @param text
     *
     * @return
     *
     * @throws Exception
     */
    public static String encryptStr(String text) throws Exception {
        return encryptStr(null, text);
    }

    /**
     * 用服务器端私钥解密文本
     *
     * @param textHex
     *
     * @return
     *
     * @throws Exception
     */
    public static String decryptStr(String textHex) throws Exception {
        return decryptStr(null, textHex);
    }

    /**
     * 公钥加密文本
     *
     * @param publicKeyBase64
     * @param text            需要加密的文本
     *
     * @return 加密好的十六进制字符串
     *
     * @throws Exception
     */
    public static String encryptStr(String publicKeyBase64, String text) throws Exception {
        RSAPublicKey publicKey = publicKeyBase64 == null ? clientPublicKey : getPublicKey(publicKeyBase64);
        return doEncryptStr(publicKey, text);
    }

    /**
     * 私钥解密文本
     *
     * @param privateKeyBase64
     * @param textHex          需要解密的十六进制文本
     *
     * @return 解密成功后的原文本
     *
     * @throws Exception
     */
    public static String decryptStr(String privateKeyBase64, String textHex) throws Exception {
        RSAPrivateKey privateKey = privateKeyBase64 == null ? serverPrivateKey : getPrivateKey(privateKeyBase64);
        return doDecryptStr(privateKey, textHex);
    }


    private static String doEncryptStr(RSAPublicKey publicKey, String text) throws Exception {
        if (publicKey == null || text == null) return null;

        Cipher ci = Cipher.getInstance(ALGORITHM, DEFAULT_PROVIDER);
        ci.init(Cipher.ENCRYPT_MODE, publicKey);

        int modulesLen = publicKey.getModulus().bitLength() >> 3; // 模长
        // 加密数据长度 <= (模长 - 11)
        String[] dataArr = splitString(text, modulesLen - 11);

        StringBuffer encryptData = new StringBuffer();
        for (String data : dataArr) {
            encryptData.append(bcd2Str(ci.doFinal(data.getBytes())));
        }
        return encryptData.toString();
    }

    private static String doDecryptStr(RSAPrivateKey privateKey, String text) throws Exception {
        if (privateKey == null || text == null) return null;

        Cipher ci = Cipher.getInstance(ALGORITHM, DEFAULT_PROVIDER);
        ci.init(Cipher.DECRYPT_MODE, privateKey);

        int modulesLen = privateKey.getModulus().bitLength() >> 3;

        byte[] data = text.getBytes();
        byte[] bcd = ASCII_To_BCD(data, data.length);

        StringBuffer decryptData = new StringBuffer();
        byte[][] decryptArrs = splitArray(bcd, modulesLen);

        for (byte[] arr : decryptArrs) {
            decryptData.append(new String(ci.doFinal(arr)));
        }
        return decryptData.toString();
    }

    private static RSAPrivateKey getPrivateKey(String privateKeyBase64) throws InvalidKeySpecException, IOException {
        if (privateKeyBase64 == null) return null;
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyBase64));
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        return privateKey;
    }

    private static RSAPublicKey getPublicKey(String publicKeyBase64) throws InvalidKeySpecException, IOException {
        if (publicKeyBase64 == null) return null;
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyBase64));
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
        return publicKey;
    }


    /**
     * ASCII码转BCD码
     */
    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

    public static byte asc_to_bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9'))
            bcd = (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            bcd = (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            bcd = (byte) (asc - 'a' + 10);
        else
            bcd = (byte) (asc - 48);
        return bcd;
    }

    /**
     * BCD转字符串
     */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * 拆分字符串
     */
    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    /**
     * 拆分数组
     */
    public static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }
}
