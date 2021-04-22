package jp.co.solxyz.jsn.academy.junitsample.infrastructure.domain.book.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.solxyz.jsn.academy.junitsample.infrastructure.database.dto.BookManagementTableDto;
import jp.co.solxyz.jsn.academy.junitsample.infrastructure.database.repository.BookManagementTableRepository;
import jp.co.solxyz.jsn.academy.junitsample.infrastructure.domain.book.service.BookManagementTableService;
import lombok.AllArgsConstructor;

/**
 * 書籍管理テーブルサービス
 */
@Service
@AllArgsConstructor
public class BookManagementTableServiceImpl implements BookManagementTableService {

	/** 書籍管理テーブルリポジトリ */
	@Autowired
	private BookManagementTableRepository bookManagementTableRepository;

	/** {@inheritDoc} */
	@Override
	public List<BookManagementTableDto> searchStockInfo() {
		return bookManagementTableRepository.findAll();
	}

	/** {@inheritDoc} */
	@Override
	@Transactional
	public BookManagementTableDto updateStockInfo(BookManagementTableDto dto) {
		return bookManagementTableRepository.saveAndFlush(dto);
	}

}