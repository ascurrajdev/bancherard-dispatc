package py.com.sodep.bancard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;


public class PluginBuilder {

	private Class<?> plugin;
	
	private List<Class<?>> clazzes_ = new ArrayList<Class<?>>();
	
	private List<String> jars_ = new ArrayList<String>();
	
	private List<String> resources_ = new ArrayList<String>();

	private String basePackage;
	
	public PluginBuilder(final Class<?> plugin) {
		this.plugin = plugin;
	}
	
	public void addBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public void addClass(final Class<?> clazz) {
		clazzes_.add(clazz);
	}

	public void addJar(final String file) {
		jars_.add(file);
	}

	public void addResource(final String file) {
		resources_.add(file);
	}
	
	
	public File generatePluginJar() throws IOException {
		
		File jarFile = File.createTempFile("plugin-test", ".jar");
		//jarFile.deleteOnExit();
		Manifest manifest = new Manifest();
		Attributes mainAttributes = manifest.getMainAttributes();

		// Create manifest stating that agent is allowed to transform classes
		mainAttributes.put(Name.MANIFEST_VERSION, "1.0");
//		mainAttributes.put(new Name("Agent-Class"), agent_.getName());
//		mainAttributes.put(new Name("Can-Retransform-Classes"), "true");
//		mainAttributes.put(new Name("Can-Redefine-Classes"), "true");
	
		JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFile), manifest);
		appendClass(jos, plugin);
		
		if (!clazzes_.isEmpty()) {
			for (Class<?> clazz : clazzes_) {
				appendClass(jos, clazz);
			}
		}
		
		// Copy all jar content in the subdir
		
//		for (int i = 0; i < jars_.size(); i++) {
//			JarInputStream jis = new JarInputStream(plugin.getClassLoader().getResourceAsStream(jars_.get(i)));
//			JarEntry jEnt;
//			while ((jEnt = jis.getNextJarEntry()) != null) {
//				if (jEnt.getName().endsWith(".class")) {
//					jos.putNextEntry(new JarEntry(jEnt.getName()));
//
//					byte[] buf = new byte[8192];
//					int count;
//					while ((count = jis.read(buf, 0, buf.length)) > 0) {
//						jos.write(buf, 0, count);
//					}
//					jos.closeEntry();
//				}
//			}
//			jis.close();
//		}

//		for (String res : resources_) {
//			appendFile(jos, plugin.getClassLoader(), res);
//		}
		
		jos.close();

		return jarFile;
	}
	
	/**
	 * Appends a Class to the jar output stream.
	 * 
	 * @param jos
	 * @param clazz
	 * @throws IOException
	 */
	private void appendClass(final JarOutputStream jos, final Class<?> clazz) throws IOException {
		String clazzFile;
		if (basePackage == null) {
			clazzFile = clazz.getName().replace('.', '/');// + ".class";
		} else {
			clazzFile = basePackage + "/" + clazz.getSimpleName() + ".class";
		}
		appendFile(jos, clazz.getClassLoader(), clazzFile);
	}

	private void appendFile(final JarOutputStream jos, final ClassLoader cl, final String fileName) throws IOException {
		InputStream is = cl.getResourceAsStream(fileName);
		if (is != null) {
			byte[] buf = new byte[8192];
			jos.putNextEntry(new JarEntry(fileName + ".class"));
			int count;
			while ((count = is.read(buf, 0, buf.length)) > 0) {
				jos.write(buf, 0, count);
			}
			jos.closeEntry();
		} else {
			throw new IOException("Could not load data from '" + fileName + "' ");
		}
	}
	
	
	public static void main(String[] args) throws IOException {
//		GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
//		Class theParsedClass = groovyClassLoader.parseClass(new File("C:/Proyectos/Bancard/desarrollo/git/bancard_ms_dispatcher/src/test/resources/groovy_test.groovy"));
//		
//		PluginBuilder builder = new PluginBuilder(theParsedClass);
//		File jar = builder.generatePluginJar();
//		System.out.println("Jar is " + jar.getAbsolutePath());
		
	}
}