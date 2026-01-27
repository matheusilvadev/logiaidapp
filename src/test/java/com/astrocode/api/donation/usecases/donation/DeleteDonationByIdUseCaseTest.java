package com.astrocode.api.donation.usecases.donation;

import com.astrocode.api.donation.domain.Donation;
import com.astrocode.api.donation.domain.interfacerepository.DonationRepository;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import com.astrocode.api.shared.utils.InstantUtils;
import com.astrocode.api.shared.vo.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DeleteDonationByIdUseCaseTest {

    @Mock
    private DonationRepository donationRepository;

    @InjectMocks
    private DeleteDonationByIdUseCase useCase;

    private MockedStatic<InstantUtils> instantUtilsMock;

    @BeforeEach
    void setUp() {
        instantUtilsMock = mockStatic(InstantUtils.class);
        instantUtilsMock.when(InstantUtils::now).thenReturn(Instant.now());
    }

    @AfterEach
    void tearDown() {
        instantUtilsMock.close();
    }

    @Test
    @DisplayName("Deve deletar doação CANCELED com sucesso")
    void shouldDeleteCanceledDonation() {
        UUID id = UUID.randomUUID();
        // Cria uma doação e cancela ela para ter o status correto
        Donation donation = Donation.create(UUID.randomUUID(), UUID.randomUUID(), Location.of("R", "1", "B", "C", "UF"));
        Donation canceled = donation.cancel();

        when(donationRepository.findById(id)).thenReturn(Optional.of(canceled));

        useCase.execute(id);

        verify(donationRepository).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção se tentar deletar doação não cancelada")
    void shouldThrowExceptionWhenDeletingActiveDonation() {
        UUID id = UUID.randomUUID();
        Donation active = Donation.create(UUID.randomUUID(), UUID.randomUUID(), Location.of("R", "1", "B", "C", "UF"));

        when(donationRepository.findById(id)).thenReturn(Optional.of(active));

        DonationUseCasesException ex = assertThrows(DonationUseCasesException.class, () -> useCase.execute(id));
    }

    @Test
    @DisplayName("Deve lançar exceção se ID não encontrado")
    void shouldThrowExceptionWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(donationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DonationUseCasesException.class, () -> useCase.execute(id));
    }

}