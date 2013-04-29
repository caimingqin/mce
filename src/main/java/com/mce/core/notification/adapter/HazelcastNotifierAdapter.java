 package com.mce.core.notification.adapter;
 
 import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import com.mce.core.notification.Notification;
import com.mce.core.notification.NotifyBox;
import com.mce.core.notification.NotifyProducer;
import com.mce.util.hazelcast.HazelcastInstanceGetter;
 
 public class HazelcastNotifierAdapter
   implements NotifyProducer
 {
   private HazelcastInstance ni = null;
   private String topicName = null;
 
   HazelcastNotifierAdapter() {
   }
   public HazelcastNotifierAdapter(String topicName) { this.ni = HazelcastInstanceGetter.get();
     this.topicName = topicName;
   }
 
   public void startProducer(NotifyBox nb)
   {
     final NotifyBox nsb = nb;
     ITopic<Notification> ntf = this.ni.getTopic(this.topicName);
     ntf.addMessageListener(new MessageListener<Notification>()
     {
       public void onMessage(Message<Notification> arg0) {
         Notification nf = (Notification)arg0.getMessageObject();
         nsb.add(nf);
       }
     });
   }
 }

