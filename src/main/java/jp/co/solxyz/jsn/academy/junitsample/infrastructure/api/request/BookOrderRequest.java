package jp.co.solxyz.jsn.academy.junitsample.infrastructure.api.request;

import lombok.Data;

@Data
public class BookOrderRequest {
    private int bookId;
    private int quantity;
}
