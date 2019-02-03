package com.cloud.demo.auth.util;

import java.io.File;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaUtil {

    /**
     * 从文件中读取公钥
     * @param fileName 公钥保存路径，相对于classpath 或绝对路径
     * @return 公钥对象
     * @throws Exception 异常抛出
     */
    public static PublicKey getPublicKey(String fileName) throws Exception{
        byte[] bytes = readFile(fileName);
        return getPublicKey(bytes);
    }

    /**
     * 从文件中读取密钥
     * @param fileName 私钥保存路径，相对于classpath 或绝对路径
     * @return 私钥对象
     * @throws Exception 异常抛出
     */
    public static PrivateKey getPrivateKey(String fileName) throws Exception{
        byte[] bytes = readFile(fileName);
        return getPrivateKey(bytes);
    }

    /**
     * 获取公钥
     * @param bytes 公钥的字节形式
     * @return
     * @throws Exception
     */
    static PublicKey getPublicKey(byte[] bytes) throws Exception{
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
    }

    /**
     * 获取密钥
     * @param bytes 私钥的字节形式
     * @return
     * @throws Exception
     */
    static PrivateKey getPrivateKey(byte[] bytes) throws Exception{
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);
    }

    /**
     * 根据密文，生成RSA公钥和私钥,并写入指定文件
     * @param publicKeyFileName 公钥文件路径
     * @param privateKeyFileName 私钥文件路径
     * @param secret 用于生成密钥的密文
     * @throws Exception  异常抛出
     */
    public static void generateKey(String publicKeyFileName, String privateKeyFileName, String secret) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(1024,secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 获取公钥并写出到文件
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        writeFile(publicKeyFileName,publicKeyBytes);
        // 获取私钥并写出到文件
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        writeFile(privateKeyFileName, privateKeyBytes);
    }

    private static byte[] readFile(String fileName) throws Exception{
        return Files.readAllBytes(new File(fileName).toPath());
    }
    private static void writeFile(String destPath, byte[] bytes) throws Exception{
        File dest = new File(destPath);
        if (!dest.exists()){
            dest.createNewFile();
        }
        Files.write(dest.toPath(), bytes);
    }
}
