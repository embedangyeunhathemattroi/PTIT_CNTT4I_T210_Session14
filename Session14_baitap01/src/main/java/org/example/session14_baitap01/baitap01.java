package org.example.session14_baitap01;

import jakarta.persistence.criteria.Order;
import jakarta.transaction.Transaction;
import org.hibernate.Session;

public class baitap01 {
    public void processPayment(Long orderId, Long walletId, double totalAmount) {

        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction(); // bắt đầu transaction

            // 1. Cập nhật trạng thái đơn hàng
            Order order = session.get(Order.class, orderId);
            order.setStatus("PAID");
            session.update(order);

            // Giả lập lỗi
            if (true) throw new RuntimeException("Kết nối đến cổng thanh toán thất bại!");

            // 2. Trừ tiền ví
            Wallet wallet = session.get(Wallet.class, walletId);
            wallet.setBalance(wallet.getBalance() - totalAmount);
            session.update(wallet);

            tx.commit(); // commit nếu mọi thứ OK

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback(); // rollback nếu có lỗi
            }

            System.out.println("Lỗi hệ thống: " + e.getMessage());

        } finally {
            session.close();
        }
    }
}
