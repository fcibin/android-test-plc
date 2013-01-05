package com.mqf.utility;

import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Classe per validare i'indirizzo IP
 */
public class ValidateIP
{

	String IP = null;

	public String getIP()
	{
		return IP;
	}

	/**
	 * @param IP Validate IP address require String
	 * @return return true il address is Valid
	 */
	public boolean validate(String IP)
	{
		this.IP = IP;
		StringTokenizer st = new StringTokenizer(IP, ".");

		// create boolean arrays
		boolean[] blnArray1 = new boolean[]
		{ false, false, false, false };
		boolean[] blnArray2 = new boolean[]
		{ true, true, true, true };// Matrice per il confronto
		boolean blnResult = false;

		int i = 0;
		while (st.hasMoreTokens())
		{
			int valid = checkRange(st.nextToken());
			if (valid == 0)
			{
				System.out.println("Token " + i + "is not valid");
				blnArray1[i] = false;
			}
			else
			{
				System.out.println("Token " + i + "is valid");
				blnArray1[i] = true;
			}
			i++;
		}

		blnResult = Arrays.equals(blnArray1, blnArray2);
		return blnResult;
	}

	private int checkRange(String tok)
	{
		int n = Integer.parseInt(tok);
		if (n >= 0 && n <= 255)
		{
			return 1;
		}
		return 0;
	}
}
