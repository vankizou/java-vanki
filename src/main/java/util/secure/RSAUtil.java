package util.secure;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
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
public class RSAUtil {
    private static final String ALGORITHM = "RSA";
    private static final int KEY_SIZE = 1024;
    private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();    // 安全服务提供者

    private static final int MAX_ENCRYPT_BLOCK = 117;   // RSA最大加密明文大小，KEY_SIZE / 8 - 11
    private static final int MAX_DECRYPT_BLOCK = 128;   // RSA最大解密密文大小，KEY_SIZE / 8

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
        String clientEn = encrypt("你好啊啊世纪东方拉拉你好啊啊世纪东方拉拉你好啊啊世纪东方拉拉你好啊啊世纪东方拉拉你好啊啊世纪东方拉拉你好啊啊世纪东方拉拉你好啊啊世纪东方拉拉你好啊啊世纪东方拉拉你好啊啊世纪东方拉拉你好啊啊世纪东方拉拉");
        System.out.println(clientEn);
        System.out.println(decrypt(SecureConfig.clientPrivateKeyBase64, clientEn));


        /*String serverEn = encryptStr(SecureConfig.serverPublicKeyBase64, "你好啊啊世纪东方拉拉你好啊啊世纪东方拉拉你");
        System.out.println(serverEn);
        System.out.println(decryptStr(serverEn));*/

//        System.out.println(decrypt("ECkbMH8EEQtuvxjw5BzbSCLWg/jo5hPJe7jv9MXjisl36yelDlSGJEnnIAzsAeAgI/3NKEjEEYAtJFe1JSr/xBVgxnRyh7usGGNi2NdCbcnC2a4qAYZCAjjFiDwX50y2Z56kmvSUFNU4yJmoDr74G6leOpNrBos8OcLYpitW5hB//PiMgdWMyCYDWXyqDteebX+xkpnRpJ+iqkh02Cyz497rL8Knd2V6eTfMrkg/DB7KCYZwgHXjysard8Fp1WW5PKBE/4M1edrWucBB6SPrOxgdab6z7QOSEjBZ40uNPtWHcIPukDVsjgRAmTOwhpYEX3VU8vkOqIkFLnqVbMTkK3CyAvup8a/gwKEWFlYuo5lBGTwgvOUfgQ+6guV0CGwbZvh/EU272GaFGckT3roxKJrCmtPUaK/R8w5H5p1Ka6E8NC5aIo4xkcmmy+DhTHnGRA3efpNj3A9RvGx6pXPC7VcK+l7ZxSsuJaMtpqf/iftHBmLxpOobu2lNmiSiKr80GhrmEYa19D3OrdKfanu5h7dQ+md1UGmpXB5SVnYQmOobzNY4knswoTi2nCK1l1XJMV1cpVM5Rs1KN5jEmwTo5mCVSur/r77+YdluMAyGAZ+8EfRSnDxk0+3txrpvIss1YJNsejiR5FeZNskC/b2ImKWy7NFGdON/s6RQIt5xP/BJ6NJ52RvUoeTzkDeu4jYfR49ToUp0u98FdL6vbH0a09WixjNvzPZxQ+mUUDGj4SG6jkoEcqKaIA8URDlV5URFEFdNxILWCb9b3C0yd+GixfQCS6jnDSzVdndOUMzVJso2tvH3Ce8rU60i0INmMbY654+aap9tRNTC3X1pLaf7AU4FzTvMiI60DL/rX2DhiQci2DzqQfS88Rq0wptnApxeiX0KIe9EdRSLSBP+gT7tMfrP72KEEy7+JG3B//kD9SRtXW5SUSdXbpH+9S+IfXLjU++Nk9i6giL1LeGDn/zni9E3aAev9sO+grfRN/d28zdxjUB0caS+LwHBAoh7a+rg"));
    }


    /**
     * 获取一对rsa公钥和私钥
     *
     * @return
     *
     * @throws Exception
     */
    private static Map<String, String> getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM, DEFAULT_PROVIDER);
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
    public static String encrypt(String text) throws Exception {
        return encrypt(null, text);
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
    public static String decrypt(String textHex) throws Exception {
        return decrypt(null, textHex);
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
    public static String encrypt(String publicKeyBase64, String text) throws Exception {
        RSAPublicKey publicKey = publicKeyBase64 == null ? clientPublicKey : getPublicKey(publicKeyBase64);
        return doEncrypt(publicKey, text);
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
    public static String decrypt(String privateKeyBase64, String textHex) throws Exception {
        RSAPrivateKey privateKey = privateKeyBase64 == null ? serverPrivateKey : getPrivateKey(privateKeyBase64);
        return doDecrypt(privateKey, textHex);
    }

    private static String doEncrypt(RSAPublicKey publicKey, String text) {
        try {
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] data = text.getBytes();
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while ((inputLen - offSet) > 0) {
                if ((inputLen - offSet) > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String doDecrypt(RSAPrivateKey privateKey, String text)
            throws Exception {
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] encryptedData = Base64.getDecoder().decode(text);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData);
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
