package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;

public class Sort {


	public void sorter(File f, boolean reversed_order) {
		try {
			ArrayList<String> rows = new ArrayList<String>();
			
			File input= new File("highscores\\input.txt");
			String s="";

			copyFile(f, input);	//copying all records to the new temporary file
			PrintWriter pw = new PrintWriter(f); //clearing the Highscores file
			pw.close();
			BufferedReader reader = new BufferedReader(new FileReader(input));
			while((s = reader.readLine())!=null)
				rows.add(s);		//reading records from the temporary file line by line
									//storing this line in collection

			Collections.sort(rows);	//sorting collection in proper order
			if(reversed_order) {
				Collections.reverse(rows);
			}
			//writing sorted records into Highscore file
			BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
			
			for(String cur: rows) {
				writer.write(cur);
				writer.newLine();
			}

			reader.close();
			writer.close();
			String s2=null;
			
			BufferedReader reader2 = new BufferedReader(new FileReader(f));
			while((s2=reader2.readLine()).isEmpty())
				rows.remove(s2);   

			reader2.close();
			BufferedWriter writer2 = new BufferedWriter(new FileWriter(f, false));
			for(String cur: rows) {
				writer2.write(cur);
				writer2.newLine();
			}
			writer2.close();
			input.delete(); //deleting temporary file
		} catch (IOException e) {
			
			e.printStackTrace();
		}


	}

	@SuppressWarnings("resource")
	private void copyFile(File sourceFile, File destFile) throws IOException {
		if(!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;

		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		}
		finally {
			if(source != null) {
				source.close();
			}
			if(destination != null) {
				destination.close();
			}
		}
	}

}
