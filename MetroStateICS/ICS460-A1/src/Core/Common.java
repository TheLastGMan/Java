package Core;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * Common utilities
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class Common {
	// this is shared to reduce the chance a same value
	// will be generated in the same interval
	private static Random _Random = new Random();
	
	/**
	 * Convert a whole number to a padded string
	 *
	 * @param value
	 *            Value to convert
	 * @param padLength
	 *            length of output, length is greater of pad length and value
	 *            length when converted
	 * @return String
	 */
	public static String numberToString(long value, int padLength) {
		// sequence number
		StringBuffer sb = new StringBuffer(padLength);
		String seqNum = Long.toString(value);
		for (int i = padLength - seqNum.length(); i > 0; i--) {
			sb.append(" ");
		}
		sb.append(seqNum);
		return sb.toString();
	}
	
	/***
	 * Checks if a generated random number [0, 1) is less than the specified
	 * value
	 *
	 * @param value
	 *            Value to check generated value against
	 * @return T/F if less than
	 */
	public static boolean checkRandomBelowLevel(double value) {
		Logging.debug("checking random number below: " + value);
		
		// soft validation
		if (value >= 1) {
			value = 0.99;
		}
		else if (value < 0) {
			value = 0;
		}

		// random's range is 0 <= x < 1
		return (_Random.nextDouble()) < value;
	}
	
	/**
	 * Serialize a packet to a byte array
	 *
	 * @return Byte array representation of this object
	 * @throws Exception
	 */
	public static <T> byte[] serialize(T obj) throws Exception {
		Logging.debug("serializing object: " + obj.getClass().getName() + " | " + obj.toString());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		byte[] result = baos.toByteArray();
		baos.close();
		return result;
	}
	
	/**
	 * Parse a packet out of a byte array
	 *
	 * @param packetStream
	 *            serialized packet
	 * @return Packet Packet result
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public static <T> T deserialize(byte[] packetStream, Class<T> type) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(packetStream);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object obj = ois.readObject();
		T pkt = type.cast(obj);
		bais.close();
		Logging.debug("deserialized packet: " + pkt.toString());
		return pkt;
	}

	/**
	 * Compute the checksum from an array of bytes
	 * logic is an add with carry formula using 15 bits
	 *
	 * @param data
	 *            array of byte data
	 * @return CheckSum
	 */
	public static short computeCheckSum(byte[] data) {
		int count = 0;
		int mask = 0x7FFF;
		for (byte d : data) {
			count += d;

			if (count > mask) {
				count &= mask;
				count += 1;
			}
		}

		return (short)count;
	}
	
	/**
	 * Get the current DateTime
	 * 
	 * @return formatted HH:mm:ss.SSS
	 */
	public static String currentDateStamp() {
		String format = "HH:mm:ss.SSS";
		DateFormat df = new SimpleDateFormat(format);
		Date now = Calendar.getInstance().getTime();
		return df.format(now);
	}
}
