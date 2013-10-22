package es.amplia.research.consumer;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.amplia.research.service.definition.TimeListener;
import es.amplia.research.service.definition.TimeService;

public class TimeConsumerActivator implements BundleActivator {

	private static Logger logger = LoggerFactory
			.getLogger(TimeConsumerActivator.class);

	ServiceListener listener = new ServiceListener() {

		public void serviceChanged(ServiceEvent event) {
			switch (event.getType()) {
			case ServiceEvent.UNREGISTERING:
				logger.info("TimeService is unregistering");
				break;
			case ServiceEvent.MODIFIED:
				logger.info("TimeService is modified");
				break;
			case ServiceEvent.MODIFIED_ENDMATCH:
				logger.info("TimeService is modified end match");
				break;
			case ServiceEvent.REGISTERED:
				logger.info("TimeService is registered");
				break;
			}

		}
	};
	
	
	public void start(BundleContext context) throws Exception {
		logger.info("Time Consumer starting..");

		// -> Using direct reference
		ServiceReference reference = context.getServiceReference(TimeService.class.getName());
		logger.info("direct reference got: {} from {} ", reference, context);

		// -> Using service Listener
		String filter = "(objectclass=" + TimeService.class.getName() + ")";
		context.addServiceListener(listener, filter);
		logger.info("added service listener");
		
		ServiceReference[] references = context.getServiceReferences(null, filter);
		if (references == null) {
			logger.info("references is null!!");
			return;
		}
		
		for (ServiceReference serviceReference : references) {
			listener.serviceChanged(new ServiceEvent(ServiceEvent.REGISTERED, serviceReference));
		}
		
		
		// -> Using white-board model
		Hashtable props = new Hashtable();
		props.put("description", "This a test listener");
		context.registerService(TimeListener.class.getName(), new DefaultTimeListener(), props);
		
		logger.info("Time Consumer started");
	}

	public void stop(BundleContext context) throws Exception {
		logger.info("Time Consumer stop");
	}

}
