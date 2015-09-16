package io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


public class MyDecompressorInputStream extends InputStream {

	private InputStream in;
	
	/**
	 * Ctor for the class MyDecompressorInputStream
	 * @param in InputStream param that we will use
	 */
	public MyDecompressorInputStream(InputStream in) {
		this.in = in;
	}
	
	/**
	 * Override for the read method from InputStream
	 *  @param arg0 
	 *  @throws IOException
	 */
	@Override
	public int read() throws IOException {
		
		return in.read();
	}
	
	public byte[] readArr() throws IOException {
		List<Byte> tempList = new ArrayList<Byte>();
		
		int cnt=0;
		byte tempZeroOne;
		int tempSeq;
		for (int i=0; i<36; i++) // get the size of the maze (x,y,z) and the entrance and goal positions
		{
			tempList.add((byte) read());
		}
		while ((tempZeroOne=(byte) read()) != -1) // get the Compressed maze and decompress it
		{
			tempSeq=(byte) read();
			for (int i=0; i<tempSeq; i++)
			{
				tempList.add(tempZeroOne);
			}
		}
		
		byte[] byteArr = new byte[tempList.size()];
		for (Byte b : tempList)
		{
			byteArr[cnt]=b.byteValue();
			cnt++;
		}
		return byteArr;
	}

	
	/**
	 * method that convert an integer to a array of 4 bytes
	 * @param num the number we want to convert
	 * @return array of 4 bytes represent the int
	 */
	public byte[] convertIntToByte(int num)
	{

	    return ByteBuffer.allocate(4).putInt(num).array();
	}
	
	public static void main(String[] args) {
		MyDecompressorInputStream m;
		try {
			m = new MyDecompressorInputStream(new FileInputStream("1.maz"));
			try {
				byte[] byteArr = m.readArr();
				for (byte b : byteArr)
				{
					System.out.println(b);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				m.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
