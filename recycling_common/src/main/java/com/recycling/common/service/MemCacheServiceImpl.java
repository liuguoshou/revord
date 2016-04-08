package com.recycling.common.service;

import com.recycling.common.config.BeanContext;
import com.recycling.common.config.MutilPropertyPlaceholderConfigurer;
import com.recycling.common.util.PropertiesReader;

import net.spy.memcached.*;
import net.spy.memcached.transcoders.SerializingTranscoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 
 * @author zb.liu
 * 
 */
public class MemCacheServiceImpl implements MemCacheService {
	/**
	 * 涓�簺甯搁噺
	 */
	public static final String CACHE_PROP_FILE = "memcache";

	public static final String ENCODING = "UTF-8";

	// 鏃ュ織
	private static Log log = LogFactory.getLog(MemCacheServiceImpl.class);

	// 杩斿洖鐨勫疄渚�
	// private static FcCacheService instance = new FcCacheServiceImpl();

	// MemcachedClient 涓ょ粍瀹归敊
	private static MemcachedClient mc1 = null;

	private static MemcachedClient mc2 = null;

	/**
	 * Default operation timeout in seconds.
	 */
	public static final int DEFAULT_MEMCACHED_TIMEOUT = 1;

	private static int opTimeout = DEFAULT_MEMCACHED_TIMEOUT;

	/**
	 * Default operation timeout in seconds.
	 */
	public static final int DEFAULT_MEMCACHED_TIMEOUT_BATCH = 3;

	private static int opTimeoutBulk = DEFAULT_MEMCACHED_TIMEOUT_BATCH;

	// 璇籦uffer闀垮害
	public static final int DEFAULT_READBUF_SIZE = 16384;
	private static int readBufSize = DEFAULT_READBUF_SIZE;

	// 鎿嶄綔闃熷垪闀垮害
	public static final int DEFAULT_OPQ_SIZE = 16384;
	private static int expHour = 24;
	private static int opQueueLen = 3;

	// 杩囨湡鏃堕棿,榛樿瀹氫负24灏忔椂
	public static final int DEFAULT_MEMCACHED_EXP_HOURS = 24;

	// 閲嶈瘯娆℃暟
	public static final int DEFAULT_MEMCACHED_RETRY = 3;
	private static int retry = DEFAULT_MEMCACHED_RETRY;

	private static final String SERVER_1 = "server1";
	private static final String SERVER_2 = "server2";
	private static final String OP_TIMEOUT = "opTimeout";
	private static final String OP_TIMEBULK = "opTimeoutBulk";
	private static final String RETRY_TIMES = "retry";
	private static final String READ_BUFFER_SIZE = "readBufSize";
	private static final String OP_QUEUE_LEN = "opQueueLen";
	private static final String EXP_HOUR = "expHour";

