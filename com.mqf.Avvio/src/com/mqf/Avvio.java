/*
  15-04-2011
 by Marco Quaglia Faccio (marcoqf73@gmail.com) 
 
 Thanks to Thomas Hergenhahn (thomas.hergenhahn@web.de) 
 for the wonderful Libnodave.
 
 android-ibnodave is free software; you can redistribute it and/or modify
 it under the terms of the GNU Library General Public License as published by
 the Free Software Foundation; either version 2, or (at your option)
 any later version.

 Libnodave is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU Library General Public License
 along with this; see the file COPYING.  If not, write to
 the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.  
 */

package com.mqf;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.mqf.utility.*;

import org.libnodave.*;

public class Avvio extends Activity
{
	private final static String newline = "\n";
	public final static String PlcIpAddress = "192.168.1.100";
	Button btnConnectRetreive, btnDelete;
	TextView lblPlcAddress, txtRisultato, lblStatus, txtStatus;
	TestISOTCP tp;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			settaLayout();
			gestiscoEventi();

			validaIndirizzoIp();
		}

	private void settaLayout()
		{
			lblPlcAddress = (TextView) findViewById(R.id.lblPlcAddress);
			btnConnectRetreive = (Button) findViewById(R.id.btnConnectRetreive);
			btnDelete = (Button) findViewById(R.id.btnDelete);
			lblStatus = (TextView) findViewById(R.id.lblStatus);
			txtStatus = (TextView) findViewById(R.id.txtStatus);
			txtRisultato = (TextView) findViewById(R.id.txtRisultato);

		}

	private void validaIndirizzoIp()
		{
			ValidateIP validaIp = new ValidateIP();
			if (validaIp.validate(PlcIpAddress))
				{
					btnConnectRetreive.setEnabled(true);
					lblPlcAddress.setText("PLC Address: " + PlcIpAddress);
				} else
				{
					btnConnectRetreive.setEnabled(false);
				}
		}

	private void gestiscoEventi()
		{
			// Gestico Evento per il Bottone Collega e Leggi
			// I handle event for the button Connect Retrive
			btnConnectRetreive.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
					{
						PlcState.ErrPlc = false;    
						long timeOut;

						timeOut = network.testConnection("192.168.1.100", 102,
								20000);

						if (timeOut > -1)
							{// connection is alive
								PlcState.ErrPlc = false;
								// System.out.println("time " +
								// String.valueOf(timeOut));
								txtStatus.setText("OK " + "timeout: "
										+ String.valueOf(timeOut));
								// Now re try to read and write
								readSomeData();
							} else
							{// connection is not alive
								PlcState.ErrPlc = true;
								txtStatus.setText("!!! " + "timeout: "
										+ String.valueOf(timeOut));
							}
					}
			});

			// Gestisco Evento per cancellare i dati letti
			// I handle event for the button delete result
			btnDelete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
					{
						txtRisultato.setText("");
					}
			});

		}

	private void readSomeData()
		{
			try
				{
					TestISOTCP.prepara();
					txtRisultato.append("=======================" + newline);
					txtRisultato.append("media: " + DB1.media
							+ newline);
					txtRisultato.append("T1: " + DB1.T1
							+ newline);
					txtRisultato.append("T2: " + DB1.T2
							+ newline);
					txtRisultato.append("T3: " + DB1.T3
							+ newline);
					txtRisultato.append("T4: " + DB1.T4
							+ newline);
					txtRisultato.append("T5: " + DB1.T5
							+ newline);
				} catch (Exception e)
				{
					PlcState.ErrPlc = true;
					Toast.makeText(this, "Errore" + e, Toast.LENGTH_SHORT)
							.show();

				}
		}
}