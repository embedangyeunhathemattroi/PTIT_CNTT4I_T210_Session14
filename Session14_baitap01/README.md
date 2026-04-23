# Phần 1 – Phân tích Transaction

## Trace logic code hiện tại

1. Mở session
2. Lấy Order
3. setStatus("PAID")
4. session.update(order)
5. Bị exception
6. catch → chỉ in log
7. đóng session

---

## Vấn đề nằm ở đâu?

Code KHÔNG sử dụng Transaction:

- Không có beginTransaction()
- Không có commit()
- Không có rollback()

---

## Hậu quả

Hibernate có thể:

- Auto flush dữ liệu xuống DB trước khi exception xảy ra
- Order đã bị update thành "PAID"

Nhưng:

- Wallet chưa kịp trừ tiền → do exception

→ Dữ liệu bị lệch (inconsistent)

---

## Thiếu gì trong vòng đời Transaction?

Thiếu 3 thứ quan trọng:

1. beginTransaction()
   → bắt đầu giao dịch

2. commit()
   → xác nhận toàn bộ thay đổi

3. rollback()
   → quay lại trạng thái ban đầu nếu có lỗi

---

## Kết luận

Không có transaction:

- Các thao tác không còn là "atomic"
- Dữ liệu dễ bị sai lệch khi có lỗi giữa chừng