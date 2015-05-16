package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;
import com.google.common.hash.Hashing;

public class ConsistentHashService  {
		
	public static void execute(){
		System.out.println("Starting Cache Client...");
    	char[] value = {
                '0', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'
            };
            List<DistributedCacheService> Clusters = new ArrayList < DistributedCacheService > ();
            // Adding three servers
            DistributedCacheService cacheA = new DistributedCacheService(
                    "http://localhost:3000");
            DistributedCacheService cacheB = new DistributedCacheService(
                    "http://localhost:3001");
            DistributedCacheService cacheC = new DistributedCacheService(
                    "http://localhost:3002");
         
            Clusters.add(cacheA);
            Clusters.add(cacheB);
            Clusters.add(cacheC);
           
            System.out.println("---- Inserting ----");
            for (int putkey = 1; putkey <= 10; putkey++) {
                int key = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(putkey)), Clusters.size());
                //System.out.println(key);
                Clusters.get(key).put(putkey, Character.toString(value[putkey]));
                System.out.println("The key value pair " + putkey + "=>" + value[putkey] + " is assigned to server " + key);
            }
            System.out.println("---- Retrieving ----");
            for (int getkey = 1; getkey <= 10; getkey++) {
                int key2 = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(getkey)), Clusters.size());
                System.out.println("The key value pair " + getkey + "=>" + Clusters.get(key2).get(getkey) + " is in server " + key2);

            }
            
            System.out.println("Existing Cache Client...");
	}
}

