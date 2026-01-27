package com.astrocode.api.donation.usecases.donation;

import com.astrocode.api.donation.domain.Donation;
import com.astrocode.api.donation.domain.interfacerepository.DonationRepository;
import com.astrocode.api.donation.usecases.donation.command.CreateDonationCommand;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import com.astrocode.api.shared.utils.InstantUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CreateDonationUseCaseTest {

    @Mock
    private DonationRepository donationRepository;

    @InjectMocks
    private CreateDonationUseCase createDonationUseCase;

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
    @DisplayName("Deve criar e salvar uma doação com sucesso")
    void shouldCreateAndSaveDonation() {
        // Arrange
        CreateDonationCommand.LocationCommand locCmd = new CreateDonationCommand.LocationCommand(
                "Rua A", "10", "Centro", "Cidade", "MA");
        CreateDonationCommand command = new CreateDonationCommand(
                UUID.randomUUID(), UUID.randomUUID(), locCmd);

        when(donationRepository.save(any(Donation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Donation result = createDonationUseCase.execute(command);

        // Assert
        assertNotNull(result);
        assertEquals(command.demandId(), result.getDemandId());

        // Verifica se o repositório foi chamado com os dados corretos
        ArgumentCaptor<Donation> captor = ArgumentCaptor.forClass(Donation.class);
        verify(donationRepository).save(captor.capture());

        Donation captured = captor.getValue();
        assertEquals("Rua A", captured.getPickupLocation().getStreet());
    }

    @Test
    @DisplayName("Deve lançar exceção se o comando for nulo")
    void shouldThrowExceptionWhenCommandIsNull() {
        DonationUseCasesException ex = assertThrows(DonationUseCasesException.class, () -> {
            createDonationUseCase.execute(null);
        });

        assertNotNull(ex);
    }

}