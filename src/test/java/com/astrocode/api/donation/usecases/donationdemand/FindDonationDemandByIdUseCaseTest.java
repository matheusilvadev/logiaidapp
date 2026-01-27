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

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindDonationDemandByIdUseCaseTest {

    @Mock
    private DonationDemandRepository donationDemandRepository;

    @InjectMocks
    private FindDonationDemandByIdUseCase useCase;

    @Test
    @DisplayName("Deve retornar a demanda quando encontrada")
    void shouldReturnDemandById() {
        UUID id = UUID.randomUUID();
        DonationDemand demand = mock(DonationDemand.class);
        when(donationDemandRepository.findById(id)).thenReturn(Optional.of(demand));

        DonationDemand result = useCase.execute(id);

        assertEquals(demand, result);
    }

    @Test
    @DisplayName("Deve lançar exceção se não encontrada")
    void shouldThrowExceptionWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(donationDemandRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DonationUseCasesException.class, () -> useCase.execute(id));
    }

}