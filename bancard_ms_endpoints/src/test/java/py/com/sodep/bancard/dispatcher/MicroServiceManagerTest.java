package py.com.sodep.bancard.dispatcher;


import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import py.com.sodep.bancard.PluginBuilder;
import py.com.sodep.bancard.dto.MicroServiceDTO;
import py.com.sodep.bancard.services.MicroServiceEntityService;
import py.com.sodep.bancard.services.impl.MicroServiceManagerImpl;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//@ActiveProfiles("test")
//@TransactionConfiguration(defaultRollback = true)
//@Transactional
//@DirtiesContext(classMode=ClassMode.AFTER_CLASS)
public class MicroServiceManagerTest {

	@Autowired
	private MicroServiceManagerImpl msManager;
	
	@Autowired
	private MicroServiceEntityService msService;
	
	private PluginBuilder pluginBuilder;

	private MicroServiceDTO msDtoMock;

	//private static final String PACKAGE_TEST = "some.dummy.packagename";
	
	private static final String MS_NAME = "dummy-service";
	
	private static final String MS_PROD = "dummy-prod";
	
	
//	@Before
	public void setUp() throws IOException {
		//Class<?> compileScript = GroovyUtils.compileScript(SCRIPT);
		//GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
		//Class theParsedClass = groovyClassLoader.parseClass(new File("C:/Proyectos/Bancard/desarrollo/git/bancard_ms_dispatcher/src/test/resources/groovy_test.groovy"));
		//groovyClassLoader.close();
		//this.pluginBuilder = new PluginBuilder(theParsedClass);
		//this.pluginBuilder.addBasePackage(PACKAGE_TEST.replace(".", "/"));
		this.msDtoMock = new MicroServiceDTO();
		//this.msDtoMock.setClassName(theParsedClass.getName());
		//this.msDtoMock.setClassName(DummyFacturas.class.getName());
		this.msDtoMock.setServiceName(MS_NAME);
		this.msDtoMock.setProduct(MS_PROD);
	}
	
//	@Test
	public void testLoadPlugin() throws IOException {
		
		// Create the MS in DB
		msService.create(this.msDtoMock);
		
		// Generate temp jar
		File jar = pluginBuilder.generatePluginJar();
		//System.out.println("Jar is " + jar.getParent());
		System.out.println("Jar is " + jar.getName());
		
		// Prepare the manager to read the Plugins
		// from temp location
		msManager.setMsJarLocation(jar.getParent());
		
		// FIXME
		// Este método no asegura que el plugin
		// que se carga no sea el que está en
		// el proyecto bancard-ms-endpoints
		// El plugin debe ser del jar, pero no
		// encontre aun la forma de lograr asegurar
		// que el que se está cargando sea del jar
		msManager.reloadMicroService(MS_NAME, MS_PROD);
		
		jar.delete();
		
	}
	
	
}
