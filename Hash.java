import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Hash <K,V> extends AbstractHashMap <K,V>{

	Scanner scn = new Scanner(System.in);	
	ArrayList<String> words = new ArrayList<String>();
	private UnsortedTableMap<K,V>[] table; 
	
	@SuppressWarnings("unchecked")
	public Hash() throws IOException
	{	
		while(true)
		{
			System.out.print("Search: ");
			String search = scn.nextLine();
			search((V) search);
		}
	}
	
	 @SuppressWarnings("unchecked")
	public V fileOperation() throws IOException 
	{
		FileReader fileReader = null;
     	BufferedReader buffReader = null;
			try {
				fileReader = new FileReader(new File("src/cartoon.txt"));
				buffReader = new BufferedReader(fileReader);
				String line ;
				while ((line = buffReader.readLine()) != null) {
					//System.out.println(line);
					 String [] tokens = line.split("\\s+");
					 for(int i = 0;i<tokens.length;i++)
					 {
						 words.add(tokens[i]);
					 }
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fileReader != null)
						fileReader.close();
					if (buffReader != null)
						buffReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return (V) words;
		}

	@SuppressWarnings("unchecked")
	public K Decimal(V value)
	{
		char[] letter = ((String) value).toCharArray();
		Integer decimal = 0;
		for(char i :letter)
		{
				decimal += i;	
		}
		decimal = decimal * letter[0];
		return (K) decimal;
	}
	
	@SuppressWarnings("unchecked")
	public K Binary(K Decimal)
	{		
		String  binary = "";
		Integer decimal = (Integer) Decimal;
		
		while(decimal > 0)
		{
			Integer i = decimal % 2;
			binary = binary + i;
			decimal = decimal / 2;	
		}
		String lastbit = binary.substring(binary.length()-localdepth);
		return (K) binary;  
	}

	
	public K Index(K Binary)
	{
		String index = (String) Binary;
		while(index.length() <32)
		{
			index = "0" + index;
		}
		return (K) index;
	}
	
	public int hash(V value)
	{
		Integer hash = 0;
		hash = (Integer) Index(Binary(Decimal(value)));
		return  hash;	
	}
	

	 public int count(V value)
		{
			Integer counter = 0;
			for(int i = 0;i<words.size();i++)
			{
				if(value.equals(words.get(i))) counter++;
			}
			return counter;
		}
	  
	
	@Override
	public Iterable<Entry<K, V>> entrySet() {
		 ArrayList<Entry<K,V>> buffer = new ArrayList<>();
	        for (int h=0; h < capacity; h++)
	            if (table[h] != null)
	                for (Entry<K,V> entry : table[h].entrySet())
	                    buffer.add(entry);
	        return buffer;
	}

	public boolean search(V value)
	{
		boolean flag = false ;
		for(int i = 0;i<words.size();i++)
		{
			if(words.get(i).equals((String)value))
			{
				System.out.print("Key:" + Decimal(value));
				System.out.print("Count:" + Binary(Decimal(value)));
				System.out.print("Index:" + Index(Binary(Decimal(value))));
				System.out.print("Global depth:" +globaldepth);
				System.out.print("Local depth"+localdepth);
				flag= true;
			}
			
			else
			{
				System.out.println("Not found.");
				flag = false;
			}
		}
		return flag;
		
		
		
	}
	@SuppressWarnings("unchecked")
	@Override
	protected void createTable() {
		table = (UnsortedTableMap<K,V>[]) new UnsortedTableMap[256];
	}

	@Override
	protected V bucketGet(int h, K k) {
		 UnsortedTableMap<K,V> bucket = table[h];
	        if (bucket == null) return null;
	        return bucket.get(k);
	}

	@Override
	protected V bucketPut(int h, K k, V v) {
		ArrayList<Entry<K,V>> bucket = new ArrayList<>(10);
		
		for(int i = 0;i<words.size();i++)
		{
			
			v = (V) words.get(i);
			k = Binary(Decimal(v));
			h = count(v);
			
			if(bucket.size() == 10)
			{
				if(globaldepth-localdepth > 0)
				{
					
				}
				else if(globaldepth-localdepth == 0)
				{
					//resize
				}	
			}		
		}
		return null;
	}

	@Override
	protected V bucketRemove(int h, K k) {
		
		return null;
	}
}