package com.astrocode.api.donation.usecases.donation;

import com.astrocode.api.donation.domain.Donation;
import com.astrocode.api.donation.domain.enums.DonationStatus;
import com.astrocode.api.donation.domain.interfacerepository.DonationRepository;
import com.astrocode.api.donation.usecases.donation.command.MarkDonationAsInTransitCommand;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MakDonationAsInTransitUseCaseTest {

    @Mock
    private DonationRepository donationRepository;

    @InjectMocks
    private MakDonationAsInTransitUseCase useCase;

    private MockedStatic<InstantUtils> instantUtilsMock;

    @BeforeEach
    void setUp() {
        instantUtilsMock = mockStatic(InstantUtils.class);
        instantUtilsMock.when(InstantUtils::now).thenReturn(Instant.parse("2026-01-27T10:00:00Z"));
    }

    @AfterEach
    void tearDown() {
        instantUtilsMock.close();
    }

    @Test
    @DisplayName("Deve marcar doação como em trânsito com sucesso")
    void shouldMarkAsInTransit() {
        UUID id = UUID.randomUUID();
        UUID transporterId = UUID.randomUUID();
        MarkDonationAsInTransitCommand command = new MarkDonationAsInTransitCommand(id, transporterId);

        Donation pendingDonation = Donation.create(UUID.randomUUID(), UUID.randomUUID(), Location.of("R", "1", "B", "C", "UF"));

        when(donationRepository.findById(id)).thenReturn(Optional.of(pendingDonation));
        when(donationRepository.save(any(Donation.class))).thenAnswer(i -> i.getArgument(0));

        Donation result = useCase.execute(command);

        assertEquals(DonationStatus.IN_TRANSIT, result.getStatus());
        assertEquals(transporterId, result.getTransporterId());
        verify(donationRepository).save(any(Donation.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se comando for nulo")
    void shouldThrowExceptionWhenCommandIsNull() {
        assertThrows(DonationUseCasesException.class, () -> useCase.execute(null));
    }

    @Test
    @DisplayName("Deve lançar exceção se doação não encontrada")
    void shouldThrowExceptionWhenDonationNotFound() {
        MarkDonationAsInTransitCommand command = new MarkDonationAsInTransitCommand(UUID.randomUUID(), UUID.randomUUID());
        when(donationRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(DonationUseCasesException.class, () -> useCase.execute(command));
    }

}