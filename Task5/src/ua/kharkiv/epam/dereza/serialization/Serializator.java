package ua.kharkiv.epam.dereza.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Utility class for serialization
 * 
 * @author Eduard_Dereza
 *
 */
public class Serializator {
		
	/**
	 * 
	 * @param objForSrl
	 * @param path
	 * @return file that contains serialized file
	 * @throws IOException
	 */
	public static File serialize(Object objForSrl, String path) throws IOException{
		File outPutFile = new File(path);
		ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(outPutFile));
		objOut.writeObject(objForSrl);
		
		objOut.close();
		return outPutFile;
	}
	
	/**
	 * 
	 * 
	 * @param objForSrl
	 * @param path
	 * @param countTimes
	 * @return file that contains serialized file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static File serializeAndWriteObjectNTimes(Object objForSrl, String path, int countTimes) throws FileNotFoundException, IOException{
		File outPutFile = new File(path);
		ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(outPutFile));
		
		for (int i = 0; i < countTimes; i++){
			objOut.writeObject(objForSrl);
			System.out.println("Size of the file :" + outPutFile.length() + "bytes");
		}
		
		objOut.close();
		return outPutFile;
	}
	
	/**
	 * 
	 * 
	 * @param path
	 * @return deserialized Object
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deserializeObject(String path) throws FileNotFoundException, IOException, ClassNotFoundException{
		File outPutFile = new File(path);
		ObjectInputStream objInp = new ObjectInputStream(new FileInputStream(outPutFile));
		
		Object obj = objInp.readObject();
		objInp.close();
		return obj;
	}
	
	/**
	 * 
	 * @param fromPath
	 * @param toPath
	 * @return compressed file
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static File compressFile(String fromPath, String toPath) throws IOException, ClassNotFoundException{
		File inputFile = new File(fromPath);
		File outPutFile = new File(toPath);
		
		ObjectInputStream objInp = new ObjectInputStream(new FileInputStream(inputFile));
		ObjectOutputStream objOut = new ObjectOutputStream(
				new GZIPOutputStream(new FileOutputStream(outPutFile)));
		
		Object readObj = objInp.readObject();
		objOut.writeObject(readObj);
		
		System.out.println("File before compressing :" + inputFile.length() + "bytes");
		System.out.println("File after compressing :" + outPutFile.length() + "bytes");
		
		objInp.close();
		objOut.close();
		return outPutFile;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		ListImpl<NetworkEquipment> list = new ListImpl<NetworkEquipment>();
		list.add(new Router("wr643", 0.7, 11, new BigDecimal(200), "a/b/g/n", "noOS"));
		list.add(new Router("wr642", 0.3, 8, new BigDecimal(230), "b/g/n", "noOS"));
		list.add(new Router("wr644", 0.2, 5, new BigDecimal(259), "a/b/g/n", "noOS"));
		list.add(new Router("wr645", 1.5, 1, new BigDecimal(320), "a/b/g/n", "noOS"));
		list.add(new Router("wr647", 2.3, 1, new BigDecimal(546), "a/b/g/n", "noOS"));
		list.add(new Router("wr646", 0.1, 4, new BigDecimal(870), "g/n/a/c", "noOS"));
		list.add(new Router("wr649", 0.6, 8, new BigDecimal(211), "g/n",	"noOS"));
		list.add(new Router("wr648", 0.4, 16, new BigDecimal(328), "a/b/g", "noOS"));
		list.add(new Router("wr650", 1.7, 12, new BigDecimal(832), "a/b/n", "noOS"));
		list.add(new WirelessRouter("wr651", 1.7, 12, new BigDecimal(1262), "a/b/n", "noOS", true));
		list.add(new WirelessRouter("wr652", 2.4, 8, new BigDecimal(1391), "a/b/n", "noOS", true));
		
		System.out.println("\n---Serialize and write Object to file 200 times---");
		Serializator.serializeAndWriteObjectNTimes(list, "D:/container.dat", 200);
		
		System.out.println("\n---Serialize and write Object to file---");
		Serializator.serialize(list, "D:/container.dat");
		
		System.out.println("\n---Compress file---");
		Serializator.compressFile("D:/container.dat", "D:/container.gz");
		
		System.out.println("\n---Compare serialized and deserialized---");
		ListImpl<NetworkEquipment> listAfterDeser = (ListImpl<NetworkEquipment>) Serializator
				.deserializeObject("D:/container.dat");
		System.out.println("list == listAfterDeser :" + (list == listAfterDeser));
		System.out.println("list equal listAfterDeser :" + (list.equals(listAfterDeser)));
		
		System.out.println("\n---Decompress---");
		FileInputStream fin = new FileInputStream("D:/container.gz");
		GZIPInputStream gis = new GZIPInputStream(fin);
		ObjectInputStream ois = new ObjectInputStream(gis);
		listAfterDeser = (ListImpl<NetworkEquipment>)ois.readObject();   
		System.out.println(listAfterDeser);		
	}
}
