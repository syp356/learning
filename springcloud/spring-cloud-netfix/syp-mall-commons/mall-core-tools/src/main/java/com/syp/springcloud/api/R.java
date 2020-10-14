package com.syp.springcloud.api;

/**
 * @Author: SYP
 * @Date: 2020/9/26
 * @Description:
 */
public class R<T> {
    private int code;
    private String msg;
    private T data;

    public R(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public R(Builder<T> builder) {
        this.code = builder.code;
        this.msg = builder.msg;
        this.data = builder.data;
    }

    public static class Builder<T>{
        private int code;
        private String msg;
        private T data;

        public Builder() {
        }

        public R buildCustomize(int code, String msg){
            this.code = code;
            this.msg = msg;
            return new R(this);
        }

        public R buildOk(){
            this.code = 200;
            this.msg = "success";
            return new R(this);
        }

        public R buildFaill(){
            this.code = -1;
            this.msg = "fail";
            return new R(this);
        }

        public Builder setData(T data){
            this.data = data;
            return this;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }

        public R buildCustomize(String msg) {
            this.code = -1;
            this.msg = msg;
            return new R(this);
        }
    }
}
