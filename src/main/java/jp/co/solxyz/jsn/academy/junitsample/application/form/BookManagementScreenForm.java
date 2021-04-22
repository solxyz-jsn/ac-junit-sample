package jp.co.solxyz.jsn.academy.junitsample.application.form;

import lombok.Data;

/**
 * 書籍管理画面フォーム
 */
@Data
public class BookManagementScreenForm {

	/** 書籍ID */
	private Integer bookId;

	/** 書籍名 */
	private String bookName;

	/** 在庫数 */
	private Integer stock;

}
