package top.rizon.springbestpractice.web.config;

/**
 * @author Rizon
 * @date 2019-08-27
 */
public interface InitConfig {
    /**
     * 程序启动完成后会调用该方法
     * 抛出异常则会导致程序终止
     *
     * @throws Exception
     */
    void init() throws Exception;
}
