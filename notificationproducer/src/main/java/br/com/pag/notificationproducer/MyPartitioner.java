package br.com.pag.notificationproducer;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class MyPartitioner implements Partitioner 	{

	private static Integer value = 0;
	
	@Override
	public void configure(Map<String, ?> configs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		// TODO Auto-generated method stub
		this.value++;
		System.out.println("@@@"+(this.value % 2));
		return (this.value % 2);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
