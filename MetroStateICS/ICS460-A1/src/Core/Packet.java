package Core;

import java.io.Serializable;

/**
 * @author Professor, Ryan Gau
 * @version 1.0
 */
public class Packet implements Serializable {
	private static final long serialVersionUID = 1L;

	public final short cksum;	// 16-bit 2-byte (0 = good, 1 = bad)
	public final short len;		// 16-bit 2-byte (includes data length)
									// 8 for ACK PACKET
									// 12 + data.length for Data Packet
	public final int ackno;		// 4-byte
	public final int seqno;		// 4-byte (Data packet Only)
	public final byte[] data;	// 1-N bytes. (Data packet only)
									// 0 for end of transmission

	public Packet(boolean checksumValid, short length, int acknowledgeNumber, int sequenceNumber, byte[] content)
			throws Exception {
		// validate/sanity checks
		if (length < 0) {
			throw new Exception("Length must be greater than or equal to zero");
		}
		if (acknowledgeNumber <= 0) {
			throw new Exception("Acknowledge Number must be greater than zero");
		}
		if (sequenceNumber < 0) {
			throw new Exception("Sequence Number must be at least zero");
		}
		if (content == null) {
			content = new byte[0];
		}

		// compute checksum
		// short crc15 = Common.computeCheckSum(content);
		// crc15 <<= 1;
		// crc15 |= (short)(checksumValid ? 0 : 1);

		// apply values
		cksum = (short)(checksumValid ? 0 : 1);
		len = length;
		ackno = acknowledgeNumber;
		seqno = sequenceNumber;
		data = content;
	}

	/**
	 * Checks if the checksum is valid or not
	 *
	 * @return T/F if valid
	 */
	public boolean checksumValid() {
		// good-old binary anding
		return (cksum & 1) == 0;
	}

	@Override
	public String toString() {
		return "CKSUM: " + cksum + " | LEN: " + len + " | ACK: " + ackno + " | SEQ: " + seqno;
	}
}
