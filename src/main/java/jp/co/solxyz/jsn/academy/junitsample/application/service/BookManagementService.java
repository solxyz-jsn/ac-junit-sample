package jp.co.solxyz.jsn.academy.junitsample.application.service;

import jp.co.solxyz.jsn.academy.junitsample.infrastructure.database.dto.BookManagementTableDto;

import java.util.List;

public interface BookManagementService {

    /**
     * 初期処理
     * @return 処理コード
     */
    List<BookManagementTableDto> init();

    /**
     * 書籍更新処理
     * @param bookId 書籍ID
     * @param bookName 書籍名
     * @param stock 在庫数
     * @return 処理コード
     */
    int update(int bookId, String bookName, int stock);

    /**
     * 発注処理
     * @param bookId 書籍ID
     * @param bookName 書籍名
     * @param quantity 発注数
     * @return 処理コード
     */
    int order(int bookId, String bookName, int quantity);

}
