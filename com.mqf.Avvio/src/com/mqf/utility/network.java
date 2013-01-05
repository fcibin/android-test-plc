package com.mqf.utility;

import java.io.*;
import java.net.*;

public class network
{
	
	/**
	 * @param addr1 IP address in byte example
	 *  byte[] { (byte) 10, (byte) 0, (byte) 0, (byte)
	 * @param port number ot the port example 102
	 * @param timeoutMs millisecond Timeout
	 * @return time out connection in millisecond -1 if timeout
	 */
	public static long testConnection(byte[] addr1, int port, int timeoutMs)
		{
			// pass in a byte array with the ipv4 address, the port & the max
			// time  out required
			// byte[] addr1 = new byte[] { (byte) 10, (byte) 0, (byte) 0, (byte)
			// 100 };

			long start = -1; // default check value
			long end = -1; // default check value
			long total = -1; // default for bad connection

			// make an unbound socket
			Socket theSock = new Socket();

			try
				{
					InetAddress addr = InetAddress.getByAddress(addr1);

					SocketAddress sockaddr = new InetSocketAddress(addr, port);

					// Create the socket with a timeout
					// when a timeout occurs, we will get timout exp.
					// also time our connection this gets very close to the real
					// time
					start = System.currentTimeMillis();
					theSock.connect(sockaddr, timeoutMs);
					end = System.currentTimeMillis();
				} catch (UnknownHostException e)
				{
					start = -1;
					end = -1;
				} catch (SocketTimeoutException e)
				{
					start = -1;
					end = -1;
				} catch (IOException e)
				{
					start = -1;
					end = -1;
				} finally
				{
					if (theSock != null)
						{
							try
								{
									theSock.close();
								} catch (IOException e)
								{
								}
						}

					if ((start != -1) && (end != -1))
						{
							total = end - start;
						}
				}

			return total; // returns -1 if timeout
		}

	/**
	 * @param addr1 IP address in string
	 * @param port number ot the port example 102
	 * @param timeoutMs millisecond Timeout
	 * @return time out connection in millisecond -1 if timeout
	 */
	public static long testConnection(String addr1, int port, int timeoutMs)
		{
			// pass in a byte array with the ipv4 address, the port & the max
			// time  out required
			// byte[] addr1 = new byte[] { (byte) 10, (byte) 0, (byte) 0, (byte)
			// 100 };
			
					

			long start = -1; // default check value
			long end = -1; // default check value
			long total = -1; // default for bad connection

			byte[] indirizzo  =  covertStringToByte(addr1);
			
			// make an unbound socket
			Socket theSock = new Socket();

			try
				{
					InetAddress addr = InetAddress.getByAddress(indirizzo);

					SocketAddress sockaddr = new InetSocketAddress(addr, port);

					// Create the socket with a timeout
					// when a timeout occurs, we will get timout exp.
					// also time our connection this gets very close to the real
					// time
					start = System.currentTimeMillis();
					theSock.connect(sockaddr, timeoutMs);
					end = System.currentTimeMillis();
				} catch (UnknownHostException e)
				{
					start = -1;
					end = -1;
				} catch (SocketTimeoutException e)
				{
					start = -1;
					end = -1;
				} catch (IOException e)
				{
					start = -1;
					end = -1;
				} finally
				{
					if (theSock != null)
						{
							try
								{
									theSock.close();
								} catch (IOException e)
								{
								}
						}

					if ((start != -1) && (end != -1))
						{
							total = end - start;
						}
				}

			return total; // returns -1 if timeout
		}
	
	/**
	 * @param address String IP Address
	 * @return IP sddress convetted in byte Array
	 */
	private static byte[] covertStringToByte(String address)
		{
			InetAddress addr = null;
			try
				{
					addr = InetAddress.getByName(address);
				} catch (UnknownHostException e)
				{
					e.printStackTrace();
				}
			byte[] bytes = addr.getAddress();
/*			for (byte b : bytes)
				{
					//System.out.println(b & 0xFF);
					// System.out.println(b);
				}*/

			return addr.getAddress();
		}

}
