package jp.co.solxyz.jsn.academy.junitsample.infrastructure.api.component;

import jp.co.solxyz.jsn.academy.junitsample.infrastructure.api.request.BookOrderRequest;
import jp.co.solxyz.jsn.academy.junitsample.infrastructure.api.response.BookOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BookOrderApi {

    private static final String URL = "https://www.solxyz.co.jp/";

    @Autowired
    private RestTemplate restTemplate;

    public BookOrderResponse order(BookOrderRequest request) {
        return restTemplate.postForObject(URL, request, BookOrderResponse.class);
    }

}
