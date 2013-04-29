 package com.mce.util.hazelcast;
 
 import com.hazelcast.config.Config;
 import com.hazelcast.config.XmlConfigBuilder;
 import com.hazelcast.core.Hazelcast;
 import com.hazelcast.core.HazelcastInstance;
 import java.io.IOException;
 import java.io.InputStream;
 import java.net.URL;
 import java.util.concurrent.atomic.AtomicBoolean;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 
 public final class HazelcastInstanceGetter
 {
   private static final Log logger = LogFactory.getLog(HazelcastInstanceGetter.class);
 
   private static final HazelcastInstanceProvider hInstanceProvider = new HazelcastInstanceProvider();
   private static final AtomicBoolean ab = new AtomicBoolean(false);
 
   public static HazelcastInstance get() {
     if (ab.compareAndSet(false, true)) {
       init();
     }
     return hInstanceProvider.get();
   }
 
   private static void init()
   {
     URL url = HazelcastInstanceGetter.class.getResource("/hazelcast.xml");
     logger.info("Configfile file load from[" + url.toString() + "]");
     Config cfg = null;
     try {
       InputStream is = url.openStream();
       cfg = new XmlConfigBuilder(is).build();
     } catch (IOException e) {
       throw new IllegalArgumentException("Can not load hazelcast.xml file[" + url.toString() + "]");
     }
 
     HazelcastInstance hz = Hazelcast.newHazelcastInstance(cfg);
     hInstanceProvider.setInstance(hz);
   }
 
   private static class HazelcastInstanceProvider
   {
     private HazelcastInstance hInstance;
 
     public void setInstance(HazelcastInstance hz)
     {
       this.hInstance = hz;
     }
     public HazelcastInstance get() {
       return this.hInstance;
     }
   }
 }

