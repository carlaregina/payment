package br.com.alurafood.payment.service.interfaces;

import br.com.alurafood.payment.dto.PaymentDto;

import br.com.alurafood.payment.exceptions.PaymentNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPaymentService {

    Page<PaymentDto> getAllPayments(Pageable pegeable);
    PaymentDto getPaymentById(Long id) throws PaymentNotFound;
    PaymentDto createPayment(PaymentDto paymentDto);
    PaymentDto upDatePayment(Long id, PaymentDto paymentDto) throws PaymentNotFound;
    void deletePayment(Long id) throws PaymentNotFound;

}
