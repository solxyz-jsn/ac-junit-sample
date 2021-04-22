package jp.co.solxyz.jsn.academy.junitsample.application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import jp.co.solxyz.jsn.academy.junitsample.application.service.BookManagementService;
import jp.co.solxyz.jsn.academy.junitsample.infrastructure.api.component.BookOrderApi;
import jp.co.solxyz.jsn.academy.junitsample.infrastructure.api.request.BookOrderRequest;
import jp.co.solxyz.jsn.academy.junitsample.infrastructure.api.response.BookOrderResponse;
import jp.co.solxyz.jsn.academy.junitsample.infrastructure.database.dto.BookManagementTableDto;
import jp.co.solxyz.jsn.academy.junitsample.infrastructure.domain.book.service.BookManagementTableService;

/**
 * 書籍管理画面サービス
 */
@Service
public class BookManagementServiceImpl implements BookManagementService {

	/** 書籍管理テーブルサービス */
    @Autowired
    private BookManagementTableService bookManagementTableService;

    /** 書籍発注API */
    @Autowired
    private BookOrderApi bookOrderApi;

	/** {@inheritDoc} */
    @Override
    public List<BookManagementTableDto> init() {
        List<BookManagementTableDto> result = null;
        try {
            result = bookManagementTableService.searchStockInfo();
        } catch (DataAccessException e) {
            // 例外時は本来であればログ等を吐いたりする
        }
        return result;
    }

	/** {@inheritDoc} */
    @Override
    public int update(int bookId, String bookName, int stock) {
        int result = 0;
        BookManagementTableDto dto = new BookManagementTableDto();
        dto.setBookId(bookId);
        dto.setBookName(bookName);
        dto.setStock(stock);
        try {
            bookManagementTableService.updateStockInfo(dto);
            result = 0;
        } catch (DataAccessException e) {
            // 例外時は本来であればログ等を吐いたりする
            result = 1;
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public int order(int bookId, String bookName, int quantity) {
        int result = 0;
        BookOrderRequest request = new BookOrderRequest();
        request.setBookId(bookId);
        request.setQuantity(quantity);

		BookOrderResponse response = null;
		try {
			response = bookOrderApi.order(request);
		} catch (RestClientException e) {
			result = 1;
			return result;
		}

		BookManagementTableDto dto = new BookManagementTableDto();
		dto.setBookId(bookId);
		dto.setBookName(bookName);
		dto.setStock(response.getStock());

		try {
			bookManagementTableService.updateStockInfo(dto);
		} catch (DataAccessException e) {
			// 例外時は本来であればログ等を吐いたりする
			result = 1;
		}
		return result;
	}
}
