package jp.co.solxyz.jsn.academy.junitsample.infrastructure.domain.book.service.impl;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import jp.co.solxyz.jsn.academy.junitsample.infrastructure.database.dto.BookManagementTableDto;
import jp.co.solxyz.jsn.academy.junitsample.infrastructure.database.repository.BookManagementTableRepository;

class BookManagementTableServiceImplTest {

	@InjectMocks
	BookManagementTableServiceImpl sut;

	@Mock
	BookManagementTableRepository repository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Nested
	class SearchStockInfo {

		@Test
		void 書籍在庫情報リスト取得成功() {
			List<BookManagementTableDto> list = new ArrayList<BookManagementTableDto>();

			doReturn(list).when(repository).findAll();

			assertThat(sut.searchStockInfo()).isEqualTo(list);
		}

		@Test
		void 書籍在庫情報リスト取得失敗() {
			List<BookManagementTableDto> list = new ArrayList<BookManagementTableDto>();

			doThrow(new DataAccessException("") {
			}).when(repository).findAll();

			Throwable exception = assertThrows(DataAccessException.class, () -> {
				sut.searchStockInfo();
			});
		}
	}

	@Nested
	class UpdateStockInfo {

		@Test
		void 書籍在庫情報更新成功() {
			BookManagementTableDto dto = new BookManagementTableDto();

			doReturn(dto).when(repository).saveAndFlush(dto);

			assertThat(sut.updateStockInfo(dto)).isEqualTo(dto);
		}

		@Test
		void 書籍在庫情報更新失敗() {
			BookManagementTableDto dto = new BookManagementTableDto();

			doThrow(new DataAccessException("") {
			}).when(repository).saveAndFlush(dto);

			Throwable exception = assertThrows(DataAccessException.class, () -> {
				sut.updateStockInfo(dto);
			});
		}
	}

}
