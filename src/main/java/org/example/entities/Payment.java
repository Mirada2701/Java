package org.example.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    @Column(nullable = false, precision = 10,scale = 2)
    private BigDecimal amount;
    @Column(length = 50)
    private String payment_method;
    @Column(length = 50)
    private String status = "pending";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
