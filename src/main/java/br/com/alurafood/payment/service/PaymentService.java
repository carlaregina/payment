package br.com.alurafood.payment.service;

import br.com.alurafood.payment.dto.PaymentDto;
import br.com.alurafood.payment.exceptions.PaymentNotFound;
import br.com.alurafood.payment.model.Payment;
import br.com.alurafood.payment.model.Status;
import br.com.alurafood.payment.repository.PaymentRepository;
import br.com.alurafood.payment.service.interfaces.IPaymentService;
import jakarta.persistence.EntityManager;

import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {

    private PaymentRepository paymentRepository;

    private ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    PaymentService(PaymentRepository paymentRepository, ModelMapper modelMapper) {
        this.paymentRepository = paymentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<PaymentDto> getAllPayments(Pageable pegeable) {
        return paymentRepository
                .findAll(pegeable)
                .map(p -> modelMapper.map(p, PaymentDto.class));

    }

    @Override
    public PaymentDto getPaymentById(Long id) throws PaymentNotFound {
        Payment pagamento = paymentRepository.findById(id)
                .orElseThrow(PaymentNotFound::new);

        return modelMapper.map(pagamento, PaymentDto.class);
    }

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment= modelMapper.map(paymentDto, Payment.class);
        payment.setStatus(Status.CREATED);
        paymentRepository.save(payment);

        return modelMapper.map(payment, PaymentDto.class);
    }

    @Override
    public PaymentDto upDatePayment(Long id, PaymentDto paymentDto) throws PaymentNotFound{
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(PaymentNotFound::new);

        modelMapper.map(paymentDto, payment);
        payment = paymentRepository.save(payment);

        return modelMapper.map(payment, PaymentDto.class);
    }

    @Override
    public void deletePayment(Long id) throws PaymentNotFound {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(PaymentNotFound::new);
        paymentRepository.delete(payment);

    }

}
