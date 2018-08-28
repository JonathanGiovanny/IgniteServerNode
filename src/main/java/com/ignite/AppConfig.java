package com.ignite;

import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

import com.ignite.model.Student;

/**
 * https://apacheignite.readme.io/docs/getting-started
 * 
 * @author jcamargos
 *
 */
public class AppConfig {

	public static void main(String[] args) {
		IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
		// The client node will search for the server node to make the operations
		igniteConfiguration.setClientMode(false);

		// Full details of CacheConfiguration:
		// https://ignite.apache.org/releases/latest/javadoc/org/apache/ignite/configuration/CacheConfiguration.html
		CacheConfiguration<Long, Student> cacheConfig = new CacheConfiguration<>();
		cacheConfig.setName("StudentCache");
		// Full details of ATOMIC:
		// https://www.gridgain.com/sdk/pe/latest/javadoc/org/apache/ignite/cache/CacheAtomicityMode.html
		cacheConfig.setAtomicityMode(CacheAtomicityMode.ATOMIC);
		// Sets number of nodes used to back up single partition for
		// CacheMode.PARTITIONED cache
		cacheConfig.setBackups(1);

		igniteConfiguration.setCacheConfiguration(cacheConfig);

		// https://apacheignite.readme.io/docs/tcpip-discovery
		TcpDiscoverySpi spi = new TcpDiscoverySpi();
		TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
		// Multicast: |1 1 1 0|  MULTICAST Address   |     Range: 224.0.0.0 - 239.255.255.255
		ipFinder.setMulticastGroup("228.10.10.157");
		spi.setIpFinder(ipFinder);

		// Override default discovery SPI.
		igniteConfiguration.setDiscoverySpi(spi);
		// Start Ignite node.
		Ignition.start(igniteConfiguration);
	}

}