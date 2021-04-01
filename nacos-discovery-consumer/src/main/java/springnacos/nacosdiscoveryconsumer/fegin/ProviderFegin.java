package springnacos.nacosdiscoveryconsumer.fegin;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("nacos-discovery-provider")
@Component
public interface ProviderFegin {

    @GetMapping("/echo/cs/{string}")
    public String removeAlyVideo(@PathVariable String string);
}
