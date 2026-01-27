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

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FindDonationByIdUseCaseTest {

    @Mock
    private DonationRepository donationRepository;

    @InjectMocks
    private FindDonationByIdUseCase useCase;

    @Test
    @DisplayName("Deve retornar doação por ID")
    void shouldReturnDonation() {
        UUID id = UUID.randomUUID();
        Donation donation = mock(Donation.class);
        when(donationRepository.findById(id)).thenReturn(Optional.of(donation));

        Donation result = useCase.execute(id);

        assertEquals(donation, result);
    }

    @Test
    @DisplayName("Deve lançar exceção se não encontrada")
    void shouldThrowExceptionWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(donationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DonationUseCasesException.class, () -> useCase.execute(id));
    }

}