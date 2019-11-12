package py.com.sodep.bancard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import py.com.sodep.bancard.web.controller.MicroServiceController;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Configuration
@EnableSwagger
@Profile(value={"default"})
@ComponentScan(basePackageClasses={MicroServiceController.class})
public class SwaggerConfig {
	
	private SpringSwaggerConfig springSwaggerConfig;
	
	@Autowired
	public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
		this.springSwaggerConfig = springSwaggerConfig;
	}
	
	// Don't forget the @Bean annotation
	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(
				apiInfo()).includePatterns("/pagos/brands/.*", "/payment.*", "/admin/services.*", "/admin/transactions.*");
		
		//.useDefaultResponseMessages(false);
	}
	
	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("Bancard API", "API for Bancard",
				"Bancard API terms of service", "maile@gmail.com",
				"Bancard API Licence Type", "Bancard API License URL");
		return apiInfo;
	}
}