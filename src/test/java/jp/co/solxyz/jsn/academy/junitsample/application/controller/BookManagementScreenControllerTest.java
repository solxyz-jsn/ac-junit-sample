package jp.co.solxyz.jsn.academy.junitsample.application.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Connection;
import java.sql.SQLException;
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
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import jp.co.solxyz.jsn.academy.junitsample.application.service.BookManagementService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest
class BookManagementScreenControllerTest {

	@Autowired
	BookManagementScreenController sut;

	@Autowired
	private MockMvc mockMvc;

	@Spy
	BookManagementService bookManagementService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
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

	@Nested
	class Init {

		@Test
		@Sql(statements = {
				"DELETE BOOK_MANAGEMENT_TBL",
		})
		void 書籍在庫情報リスト取得成功_0件() {
			try {
				mockMvc.perform(get("manage/book"))
						.andExpect(status().isOk())
						.andExpect(view().name("bookmanager"))
						.andExpect(model().attribute("books", any(List.class)));
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			fail();
			//			ModelAndView actual = sut.init();
			//			List<BookManagementTableDto> list = actual.assertThat(actual.size()).isEqualTo(0);
		}

		@Test
		@Sql(statements = {
				"DELETE BOOK_MANAGEMENT_TBL",
				"INSERT INTO BOOK_MANAGEMENT_TBL (BOOK_ID, BOOK_NAME, STOCK, VERSION) VALUES (1, 'Spring boot実践入門', 10, 1)"
		})
		void 書籍在庫情報リスト取得成功_1件() {
			fail();
		}

		@Test
		@Sql(statements = {
				"DELETE BOOK_MANAGEMENT_TBL",
				"INSERT INTO BOOK_MANAGEMENT_TBL (BOOK_ID, BOOK_NAME, STOCK, VERSION) VALUES (1, 'Spring boot実践入門', 10, 1)",
				"INSERT INTO BOOK_MANAGEMENT_TBL (BOOK_ID, BOOK_NAME, STOCK, VERSION) VALUES (2, 'JUnit詳解', 200, 3)"
		})
		void 書籍在庫情報リスト取得成功_2件() {
			fail();
		}
	}

	@Nested
	class UpdateBookInfo {

	}

	@Nested
	class OrderBook {

	}

}
