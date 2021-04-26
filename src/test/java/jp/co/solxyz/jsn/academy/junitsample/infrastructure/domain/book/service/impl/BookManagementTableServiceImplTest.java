package jp.co.solxyz.jsn.academy.junitsample.infrastructure.domain.book.service.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.SQLException;

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
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.co.solxyz.jsn.academy.junitsample.infrastructure.database.dto.BookManagementTableDto;
import jp.co.solxyz.jsn.academy.junitsample.infrastructure.database.repository.BookManagementTableRepository;

/**
 * BookManagementTableServiceImplのテスト
 * DBアクセスを行う（モックを使用しない）
 * 
 * @author JSN
 *
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class BookManagementTableServiceImplTest {

	@Autowired
	BookManagementTableServiceImpl sut;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private BookManagementTableRepository bookManagementTableRepository;

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

	@Nested
	class SearchStockInfo {

		@Test
		@Sql(statements = {
				"DELETE BOOK_MANAGEMENT_TBL",
				"INSERT INTO BOOK_MANAGEMENT_TBL (BOOK_ID, BOOK_NAME, STOCK, VERSION) VALUES (1, 'Spring boot実践入門', 10, 1)",
				"INSERT INTO BOOK_MANAGEMENT_TBL (BOOK_ID, BOOK_NAME, STOCK, VERSION) VALUES (2, 'JUnit詳解', 200, 3)"
		})
		void 書籍在庫情報リスト取得成功_2件() {
			var actual = sut.searchStockInfo();
			assertThat(actual).hasSize(2);
		}

		@Test
		@Sql(statements = {
				"DELETE BOOK_MANAGEMENT_TBL",
				"INSERT INTO BOOK_MANAGEMENT_TBL (BOOK_ID, BOOK_NAME, STOCK, VERSION) VALUES (1, 'Spring boot実践入門', 10, 1)"
		})
		void 書籍在庫情報リスト取得成功_1件() {
			var actual = sut.searchStockInfo();
			assertThat(actual).hasSize(1);
		}

		@Test
		@Sql(statements = {
				"DELETE BOOK_MANAGEMENT_TBL"
		})
		void 書籍在庫情報リスト取得成功_0件() {
			var actual = sut.searchStockInfo();
			assertThat(actual).hasSize(0);
		}

		// TODO 
		@Test
		void 書籍在庫情報リスト取得失敗() {
			fail("未実装");
		}
	}

	@Nested
	class UpdateStockInfo {

		@Test
		void 書籍在庫情報更新成功() {
			final String BOOK_NAME = "bookname";
			BookManagementTableDto dto = new BookManagementTableDto();
			dto.setBookId(5);
			dto.setBookName(BOOK_NAME);
			dto.setStock(100);
			dto.setVersion(1);

			var actualDto = sut.updateStockInfo(dto);
			assertThat(actualDto).isEqualTo(dto);
			assertThat(actualDto.getBookName()).isEqualTo(BOOK_NAME);
		}

		class MyDataAccessException extends DataAccessException {

			public MyDataAccessException() {
				super("Mocked Error");
			}

		}

		@Test
		void 書籍在庫情報更新失敗() throws Exception {

			BookManagementTableDto dto = new BookManagementTableDto();
			dto.setBookId(6);
			dto.setBookName("test");
			dto.setStock(100);
			dto.setVersion(1);

			// エラーを発生させるため、今回だけMock化する
			BookManagementTableRepository mock = mock(BookManagementTableRepository.class);

			// PowerMockが利用不可能な場合の注入方法（最終手段）
			// var field = BookManagementTableServiceImpl.class.getDeclaredField("bookManagementTableRepository");
			// field.setAccessible(true);
			// field.set(sut, mock);

			Whitebox.setInternalState(sut, BookManagementTableRepository.class, mock);

			try {
				doThrow(new MyDataAccessException()).when(bookManagementTableRepository).saveAndFlush(dto);

				sut.updateStockInfo(dto);

				fail("Exception is not fired.");
			} catch (Exception e) {
				System.out.println("Success.");
			}
		}
	}

}
