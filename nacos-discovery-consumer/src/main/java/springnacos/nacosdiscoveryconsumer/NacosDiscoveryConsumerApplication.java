package springnacos.nacosdiscoveryconsumer;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
@NacosPropertySource(dataId = "nacos-discovery-consumer", autoRefreshed = true, groupId = "DEFAULT_GROUP", type = ConfigType.PROPERTIES)
@RefreshScope
public class NacosDiscoveryConsumerApplication {
	@RestController
	public class NacosController{

		@Autowired
		private LoadBalancerClient loadBalancerClient;
		@Autowired
		private RestTemplate restTemplate;

		@Value("${spring.application.name}")
		private String appName;

		@GetMapping("/echo/app-name")
		public String echoAppName(){
			//使用 LoadBalanceClient 和 RestTemolate 结合的方式来访问
			ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-discovery-provider");
			String url = String.format("http://%s:%s/echo/%s",serviceInstance.getHost(),serviceInstance.getPort(),appName);
			System.out.println("request url:"+url);
			return restTemplate.getForObject(url,String.class);
		}

	}

	//实例化 RestTemplate 实例
	@Bean
	public RestTemplate restTemplate(){

		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(NacosDiscoveryConsumerApplication.class, args);
	}

}