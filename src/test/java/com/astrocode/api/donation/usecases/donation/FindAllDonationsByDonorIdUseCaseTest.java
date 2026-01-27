package com.astrocode.api.donation.usecases.donation;

import com.astrocode.api.donation.domain.Donation;
import com.astrocode.api.donation.domain.interfacerepository.DonationRepository;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FindAllDonationsByDonorIdUseCaseTest {

    @Mock
    private DonationRepository donationRepository;

    @InjectMocks
    private FindAllDonationsByDonorIdUseCase useCase;

    @Test
    @DisplayName("Deve retornar lista do doador quando houver registros globais")
    void shouldReturnDonorList() {
        UUID donorId = UUID.randomUUID();
        when(donationRepository.findAll()).thenReturn(List.of(mock(Donation.class)));

        List<Donation> donorList = List.of(mock(Donation.class));
        when(donationRepository.findByDonorId(donorId)).thenReturn(donorList);

        List<Donation> result = useCase.execute(donorId);

        assertEquals(donorList, result);
    }

    @Test
    @DisplayName("Deve lançar exceção se não houver NENHUMA doação no sistema")
    void shouldThrowExceptionWhenGlobalListIsEmpty() {
        when(donationRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(DonationUseCasesException.class, () -> useCase.execute(UUID.randomUUID()));
    }

}