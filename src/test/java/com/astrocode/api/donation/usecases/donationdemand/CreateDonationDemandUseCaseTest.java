package com.astrocode.api.donation.usecases.donationdemand;

import com.astrocode.api.donation.domain.DonationDemand;
import com.astrocode.api.donation.domain.enums.DonationItemCategory;
import com.astrocode.api.donation.domain.interfacerepository.DonationDemandRepository;
import com.astrocode.api.donation.usecases.donationdemand.command.CreateDonationDemandCommand;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CreateDonationDemandUseCaseTest {

    @Mock
    private DonationDemandRepository donationDemandRepository;

    @InjectMocks
    private CreateDonationDemandUseCase useCase;

    private MockedStatic<InstantUtils> instantUtilsMock;

    @BeforeEach
    void setUp() {
        instantUtilsMock = mockStatic(InstantUtils.class);
        instantUtilsMock.when(InstantUtils::now).thenReturn(Instant.parse("2026-03-01T10:00:00Z"));
    }

    @AfterEach
    void tearDown() {
        instantUtilsMock.close();
    }

    @Test
    @DisplayName("Deve criar e salvar uma demanda de doação com sucesso")
    void shouldCreateAndSaveDemand() {
        // Arrange
        Location location = Location.of("Rua X", "10", "Centro", "Cidade", "UF");
        List<DonationDemandItem> items = List.of(
                DonationDemandItem.of(DonationItemCategory.FOOD, "Arroz", 2, "kg")
        );
        CreateDonationDemandCommand command = new CreateDonationDemandCommand(
                UUID.randomUUID(), location, items, "Notas"
        );

        when(donationDemandRepository.save(any(DonationDemand.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // Act
        DonationDemand result = useCase.execute(command);

        // Assert
        assertNotNull(result);
        assertEquals(command.beneficiaryId(), result.getBeneficiaryId());
        assertEquals("Notas", result.getNotes());

        verify(donationDemandRepository).save(any(DonationDemand.class));
    }

    @Test
    @DisplayName("Deve lançar exceção se o comando for nulo")
    void shouldThrowExceptionWhenCommandIsNull() {
        assertThrows(DonationUseCasesException.class, () -> useCase.execute(null));
        verify(donationDemandRepository, never()).save(any());
    }

}