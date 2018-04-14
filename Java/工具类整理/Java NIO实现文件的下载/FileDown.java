package com.anssy.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mr.薛
 * Java NIO实现文件的下载
 */
public class FileDown {

	
	private void output(HttpServletResponse response, String filePathAndFileName, String mimeType)
    throws IOException {
	
	  File file = new File(filePathAndFileName);
	
	  // set response headers
	  response.setContentType((mimeType != null) ? mimeType : "application/octet-stream");
	  response.setContentLength((int) file.length());
	
	  // read and write file
	  ServletOutputStream op = response.getOutputStream();
	  // 128 KB buffer
	  int bufferSize = 131072;
	  FileInputStream fileInputStream = new FileInputStream(file);
	  FileChannel fileChannel = fileInputStream.getChannel();
	  // 6x128 KB = 768KB byte buffer
	  ByteBuffer bb = ByteBuffer.allocateDirect(786432);
	  byte[] barray = new byte[bufferSize];
	  int nRead, nGet;
	
	  try {
	    while ((nRead = fileChannel.read(bb)) != -1) {
	      if (nRead == 0)
	        continue;
	      bb.position(0);
	      bb.limit(nRead);
	      while (bb.hasRemaining()) {
	        nGet = Math.min(bb.remaining(), bufferSize);
	        // read bytes from disk
	        bb.get(barray, 0, nGet);
	        // write bytes to output
	        op.write(barray);
	      }
	      bb.clear();
	    }
	  } catch (IOException e) {
	    e.printStackTrace();
	  } finally {
	    bb.clear();
	    fileChannel.close();
	    fileInputStream.close();
	  }
	}
	
}
