package dev.j3c.mspractice.mapper;

import dev.j3c.mspractice.collection.TransactionResult;
import dev.j3c.mspractice.dto.TransactionResultDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TransactionResultMapper {

    public Function<TransactionResultDto, TransactionResult> mapFromDtoToEntity() {
        return (TransactionResultDto transactionResultDto) -> TransactionResult.builder()
                .id(transactionResultDto.getId())
                .userId(transactionResultDto.getUserId())
                .hasErrors(transactionResultDto.isHasErrors())
                .message(transactionResultDto.getMessage())
                .date(transactionResultDto.getDate())
                .build();
    }

    public Function<TransactionResult, TransactionResultDto> mapFromEntityToDto() {
        return (TransactionResult transactionResult) -> TransactionResultDto.builder()
                .id(transactionResult.getId())
                .userId(transactionResult.getUserId())
                .hasErrors(transactionResult.isHasErrors())
                .message(transactionResult.getMessage())
                .date(transactionResult.getDate())
                .build();
    }

}
