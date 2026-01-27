package com.astrocode.api.donation.usecases.donationdemand;

import com.astrocode.api.donation.domain.DonationDemand;
import com.astrocode.api.donation.domain.interfacerepository.DonationDemandRepository;
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
class FindAllDonationDemandUseCaseTest {

    @Mock
    private DonationDemandRepository donationDemandRepository;

    @InjectMocks
    private FindAllDonationDemandUseCase useCase;

    @Test
    @DisplayName("Deve retornar lista de demandas quando não estiver vazia")
    void shouldReturnAllDemands() {
        List<DonationDemand> list = List.of(mock(DonationDemand.class));

        // Configura o mock para retornar a lista nas chamadas
        when(donationDemandRepository.findAll()).thenReturn(list);

        List<DonationDemand> result = useCase.execute();

        assertFalse(result.isEmpty());
        assertEquals(list, result);
        // O método executa findAll() duas vezes (uma no if, outra no return)
        verify(donationDemandRepository, times(2)).findAll();
    }

    @Test
    @DisplayName("Deve lançar exceção se a lista estiver vazia")
    void shouldThrowExceptionWhenListIsEmpty() {
        when(donationDemandRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(DonationUseCasesException.class, () -> useCase.execute());
    }

}