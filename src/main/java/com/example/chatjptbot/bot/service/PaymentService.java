package com.example.chatjptbot.bot.service;

import com.example.chatjptbot.bot.entity.Payment;
import com.example.chatjptbot.bot.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

private final PaymentRepository paymentRepository;



@Autowired
public PaymentService(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
}


public void save(Payment payment) {
    paymentRepository.save(payment);
}
public Payment findById(Long id) {
    return paymentRepository.findById(id).orElse(null);
}
public List<Payment> findAll() {
    return paymentRepository.findAll();
}
public void deleteById(Long id) {
    paymentRepository.deleteById(id);
}


}
