package com.astrocode.api.donation.usecases.donationdemand;

import com.astrocode.api.donation.domain.DonationDemand;
import com.astrocode.api.donation.domain.enums.DemandStatus;
import com.astrocode.api.donation.domain.interfacerepository.DonationDemandRepository;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteDonationDemandByIdTest {

    @Mock
    private DonationDemandRepository donationDemandRepository;

    @InjectMocks
    private DeleteDonationDemandById useCase;

    @Test
    @DisplayName("Deve deletar uma demanda CANCELED com sucesso")
    void shouldDeleteCanceledDemand() {
        UUID id = UUID.randomUUID();
        DonationDemand demand = mock(DonationDemand.class);

        when(demand.getDemandStatus()).thenReturn(DemandStatus.CANCELED);
        when(donationDemandRepository.findById(id)).thenReturn(Optional.of(demand));

        useCase.execute(id);

        verify(donationDemandRepository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar demanda que não está CANCELED")
    void shouldThrowExceptionWhenDeletingActiveDemand() {
        UUID id = UUID.randomUUID();
        DonationDemand demand = mock(DonationDemand.class);

        when(demand.getDemandStatus()).thenReturn(DemandStatus.OPEN);
        when(donationDemandRepository.findById(id)).thenReturn(Optional.of(demand));

        assertThrows(DonationUseCasesException.class, () -> useCase.execute(id));

        verify(donationDemandRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deve lançar exceção se o ID não for encontrado")
    void shouldThrowExceptionWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(donationDemandRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DonationUseCasesException.class, () -> useCase.execute(id));
        verify(donationDemandRepository, never()).deleteById(any());
    }

}