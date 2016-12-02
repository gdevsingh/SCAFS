import java.net.*;
import java.io.*;
import javax.crypto.spec.SecretKeySpec ;
import javax.crypto.Cipher ;
import javax.crypto.spec.IvParameterSpec ;

/**
	*This class is responsible for Sending Files to Receivers and storing them in directory FilesReceived
	*@author Gurkeerat Singh
	*		Gurdev Singh
	*@version 0.4 20-10-16
	*@see http://stackoverflow.com/questions/4687615/how-to-achieve-transfer-file-between-client-and-server-using-java-socket
	*@see http://docs.oracle.com/javase/tutorial/networking/sockets/
	*/
public class TCPSender 
{
	FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;
	//public static final int FILE_SIZE = Integer.MAX_VALUE;
	/**
	*This method is sending a specified file to specified IPAddress in the network
	*@param IPAdd IPAddress of the peer who shall receive the file
	*@param fileName name of the file to send
	*/
	public void sendAFile(String IPAdd, String fileName) 
	{
		
 	   {
			System.out.println("--sending file--");
			String x="";
		
            try
            {
				//Connecting to the server socket in TCPReceiver
				
				
				// create blank file
                File myFile = new File (fileName); // throws FileNotFoundException
				if(myFile.exists())
				{

				Socket sock = new Socket(IPAdd,4001);
				System.out.println("Accepted connection : " + sock);
                byte [] mybytearray  = new byte [(int)myFile.length()];
                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(fis);
                bis.read(mybytearray,0,mybytearray.length);
                os = sock.getOutputStream();
                System.out.println("Sending " + fileName + "(" + mybytearray.length + " bytes)");
                //writing file bytes to output stream
                os.write(mybytearray,0,mybytearray.length);
                os.flush();
                System.out.println("Sent.");
                sock.close();
            	}
            	else
            	{
            		System.out.println("no such file exists!");
            	}
				
				//System.out.println("socket closed");
            }
            catch (UnknownHostException he){System.out.println();}
            catch (IOException ioe){System.out.println();}
			finally
			{
				try{fis=null;bis=null;sock.close();}catch(Exception e){System.out.println();}
			}
        
		}
	}


}
