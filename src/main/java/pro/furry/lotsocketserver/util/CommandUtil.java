package pro.furry.lotsocketserver.util;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author NuoTian
 * @date 2023/7/10
 */
@Slf4j
@Component
public class CommandUtil {
    @Value("${app.key}")
    private String amapKey;

    public @Nullable String getRoute(@NotNull String param) {
        int origIndexStart = param.indexOf("orig(");
        int destIndexStart = param.indexOf("dest(");
        if (origIndexStart == -1 || destIndexStart == -1)
            return null;

        String originTemp = param.substring(origIndexStart + 5);
        String destTemp = param.substring(destIndexStart + 5);

        String origin = originTemp.substring(0, originTemp.indexOf(")"));
        String destination = destTemp.substring(0, destTemp.indexOf(")"));
        log.info("orig {}, dest {}", origin, destination);

        String url = "https://restapi.amap.com/v5/direction/walking";
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("key", amapKey);
        params.add("origin", origin);
        params.add("destination", destination);
        params.add("alternative_route", "1");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.queryParams(params).build().encode().toUri();

        ResponseEntity<String> response = new RestTemplate().getForEntity(uri, String.class);
        if (response.getStatusCode().isError())
            return null;

        log.info("{}", response.getBody());
        return "";
    }
}
