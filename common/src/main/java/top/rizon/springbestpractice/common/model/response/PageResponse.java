package top.rizon.springbestpractice.common.model.response;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.rizon.springbestpractice.common.model.request.PageParam;

/**
 * @author Rizon
 * @date 2019/12/3
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class PageResponse<T> extends Response<T> {
    private PageParam pagination;

    public PageResponse(PageParam pagination) {
        this.pagination = pagination;
    }

    public PageResponse(int code, String message, T data, PageParam pagination) {
        super(code, message, data);
        this.pagination = pagination;
    }

    public static <T> PageResponse<T> success(T data, PageParam pagination) {
        return new PageResponse<>(CODE_SUCCESS, MESSAGE_SUCCESS, data, pagination);
    }

    public static <T> PageResponse<T> success(String message, T data, PageParam pagination) {
        return new PageResponse<>(CODE_SUCCESS, message, data, pagination);
    }

    public static <T> PageResponse<T> failure(T data, PageParam pagination) {
        return new PageResponse<>(CODE_FAILURE, MESSAGE_FAILURE, data, pagination);
    }

    public static <T> PageResponse<T> failure(String message, T data, PageParam pagination) {
        return new PageResponse<>(CODE_FAILURE, message, data, pagination);
    }


}
