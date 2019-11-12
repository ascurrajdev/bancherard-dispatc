package py.com.sodep.bancard.api.utils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import py.com.sodep.bancard.api.client.ClientConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BancardClientUtils {

	private static final ObjectMapper mapper = new ObjectMapper();
	
	private BancardClientUtils() {
	}

	/**
	 * Lee la configuración de un cliente WS a partir de un archivo ".json".
	 * <p>
	 * Construye una configuración del cliente WS que debe implementar {@link ClientConfig}.
	 * La implementación de {@link ClientConfig} es proveída por el parámetros <code>clazz</code>.
	 * 
	 * @param resource
	 * @param clazz
	 * @return
	 */
	public static ClientConfig readConfig(String resource, Class<?> clazz) {
		ClientConfig clientConfig;
		try {
			InputStream inputStream = BancardClientUtils.class.getResourceAsStream(resource);
			clientConfig = (ClientConfig) mapper.readValue(inputStream, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return clientConfig;
	}

	public static String decodePassword(String password, String encoding) {
		String encodedPass = null;
		try {
			encodedPass = new String(hexStringToByteArray(password), encoding);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return encodedPass;
	}

	private static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
}
