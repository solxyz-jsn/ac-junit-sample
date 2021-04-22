package jp.co.solxyz.jsn.academy.junitsample.infrastructure.domain.book.service;

import jp.co.solxyz.jsn.academy.junitsample.infrastructure.database.dto.BookManagementTableDto;

import java.util.List;

/**
 * 書籍在庫情報処理サービス
 */
public interface BookManagementTableService {

    /**
     * 書籍在庫情報全検索
     * @return 書籍在庫情報リスト
     */
    List<BookManagementTableDto> searchStockInfo();

    /**
     * 書籍在庫情報更新
     * @param dto 更新情報
     * @return 書籍更新結果DTO
     */
    BookManagementTableDto updateStockInfo(BookManagementTableDto dto);

}