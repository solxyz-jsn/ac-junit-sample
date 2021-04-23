package jp.co.solxyz.jsn.academy.junitsample.application.service.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;

import jp.co.solxyz.jsn.academy.junitsample.infrastructure.api.component.BookOrderApi;
import jp.co.solxyz.jsn.academy.junitsample.infrastructure.api.request.BookOrderRequest;
import jp.co.solxyz.jsn.academy.junitsample.infrastructure.api.response.BookOrderResponse;
import jp.co.solxyz.jsn.academy.junitsample.infrastructure.database.dto.BookManagementTableDto;
import jp.co.solxyz.jsn.academy.junitsample.infrastructure.domain.book.service.BookManagementTableService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
//@ExtendWith(DBUnitExtension.class)
class BookManagementServiceImplTest {
	@InjectMocks
	BookManagementServiceImpl sut;

	@Spy
	BookManagementTableService bookManagementTableService;

	@Mock
	BookOrderApi bookOrderApi;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setup() {

	}

	@AfterEach
	public void after() throws Exception {

		// DBコネクション取得
		Connection conn;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			IDatabaseConnection dbconn = new DatabaseConnection(conn);

			QueryDataSet dataSet = new QueryDataSet(dbconn);
			// retrieve all rows from specified table
			dataSet.addTable("BOOK_MANAGEMENT_TBL");

			// DatabaseOperation.DELETE_ALL.execute(dbconn, dbconn.createDataSet(new String[] { "BOOK_MANAGEMENT_TBL" }));
			DatabaseOperation.DELETE_ALL.execute(dbconn, dataSet);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (DatabaseUnitException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	@Test
	@Sql(scripts = "classpath:data.sql")
	void sample() throws Exception {
		try (var mock = MockitoAnnotations.openMocks(this)) {

			System.out.println(sut.init());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Nested
	class Init {

		@Test
		@Sql(scripts = "classpath:data.sql")
		void 一覧取得成功() throws Exception {
			try (var mock = MockitoAnnotations.openMocks(this)) {
				List<BookManagementTableDto> result = new ArrayList<>();

				doReturn(result).when(bookManagementTableService).searchStockInfo();

				assertThat(sut.init()).isEqualTo(result);
			}
		}

		@Test
		@Sql(scripts = "classpath:data.sql")
		void Database() throws SQLException, DatabaseUnitException {

//			try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
//
//				IDatabaseConnection dbconn = new DatabaseConnection(conn);
//				var query = new QueryDataSet(dbconn);
//
//				query.addTable("");
//
//				var result = jdbcTemplate.queryForList(
//						"SELECT BOOK_ID, BOOK_NAME, STOCK, VERSION FROM BOOK_MANAGEMENT_TBL",
//						new BeanPropertyRowMapper<BookManagementTableDto>(BookManagementTableDto.class));
//
//				System.out.println(result);
//			}

			List<BookManagementTableDto> list = sut.init();
			assertThat(list.size()).isEqualTo(3);

			var first = list.get(0);

			assertThat(first.getBookId()).isEqualTo(1);
		}

		@Test
		@Sql(scripts = "classpath:data.sql")
		void 一覧取得失敗() {
			doThrow(new DataAccessException("") {
			}).when(bookManagementTableService).searchStockInfo();

			//			Throwable exception = assertThrows(DataAccessException.class, () -> {
			//				sut.init();
			//			});
			sut.init();
		}
	}

	@Nested
	class Update {

		@Test
		void 在庫情報更新成功() {
			BookManagementTableDto dto = new BookManagementTableDto();

			doReturn(dto).when(bookManagementTableService).updateStockInfo(any(BookManagementTableDto.class));

			assertThat(sut.update(0, "", 0)).isEqualTo(0);
		}

		@Test
		void 在庫情報更新失敗() {
			doThrow(new DataAccessException("") {
			}).when(bookManagementTableService).updateStockInfo(any(BookManagementTableDto.class));

			assertThat(sut.update(0, "", 0)).isEqualTo(1);
		}
	}

	@Nested
	class Order {

		@Test
		void 発注成功_登録成功() {
			BookOrderResponse res = new BookOrderResponse();
			BookManagementTableDto dto = new BookManagementTableDto();

			doReturn(res).when(bookOrderApi).order(any(BookOrderRequest.class));
			doReturn(dto).when(bookManagementTableService).updateStockInfo(any(BookManagementTableDto.class));

			assertThat(sut.order(0, "", 0)).isEqualTo(0);
		}

		@Test
		void 発注成功_登録失敗() {
			BookOrderResponse res = new BookOrderResponse();

			doReturn(res).when(bookOrderApi).order(any(BookOrderRequest.class));
			doThrow(new DataAccessException("") {
			}).when(bookManagementTableService).updateStockInfo(any(BookManagementTableDto.class));

			assertThat(sut.order(0, "", 0)).isEqualTo(1);
		}

		@Test
		void 発注失敗() {
			doThrow(new RestClientException("") {
			}).when(bookOrderApi).order(any(BookOrderRequest.class));

			assertThat(sut.order(0, "", 0)).isEqualTo(1);
		}
	}

}
