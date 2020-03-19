package com.yang.test.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import com.yang.test.test.CipherUtils;
import com.yang.test.test.SignUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PasswordFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import sun.misc.BASE64Decoder;

/**
 * @Description: ��ȡ��Կ������
 * @author chenwenbin@dhcc.com.cn
 * @date 2017��12��08�� ����16:36:44
 */
public class KeyUtils2 {

	private final static Logger logger = LoggerFactory.getLogger(KeyUtils2.class);

	private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();

	/**
	 * @Fields password : ֤������
	 */
	private static char[] password = "".toCharArray();
	/**
	 * �����㷨RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";
	/**
	 * @Fields keyPath : ˽Կ·��
	 */
	private static String keyPath;
	/**
	 * ˽Կ
	 */
	public static RSAPrivateKey privateKey;

	/**
	 * ��Կ
	 */
	public static RSAPublicKey publicKey;

	/**
	 * ��ȡ��Կ
	 * @return ��ǰ�Ĺ�Կ����
	 */
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * ��ȡ˽Կ
	 * @return ��ǰ��˽Կ����
	 */
	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}


	/**
	 * ���ļ����������м�����Կ
	 * @param in ��Կ������
	 * @throws Exception ������Կʱ�������쳣
	 */
	public String readKey(InputStream in) throws Exception{
		StringBuilder sb= new StringBuilder();
		try {
			BufferedReader br= new BufferedReader(new InputStreamReader(in));
			String readLine= null;
			while((readLine= br.readLine())!=null){
				if(readLine.charAt(0)=='-'){
					continue;
				}else{
					sb.append(readLine);
					sb.append('\r');
				}
			}
		} catch (IOException e) {
			logger.error("��������ȡ����",e);
		} catch (NullPointerException e) {
			logger.error("������Ϊ��",e);
		}
		return sb.toString();
	}

	/**
	 * ���ַ����м��ع�Կ
	 * @param publicKeyStr ��Կ�����ַ���
	 * @throws Exception ���ع�Կʱ�������쳣
	 */
	public static void loadPublicKey(String publicKeyStr) throws Exception{
		try {
			BASE64Decoder base64Decoder= new BASE64Decoder();
			byte[] buffer= base64Decoder.decodeBuffer(publicKeyStr);
			KeyFactory keyFactory= KeyFactory.getInstance(KEY_ALGORITHM);
			X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);
			KeyUtils2.publicKey= (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			logger.error("�޴��㷨",e);
		} catch (InvalidKeySpecException e) {
			logger.error("��Կ�Ƿ�",e);
		} catch (IOException e) {
			logger.error("��Կ�������ݶ�ȡ����",e);
		} catch (NullPointerException e) {
			logger.error("��Կ����Ϊ��",e);
		}
	}

	/**
	 * ���ַ����м���Կ
	 * @param publicKeyStr ��Կ�����ַ���
	 * @throws Exception ���ع�Կʱ�������쳣
	 */
	public void loadPrivateKey(String privateKeyStr) throws Exception{
		try {
			BASE64Decoder base64Decoder= new BASE64Decoder();
			byte[] buffer= base64Decoder.decodeBuffer(privateKeyStr);
			PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory= KeyFactory.getInstance(KEY_ALGORITHM);
			KeyUtils2.privateKey= (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			logger.error("�޴��㷨",e);
		} catch (InvalidKeySpecException e) {
			logger.error("˽Կ�Ƿ�",e);
		} catch (IOException e) {
			logger.error("˽Կ�������ݶ�ȡ����",e);
		} catch (NullPointerException e) {
			logger.error("˽Կ����Ϊ��",e);
		}
	}

	public void getPriKey(String path) {
		try {
			if(StringUtils.equals(keyPath,path) && privateKey != null) {
				return;
			}
			logger.info("��ȡ����·����"+path);
			File file = new File(path);
			if(!file.exists()) {
				logger.warn("֤�鲻���ڣ���������");
			}
			Security.addProvider(DEFAULT_PROVIDER);
			System.out.println("str.getBytes().length:"+FileUtils.readFileToByteArray(file).length);
			ByteArrayInputStream bais = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
			InputStreamReader inputStreamReader = new InputStreamReader(bais);
			PEMReader reader = new PEMReader(inputStreamReader, new PasswordFinder() {
				@Override
				public char[] getPassword() {
					return password;
				}
			});
			KeyPair keyPair = (KeyPair) reader.readObject();
			IOUtils.closeQuietly(bais);
			IOUtils.closeQuietly(inputStreamReader);
			IOUtils.closeQuietly(reader);
			KeyUtils2.privateKey = (RSAPrivateKey) keyPair.getPrivate();
			KeyUtils2.keyPath = path;
		} catch (Exception e) {
			logger.error("��ȡ˽Կ�쳣",e);
		}
	}

	public void getPriKey2(String str) {
		try {
			Security.addProvider(DEFAULT_PROVIDER);
			System.out.println("str.getBytes().length:"+str.getBytes().length);
			ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes());
			InputStreamReader inputStreamReader = new InputStreamReader(bais);
			PEMReader reader = new PEMReader(inputStreamReader, new PasswordFinder() {
				@Override
				public char[] getPassword() {
					return password;
				}
			});
			KeyPair keyPair = (KeyPair) reader.readObject();
			IOUtils.closeQuietly(bais);
			IOUtils.closeQuietly(inputStreamReader);
			IOUtils.closeQuietly(reader);
			KeyUtils.privateKey = (RSAPrivateKey) keyPair.getPrivate();
		} catch (Exception e) {
			logger.error("获取私钥异常",e);
		}
	}

	public void getPubKey(String path){
		KeyFactory keyFactory;
		KeySpec ks;
		try {
			byte[] decode = Base64.decodeBase64(path);
			byte[] sshrsa = new byte[] { 0, 0, 0, 7, 's', 's', 'h', '-', 'r', 's', 'a' };
			int start_index = sshrsa.length;
			/* Decode the public exponent */
			int len = decodeUInt32(decode, start_index);
			start_index += 4;
			byte[] pe_b = new byte[len];
			for (int i = 0; i < len; i++) {
				pe_b[i] = decode[start_index++];
			}
			BigInteger pe = new BigInteger(pe_b);
			/* Decode the modulus */
			len = decodeUInt32(decode, start_index);
			start_index += 4;
			byte[] md_b = new byte[len];
			for (int i = 0; i < len; i++) {
				md_b[i] = decode[start_index++];
			}
			BigInteger md = new BigInteger(md_b);
			keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			ks = new RSAPublicKeySpec(md, pe);
			KeyUtils2.publicKey = (RSAPublicKey) keyFactory.generatePublic(ks);
		} catch (Exception e) {
			logger.error("��ȡ��Կ�쳣",e);
		}
	}

	public RSAPublicKey getsPubKey(String path) throws Exception {
		KeyFactory keyFactory;
		KeySpec ks;
		String pubKeyStr="";
		try {
			pubKeyStr = FileUtils.readFileToString(new File(path));
			pubKeyStr = StringUtils.split(pubKeyStr," ")[1];
			byte[] decode = Base64.decodeBase64(pubKeyStr);
			byte[] sshrsa = new byte[] { 0, 0, 0, 7, 's', 's', 'h', '-', 'r', 's', 'a' };
			int start_index = sshrsa.length;
			/* Decode the public exponent */
			int len = decodeUInt32(decode, start_index);
			start_index += 4;
			byte[] pe_b = new byte[len];
			for (int i = 0; i < len; i++) {
				pe_b[i] = decode[start_index++];
			}
			BigInteger pe = new BigInteger(pe_b);
			/* Decode the modulus */
			len = decodeUInt32(decode, start_index);
			start_index += 4;
			byte[] md_b = new byte[len];
			for (int i = 0; i < len; i++) {
				md_b[i] = decode[start_index++];
			}
			BigInteger md = new BigInteger(md_b);
			keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			ks = new RSAPublicKeySpec(md, pe);
			return (RSAPublicKey) keyFactory.generatePublic(ks);
		} catch (Exception e) {
			logger.error("��ȡ��Կ�쳣",e);
			KeyUtils2.loadPublicKey(pubKeyStr);
			return KeyUtils2.publicKey;
		}
	}

	public static int decodeUInt32(byte[] key, int start_index) {
		byte[] test = Arrays.copyOfRange(key, start_index, start_index + 4);
		return new BigInteger(test).intValue();
	}

	private static String mainPubKey = "AAAAB3NzaC1yc2EAAAADAQABAAAAgQC8rWGrUEQcmc7PNX1pKkzszXiUubfOuBW6iE6P/o/eg9YcC9dGkv2QOPL2b+RSTPhMH0uXhNgW9gdRTlF+wszbBU/Wj004Zr8TRnsncPh2IdqZj8IotCBn+ZqGy2ECCA/Vs1ig64YJg64qKahRHs1S3OGBM/wngcFaPr3zO4w8Dw==";

	public static void main(String[] args) throws Exception {
		KeyUtils2 fdkeyUtils=new KeyUtils2();
		fdkeyUtils.getPriKey("F:\\Downfile\\Wechat\\WeChat Files\\wxid_e3d6eiw78nja22\\FileStorage\\File\\2019-09\\公钥私钥2\\guomei.id_rsa");
		fdkeyUtils.getPubKey(mainPubKey);

		CipherUtils cipherUtils = new CipherUtils();
		String origin = "{'brNo': 'GOME','projNo': '2015','custName': '小七','idType': '0','idNo': '13068119931015201X','acno': '173317065','cardChn': '2','serialNo': 'SE0010','agreeCode': '20190911'}";
		System.out.println(origin.length());
		byte[] encrypt = cipherUtils.encrypt(fdkeyUtils.getPublicKey(), origin.getBytes("UTF-8"));
		String encryptRequestStr=Base64.encodeBase64String(encrypt);
		System.out.println("华润加密后数据:" + encryptRequestStr);

		String sign = SignUtils.sign(origin.getBytes("UTF-8"),fdkeyUtils.getPrivateKey());
		System.out.println("华润加签后数据:" + sign);

		byte[]   var1=Base64.decodeBase64("k7ZlKAFGjXH9i5/KGVIB0YsfLdIt3AEZ3LfOZimFWAtJq59DTgfHtd8yvOelXbToYGBOKIfSgitSRFMfnlMmWo4BNuTCNHOLlrUYA6rCRAOrXo21870Fl9jhuyqX7sSPK1psCVEndxAysMV2yKVAV5YgK8ZEfwRrPxb1AEjEpSVkT+HBiI3cQysPPKduPSC0LVAvhd3yOK3Cua6gq7kCRfg02zUysWWpgDG3buDpxsEDD2oVv72zFsxirTse3otlMONgOYil2Zm4B6YLE4Yc4SAyj6xoIx2F8/mtm72v8gJQrXI/GuTSDpPhg7bBbTAvGUmTFtJ7vCbBOYbuILAmuA==");
		byte[] dencrypt = cipherUtils.decrypt(fdkeyUtils.getPrivateKey(),var1);
		String decryptString = new String(dencrypt,"UTF-8");
		System.out.println("华润解密后数据:" + decryptString);

	}
}
