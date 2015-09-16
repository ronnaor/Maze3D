package io;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class MyCompressorOutputStream extends OutputStream {

	private OutputStream out;
	
	
	/**
	 * Ctor for the class MyCompressorOutputStream
	 * @param out OutputStream param that we will use
	 */
	public MyCompressorOutputStream(OutputStream out) {
		this.out = out;
	}
	/**
	 * Override for the write method from OutputStream
	 *  @param arg0 
	 *  @throws IOException
	 */
	@Override 
	public void write(int arg0) throws IOException {
		out.write(arg0);

	}

	/**
	 * write method that gets array of bytes
	 * @param byteArr array of bytes
	 * @throws IOException
	 */
	public void write(byte[] byteArr) throws IOException {
		ByteBuffer buf = ByteBuffer.wrap(byteArr,0,byteArr.length);
		int temp;
		int tempZeroOne;
		int cnt;
		for (int i=0; i<9; i++) // get the size of the maze (x,y,z) and the entrance and goal positions and use write(int arg0) method
		{
			temp = buf.getInt();
			byte[] TempArr = new byte[4];
			TempArr =convertIntToByte(temp);
			for (int j=0; j<4;j++)
			{
				write(TempArr[j]);

			}
			
		}
		
		tempZeroOne= buf.get();
		cnt = 1;
		
		while (buf.hasRemaining()) // get the maze and Compress it and than use the write(int arg0) method
		{
			temp = buf.get();
			if (temp==tempZeroOne)
			{
				cnt++;
			}
			else
			{
				write(tempZeroOne);
				write(cnt);
				tempZeroOne=temp;
				cnt=1;
			}
		}
		
		write(tempZeroOne);
		write(cnt);
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
