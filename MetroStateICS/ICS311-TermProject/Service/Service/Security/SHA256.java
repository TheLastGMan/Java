package Service.Security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 implements ICryptoEngine
{
	@Override
	public boolean CanDecrypt()
	{
		return false;
	}
	
	@Override
	public boolean CanEncrypt()
	{
		return true;
	}
	
	@Override
	public byte[] Encrypt(String data)
	{
		MessageDigest digest;
		try
		{
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
			return hash;
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String Decrypt(byte[] data)
	{
		return null;
	}
}
