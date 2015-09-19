package io;


import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Class MyDecompressorInputStream that extends InputStream
 */
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
	
	public int read(byte[] arr) throws IOException {
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
		
		for (Byte b : tempList)
		{
			arr[cnt]=b.byteValue();
			cnt++;
		}
		return 0;
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
	

}
