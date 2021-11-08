package com.appwelt.retailer.captain.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Class FileTools.java
 * @description Tools for files
 * @author S�bastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros    <gros.bertrand@gmail.com>
 * @version 2011-02-02
 */
public class FileTools {

	public static String DownloadFrom = "";
	public static boolean bUpdated = false;
	public static String DeviceID = "D00001";

	/**
	 * copie le fichier source dans le fichier resultat
	 * retourne vrai si cela r�ussit
	 */
	public static boolean copyFile(File source, File dest) {
		try {
			// Declaration et ouverture des flux
			FileInputStream sourceFile = new FileInputStream(source);

			try {
				FileOutputStream destinationFile = null;

				try {
					destinationFile = new FileOutputStream(dest);

					// Lecture par segment de 0.5Mo
					byte buffer[] = new byte[512 * 1024];
					int nbLecture;

					while ((nbLecture = sourceFile.read(buffer)) != -1) {
						destinationFile.write(buffer, 0, nbLecture);
					}
				} finally {
					destinationFile.close();
				}
			} finally {
				sourceFile.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false; // Erreur
		}

		return true; // R�sultat OK
	}



	/**
	 * D�place le fichier source dans le fichier r�sultat
	 */
	public static boolean moveFile(File source, File destination) {
		if (!destination.exists()) {
			// On essaye avec renameTo
			boolean result = source.renameTo(destination);
			if (!result) {
				// On essaye de copier
				result = true;
				result &= copyFile(source, destination);
				if (result) result &= source.delete();

			}
			return (result);
		} else {
			// Si le fichier destination existe, on annule ...
			return (false);
		}
	}

	public static boolean CreateZipFile(String FilePath, String FileName, String OutputFileName) {
		try {
			FileOutputStream fos = new FileOutputStream(FilePath+ "/" + OutputFileName);
			ZipOutputStream zipOS = new ZipOutputStream(fos);
			if (FileName.equals("*.*"))
			{
				File directoryPath = new File(FilePath);
				String filenames[] = directoryPath.list();
				for (int i=0; i< filenames.length; i++) {
					if (!filenames[i].equals("__MACOSX") && !filenames[i].contains(".zip"))
						writeToZipFile(FilePath + filenames[i], zipOS);
				}
			}
			else
				writeToZipFile(FilePath + FileName, zipOS);
			zipOS.close();
			fos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * Add a file into Zip archive in Java.
	 *
	 * @param path
	 * @param zipStream
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void writeToZipFile(String path, ZipOutputStream zipStream)
			throws FileNotFoundException, IOException {
		//System.out.println("Writing file : '" + path + "' to zip file");
		File aFile = new File(path);
		FileInputStream fis = new FileInputStream(aFile);
		String[] arrPath = path.split("/");
		ZipEntry zipEntry = new ZipEntry(arrPath[arrPath.length-1]);
		zipStream.putNextEntry(zipEntry);
		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zipStream.write(bytes, 0, length);
		}
		zipStream.closeEntry();
		fis.close();
	}


	public static void unzip(String zipFilePath, String destDir) {

		FileInputStream fis;
		//buffer for read and write data to file
		byte[] buffer = new byte[1024];
		try {
			fis = new FileInputStream(zipFilePath);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze = zis.getNextEntry();
			while(ze != null){
				String fileName = ze.getName();
				File newFile = new File(destDir + File.separator + fileName);
				//create directories for sub directories in zip
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			//close last ZipEntry
			zis.closeEntry();
			zis.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public  static String encryptString (String value){
		try {
			String xyz = "Arati";
			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method is called to calculate message digest
			//  of an input digest() return array of byte
			byte[] messageDigest = md.digest(xyz.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
            return hashtext;
		}catch (Exception e) {
			Log.i("FileTools", "ERROR>>Exception: "+e.getLocalizedMessage());
		}
		return  null;
	}


	/**
	 * This functions write the string message into command log file.
	 * @param context Context Application Context to open the external file folder.
	 * @param Source String To identify the communication Protocol
	 * @param Type String Command_Send, Command_Head received , Command_Data Received
	 * @param strMessage
	 */
	public static void writeCommandLog(Context context, String Source, String Type, String strMessage, String ActualData, boolean Format) {

		File logDirectory = new File(Environment.getExternalStorageDirectory().getPath() + "/" + Constants.FOLDER_NAME);
		File file = new File(logDirectory, Constants.LOG_FILE_NAME);

		if (!logDirectory.exists()) {
			logDirectory.mkdir();
		}
		if (!file.exists()) {
			try {
				Log.e("File created ", "File created.");
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {

			FileWriter out = new FileWriter(file, true);

			String timestamp = new SimpleDateFormat("yyyyMMddhhmmss.SSSZ-").format(Calendar.getInstance().getTime());

			out.write("\n \n" + timestamp + Source + "\n");

			if (Format == true)
				out.write(FormatLogMessage(strMessage, ActualData,  Type));
			else {
				out.write("\n" + Type);
				out.write(strMessage);
			}
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		file = null;
	}

	public static String FormatLogMessage(String strMessage, String ActualData, String sCmdSendingType)
	{
		String out = "";

		String Command = "";



		String dt = "";
		String tm = strMessage.substring(24,31);
		String timestamp = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

		out = out + "String: " + strMessage + "\n";
		out = out + "Date: " + timestamp + ", Time: ";

		for (int l=24; l < 31; l=l+2)
			out = out + String.valueOf(Integer.parseInt(strMessage.substring(l, l+2),16) ) + ":";

		return out;
	}

	/**
	 * Get local ip address of the phone
	 *
	 * @return ipAddress
	 */
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("ServerActivity", ex.toString());
		}
		return null;
	}


}