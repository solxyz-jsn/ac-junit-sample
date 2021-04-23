package jp.co.solxyz.jsn.academy.junitsample.infrastructure.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.solxyz.jsn.academy.junitsample.infrastructure.database.dto.BookManagementTableDto;

/**
 * 商品管理テーブルリポジトリ
 */
@Repository
public interface  BookManagementTableRepository extends JpaRepository<BookManagementTableDto, Integer> {

}