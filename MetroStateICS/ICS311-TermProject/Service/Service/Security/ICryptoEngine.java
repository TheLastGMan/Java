package Service.Security;

public interface ICryptoEngine
{
	boolean CanDecrypt();
	
	boolean CanEncrypt();
	
	byte[] Encrypt(String data);
	
	String Decrypt(byte[] data);
}
