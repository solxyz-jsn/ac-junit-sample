package jp.co.solxyz.jsn.academy.junitsample.infrastructure.api.response;

import lombok.Data;

@Data
public class BookOrderResponse {
    private int bookId;
    private int stock;
    private int status;
    private String message;
}
