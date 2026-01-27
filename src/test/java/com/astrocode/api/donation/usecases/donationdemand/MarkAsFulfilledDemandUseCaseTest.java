package com.astrocode.api.donation.usecases.donationdemand;

import com.astrocode.api.donation.domain.DonationDemand;
import com.astrocode.api.donation.domain.enums.DemandStatus;
import com.astrocode.api.donation.domain.enums.DonationItemCategory;
import com.astrocode.api.donation.domain.interfacerepository.DonationDemandRepository;
import com.astrocode.api.donation.usecases.donationdemand.command.MarksAsFulfilledDemandCommand;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import com.astrocode.api.shared.utils.InstantUtils;
import com.astrocode.api.shared.vo.DonationDemandItem;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MarkAsFulfilledDemandUseCaseTest {

    @Mock
    private DonationDemandRepository donationDemandRepository;

    @InjectMocks
    private MarkAsFulfilledDemandUseCase useCase;

    private MockedStatic<InstantUtils> instantUtilsMock;

    @BeforeEach
    void setUp() {
        instantUtilsMock = mockStatic(InstantUtils.class);
        instantUtilsMock.when(InstantUtils::now).thenReturn(Instant.parse("2026-03-01T16:00:00Z"));
    }

    @AfterEach
    void tearDown() {
        instantUtilsMock.close();
    }

    @Test
    @DisplayName("Deve marcar como cumprida (Fulfilled) e salvar")
    void shouldMarkAsFulfilledAndSave() {
        UUID demandId = UUID.randomUUID();
        MarksAsFulfilledDemandCommand command = new MarksAsFulfilledDemandCommand(demandId);

        DonationDemand acceptedDemand = DonationDemand.create(
                UUID.randomUUID(),
                Location.of("Rua", "1", "B", "C", "UF"),
                List.of(DonationDemandItem.of(DonationItemCategory.FOOD, "Item", 1, "un")),
                "Note"
        ).accept(UUID.randomUUID());

        when(donationDemandRepository.findById(demandId)).thenReturn(Optional.of(acceptedDemand));
        when(donationDemandRepository.save(any(DonationDemand.class))).thenAnswer(i -> i.getArgument(0));

        DonationDemand result = useCase.execute(command);

        assertEquals(DemandStatus.FULFILLED, result.getDemandStatus());
        verify(donationDemandRepository).save(any(DonationDemand.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se comando nulo")
    void shouldThrowExceptionWhenCommandNull() {
        assertThrows(DonationUseCasesException.class, () -> useCase.execute(null));
    }

    @Test
    @DisplayName("Deve lançar exceção se não encontrada")
    void shouldThrowExceptionWhenNotFound() {
        MarksAsFulfilledDemandCommand command = new MarksAsFulfilledDemandCommand(UUID.randomUUID());
        when(donationDemandRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(DonationUseCasesException.class, () -> useCase.execute(command));
    }

}