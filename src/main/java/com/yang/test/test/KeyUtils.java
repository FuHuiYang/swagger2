package com.yang.test.test;

import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PasswordFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.Arrays;

/**
 * @Description: 获取密钥工具类
 * @author chenwenbin@dhcc.com.cn
 * @date 2017年12月08日 上午16:36:44
 */
public class KeyUtils {

	private final static Logger logger = LoggerFactory.getLogger(KeyUtils.class);

	private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();

	/**
	 * @Fields password : 证书密码
	 */
	private static char[] password = "".toCharArray();
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";
	/**
	 * @Fields keyPath : 私钥路径
	 */
	private static String keyPath;
	/**
	 * 私钥
	 */
	public static RSAPrivateKey privateKey;

	/**
	 * 公钥
	 */
	public static RSAPublicKey publicKey;

	/**
	 * 获取公钥
	 * @return 当前的公钥对象
	 */
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * 获取私钥
	 * @return 当前的私钥对象
	 */
	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}
	private  static  String zifangPubKey="AAAAB3NzaC1yc2EAAAABIwAAAQEA0JFyBLWrjorgBBRR6cA/Ncq4/MsBZKxw2X4iCZw1opJskChwjxzhkQJnITGL4lwQBgL2hzwAZQIb2qbX9YFKic6coV6pZJPI4jUV6U+K+/8NAFFMjUmdhqTXmzJ2A8OuxXGHB24bkSgv0EOsdD3RdTpApNy+UJUTP5PJ/OptpLbrQak/6RXaFjp3ViABuI7f8goLAsEA4z1sk/pIXRC7apVzb8KNY0WtDSo5Q15PYWZQuIIIF1hLyCRhHZyaLQ93OIn92dOZ9d9tKb05hvHeOu2Mg5B1uBlV9YD7koVKP1Eu+rcQ2iWMYQI998Ta3/CL1FrzIAtkexyx0IXhthDjFQ==";
	private static String gomePubKey="AAAAB3NzaC1yc2EAAAABIwAAAQEAtctyJOZfmuC+lmoIH2/P5Optb1f7hr6qugmJl6C7aQtIQ0WaH7zO3gHh97oSgWykrcsQJ93IIOIwZtlzCv8uvKXox5GZ05n7dZ3mXVifJ3R+JxW8TRT2eFgOzFDlGF/tR/StYTSQVHi9zqj9DVX/5ejCRbXqvitRP9pwsnwDaFmsJEyVi/iC+a8PzSYtCzXLUrgGO0KBpeZtFv5BYW+6aeagLYm2sHSITcM3Rg2iE5H0rlGLrTnQpfhjic1hBvzf/PS2ea1F3N+VDaiVKtFB9eGcIELtB5IybaIlyKd3VXUoK2IRj16yvyfaoz93Aifwg5KjGpcujMq7A4y2lvfUKw== ";
	private static String gomePriKey = "-----BEGIN RSA PRIVATE KEY-----\r\n" +
			"MIIEogIBAAKCAQEAtctyJOZfmuC+lmoIH2/P5Optb1f7hr6qugmJl6C7aQtIQ0Wa\r\n" +
			"H7zO3gHh97oSgWykrcsQJ93IIOIwZtlzCv8uvKXox5GZ05n7dZ3mXVifJ3R+JxW8\r\n" +
			"TRT2eFgOzFDlGF/tR/StYTSQVHi9zqj9DVX/5ejCRbXqvitRP9pwsnwDaFmsJEyV\r\n" +
			"i/iC+a8PzSYtCzXLUrgGO0KBpeZtFv5BYW+6aeagLYm2sHSITcM3Rg2iE5H0rlGL\r\n" +
			"rTnQpfhjic1hBvzf/PS2ea1F3N+VDaiVKtFB9eGcIELtB5IybaIlyKd3VXUoK2IR\r\n" +
			"j16yvyfaoz93Aifwg5KjGpcujMq7A4y2lvfUKwIBIwKCAQEAlqFBUcYx9VshkpJe\r\n" +
			"gHKWT/VwnhWzJoC5WFELmuRDguwXTa6y5xjIqVIEX4uLrRDgODqJuqHRtNirwu6v\r\n" +
			"x0h3LpgYpVtiMvvtm/fcIXVQq6mqWuYfrZUGu3wphLgOR2Vs1ThyZnwCjyI23m7D\r\n" +
			"Cwy+FkSDthpjal5n5HMqLXyyXcU/ExmEqfX5CuHHfIrU6v1U9E2XrKIOAQETXbhU\r\n" +
			"eI508zJsdoBNp9ISIB1A0JUZy9WH3FNUDLjhuOwopF3xPen3LxPnnzqtiqbZHkkC\r\n" +
			"VRhD1RxerfSy4VIDnReothlyhvI8/j6XNh9CGf1/jEPF93uAjWryfI47JQxy6Ljq\r\n" +
			"Yy1lGwKBgQDY0f5kfpUwfTOcIPMPxVqviv31xHGF7w3YI3xSYV4UO8zEgVhMIcRx\r\n" +
			"XbXGXwXxWU2iFxuIAO5oh5bn1NrnmMWIWy7SPT9qdnaV9RGoQGqa6AwYzYBvekNh\r\n" +
			"VDjnCygmYAFnkfOQRXOuzr523Ykkww+mwa0+8TVq/WVxHSGdFXcBOQKBgQDWpS9k\r\n" +
			"zYEUQzX+f9It9UhCfZFiPIj8d9dInOFCbpHbPY2NvBInUWMbi4A0VTlCJV0YjU0I\r\n" +
			"Ki57AWfGPgWCahz4V0zD3VwBICzeku9c2/nrQZjKbyhn3nkTW9Ea2OUEh40wXcHc\r\n" +
			"cod/MiXJ1c50+e0qMbPD61UAl6W2Y52UsYfUgwKBgBKVqBc+DMmkVOF/KsbWZtvY\r\n" +
			"tq6qcCFsQwPlyNPcdcc4U2FM6k+rH3dubqqabjlCKznHd2Nty0rJyxsvgHpAS3IW\r\n" +
			"cbo/w5tpPV1PhSutv/6mLOwu3x9/gh5JDDEPlbolfHaXe0bhYa/l1c+slroCF0jO\r\n" +
			"xbTwGoWDbxhS9ECqEYPDAoGADEP0FGODuAPXMx0/NdOAeNP5rdeSzJkiPqng/HtY\r\n" +
			"yrMPZy9RfpbvuG5fGO7tVDymO+rRM6qjkgAUjvw61ECT8O8Ls2u8HVJLtPJ0E+9Q\r\n" +
			"HBJggJijOSKn1ThyWU44+PHNjbw29qd1dPuNIXnuildso1NMGdLu6hdLTEA04+zj\r\n" +
			"MLcCgYEAhNov8phHBtKXy384c1CK1fmz7viva9hu/ROr4pI5ydclhaNXsmfF5AkS\r\n" +
			"IQ9/MAktPAJfeu8gEne6a5sicMqKYWCB1m3VBTDN6Ej1YyKKP0NkQlh5imFrdPMr\r\n" +
			"iulopwn9TpPfTWgUsQ6wPTIz+sbjoLndQArWl5WDXIeu2rCpims=\r\n" +
			"-----END RSA PRIVATE KEY-----";
	/**
	 * 从文件中输入流中加载密钥
	 * @param in 公钥输入流
	 * @throws Exception 加载密钥时产生的异常
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
			logger.error("数据流读取错误",e);
		} catch (NullPointerException e) {
			logger.error("输入流为空",e);
		}
		return sb.toString();
	}

	/**
	 * 从字符串中加载公钥
	 * @param publicKeyStr 公钥数据字符串
	 * @throws Exception 加载公钥时产生的异常
	 */
	public static void loadPublicKey(String publicKeyStr) throws Exception{
		try {
			BASE64Decoder base64Decoder= new BASE64Decoder();
			byte[] buffer= base64Decoder.decodeBuffer(publicKeyStr);
			KeyFactory keyFactory= KeyFactory.getInstance(KEY_ALGORITHM);
			X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);
			KeyUtils.publicKey= (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			logger.error("无此算法",e);
		} catch (InvalidKeySpecException e) {
			logger.error("公钥非法",e);
		} catch (IOException e) {
			logger.error("公钥数据内容读取错误",e);
		} catch (NullPointerException e) {
			logger.error("公钥数据为空",e);
		}
	}

	/**
	 * 从字符串中加载钥
	 * @param publicKeyStr 公钥数据字符串
	 * @throws Exception 加载公钥时产生的异常
	 */
	public static void loadPrivateKey(String privateKeyStr) throws Exception{
		try {
			BASE64Decoder base64Decoder= new BASE64Decoder();
			byte[] buffer= base64Decoder.decodeBuffer(privateKeyStr);
			PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory= KeyFactory.getInstance(KEY_ALGORITHM);
			KeyUtils.privateKey= (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			logger.error("无此算法",e);
		} catch (InvalidKeySpecException e) {
			logger.error("私钥非法",e);
		} catch (IOException e) {
			logger.error("私钥数据内容读取错误",e);
		} catch (NullPointerException e) {
			logger.error("私钥数据为空",e);
		}
	}

	public void getPubKey(String pubKeyStr){
		KeyFactory keyFactory;
		KeySpec ks;
		try {
//			String pubKeyStr = FileUtils.readFileToString(new File(path));
//			pubKeyStr = StringUtils.split(pubKeyStr," ")[1];
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
			KeyUtils.publicKey = (RSAPublicKey) keyFactory.generatePublic(ks);
		} catch (Exception e) {
			logger.error("获取公钥异常",e);
		}
	}

	public void getPriKey(String path) {
		try {
//			if(StringUtils.equals(keyPath,path) && privateKey != null) {
//				return;
//			}
//			logger.info("获取到新路径："+path);
//			File file = new File(path);
//			if(!file.exists()) {
//				logger.warn("证书不存在，请检查配置");
//			}
			String aa="-----BEGIN RSA PRIVATE KEY-----\r\n" +
					"MIIEogIBAAKCAQEAwlplUMkPRC6JJDp/TeZ1ApWosHeTXAGo0s5ugbI4TdKeVtbX\r\n" +
					"Luepp/n1KWfZShSoTMKNI12dCfF/EFQC6fHYA6MJMgxqk96Z5Btz8p1Uozn6xL0H\r\n" +
					"QHkRrJcLc5K4EloOAe91AfzF0C0ojLAg5PsrcovV0vJD0zT4HbQQdTE+0cdjRzVV\r\n" +
					"wMja5GghCDfzpg+uT9iUiU3LCUKF27+quztVR1hYgMzd7jqR7CaaNSs40QMIXyEq\r\n" +
					"AcsXkb1i7sCb2s7q6NP5GATF9z+ujuCyJwGNDEpFujr/Vdpj4B0BrCjjHy1luhWM\r\n" +
					"aanOulzETmg4NKcqyaH2c/snfnfCFjMhsyxpMQIDAQABAoIBAF0r7MovuSvci05c\r\n" +
					"5M65nnqguH/wsyo2eXKmGAABKlDUmafT0tDfqkum3sr8yARlptE1q2e65x/vMBTp\r\n" +
					"0YPJtt73d98QaL9+xZO/PXSfzAV8NCXA4Qsdf0WYG5tKSQgzN+fhndxYC5TjMT9Q\r\n" +
					"6FQdvRRqyRUZoz5LdG749bLY6t6xuf+WjqjHCrlAN86SkaKweQS1evPu/HS0uDCg\r\n" +
					"rcwVBhn6hlz7msf36V6U3R7+f7rzhY7O9I83zMwUF2t9VldRU7NMVY2PxMXRzIl5\r\n" +
					"ixlJ8+nPb4M1vIwVrEvP/LZtuSxAuxENgb45UD3MadZSXw0fiEydfBI2ZfPblrqy\r\n" +
					"8ejAMCkCgYEA/EDouTp4efibbPpFNbgU3Em3pvtItPMIZNeFP9C7OYpnZ8J2BubR\r\n" +
					"2STTjguh22u/RoBS7/8uIquTLmm7omqqoSy6OggJjKaxk7YvBJL10XARhG5Y+eSJ\r\n" +
					"tasUBOeByXvimOg8UcY1wOTP1jl7052xoiI1Fr7b0hVIBFHVDdZfNPcCgYEAxT1Y\r\n" +
					"DKphXQ50UcO3EBX0NNoXepJ8Trgjb+V4RniZJ4dJ0XExPwDeWsK+Q44KHeBwWzuk\r\n" +
					"VW+E2qmysQcGnyaVIdo3TRHtF5nNfrNLDhQo1BKzptNo6JEfuRjr6QvQxfTCNZMe\r\n" +
					"e3OQ4OmKdLdt58vJicsmE6WF3oKlKxS2GxFn0RcCf2OFOEvbxaxZaPiVxLhs584B\r\n" +
					"R3OZsBWzzJv8a5XBxBvvftxw3B/c8O26mbxw2mq3F7+44P/OrWUATBEDpgNuEeOE\r\n" +
					"wfQjN6FGIskkF2i2kkUO8dpNrszoXWxPEpbcv1e+iKfK+AFLMenoXBcolrHmhS5y\r\n" +
					"UH+ozREVVFRf9K6A5Q8CgYEAsrpFoSR7GZHRgY3xwlfCdGnzwJQgpExjgC/VbzY0\r\n" +
					"wgbrsyf40fh9hIzXT+vT6wLHjURcQzbMkkAXtiaBr5GMraJJXkMSLsKl3VumdVl7\r\n" +
					"LcDMni0P8T4gGuAkVYbpZZfGrDll88Pi0ouNdPoKU3Lsazp0hHUG+3wzIvifk6OQ\r\n" +
					"CX0CgYEAvt5vspRfMa6ZeNs+n2BWJNPnEQbyX8prikBrLJ7FJppfbqEIKLO5s6t3\r\n" +
					"iR3LOJeCvq9wqVk2L7eOscF4IvQAx0ndxS31qsQPsBjl2QbKv2MwQM47m84dn03P\r\n" +
					"chjUeMBvZsOGbY9QQyRYp1tyGzeuk2l2P4DgYsR7f7BOlPVwmZs=\r\n" +
					"-----END RSA PRIVATE KEY-----\r\n";
			Security.addProvider(DEFAULT_PROVIDER);
			ByteArrayInputStream bais = new ByteArrayInputStream(path.getBytes("UTF-8"));
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
//			KeyUtils.keyPath = path;
		} catch (Exception e) {
			logger.error("获取私钥异常",e);
		}
	}


	public static int decodeUInt32(byte[] key, int start_index) {
		byte[] test = Arrays.copyOfRange(key, start_index, start_index + 4);
		return new BigInteger(test).intValue();
	}

	public static void main(String[] args) throws Exception {
		/**-----加密解密，加签解签-----**/
//		KeyUtils keyUtils=new KeyUtils();
		try{
//        keyUtils.getPriKey("C:\\Users\\Administrator\\.ssh\\lxy.id_rsa");
//			keyUtils.getPriKey("-----BEGIN RSA PRIVATE KEY-----\r\n" +
//				"MIIEogIBAAKCAQEAwlplUMkPRC6JJDp/TeZ1ApWosHeTXAGo0s5ugbI4TdKeVtbX\r\n" +
//				"Luepp/n1KWfZShSoTMKNI12dCfF/EFQC6fHYA6MJMgxqk96Z5Btz8p1Uozn6xL0H\r\n" +
//				"QHkRrJcLc5K4EloOAe91AfzF0C0ojLAg5PsrcovV0vJD0zT4HbQQdTE+0cdjRzVV\r\n" +
//				"wMja5GghCDfzpg+uT9iUiU3LCUKF27+quztVR1hYgMzd7jqR7CaaNSs40QMIXyEq\r\n" +
//				"AcsXkb1i7sCb2s7q6NP5GATF9z+ujuCyJwGNDEpFujr/Vdpj4B0BrCjjHy1luhWM\r\n" +
//				"aanOulzETmg4NKcqyaH2c/snfnfCFjMhsyxpMQIDAQABAoIBAF0r7MovuSvci05c\r\n" +
//				"5M65nnqguH/wsyo2eXKmGAABKlDUmafT0tDfqkum3sr8yARlptE1q2e65x/vMBTp\r\n" +
//				"0YPJtt73d98QaL9+xZO/PXSfzAV8NCXA4Qsdf0WYG5tKSQgzN+fhndxYC5TjMT9Q\r\n" +
//				"6FQdvRRqyRUZoz5LdG749bLY6t6xuf+WjqjHCrlAN86SkaKweQS1evPu/HS0uDCg\r\n" +
//				"rcwVBhn6hlz7msf36V6U3R7+f7rzhY7O9I83zMwUF2t9VldRU7NMVY2PxMXRzIl5\r\n" +
//				"ixlJ8+nPb4M1vIwVrEvP/LZtuSxAuxENgb45UD3MadZSXw0fiEydfBI2ZfPblrqy\r\n" +
//				"8ejAMCkCgYEA/EDouTp4efibbPpFNbgU3Em3pvtItPMIZNeFP9C7OYpnZ8J2BubR\r\n" +
//				"2STTjguh22u/RoBS7/8uIquTLmm7omqqoSy6OggJjKaxk7YvBJL10XARhG5Y+eSJ\r\n" +
//				"tasUBOeByXvimOg8UcY1wOTP1jl7052xoiI1Fr7b0hVIBFHVDdZfNPcCgYEAxT1Y\r\n" +
//				"DKphXQ50UcO3EBX0NNoXepJ8Trgjb+V4RniZJ4dJ0XExPwDeWsK+Q44KHeBwWzuk\r\n" +
//				"VW+E2qmysQcGnyaVIdo3TRHtF5nNfrNLDhQo1BKzptNo6JEfuRjr6QvQxfTCNZMe\r\n" +
//				"e3OQ4OmKdLdt58vJicsmE6WF3oKlKxS2GxFn0RcCf2OFOEvbxaxZaPiVxLhs584B\r\n" +
//				"R3OZsBWzzJv8a5XBxBvvftxw3B/c8O26mbxw2mq3F7+44P/OrWUATBEDpgNuEeOE\r\n" +
//				"wfQjN6FGIskkF2i2kkUO8dpNrszoXWxPEpbcv1e+iKfK+AFLMenoXBcolrHmhS5y\r\n" +
//				"UH+ozREVVFRf9K6A5Q8CgYEAsrpFoSR7GZHRgY3xwlfCdGnzwJQgpExjgC/VbzY0\r\n" +
//				"wgbrsyf40fh9hIzXT+vT6wLHjURcQzbMkkAXtiaBr5GMraJJXkMSLsKl3VumdVl7\r\n" +
//				"LcDMni0P8T4gGuAkVYbpZZfGrDll88Pi0ouNdPoKU3Lsazp0hHUG+3wzIvifk6OQ\r\n" +
//				"CX0CgYEAvt5vspRfMa6ZeNs+n2BWJNPnEQbyX8prikBrLJ7FJppfbqEIKLO5s6t3\r\n" +
//				"iR3LOJeCvq9wqVk2L7eOscF4IvQAx0ndxS31qsQPsBjl2QbKv2MwQM47m84dn03P\r\n" +
//				"chjUeMBvZsOGbY9QQyRYp1tyGzeuk2l2P4DgYsR7f7BOlPVwmZs=\r\n" +
//				"-----END RSA PRIVATE KEY-----");
//		keyUtils.getPubKey("AAAAB3NzaC1yc2EAAAADAQABAAABAQDCWmVQyQ9ELokkOn9N5nUClaiwd5NcAajSzm6BsjhN0p5W1tcu56mn+fUpZ9lKFKhMwo0jXZ0J8X8QVALp8dgDowkyDGqT3pnkG3PynVSjOfrEvQdAeRGslwtzkrgSWg4B73UB/MXQLSiMsCDk+ytyi9XS8kPTNPgdtBB1MT7Rx2NHNVXAyNrkaCEIN/OmD65P2JSJTcsJQoXbv6q7O1VHWFiAzN3uOpHsJpo1KzjRAwhfISoByxeRvWLuwJvazuro0/kYBMX3P66O4LInAY0MSkW6Ov9V2mPgHQGsKOMfLWW6FYxpqc66XMROaDg0pyrJofZz+yd+d8IWMyGzLGkx");

		KeyUtils fdkeyUtils=new KeyUtils();
			fdkeyUtils.getPriKey("-----BEGIN RSA PRIVATE KEY-----\r\n" +
					"MIIEogIBAAKCAQEAtctyJOZfmuC+lmoIH2/P5Optb1f7hr6qugmJl6C7aQtIQ0Wa\r\n" +
					"H7zO3gHh97oSgWykrcsQJ93IIOIwZtlzCv8uvKXox5GZ05n7dZ3mXVifJ3R+JxW8\r\n" +
					"TRT2eFgOzFDlGF/tR/StYTSQVHi9zqj9DVX/5ejCRbXqvitRP9pwsnwDaFmsJEyV\r\n" +
					"i/iC+a8PzSYtCzXLUrgGO0KBpeZtFv5BYW+6aeagLYm2sHSITcM3Rg2iE5H0rlGL\r\n" +
					"rTnQpfhjic1hBvzf/PS2ea1F3N+VDaiVKtFB9eGcIELtB5IybaIlyKd3VXUoK2IR\r\n" +
					"j16yvyfaoz93Aifwg5KjGpcujMq7A4y2lvfUKwIBIwKCAQEAlqFBUcYx9VshkpJe\r\n" +
					"gHKWT/VwnhWzJoC5WFELmuRDguwXTa6y5xjIqVIEX4uLrRDgODqJuqHRtNirwu6v\r\n" +
					"x0h3LpgYpVtiMvvtm/fcIXVQq6mqWuYfrZUGu3wphLgOR2Vs1ThyZnwCjyI23m7D\r\n" +
					"Cwy+FkSDthpjal5n5HMqLXyyXcU/ExmEqfX5CuHHfIrU6v1U9E2XrKIOAQETXbhU\r\n" +
					"eI508zJsdoBNp9ISIB1A0JUZy9WH3FNUDLjhuOwopF3xPen3LxPnnzqtiqbZHkkC\r\n" +
					"VRhD1RxerfSy4VIDnReothlyhvI8/j6XNh9CGf1/jEPF93uAjWryfI47JQxy6Ljq\r\n" +
					"Yy1lGwKBgQDY0f5kfpUwfTOcIPMPxVqviv31xHGF7w3YI3xSYV4UO8zEgVhMIcRx\r\n" +
					"XbXGXwXxWU2iFxuIAO5oh5bn1NrnmMWIWy7SPT9qdnaV9RGoQGqa6AwYzYBvekNh\r\n" +
					"VDjnCygmYAFnkfOQRXOuzr523Ykkww+mwa0+8TVq/WVxHSGdFXcBOQKBgQDWpS9k\r\n" +
					"zYEUQzX+f9It9UhCfZFiPIj8d9dInOFCbpHbPY2NvBInUWMbi4A0VTlCJV0YjU0I\r\n" +
					"Ki57AWfGPgWCahz4V0zD3VwBICzeku9c2/nrQZjKbyhn3nkTW9Ea2OUEh40wXcHc\r\n" +
					"cod/MiXJ1c50+e0qMbPD61UAl6W2Y52UsYfUgwKBgBKVqBc+DMmkVOF/KsbWZtvY\r\n" +
					"tq6qcCFsQwPlyNPcdcc4U2FM6k+rH3dubqqabjlCKznHd2Nty0rJyxsvgHpAS3IW\r\n" +
					"cbo/w5tpPV1PhSutv/6mLOwu3x9/gh5JDDEPlbolfHaXe0bhYa/l1c+slroCF0jO\r\n" +
					"xbTwGoWDbxhS9ECqEYPDAoGADEP0FGODuAPXMx0/NdOAeNP5rdeSzJkiPqng/HtY\r\n" +
					"yrMPZy9RfpbvuG5fGO7tVDymO+rRM6qjkgAUjvw61ECT8O8Ls2u8HVJLtPJ0E+9Q\r\n" +
					"HBJggJijOSKn1ThyWU44+PHNjbw29qd1dPuNIXnuildso1NMGdLu6hdLTEA04+zj\r\n" +
					"MLcCgYEAhNov8phHBtKXy384c1CK1fmz7viva9hu/ROr4pI5ydclhaNXsmfF5AkS\r\n" +
					"IQ9/MAktPAJfeu8gEne6a5sicMqKYWCB1m3VBTDN6Ej1YyKKP0NkQlh5imFrdPMr\r\n" +
					"iulopwn9TpPfTWgUsQ6wPTIz+sbjoLndQArWl5WDXIeu2rCpims=\r\n" +
					"-----END RSA PRIVATE KEY-----\r\n");
			fdkeyUtils.getPubKey("AAAAB3NzaC1yc2EAAAABIwAAAQEAtctyJOZfmuC+lmoIH2/P5Optb1f7hr6qugmJl6C7aQtIQ0WaH7zO3gHh97oSgWykrcsQJ93IIOIwZtlzCv8uvKXox5GZ05n7dZ3mXVifJ3R+JxW8TRT2eFgOzFDlGF/tR/StYTSQVHi9zqj9DVX/5ejCRbXqvitRP9pwsnwDaFmsJEyVi/iC+a8PzSYtCzXLUrgGO0KBpeZtFv5BYW+6aeagLYm2sHSITcM3Rg2iE5H0rlGLrTnQpfhjic1hBvzf/PS2ea1F3N+VDaiVKtFB9eGcIELtB5IybaIlyKd3VXUoK2IRj16yvyfaoz93Aifwg5KjGpcujMq7A4y2lvfUKw==");
//        fdkeyUtils.getPriKey("C:\\Users\\Administrator\\lxy\\lxy.id_rsa");
//		fdkeyUtils.getPriKey("-----BEGIN RSA PRIVATE KEY-----\r\n" +
//				"MIICXQIBAAKBgQC8rWGrUEQcmc7PNX1pKkzszXiUubfOuBW6iE6P/o/eg9YcC9dG\r\n" +
//				"kv2QOPL2b+RSTPhMH0uXhNgW9gdRTlF+wszbBU/Wj004Zr8TRnsncPh2IdqZj8Io\r\n" +
//				"tCBn+ZqGy2ECCA/Vs1ig64YJg64qKahRHs1S3OGBM/wngcFaPr3zO4w8DwIDAQAB\r\n" +
//				"AoGAJbVZMc3m7vhmds8v81a67RDbqo27iJsN7KvVV73+rvAWWKCdM8Oo3awkEtCm\r\n" +
//				"BzTO4xfWGcA5F82wBLXsX7M7Ear4Bq9PEx9X0EOpMFEq7ROH3D4fIJPobzlVvs9U\r\n" +
//				"ZW4nzVdxGwtpw7aq56MGLyazBdZdTxFgvjZeKRecXdYGB4ECQQDmx3EuiGgbGTU2\r\n" +
//				"6vh3AIUJxgUfZZ6OEN5OkaVCMWVhez8IPP8jLyHGf5+Plina47tdsg8aUR9lXaEC\r\n" +
//				"cPzRobShAkEA0UwMRBTchcGhAzizXfmyMH5IkNGFrBSO6SExLh8357ghkdjbWhcM\r\n" +
//				"oaaAoqKWV3Q7ucI9UL/f55xgHW4fI0iCrwJAFbLW73sf3rxmBn3dUsMXvy2BOcyC\r\n" +
//				"NfVcEaMcGLcwQxQlfw7NJm++Cz3gsM17rfsPmOWvCdhqzUsaYnJhMwyzQQJBAKeD\r\n" +
//				"K0oOPFWdnOX8KqsrXy+q0x99YnRQ2/TBCSC2AcOnPs8BdsaLLiPvJIOGh7elIuxR\r\n" +
//				"B0wq1eUE5nSHn8pYjacCQQCSFdGx2szkmpkss5/06uBgN5gkzodfXLWqjTEFqP76\r\n" +
//				"k6qd2u+HtHHXSAMh86nPc7iA8Q51j4C0yVF5kH2CJDnX\r\n" +
//				"-----END RSA PRIVATE KEY-----");
//		fdkeyUtils.getPubKey("AAAAB3NzaC1yc2EAAAADAQABAAAAgQC8rWGrUEQcmc7PNX1pKkzszXiUubfOuBW6iE6P/o/eg9YcC9dGkv2QOPL2b+RSTPhMH0uXhNgW9gdRTlF+wszbBU/Wj004Zr8TRnsncPh2IdqZj8IotCBn+ZqGy2ECCA/Vs1ig64YJg64qKahRHs1S3OGBM/wngcFaPr3zO4w8Dw==");

		CipherUtils cipherUtils = new CipherUtils();
//		String origin = "{'ADDR_STREET1':'130681','ADDR_STREET2':'河北省涿州市豆庄乡南香营村79号','EXPIRYDATE':'20240506','ISSDATE':'20140506','IsNongLoan':'2','IsNongYe':'2','IsNonghu':'2','TranNo':'SE0010','TxnDt':'20190911','TxnSrlNo':'GMJR20190911GMCMC19091117332404323','TxnTs':'173317065','UnitKind':'N','WorAdd':'110101','WorAddDes':'北京市北京市市辖区朝阳区鹏润大厦','WorCTel':'15801003752','amount':45500,'birthday':'19931015','comm_add':'110105','comm_zip':'北京市北京市市辖区朝阳区鹏润大厦','edu_degree':'2','edu_experience':'10','emcontact1':'0302','emcontact1Name':'b','emcontact1Tel':'18211078563','emcontact2':'0302','emcontact2Name':'小七','emcontact2Tel':'15550000066','family_add':'110105','family_addDes':'北京市北京市市辖区朝阳区鹏润大厦','family_status':'7','filerequest_no':'GMJRGMLOC19091117324900069','headShip':'4','id_no':'13068119931015201X','id_type':'Ind01','income':'16000','marriage':'10','nation':'02','nationality':'CHN','occupation':'0','open_id':'GMJR13068119931015201X','partner':'GMJR','position':'0','request_no':'GMJRGMLOC19091117324900069','sex':'1','telephone':'15801003752','user_Type':'0310','user_data':[{'data_desc':'gps经度','data_name':'longitude','data_value':'116.466574'},{'data_desc':'gps维度','data_name':'latitude','data_value':'39.958729'},{'data_desc':'申请GPS城市','data_name':'gpsApplyCity','data_value':'北京市北京市市辖区'}],'user_name':'史雪飞','workcorp':'国美','worktype':'33'}";
		String origin="{\"dealDesc\":\"异常\",\"dealNo\":\"04\"}";
		System.out.println(origin.length());
		byte[] encrypt = cipherUtils.encrypt(fdkeyUtils.getPublicKey(), origin.getBytes("UTF-8"));
		String encryptRequestStr= Base64.encodeBase64String(encrypt);
		System.out.println("加密数据:"+encryptRequestStr);
//
//		String sign=SignUtils.sign(origin.getBytes("UTF-8"),keyUtils.getPrivateKey());
//		System.out.println("加签数据:"+sign);

		byte[]   var1= Base64.decodeBase64(encryptRequestStr);
		byte[] dencrypt = cipherUtils.decrypt(fdkeyUtils.getPrivateKey(),var1);
		String decryptString = new String(dencrypt,"UTF-8");
		System.out.println("解密数据:"+decryptString);
//
//		boolean verify = SignUtils.verify(decryptString.getBytes("UTF-8"),keyUtils.getPublicKey(),sign);
//		System.out.println("验签状态："+verify);
	} catch (Exception e) {
		e.printStackTrace();
	}
//		KeyUtils fdkeyUtils2=new KeyUtils();
//		fdkeyUtils2.getPriKey(gomePriKey);
//		fdkeyUtils2.getPubKey(gomePubKey);
//		CipherUtils cipherUtils = new CipherUtils();
//
////		KeyUtils fdkeyUtils=new KeyUtils();
//		String origin = "123123";
////		fdkeyUtils.getPubKey(gomePubKey);
////		byte[] encrypt = cipherUtils.encrypt(fdkeyUtils2.getPublicKey(), origin.getBytes("UTF-8"));
////		String encryptRequestStr= Base64.encodeBase64String(encrypt);
////		System.out.println("加密数据:"+encryptRequestStr);
////		System.out.println("k7ZlKAFGjXH9i5/KGVIB0YsfLdIt3AEZ3LfOZimFWAtJq59DTgfHtd8yvOelXbToYGBOKIfSgitSRFMfnlMmWo4BNuTCNHOLlrUYA6rCRAOrXo21870Fl9jhuyqX7sSPK1psCVEndxAysMV2yKVAV5YgK8ZEfwRrPxb1AEjEpSVkT+HBiI3cQysPPKduPSC0LVAvhd3yOK3Cua6gq7kCRfg02zUysWWpgDG3buDpxsEDD2oVv72zFsxirTse3otlMONgOYil2Zm4B6YLE4Yc4SAyj6xoIx2F8/mtm72v8gJQrXI/GuTSDpPhg7bBbTAvGUmTFtJ7vCbBOYbuILAmuA==".equals(encryptRequestStr));
//		byte[]   var1= Base64.decodeBase64("k7ZlKAFGjXH9i5/KGVIB0YsfLdIt3AEZ3LfOZimFWAtJq59DTgfHtd8yvOelXbToYGBOKIfSgitSRFMfnlMmWo4BNuTCNHOLlrUYA6rCRAOrXo21870Fl9jhuyqX7sSPK1psCVEndxAysMV2yKVAV5YgK8ZEfwRrPxb1AEjEpSVkT+HBiI3cQysPPKduPSC0LVAvhd3yOK3Cua6gq7kCRfg02zUysWWpgDG3buDpxsEDD2oVv72zFsxirTse3otlMONgOYil2Zm4B6YLE4Yc4SAyj6xoIx2F8/mtm72v8gJQrXI/GuTSDpPhg7bBbTAvGUmTFtJ7vCbBOYbuILAmuA==");
//		byte[] dencrypt = cipherUtils.decrypt(fdkeyUtils2.getPrivateKey(),var1);
//		String decryptString = new String(dencrypt,"UTF-8");
//		System.out.println("华润解密后数据:" + decryptString);
	}
}
