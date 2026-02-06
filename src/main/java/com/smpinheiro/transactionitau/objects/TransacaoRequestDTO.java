package com.smpinheiro.transactionitau.objects;

import java.time.OffsetDateTime;

public record TransacaoRequestDTO(Double value, OffsetDateTime dataHora) {

}
