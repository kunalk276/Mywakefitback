package com.wakefit.ecommerce.service;

import com.wakefit.ecommerce.entity.Payment;

public interface PaymentService {

    Payment addPayment(Payment payment);

    Payment getPaymentById(Long paymentId);
}
