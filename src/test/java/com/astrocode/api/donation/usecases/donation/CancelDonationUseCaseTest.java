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
import org.mockito.ArgumentCaptor;
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
class CancelDonationUseCaseTest {

    @Mock
    private DonationRepository donationRepository;

    @InjectMocks
    private CancelDonationUseCase useCase;

    private MockedStatic<InstantUtils> instantUtilsMock;

    @BeforeEach
    void setUp() {
        instantUtilsMock = mockStatic(InstantUtils.class);
        instantUtilsMock.when(InstantUtils::now).thenReturn(Instant.parse("2026-02-20T10:00:00Z"));
    }

    @AfterEach
    void tearDown() {
        instantUtilsMock.close();
    }

    @Test
    @DisplayName("Deve cancelar e salvar a doação com sucesso")
    void shouldCancelAndSaveDonation() {
        UUID id = UUID.randomUUID();
        // Cria uma doação ativa
        Donation activeDonation = Donation.create(
                UUID.randomUUID(),
                UUID.randomUUID(),
                Location.of("Rua", "1", "Bairro", "Cidade", "UF")
        );

        when(donationRepository.findById(id)).thenReturn(Optional.of(activeDonation));
        // Configura o save para retornar o objeto que foi passado para ele
        when(donationRepository.save(any(Donation.class))).thenAnswer(inv -> inv.getArgument(0));

        Donation result = useCase.execute(id);

        // Asserções
        assertEquals(DonationStatus.CANCELED, result.getStatus());

        // Verifica se o repositório realmente salvou
        ArgumentCaptor<Donation> captor = ArgumentCaptor.forClass(Donation.class);
        verify(donationRepository).save(captor.capture());

        assertEquals(DonationStatus.CANCELED, captor.getValue().getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção se ID não encontrado")
    void shouldThrowExceptionWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(donationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DonationUseCasesException.class, () -> useCase.execute(id));

        // Garante que nada foi salvo se falhou na busca
        verify(donationRepository, never()).save(any());
    }

}