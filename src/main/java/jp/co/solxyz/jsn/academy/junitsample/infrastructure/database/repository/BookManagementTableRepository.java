package jp.co.solxyz.jsn.academy.junitsample.infrastructure.database.repository;

import jp.co.solxyz.jsn.academy.junitsample.infrastructure.database.dto.BookManagementTableDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 商品管理テーブルリポジトリ
 */
@Repository
public interface  BookManagementTableRepository extends JpaRepository<BookManagementTableDto, Integer> {

}