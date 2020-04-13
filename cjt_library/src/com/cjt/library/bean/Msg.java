package com.cjt.library.bean;

public class Msg {

        private String result;

        private Object message;

    /**
     * 得到结果
     * @return
     */
    public String getResult() {
            return result;
        }

    /**
     * 设置结果
     * @param result
     */
    public void setResult(String result) {
            this.result = result;
        }

    /**
     * 得到信息
     * @return
     */
    public Object getMessage() { return message; }

    /**
     * 设置信息
     * @param message
     */
        public void setMessage(Object message) {
            this.message = message;
        }

    /**
     * 设置Msg的构造方法
     * @param result
     * @param message
     */
    public Msg(String result, Object message) {
            super();
            this.result = result;
            this.message = message;
        }

        @Override
        public String toString() {
            return "Msg :" + result + "  信息 [ " + message +" ]";
        }

    }

