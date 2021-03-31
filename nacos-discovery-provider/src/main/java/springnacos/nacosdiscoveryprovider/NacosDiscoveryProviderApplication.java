package springnacos.nacosdiscoveryprovider;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
@NacosPropertySource(dataId = "nacos-discovery-provider.properties", type = ConfigType.PROPERTIES, autoRefreshed = true, properties = @NacosProperties(namespace = "b258991f-3fb4-43b8-9dd1-0c6c04620f03"))
public class NacosDiscoveryProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosDiscoveryProviderApplication.class, args);
	}


	@RestController
	public class EchoController {
		@GetMapping(value = "/echo/{string}")
		public String echo(@PathVariable String string) {
			return "Hello Nacos Discovery " + string;
		}

		@GetMapping(value = "/ech/{string}")
		public String echocs(@PathVariable String string) {
			return "Hello Nacos Discovery " + string;
		}
	}
}
