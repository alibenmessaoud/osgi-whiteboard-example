package es.amplia.research;

import static org.ops4j.pax.exam.CoreOptions.bundle;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.options;

import java.io.File;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.amplia.research.service.definition.TimeService;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class RunningTest {
 
	private static Logger logger = LoggerFactory.getLogger(RunningTest.class);
	
	private static final File baseDir = new File(".");
	
	@Inject
    private BundleContext context;
	
    @Inject
    private TimeService service;
    
    @Configuration
    public Option[] config() {
 
    	return options(
    			junitBundles(),
    			//mavenBundle().groupId("es.amplia.research").artifactId("osgi-service-bundle").version("1.0.0-SNAPSHOT"),
    			//mavenBundle().groupId("es.amplia.research").artifactId("osgi-consumer-bundle").version("1.0.0-SNAPSHOT")
    			bundle("reference:file:"+baseDir.getAbsolutePath()+"/../service-bundle/target/classes"),
    			bundle("reference:file:"+baseDir.getAbsolutePath()+"/../consumer-bundle/target/classes")
            );
    }
 
    @Test
    public void getService() throws InterruptedException, InvalidSyntaxException {
    	Assert.assertNotNull(service);
    	
//    	Hashtable props = null;
//		context.registerService(TimeListener.class.getName(), new JunitTimeListener(), props );
//		logger.info("register service from junit");
  
    	ServiceReference[] services = context.getAllServiceReferences(TimeService.class.getName(), null);	
    	if (services == null) {
    		logger.info("services is null from junit test");
    		return;
    	}
    	
    	for (ServiceReference serviceReference : services) {
			logger.info("Service {} available from junit test", serviceReference);
		}
    	
    }    
    
}