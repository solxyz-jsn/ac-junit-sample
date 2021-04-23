package jp.co.solxyz.jsn.academy.junitsample.infrastructure.domain.book.service.impl;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class BookManagementTableServiceImplTest {

	@Autowired
	BookManagementTableServiceImpl sut;

	//	@Mock
	//	BookManagementTableRepository repository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setup() {
		//		MockitoAnnotations.initMocks(this);
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
	void 実際に叩く() {
		sut.searchStockInfo().iterator().forEachRemaining((book) -> {
			System.out.println(book.getBookName());
			fail();
		});
	}

	@Test
	@Sql(statements = {
		"DELETE BOOK_MANAGEMENT_TBL",
		"INSERT INTO BOOK_MANAGEMENT_TBL (BOOK_ID, BOOK_NAME, STOCK, VERSION) VALUES (1, 'Spring boot実践入門', 10, 1)",
		"INSERT INTO BOOK_MANAGEMENT_TBL (BOOK_ID, BOOK_NAME, STOCK, VERSION) VALUES (2, 'JUnit詳解', 200, 3)",
		"INSERT INTO BOOK_MANAGEMENT_TBL (BOOK_ID, BOOK_NAME, STOCK, VERSION) VALUES (3, 'H2DBをはじめる', 1, 1)"
	})
	void gettingDB() throws Exception {

		var list = sut.searchStockInfo();
		assertThat(list.size()).isEqualTo(3);
	}

	//	@Nested
	//	class SearchStockInfo {
	//
	//		@Test
	//		void 書籍在庫情報リスト取得成功() {
	//			List<BookManagementTableDto> list = new ArrayList<BookManagementTableDto>();
	//
	//			doReturn(list).when(repository).findAll();
	//
	//			assertThat(sut.searchStockInfo()).isEqualTo(list);
	//		}
	//
	//		
	//
	//		@Test
	//		void 書籍在庫情報リスト取得失敗() {
	//			List<BookManagementTableDto> list = new ArrayList<BookManagementTableDto>();
	//
	//			doThrow(new DataAccessException("") {
	//			}).when(repository).findAll();
	//
	//			Throwable exception = assertThrows(DataAccessException.class, () -> {
	//				sut.searchStockInfo();
	//			});
	//		}
	//	}

	//	@Nested
	//	class UpdateStockInfo {
	//
	//		@Test
	//		void 書籍在庫情報更新成功() {
	//			BookManagementTableDto dto = new BookManagementTableDto();
	//
	//			doReturn(dto).when(repository).saveAndFlush(dto);
	//
	//			assertThat(sut.updateStockInfo(dto)).isEqualTo(dto);
	//		}
	//
	//		@Test
	//		void 書籍在庫情報更新失敗() {
	//			BookManagementTableDto dto = new BookManagementTableDto();
	//
	//			doThrow(new DataAccessException("") {
	//			}).when(repository).saveAndFlush(dto);
	//
	//			Throwable exception = assertThrows(DataAccessException.class, () -> {
	//				sut.updateStockInfo(dto);
	//			});
	//		}
	//	}

}
