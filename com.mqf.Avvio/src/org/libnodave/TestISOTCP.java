/*
 Test and demo program for Libnodave, a free communication libray for Siemens S7.
 
 **********************************************************************
 * WARNING: This and other test programs overwrite data in your PLC.  *
 * DO NOT use it on PLC's when anything is connected to their outputs.*
 * This is alpha software. Use entirely on your own risk.             * 
 **********************************************************************
 
 (C) Thomas Hergenhahn (thomas.hergenhahn@web.de) 2002, 2004.

 This is free software; you can redistribute it and/or modify
 it under the terms of the GNU Library General Public License as published by
 the Free Software Foundation; either version 2, or (at your option)
 any later version.

 This is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU Library General Public License
 along with Visual; see the file COPYING.  If not, write to
 the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.  
 */
package org.libnodave;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.libnodave.PLCinterface;

import com.mqf.*;

public class TestISOTCP
{
	static boolean doWrite = true;
	static int useProtocol = Nodave.PROTOCOL_ISOTCP;
	static int slot = 2;

	public int i, j;
	public char buf[];
	public byte buf1[];
	public PLCinterface di;
	public TCPConnection dc;
	public Socket sock;

	TestISOTCP(String host) {

		buf = new char[Nodave.OrderCodeSize];
		buf1 = new byte[Nodave.PartnerListSize];
		try
			{
				System.out.println("Sono nel costruttore");

				sock = new Socket(host, 102);
			} catch (SocketException e)
			{
				System.out.println("Errore con soket in costruttore " + e);
				e.printStackTrace();

			} catch (IOException e)
			{
				System.out.println("Errore in costruttore " + e);
				e.printStackTrace();
			}

		System.out.println("Esco dal costruttore");
	}

	public static void prepara()
		{
			// Plc Address to connect 
			TestISOTCP tp = new TestISOTCP("192.168.1.100");
			tp.runSomething();
		}

	void runSomething()
		{
			OutputStream oStream = null;
			InputStream iStream = null;
			System.out.println("sono in run2");
			byte[] by;
			if (sock != null)
				{
					try
						{
							oStream = sock.getOutputStream();
						} catch (IOException e)
						{
							PlcState.ErrPlc = true;
							System.out
									.println("oStream = sock.getOutputStream() "
											+ e);
						}
					try
						{
							iStream = sock.getInputStream();
						} catch (IOException e)
						{
							PlcState.ErrPlc = true;
							System.out
									.println("iStream = sock.getInputStream() "
											+ e);
						}
					di = new PLCinterface(oStream, iStream, "IF1", 3,
							Nodave.PROTOCOL_ISOTCP);

					dc = new TCPConnection(di, 2, 0);

					int res = dc.connectPLC();
					if (0 == res)// Here we Read from PLC
						{

						
						    dc.readBytes(Nodave.DB,19,1610,4,null);
						    DB1.media = dc.getFloat();
						    
							dc.readBytes(Nodave.DB,19,30,4,null);
							DB1.T1 = dc.getFloat();
							
							dc.readBytes(Nodave.DB,19,34,4,null);
							DB1.T2 = dc.getFloat();
							
							dc.readBytes(Nodave.DB,19,38,4,null);
							DB1.T3 = dc.getFloat();
							
							dc.readBytes(Nodave.DB,19,42,4,null);
							DB1.T4 = dc.getFloat();
							
							dc.readBytes(Nodave.DB,19,46,4,null);
							DB1.T5 = dc.getFloat();
							
							byte[] val = { (byte) 0x10 };
							dc.writeBytes(Nodave.DB,8,1,1,val);

							val[0] = (byte) 0x0;
							dc.writeBytes(Nodave.DB,8,1,1,val);
							
							/*System.out
									.println("Trying to read 16 bytes from FW0.\n");
							dc.readBytes(Nodave.FLAGS, 0, 0, 16, null);// Read
																		// Flag
							Merker.MD0 = dc.getU32();
							Merker.MD4 = dc.getU32();
							Merker.MD8 = dc.getU32();
							Merker.MD12 = dc.getFloat();
							System.out.println("4 DWORDS " + Merker.MD0 + " "
									+ Merker.MD4 + " " + Merker.MD8);
							System.out.println("1 Float: " + Merker.MD12);

							if (doWrite)// Here we write from PLC
								{
									System.out
											.println("Now we write back these data after incrementing the first 3 by 1,2,3 and the float by 1.1.\n");

									by = Nodave.bswap_32(Merker.MD0 + 1);
									dc.writeBytes(Nodave.FLAGS, 0, 0, 4, by);

									by = Nodave.bswap_32(Merker.MD4 + 1);
									dc.writeBytes(Nodave.FLAGS, 0, 4, 4, by);

									by = Nodave.bswap_32(Merker.MD8 + 1);
									dc.writeBytes(Nodave.FLAGS, 0, 8, 4, by);

									by = Nodave.toPLCfloat(Merker.MD12 + 1.1);
									dc.writeBytes(Nodave.FLAGS, 0, 12, 4, by);

									dc.readBytes(Nodave.FLAGS, 0, 0, 16, null);
									Merker.MD0 = dc.getU32();
									Merker.MD4 = dc.getU32();
									Merker.MD8 = dc.getU32();
									Merker.MD12 = dc.getFloat();
									System.out.println("FD0: " + Merker.MD0);
									System.out.println("FD4:" + Merker.MD4);
									System.out.println("FD8:" + Merker.MD8);
									System.out.println("FD12: " + Merker.MD12);

								} // doWrite*/

							System.out.println("Now disconnecting\n");
							dc.disconnectPLC();
							di.disconnectAdapter();
						} else
						{
							PlcState.ErrPlc = true;
							System.out
									.println("Couldn't connect to PLC. Error:"
											+ res);
						}
				} else
				{
					PlcState.ErrPlc = true;
					System.out.println("Couldn't open connection");
				}
		}

}