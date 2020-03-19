package com.yang.test.test;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @Description: 加密/解密工具类
 * @author chenwenbin@dhcc.com.cn
 * @date 2017年12月08日 上午16:36:44
 */
public class CipherUtils {

	private final static Logger logger = LoggerFactory.getLogger(CipherUtils.class);

	private static final int MAX_DECRYPT_BLOCK = 128;

	private static final int MAX_ENCRYPT_BLOCK = 117;

	private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();
	/**
	 * 字节数据转字符串专用集合
	 */
	private static final char[] HEX_CHAR= {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 加密过程
	 * @param publicKey 公钥
	 * @param plainTextData 明文数据
	 * @return
	 * @throws Exception 加密过程中的异常信息
	 */
	public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception{
		if(publicKey== null){
			logger.warn("加密公钥为空, 请设置");
		}
		Cipher cipher= null;
		try {
			cipher= Cipher.getInstance(KEY_ALGORITHM, DEFAULT_PROVIDER);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			int inputLen = plainTextData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache = null;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(plainTextData, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(plainTextData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] output = out.toByteArray();
			return output;
		} catch (NoSuchAlgorithmException e) {
			logger.error("无此加密算法",e);
		} catch (NoSuchPaddingException e) {
			logger.error("异常",e);
		}catch (InvalidKeyException e) {
			logger.error("加密公钥非法,请检查",e);
		} catch (IllegalBlockSizeException e) {
			logger.error("明文长度非法",e);
		} catch (BadPaddingException e) {
			logger.error("明文数据已损坏",e);
		}
		return null;
	}

	/**
	 * 加密过程
	 * @param publicKey 私钥
	 * @param plainTextData 明文数据
	 * @return
	 * @throws Exception 加密过程中的异常信息
	 */
	public byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData) throws Exception{
		if(privateKey== null){
			logger.warn("加密公钥为空, 请设置");
		}
		Cipher cipher= null;
		try {
			cipher= Cipher.getInstance(KEY_ALGORITHM, DEFAULT_PROVIDER);
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);

			int inputLen = plainTextData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache = null;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(plainTextData, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(plainTextData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] output = out.toByteArray();
			return output;
		} catch (NoSuchAlgorithmException e) {
			logger.error("无此加密算法",e);
		} catch (NoSuchPaddingException e) {
			logger.error("异常",e);
		}catch (InvalidKeyException e) {
			logger.error("加密私钥非法,请检查",e);
		} catch (IllegalBlockSizeException e) {
			logger.error("明文长度非法",e);
		} catch (BadPaddingException e) {
			logger.error("明文数据已损坏",e);
		}
		return null;
	}

	/**
	 * 解密过程
	 * @param privateKey 私钥
	 * @param cipherData 密文数据
	 * @return 明文
	 * @throws Exception 解密过程中的异常信息
	 */
	public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception{
		if (privateKey== null){
			logger.warn("解密私钥为空, 请设置");
		}
		Cipher cipher= null;
		try {
			cipher= Cipher.getInstance(KEY_ALGORITHM, DEFAULT_PROVIDER);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			int inputLen = cipherData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher
							.doFinal(cipherData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher
							.doFinal(cipherData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] output = out.toByteArray();
			return output;
		} catch (NoSuchAlgorithmException e) {
			logger.error("无此解密算法",e);
		} catch (NoSuchPaddingException e) {
			logger.error("异常",e);
		}catch (InvalidKeyException e) {
			logger.error("解密私钥非法,请检查",e);
		} catch (IllegalBlockSizeException e) {
			logger.error("密文长度非法",e);
		} catch (BadPaddingException e) {
			logger.error("密文数据已损坏",e);
		}
		return null;
	}


	/**
	 * 字节数据转十六进制字符串
	 * @param data 输入数据
	 * @return 十六进制内容
	 */
	public String byteArrayToString(byte[] data){
		StringBuilder stringBuilder= new StringBuilder();
		for (int i=0; i<data.length; i++){
			//取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0)>>> 4]);
			//取出字节的低四位 作为索引得到相应的十六进制标识符
			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
			if (i<data.length-1){
				stringBuilder.append(' ');
			}
		}
		return stringBuilder.toString();
	}
}