package jp.co.solxyz.jsn.academy.junitsample.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.solxyz.jsn.academy.junitsample.application.form.BookManagementScreenForm;
import jp.co.solxyz.jsn.academy.junitsample.application.service.BookManagementService;
import jp.co.solxyz.jsn.academy.junitsample.infrastructure.database.dto.BookManagementTableDto;
import lombok.AllArgsConstructor;

/**
 * 書籍管理画面コントローラ
 */
@Controller
@RequestMapping("manage/book")
@AllArgsConstructor
public class BookManagementScreenController {

	/** 書籍管理画面サービス */
    @Autowired
    private BookManagementService bookManagementService;

    /**
     * Form初期処理
     * @return Form
     */
    @ModelAttribute
    BookManagementScreenForm setUpForm() {
        return new BookManagementScreenForm();
    }
    
    /**
     * 初期表示
     * @return 画面
     */
    @GetMapping
    public ModelAndView init() {
    	ModelAndView view = new ModelAndView("bookmanager");
    	
    	List<BookManagementTableDto> result = bookManagementService.init();
    	
    	if (result != null) {
    		view.addObject("books", result);
    	} else {
    		view.addObject("message", "書籍情報がありません。");
    	}
    	
        return view;
    }

    /**
     * 書籍変更処理
     * @return 画面
     */
    @PostMapping(params="update")
    public ModelAndView updateBookInfo(BookManagementScreenForm form) {
    	ModelAndView view = new ModelAndView("bookmanager");
    	int status = bookManagementService.update(form.getBookId(), form.getBookName(), form.getStock());
    	
    	if (status == 0) {
    		view.addObject("message", "正常に更新されました。");
    	} else {
    		view.addObject("message", "更新に失敗しました。");
    	}
    	
        return view;
    }

    /**
     * 書籍発注処理
     * @return 画面
     */
    @PostMapping(params="order")
    public ModelAndView orderBook(BookManagementScreenForm form) {
    	ModelAndView view = new ModelAndView("bookmanager");
    	int status = bookManagementService.order(form.getBookId(), form.getBookName(), form.getStock());
    	
    	if (status == 0) {
    		view.addObject("message", "正常に発注されました。");
    	} else {
    		view.addObject("message", "発注に失敗しました。");
    	}
    	
        return view;
    }

}