package br.com.alurafood.payment.exceptions;

import br.com.alurafood.payment.configurations.Translator;

public class PaymentNotFound extends Exception{

    public PaymentNotFound() {
        super(Translator.toLocale("payment_not_found"));
    }

    public PaymentNotFound(String message) {
        super(message);
    }
}
