/**
 *
 */
package UnitTests;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.*;

import Core.*;
import Server.ServerPacketProcessor;

/**
 * Test Packet Processing
 *
 * @author Ryan Gau
 * @version 1.0
 */
public class PacketProcessorTest {

	@Test
	public void AddFewPacketsTest() {
		try {
			// Packet(boolean checksumValid, short length, int
			// acknowledgeNumber, int sequenceNumber, byte[] content)
			ServerPacketProcessor processor = new ServerPacketProcessor();
			processor.addPacket(new Packet(true, (short)12, 1, 2, new byte[] {}));
			processor.addPacket(new Packet(true, (short)12, 1, 3, new byte[] {}));
			processor.addPacket(new Packet(true, (short)12, 1, 1, new byte[] {}));

			// duplicate check, success, then error
			// debug output of duplicate, no insertion
			processor.addPacket(new Packet(false, (short)12, 1, 4, new byte[] {}));
			processor.addPacket(new Packet(false, (short)12, 1, 4, new byte[] {}));
			processor.addPacket(new Packet(true, (short)12, 1, 4, new byte[] {}));

			// duplicate check, duplicate false first
			// debug output duplicate, no error, inserting
			processor.addPacket(new Packet(true, (short)12, 1, 5, new byte[] {}));
			processor.addPacket(new Packet(false, (short)12, 1, 5, new byte[] {}));
			processor.addPacket(new Packet(false, (short)12, 1, 5, new byte[] {}));

			int index = 1;
			for (Packet pkt : processor.getPacketBuffer()) {
				if (pkt.seqno != index) {
					Assert.fail("Incorrect packet order: expected: " + index + " | actual: " + pkt.seqno);
				}

				index += 1;
			}
		}
		catch (Exception ex) {
			Assert.fail(ex.getMessage());
		}
	}

	@Test
	public void ProcessPacketsTest() {
		try {
			// Arrange
			ServerPacketProcessor processor = new ServerPacketProcessor();
			String expected = "Hello World";
			StringBuffer actual = new StringBuffer("");
			AtomicBoolean isDone = new AtomicBoolean();

			processor.ProcessPacketEventHandler.addHandler((eventArgs) -> {
				try {
					actual.append(new String(eventArgs.dataStream, "UTF-8"));
				}
				catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			processor.PacketRxCompletedEventHandler.addHandler((eventArgs) -> {
				isDone.set(true);
				processor.stop();
			});

			// Act
			processor.start();

			// out of order packets
			processor.addPacket(new Packet(true, (short)12, 1, 1, new String("Hello ").getBytes("UTF-8")));
			Thread.sleep(500);
			processor.addPacket(new Packet(true, (short)12, 1, 3, new byte[] {}));
			Thread.sleep(500);
			processor.addPacket(new Packet(true, (short)12, 1, 2, new String("World").getBytes("UTF-8")));
			Thread.sleep(500);

			while (!isDone.get()) {
				Thread.sleep(100);
			}

			// Assert
			Assert.assertTrue("Expected (" + expected + ") does not match actual (" + actual + ")",
					expected.equals(actual.toString()));
		}
		catch (Exception ex) {
			Assert.fail(ex.getMessage());
		}
	}

	@Test
	public void PacketPackingTest() {
		try {
			Packet pkt = new Packet(true, (short)16, 1, 2, new byte[] { 31, 41, 59, 26 });
			byte[] pktS = Common.serialize(pkt);
			Packet pktD = Common.deserialize(pktS, Packet.class);
			Assert.assertTrue("Packets do not match", pkt.ackno == pktD.ackno);
		}
		catch (Exception ex) {
			Assert.fail("Error: " + ex.getMessage());
		}
	}
}
