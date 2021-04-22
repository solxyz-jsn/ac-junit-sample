package jp.co.solxyz.jsn.academy.junitsample.infrastructure.api.component;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import jp.co.solxyz.jsn.academy.junitsample.infrastructure.api.request.BookOrderRequest;
import jp.co.solxyz.jsn.academy.junitsample.infrastructure.api.response.BookOrderResponse;

class BookOrderApiTest {

	private static final String URL = "https://www.solxyz.co.jp/";

	@InjectMocks
	BookOrderApi sut;

	@Mock
	RestTemplate restTemplate;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void test() {
		BookOrderResponse res = new BookOrderResponse();
		BookOrderRequest req = new BookOrderRequest();

		when(restTemplate.postForObject(eq(URL), any(BookOrderRequest.class), eq(BookOrderResponse.class)))
				.thenReturn(res);

		assertThat(sut.order(req)).isEqualTo(res);
	}

}
