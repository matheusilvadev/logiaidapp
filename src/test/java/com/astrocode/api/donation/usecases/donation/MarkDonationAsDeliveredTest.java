package com.astrocode.api.donation.usecases.donation;

import com.astrocode.api.donation.domain.Donation;
import com.astrocode.api.donation.domain.enums.DonationStatus;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MarkDonationAsDeliveredTest {

    @Mock
    private DonationRepository donationRepository;

    @InjectMocks
    private MarkDonationAsDelivered useCase;

    private MockedStatic<InstantUtils> instantUtilsMock;

    @BeforeEach
    void setUp() {
        instantUtilsMock = mockStatic(InstantUtils.class);
        instantUtilsMock.when(InstantUtils::now).thenReturn(Instant.parse("2026-02-20T15:00:00Z"));
    }

    @AfterEach
    void tearDown() {
        instantUtilsMock.close();
    }

    @Test
    @DisplayName("Deve marcar como entregue e salvar com sucesso")
    void shouldMarkAsDeliveredAndSave() {
        UUID id = UUID.randomUUID();
        Donation inTransitDonation = Donation.restore(
                id,
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                Location.of("Rua", "1", "B", "C", "UF"),
                DonationStatus.IN_TRANSIT,
                Instant.now(), Instant.now(), null, null
        );

        when(donationRepository.findById(id)).thenReturn(Optional.of(inTransitDonation));
        when(donationRepository.save(any(Donation.class))).thenAnswer(inv -> inv.getArgument(0));

        Donation result = useCase.execute(id);

        assertEquals(DonationStatus.DELIVERED, result.getStatus());
        verify(donationRepository).save(any(Donation.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se doação não encontrada")
    void shouldThrowExceptionWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(donationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DonationUseCasesException.class, () -> useCase.execute(id));
        verify(donationRepository, never()).save(any());
    }

}