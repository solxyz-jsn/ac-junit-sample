package jp.co.solxyz.jsn.academy.junitsample.application.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.database.rider.junit5.DBUnitExtension;

import jp.co.solxyz.jsn.academy.junitsample.infrastructure.api.component.BookOrderApi;
import jp.co.solxyz.jsn.academy.junitsample.infrastructure.domain.book.service.BookManagementTableService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ExtendWith(DBUnitExtension.class)
class BookManagementServiceImplTest {
	@InjectMocks
	BookManagementServiceImpl sut;

	@Mock
	BookManagementTableService bookManagementTableService;

	@Mock
	BookOrderApi bookOrderApi;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterEach
	public void after() {
		// DBコネクション取得
		Connection conn;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			IDatabaseConnection dbconn = new DatabaseConnection(conn);

			QueryDataSet dataSet = new QueryDataSet(dbconn);
			// retrieve all rows from specified table
			dataSet.addTable("BOOK_MANAGEMENT_TBL");

			//			DatabaseOperation.DELETE_ALL.execute(dbconn, dbconn.createDataSet(new String[] { "BOOK_MANAGEMENT_TBL" }));
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
	//	@Sql(scripts = "classpath:schema.sql")
	void sample() {
		System.out.println(sut.init());
	}

	//	@Nested
	//	class Init {
	//
	//		@Test
	//		void 一覧取得成功() {
	//			List<BookManagementTableDto> result = new ArrayList();
	//
	//			doReturn(result).when(bookManagementTableService).searchStockInfo();
	//
	//			assertThat(sut.init()).isEqualTo(result);
	//		}
	//
	//		@Test
	//		void 一覧取得失敗() {
	//			doThrow(new DataAccessException("") {
	//			}).when(bookManagementTableService).searchStockInfo();
	//
	//			Throwable exception = assertThrows(DataAccessException.class, () -> {
	//				sut.init();
	//			});
	//		}
	//	}

	//	@Nested
	//	class Update {
	//
	//		@Test
	//		void 在庫情報更新成功() {
	//			BookManagementTableDto dto = new BookManagementTableDto();
	//
	//			doReturn(dto).when(bookManagementTableService).updateStockInfo(any(BookManagementTableDto.class));
	//
	//			assertThat(sut.update(0, "", 0)).isEqualTo(0);
	//		}
	//
	//		@Test
	//		void 在庫情報更新失敗() {
	//			doThrow(new DataAccessException("") {
	//			}).when(bookManagementTableService).updateStockInfo(any(BookManagementTableDto.class));
	//
	//			assertThat(sut.update(0, "", 0)).isEqualTo(1);
	//		}
	//	}

	//	@Nested
	//	class Order {
	//
	//		@Test
	//		void 発注成功_登録成功() {
	//			BookOrderResponse res = new BookOrderResponse();
	//			BookManagementTableDto dto = new BookManagementTableDto();
	//
	//			doReturn(res).when(bookOrderApi).order(any(BookOrderRequest.class));
	//			doReturn(dto).when(bookManagementTableService).updateStockInfo(any(BookManagementTableDto.class));
	//
	//			assertThat(sut.order(0, "", 0)).isEqualTo(0);
	//		}
	//
	//		@Test
	//		void 発注成功_登録失敗() {
	//			BookOrderResponse res = new BookOrderResponse();
	//
	//			doReturn(res).when(bookOrderApi).order(any(BookOrderRequest.class));
	//			doThrow(new DataAccessException("") {
	//			}).when(bookManagementTableService).updateStockInfo(any(BookManagementTableDto.class));
	//
	//			assertThat(sut.order(0, "", 0)).isEqualTo(1);
	//		}
	//
	//		@Test
	//		void 発注失敗() {
	//			doThrow(new RestClientException("") {
	//			}).when(bookOrderApi).order(any(BookOrderRequest.class));
	//
	//			assertThat(sut.order(0, "", 0)).isEqualTo(1);
	//		}
	//	}

}