	/**
	 * 鑾峰彇SessionService瀹炰緥
	 * 
	 * @return 涓�釜瀹炰緥
	 * @throws java.io.IOException
	 */
	public static MemCacheService getInstance(String prop_file) {
		// ------------瑁呰浇memchache淇℃伅-----------------------
		String server1 = PropertiesReader.getValue(prop_file, "remove_cache_server1");
		String server2 = PropertiesReader.getValue(prop_file, "remove_cache_server1");
		try {
			opTimeout = Integer.parseInt(PropertiesReader.getValue(prop_file,
					"opTimeout"));
			opTimeoutBulk = Integer.parseInt(PropertiesReader.getValue(
					prop_file, "opTimeoutBulk"));
			retry = Integer.parseInt(PropertiesReader.getValue(prop_file,
					"retry"));
			readBufSize = Integer.parseInt(PropertiesReader.getValue(prop_file,
					"readBufSize"));
			opQueueLen = Integer.parseInt(PropertiesReader.getValue(prop_file,
					"opQueueLen"));
			expHour = Integer.parseInt(PropertiesReader.getValue(prop_file,
					"expHour"));
		} catch (Exception e) {
			log.error("loading properties fail, use default config!");
		}
		// 浠庨厤缃枃浠朵腑璇诲彇鐩稿簲鐨勯厤缃俊鎭�
		try {
			mc1 = new MemcachedClient(new DefaultConnectionFactory() {

				@Override
				public HashAlgorithm getHashAlg() {
					return HashAlgorithm.CRC32_HASH;
				}

				@Override
				public long getOperationTimeout() {
					return opTimeout * 1000;
				}

				@Override
				public int getReadBufSize() {
					return readBufSize;
				}

				@Override
				public OperationFactory getOperationFactory() {
					return super.getOperationFactory();
				}

				@Override
				public int getOpQueueLen() {
					return opQueueLen;
				}

				@Override
				public boolean isDaemon() {
					return true;
				}

			}, AddrUtil.getAddresses(server1));

			mc2 = new MemcachedClient(new DefaultConnectionFactory() {

				@Override
				public HashAlgorithm getHashAlg() {
					return HashAlgorithm.CRC32_HASH;
				}

				@Override
				public long getOperationTimeout() {
					return opTimeout * 1000;
				}

				@Override
				public int getReadBufSize() {
					return readBufSize;
				}

				@Override
				public OperationFactory getOperationFactory() {
					return super.getOperationFactory();
				}

				@Override
				public int getOpQueueLen() {
					return opQueueLen;
				}

				@Override
				public boolean isDaemon() {
					return true;
				}

			}, AddrUtil.getAddresses(server2));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 浣跨敤gb2312缂栫爜
		SerializingTranscoder x1 = (SerializingTranscoder) mc1.getTranscoder();
		x1.setCharset(ENCODING);
		SerializingTranscoder x2 = (SerializingTranscoder) mc2.getTranscoder();
		x2.setCharset(ENCODING);
		return mc;
	}

	private static MemCacheServiceImpl mc = null;

	public static MemCacheService getInstance() {
		if (mc == null) {
			mc = new MemCacheServiceImpl();
		}
		if (mc.mc1 == null || mc.mc2 == null) {
			mc = new MemCacheServiceImpl();
		}
		return mc;
	}

	/**
	 * 绉佹湁鏋勯�鏂规硶,鍒濆鍖杕emcached
	 *
	 * @throws java.io.IOException
	 *
	 */
	private MemCacheServiceImpl() {

		// ------------瑁呰浇memchache淇℃伅-----------------------
		MutilPropertyPlaceholderConfigurer propertyConfigurer = (MutilPropertyPlaceholderConfigurer) BeanContext
				.getBean("propertyConfigurer");
		if (propertyConfigurer == null)
			return;

		String server1 = propertyConfigurer.getProperty(SERVER_1);
		String server2 = propertyConfigurer.getProperty(SERVER_2);
		try {
			opTimeout = Integer.parseInt(propertyConfigurer
					.getProperty(OP_TIMEOUT));
			opTimeoutBulk = Integer.parseInt(propertyConfigurer
					.getProperty(OP_TIMEBULK));
			retry = Integer.parseInt(propertyConfigurer
					.getProperty(RETRY_TIMES));
			readBufSize = Integer.parseInt(propertyConfigurer
					.getProperty(READ_BUFFER_SIZE));
			opQueueLen = Integer.parseInt(propertyConfigurer
					.getProperty(OP_QUEUE_LEN));
			expHour = Integer
					.parseInt(propertyConfigurer.getProperty(EXP_HOUR));
		} catch (Exception e) {
			log.error("loading properties fail, use default config!");
		}
		// 浠庨厤缃枃浠朵腑璇诲彇鐩稿簲鐨勯厤缃俊鎭�
		try {
			mc1 = new MemcachedClient(new DefaultConnectionFactory() {

				@Override
				public HashAlgorithm getHashAlg() {
					return HashAlgorithm.CRC32_HASH;
				}

				@Override
				public long getOperationTimeout() {
					return opTimeout * 1000;
				}

				@Override
				public int getReadBufSize() {
					return readBufSize;
				}

				@Override
				public OperationFactory getOperationFactory() {
					return super.getOperationFactory();
				}

				@Override
				public int getOpQueueLen() {
					return opQueueLen;
				}

				@Override
				public boolean isDaemon() {
					return true;
				}

			}, AddrUtil.getAddresses(server1));

			mc2 = new MemcachedClient(new DefaultConnectionFactory() {

				@Override
				public HashAlgorithm getHashAlg() {
					return HashAlgorithm.CRC32_HASH;
				}

				@Override
				public long getOperationTimeout() {
					return opTimeout * 1000;
				}

				@Override
				public int getReadBufSize() {
					return readBufSize;
				}

				@Override
				public OperationFactory getOperationFactory() {
					return super.getOperationFactory();
				}

				@Override
				public int getOpQueueLen() {
					return opQueueLen;
				}

				@Override
				public boolean isDaemon() {
					return true;
				}

			}, AddrUtil.getAddresses(server2));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 浣跨敤gb2312缂栫爜
		SerializingTranscoder x1 = (SerializingTranscoder) mc1.getTranscoder();
		x1.setCharset(ENCODING);
		SerializingTranscoder x2 = (SerializingTranscoder) mc2.getTranscoder();
		x2.setCharset(ENCODING);
	}

	/**
	 * 鑾峰彇涓�釜瀵硅薄(鍚噸璇曟満鍒�
	 *
	 * @param key
	 * @return piggie 2009-10-16 version 2.2.1
	 */
	@Override
	public Object get(String key) {
		Object result = null;
		try {
			for (int i = 0; i < retry; i++) {
				result = _get(key);
				if (result == null) {
					// log.info("get info from cache failed begin to retry " +
					// i);
				} else {
					break;
				}
			}
			if (result == null) {
				// log.info("[FAIL] completely failed when getting info from cache after "
				// + retry + " times");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * 鑾峰彇涓�釜瀵硅薄
	 *
	 * @throws java.util.concurrent.ExecutionException
	 * @throws InterruptedException
	 */
	private Object _get(String key) {
		// TODO Auto-generated method stub
		// log.info("[ACCESS] begin to get info from cache...");
		Object myObj = null;
		if (mc1 == null || mc2 == null) {
			mc = (MemCacheServiceImpl) MemCacheServiceImpl.getInstance();
			mc1 = mc.mc1;
			mc2 = mc.mc2;
		}
		try {
			Future<Object> f = mc1.asyncGet(key);
			try {
				myObj = f.get(opTimeout, TimeUnit.SECONDS);
			} catch (TimeoutException e) {
				e.printStackTrace();
				f.cancel(false);
			} catch (InterruptedException e) {
				e.printStackTrace();
				f.cancel(false);
			} catch (ExecutionException e) {
				e.printStackTrace();
				f.cancel(false);
			}

			if (myObj == null) {
				Future<Object> f2 = mc2.asyncGet(key);
				try {
					myObj = f2.get(opTimeout, TimeUnit.SECONDS);
				} catch (TimeoutException e) {
					e.printStackTrace();
					f2.cancel(false);
				} catch (InterruptedException e) {
					e.printStackTrace();
					f2.cancel(false);
				} catch (ExecutionException e) {
					e.printStackTrace();
					f2.cancel(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myObj != null) {
			// log.info("MemCacheServiceImpl._get,key=" + key + ",object="
			// + myObj.getClass());
		} else {
			// log.info("MemCacheServiceImpl._get,key=" + key + ",object=null");
		}
		return myObj;

	}

	/**
	 * 鑾峰彇涓�壒瀵硅薄
	 */
	@Override
	public Map<String, Object> getBulk(Set<String> keys) {
		// TODO Auto-generated method stub
		log.info("[ACCESS]begin to get info from cache in bulk...");
		Map<String, Object> ret = null;

		try {
			Future<Map<String, Object>> f = mc1.asyncGetBulk(keys);
			try {
				ret = f.get(opTimeoutBulk, TimeUnit.SECONDS);
			} catch (TimeoutException e) {
				// Since we don't need this, go ahead and cancel the operation.
				// This
				// is not strictly necessary, but it'll save some work on the
				// server.
				log.info("[FAIL]time out when getting objects from cache server1...");
				f.cancel(false);
				// Do other timeout related stuff
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.info("[FAIL]thread been interrupted while waiting when getting object from cache server1...");
				f.cancel(false);
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				log.info("[FAIL]exception when getting object from cache server1...");
				f.cancel(false);
			}

			if (ret == null) {
				Future<Map<String, Object>> f2 = mc2.asyncGetBulk(keys);
				try {
					ret = f2.get(opTimeoutBulk, TimeUnit.SECONDS);
				} catch (TimeoutException e) {
					// Since we don't need this, go ahead and cancel the
					// operation. This
					// is not strictly necessary, but it'll save some work on
					// the server.
					log.info("[FAIL]time out when getting objects from cache server2...");
					f2.cancel(false);
					// Do other timeout related stuff
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					log.info("[FAIL]thread been interrupted while waiting when getting object from cache server2...");
					f2.cancel(false);
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					log.info("[FAIL]exception when getting object from cache server2...");
					f2.cancel(false);
				}
			}
		} catch (Exception e) {
			log.error(
					"[ERROR]other exception when getting objects from fengchao cache...",
					e);
		}

		if (ret != null) {
			for (String key : keys) {
				if (ret.get(key) != null) {
					log.info("[GET]SHOOTED" + "\tKey=" + key + "\tValue="
							+ ret.get(key).toString());
				}
			}
		}

		return ret;
	}

	/**
	 * 瀛樺叆涓�釜瀵硅薄(鍚噸璇曟満鍒�
	 * 
	 * @param key
	 * @param value
	 * @return piggie 2009-10-16 version 2.2.1
	 */
	@Override
	public boolean set(String key, Object value) {
		boolean result = false;
		for (int i = 0; i < retry; i++) {
			result = _set(key, value);
			if (!result) {
				log.info("set info into cache failed begin to retry " + i);
			} else {
				break;
			}
		}
		if (!result) {
			log.error("[FAIL] completely failed when setting info into cache after "
					+ retry + " times");
		}
		return result;
	}

	/**
	 * 瀛樺叆涓�釜瀵硅薄
	 */
	private boolean _set(String key, Object value) {
		// mc1.delete(key);
		// mc2.delete(key);
		boolean ret = false;
		if (mc1 == null || mc2 == null) {
			mc = (MemCacheServiceImpl) MemCacheServiceImpl.getInstance();
			mc1 = mc.mc1;
			mc2 = mc.mc2;
		}
		Future<Boolean> f = mc1.set(key, expHour * 60 * 60, value);
		Future<Boolean> f2 = mc2.set(key, expHour * 60 * 60, value);
		try {
			boolean fs1 = f.get(opTimeout, TimeUnit.SECONDS);
			boolean fs2 = f2.get(opTimeout, TimeUnit.SECONDS);
			ret = fs1 || fs2;

			if (!fs1) {
				log.info("[FAIL]CACHE SET FAIL:server1 set failed: " + "Key="
						+ key + "\tValue=" + value.toString());
			} else if (!fs2) {
				log.info("[FAIL]CACHE SET FAIL:server2 set failed: " + "Key="
						+ key + "\tValue=" + value.toString());
			}
		} catch (TimeoutException e) {
			// Since we don't need this, go ahead and cancel the
			// operation. This
			// is not strictly necessary, but it'll save some work on
			// the server.
			log.info("[FAIL]time out when getting objects from cache server2...");
			f.cancel(false);
			f2.cancel(false);
			// Do other timeout related stuff
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error(
					"[ERROR]exception when setting fengchao cache - thread been interrupted...",
					e);
			f.cancel(false);
			f2.cancel(false);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			log.error(
					"[ERROR]exception when setting fengchao cache - exception when getting status...",
					e);
			f.cancel(false);
			f2.cancel(false);
		} catch (Exception e) {
			log.error(
					"[ERROR]exception when setting fengchao cache - other exceptions...",
					e);
			f.cancel(false);
			f2.cancel(false);
		}

		if (value != null) {
			log.info("MemCacheServiceImpl.set,key=" + key + ",value="
					+ value.getClass());
		} else {
			log.info("MemCacheServiceImpl.set,key=" + key + ",value=null");
		}
		return ret;

	}

	/**
	 * <p>
	 * 绉婚櫎涓�釜瀵硅薄
	 * </p>
	 * 
	 * @see
	 * @param key
	 * @param value
	 * @return
	 * @author futuremining
	 * @date 2009-1-12
	 * @version 1.0.0
	 */
	@Override
	public boolean remove(String key) {
		boolean ret = false;
		if (mc1 == null || mc2 == null) {
			mc = (MemCacheServiceImpl) MemCacheServiceImpl.getInstance();
			mc1 = mc.mc1;
			mc2 = mc.mc2;
		}
		Future<Boolean> f = mc1.delete(key);
		Future<Boolean> f2 = mc2.delete(key);

		try {
			ret = f.get(opTimeout, TimeUnit.SECONDS)
					&& f2.get(opTimeout, TimeUnit.SECONDS); // !!
															// 璇ヨ鐨勫垽鏂彧闄愪簬2鍙颁笉鍚岀殑memcached鏈嶅姟鍣�
		} catch (TimeoutException e) {
			// Since we don't need this, go ahead and cancel the
			// operation. This
			// is not strictly necessary, but it'll save some work on
			// the server.
			log.info("[FAIL]time out when getting objects from cache server2...");
			f.cancel(false);
			f2.cancel(false);
			// Do other timeout related stuff
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error(
					"[ERROR]exception when deleting fengchao cache - thread been interrupted...",
					e);
			f.cancel(false);
			f2.cancel(false);
			ret = false;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			log.error(
					"[ERROR]exception when deleting fengchao cache - exception when getting status...",
					e);
			f.cancel(false);
			f2.cancel(false);
			ret = false;
		} catch (Exception e) {
			log.error(
					"[ERROR]exception when deleting fengchao cache - other exceptions...",
					e);
			f.cancel(false);
			f2.cancel(false);
			ret = false;
		}

		log.info("[REMOVE]" + ret + "\tKey=" + key);

		return ret; // 濡傛灉閰嶄簡鐩稿悓鐨勶紝鍗充娇 remove鎴愬姛
		// 锛屼篃浼氳繑鍥瀎alse锛屽洜姝ゆ杩斿洖鍊兼湁鎰忎箟浠呭綋閰嶇疆涓ゅ彴涓嶅悓memcached鏈嶅姟鍣�
	}

	@Override
	public boolean set(String key, Object value, int exp) {
		// mc1.delete(key);
		// mc2.delete(key);
		boolean ret = false;
		Future<Boolean> f = mc1.set(key, exp, value);
		Future<Boolean> f2 = mc2.set(key, exp, value);

		try {
			boolean fs1 = f.get(opTimeout, TimeUnit.SECONDS);
			boolean fs2 = f2.get(opTimeout, TimeUnit.SECONDS);
			ret = fs1 || fs2;

			if (!fs1) {
				log.info("[FAIL]CACHE SET FAIL:server1 set failed: " + "Key="
						+ key + ",Value=" + value.toString());
			} else if (!fs2) {
				log.info("[FAIL]CACHE SET FAIL:server2 set failed: " + "Key="
						+ key + ",Value=" + value.toString());
			}
		} catch (Exception e) {
			if (!"LOGIN_IP".equalsIgnoreCase(key)) {
				log.info("MemCacheServiceImpl.set,key=" + key + ",value="
						+ value + ",Exception");
			}
			e.printStackTrace();
			f.cancel(false);
			f2.cancel(false);
		}
		if (value != null) {
			if (!"LOGIN_IP".equalsIgnoreCase(key)) {
				log.info("MemCacheServiceImpl.set,key=" + key + ",value="
						+ value.getClass());
			}
		} else {
			if (!"LOGIN_IP".equalsIgnoreCase(key)) {
				log.info("MemCacheServiceImpl.set,key=" + key + ",value=null");
			}
		}
		return ret;
	}
}