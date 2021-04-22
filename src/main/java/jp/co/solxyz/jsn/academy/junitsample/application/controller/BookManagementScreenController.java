package jp.co.solxyz.jsn.academy.junitsample.application.controller;

import jp.co.solxyz.jsn.academy.junitsample.application.service.BookManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 書籍管理画面
 */
@Controller
@RequestMapping("manage/book")
public class BookManagementScreenController {

    @Autowired
    private BookManagementService bookManagementService;

    /**
     * 初期表示
     * @return
     */
    @GetMapping
    public ModelAndView init() {
        return new ModelAndView("bookmanager");
    }

    /**
     * 書籍変更処理
     */
    @PostMapping
    public ModelAndView updateStock() {
        return new ModelAndView("bookmanager");
    }

    /**
     * 発注処理
     */
    @PostMapping
    public ModelAndView orderBook() {
        return new ModelAndView("bookmanager");
    }

}