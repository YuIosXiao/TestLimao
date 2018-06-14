package com.first.saccelerator.model;

/**
 * Created by lmn on 2017/4/20 0019.
 * Comsunny支付创建订单(认证) 解析类
 */
public class VerifyIapResponse {

    /**
     * transaction_log : {"order_number":"20170420149265024871350863"}
     */

    private TransactionLogBean transaction_log;

    public TransactionLogBean getTransaction_log() {
        return transaction_log;
    }

    public void setTransaction_log(TransactionLogBean transaction_log) {
        this.transaction_log = transaction_log;
    }

    public static class TransactionLogBean {
        /**
         * order_number : 20170527149586928854346b6
         * payment_url : https://dispatch.5ydoc.com/Api/getOrderView?order_id=20170527149586928854346b6
         */

        private String order_number;
        private String payment_url;

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getPayment_url() {
            return payment_url;
        }

        public void setPayment_url(String payment_url) {
            this.payment_url = payment_url;
        }

        @Override
        public String toString() {
            return "TransactionLogBean{" +
                    "order_number='" + order_number + '\'' +
                    ", payment_url='" + payment_url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "VerifyIapResponse{" +
                "transaction_log=" + transaction_log +
                '}';
    }
}
