package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;

public class Client {
	
	private static HashMap<String,String> values = new HashMap<String,String>();
	static List<String> servers = new ArrayList<String>();
	private static final Funnel<CharSequence> defaultCharset = Funnels.stringFunnel();
	
	public static void main(String[] args) throws Exception {
		//--For consistent hashing--
		ConsistentHashService.execute();
		
		//--For Rendezvous Hashing--
		/*values.put("1","a");
		values.put("2","b");
		values.put("3","c");
		values.put("4","d");
		values.put("5","e");
		values.put("6","f");
		values.put("7","g");
		values.put("8","h");
		values.put("9","i");
		values.put("10","j");
		
		servers.add("http://localhost:3000");
		servers.add("http://localhost:3001");
		servers.add("http://localhost:3002");
		
		System.out.println("Starting Cache Client...");
		
		rendezvousHash();
		System.out.println("Exiting Cache Client...");*/
	}

	public static void rendezvousHash(){
		RendezvousHash<String, String> rendezvousHash = new RendezvousHash(Hashing.murmur3_128(), defaultCharset, defaultCharset, servers);
        System.out.println("---- Inserting ----");
		for(Map.Entry<String,String> entry : values.entrySet()){ // iterate through hashmap
			//System.out.println(entry.getKey() + " " + entry.getValue());
			String node = rendezvousHash.get(entry.getKey());
			DistributedCacheService cache = new DistributedCacheService(node);
			cache.put(Long.parseLong(entry.getKey()), entry.getValue());
			System.out.println("The key value pair " + entry.getKey() + "==>" + entry.getValue() + " is assigned to " + node);
		}
        System.out.println("---- Retrieving ----");
		for(Map.Entry<String,String> entry : values.entrySet()){ // iterate through hashmap
			String node = rendezvousHash.get(entry.getKey());
			DistributedCacheService cache = new DistributedCacheService(node);
			cache.get(Long.parseLong(entry.getKey()));
			System.out.println("The key value pair " + entry.getKey() + "==>" + entry.getValue() + " is in server " +node );
		}
	}
}


