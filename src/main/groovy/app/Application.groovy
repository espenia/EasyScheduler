package app

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.stereotype.Component
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

import javax.servlet.Filter
import javax.servlet.FilterConfig
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@SpringBootApplication
@ServletComponentScan
@EnableMongoRepositories(basePackages = ["Repositorios"])
@ComponentScan(basePackages = ["Controladores", "Servicios"])
@Configuration
@EnableSwagger2
class Application {

	static void main(String[] args) {
		SpringApplication.run(Application.class, args)
	}

	@Component
	class ConfigCtrl implements Filter {

		@Override
		void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
			final HttpServletResponse response = (HttpServletResponse) res
			response.setHeader("Access-Control-Allow-Origin", "*")
			response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE, PATCH")
			response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type")
			response.setHeader("Access-Control-Max-Age", "3600")
			if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
				response.setStatus(HttpServletResponse.SC_OK)
			} else {
				chain.doFilter(req, res)
			}
		}
		@Override
		void destroy() {
		}
		@Override
		void init(FilterConfig config) throws ServletException {
		}
	}

	@Autowired
	private Environment environment;

	@Bean
	MongoClient mongoClient() {
		return MongoClients.create(environment.getProperty("spring.data.mongodb.uri"))
	}

	@Bean
	MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(), environment.getProperty("spring.data.mongodb.database"))
	}

	@Bean
	Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
	}
}


