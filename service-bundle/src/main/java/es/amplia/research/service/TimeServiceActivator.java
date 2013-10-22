package es.amplia.research.service;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.amplia.research.service.definition.TimeListener;
import es.amplia.research.service.definition.TimeService;
import es.amplia.research.service.implementation.TimeServiceImpl;

public class TimeServiceActivator implements BundleActivator {

	private static Logger logger = LoggerFactory
			.getLogger(TimeServiceActivator.class);

	ServiceListener serviceListener = new ServiceListener() {
		public void serviceChanged(ServiceEvent event) {
			ServiceReference serviceRef = event.getServiceReference();
			switch (event.getType()) {
			case ServiceEvent.REGISTERED: {
				logger.info("New Time Listener registered!! {} ", serviceRef);
			}
				break;
			case ServiceEvent.UNREGISTERING: {
				logger.info("New Time Listener unregistered!! {} ", serviceRef);
			}
				break;
			}

		}
	};

	public void start(BundleContext context) throws Exception {
		logger.info("Time Service starting...");
		try {
			// -> Register the service
			Hashtable props = new Hashtable();
			props.put("description", "This a test service");
			ServiceRegistration service = context.registerService(TimeService.class.getName(), new TimeServiceImpl(), props);
			logger.info("Time Service registered in "+ context);
			
			
			// -> Register the service listener		
			String filter = "(objectclass=" + TimeListener.class.getName() + ")";
			context.addServiceListener(serviceListener, filter);
			logger.info("time service listener registered");
			ServiceReference[] srl = context.getServiceReferences(null, filter);
		      for(int i = 0; srl != null && i < srl.length; i++) {
		    	  serviceListener.serviceChanged(new ServiceEvent(ServiceEvent.REGISTERED, srl[i]));
		      }
			
			// -> List registered services
			ServiceReference[] services = context.getAllServiceReferences(TimeService.class.getName(), null);
			if (services == null) {
				logger.info("Service not registered");
				return;
			}
			
			for (ServiceReference serviceReference : services) {
				logger.info("Service: {}", serviceReference);
			}
		} catch (Throwable e) {
			logger.error("Error starting time service",e);
			throw new Exception(e);
		}
		
	}

	public void stop(BundleContext context) throws Exception {
		logger.info("Time Service stop");

	}

}
