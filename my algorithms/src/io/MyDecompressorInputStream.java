package io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


public class MyDecompressorInputStream extends InputStream {

	private InputStream in;
	public MyDecompressorInputStream() {

	}
	
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
		// TODO Auto-generated method stub
		return 0;
	}
	
	public byte[] readArr(byte[] arr) throws IOException {
		List<Byte> tempList = new ArrayList<Byte>();
		ByteBuffer buf = ByteBuffer.wrap(arr,0,arr.length);
		int cnt=0;
		byte tempZeroOne;
		int tempSeq;
		for (int i=0; i<36; i++) // get the size of the maze (x,y,z) and the entrance and goal positions
		{
			tempList.add(buf.get());
		}
		while (buf.hasRemaining()) // get the Compressed maze and decompress it
		{
			tempZeroOne=buf.get();
			tempSeq=buf.get();
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
		MyDecompressorInputStream m = new MyDecompressorInputStream();
		byte[] bb = new byte[42];
		byte[] bbb = new byte[4];
		for(int j=0;j<9;j++)
		{
			int temp=0;
			bbb =m.convertIntToByte(300);
			for (int i=j*4; i<((j*4)+4);i++)
			{
				bb[i]=bbb[temp];
				temp++;
			}
		}
		bb[36]=0;
		bb[37]=4;
		bb[38]=1;
		bb[39]=3;
		bb[40]=0;
		bb[41]=5;
		System.out.println("------------------------------------------------");
		try {
			byte[] t=m.readArr(bb);
			for(byte a: t)
			{
				System.out.println(a);
			}
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
		try {
			m.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
}
