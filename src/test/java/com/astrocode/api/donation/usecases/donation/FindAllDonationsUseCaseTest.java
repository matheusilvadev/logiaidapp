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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FindAllDonationsUseCaseTest {

    @Mock
    private DonationRepository donationRepository;

    @InjectMocks
    private FindAllDonationsUseCase useCase;

    @Test
    @DisplayName("Deve retornar lista de doações quando houver registros")
    void shouldReturnListWhenNotEmpty() {
        // Mock deve retornar uma lista na primeira chamada (check isEmpty) e na segunda (return)
        List<Donation> list = List.of(mock(Donation.class));
        when(donationRepository.findAll()).thenReturn(list);

        List<Donation> result = useCase.execute();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(donationRepository, times(2)).findAll();
    }

    @Test
    @DisplayName("Deve lançar exceção quando lista estiver vazia")
    void shouldThrowExceptionWhenListIsEmpty() {
        when(donationRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(DonationUseCasesException.class, () -> useCase.execute());
    }

}