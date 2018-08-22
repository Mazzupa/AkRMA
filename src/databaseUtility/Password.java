package databaseUtility;
import java.security.*;

public class Password {

	public static String Hash(String password) {

		try {
			MessageDigest sha512 = MessageDigest.getInstance("SHA-512");

			byte[] passHash = sha512.digest(password.getBytes());

			return _bytesToHex(passHash);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static final String _bytesToHex(byte[] hash) {

		StringBuffer hexString = new StringBuffer();

		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}
}