package br.com.alurafood.payment.controller;

import br.com.alurafood.payment.dto.PaymentDto;
import br.com.alurafood.payment.exceptions.PaymentNotFound;
import br.com.alurafood.payment.service.interfaces.IPaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.service.spi.InjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private IPaymentService paymentService;

    public PaymentController(IPaymentService paymentService){
        this.paymentService = paymentService;

    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getById(@PathVariable @NotNull Long id) throws PaymentNotFound {
        PaymentDto paymentDto = paymentService.getPaymentById(id);
        return ResponseEntity.ok(paymentDto);
    }

    @GetMapping
    public Page<PaymentDto> list(@PageableDefault(size = 10) Pageable peageble) {
        return paymentService.getAllPayments(peageble);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> register(@RequestBody @Valid PaymentDto dto, UriComponentsBuilder uriBuilder) {
        PaymentDto paymentDto = paymentService.createPayment(dto);
        URI address = uriBuilder.path("/payments/{id}").buildAndExpand(paymentDto.getId()).toUri();

        return ResponseEntity.created(address).body(paymentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> update(@PathVariable @NotNull Long id, @RequestBody @Valid PaymentDto dto) throws PaymentNotFound {
        PaymentDto paymentDto = paymentService.upDatePayment(id, dto);
        return ResponseEntity.ok(paymentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDto> remove(@PathVariable @NotNull Long id) throws PaymentNotFound {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }


}
