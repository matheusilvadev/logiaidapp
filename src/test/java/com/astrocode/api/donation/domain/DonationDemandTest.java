package com.astrocode.api.donation.domain;

import com.astrocode.api.donation.domain.enums.DemandStatus;
import com.astrocode.api.donation.domain.enums.DonationItemCategory;
import com.astrocode.api.donation.domain.exceptions.DonationDemandException;
import com.astrocode.api.shared.utils.InstantUtils;
import com.astrocode.api.shared.vo.DonationDemandItem;
import com.astrocode.api.shared.vo.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DonationDemandTest {

    private MockedStatic<InstantUtils> instantUtilsMock;
    private Instant fixedInstant;

    private UUID beneficiaryId;
    private Location validLocation;
    private List<DonationDemandItem> validItems;

    @BeforeEach
    void setUp() {
        // Congela o tempo para testes determinísticos
        fixedInstant = Instant.parse("2026-01-27T10:00:00Z");
        instantUtilsMock = Mockito.mockStatic(InstantUtils.class);
        instantUtilsMock.when(InstantUtils::now).thenReturn(fixedInstant);

        // Dados comuns para os testes
        beneficiaryId = UUID.randomUUID();
        validLocation = Location.of("RUA A", "123", "Centro", "Cidade", "MA");
        validItems = List.of(
                DonationDemandItem.of(DonationItemCategory.FOOD, "Arroz", 5, "kg")
        );
    }

    @AfterEach
    void tearDown() {
        // fecha o mock estático após cada teste para não afetar outros
        instantUtilsMock.close();
    }

    // =========================================================================
    // TESTES DO MÉTODO: create
    // =========================================================================
    @Test
    @DisplayName("Deve criar uma demanda com sucesso (Status OPEN e datas iniciais)")
    void shouldCreateDonationDemandSuccessfully() {
        String notes = "Urgente";

        DonationDemand demand = DonationDemand.create(beneficiaryId, validLocation, validItems, notes);

        assertNotNull(demand.getId());
        assertEquals(beneficiaryId, demand.getBeneficiaryId());
        assertNull(demand.getDonorId()); // Não tem doador ao criar
        assertEquals(DemandStatus.OPEN, demand.getDemandStatus());
        assertEquals(validLocation, demand.getDeliveryLocation());
        assertEquals(notes, demand.getNotes());
        assertEquals(validItems, demand.getItems());

        // Verifica timestamps
        assertEquals(fixedInstant, demand.getCreatedAt());
        assertEquals(fixedInstant, demand.getUpdatedAt());
        assertNull(demand.getAcceptedAt());
        assertNull(demand.getFulfilledAt());
        assertNull(demand.getCanceledAt());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar demanda sem itens")
    void shouldThrowExceptionWhenCreatingWithoutItems() {
        DonationDemandException exception = assertThrows(DonationDemandException.class, () -> {
            DonationDemand.create(beneficiaryId, validLocation, Collections.emptyList(), "Notes");
        });

        assertEquals("Donation Demand must contain at least one item", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar demanda com lista de itens nula")
    void shouldThrowExceptionWhenCreatingWithNullItems() {
        assertThrows(DonationDemandException.class, () -> {
            DonationDemand.create(beneficiaryId, validLocation, null, "Notes");
        });
    }

    // =========================================================================
    // TESTES DO MÉTODO: restore
    // =========================================================================

    @Test
    @DisplayName("Deve restaurar uma demanda com todos os atributos preservados")
    void shouldRestoreDonationDemand() {
        UUID id = UUID.randomUUID();
        UUID donorId = UUID.randomUUID();
        Instant createdAt = fixedInstant.minusSeconds(3600);

        DonationDemand restored = DonationDemand.restore(
                id, beneficiaryId, donorId, DemandStatus.ACCEPTED,
                validLocation, "Notes", validItems,
                createdAt, fixedInstant, fixedInstant, null, null
        );

        assertEquals(id, restored.getId());
        assertEquals(donorId, restored.getDonorId());
        assertEquals(DemandStatus.ACCEPTED, restored.getDemandStatus());
        assertEquals(createdAt, restored.getCreatedAt()); // Mantém data antiga
        assertEquals(fixedInstant, restored.getUpdatedAt());
    }

    // =========================================================================
    // TESTES DO MÉTODO: accept
    // =========================================================================

    @Test
    @DisplayName("Deve aceitar uma demanda com sucesso (Transição OPEN -> ACCEPTED)")
    void shouldAcceptDonationDemand() {
        DonationDemand demand = DonationDemand.create(beneficiaryId, validLocation, validItems, "Notes");
        UUID donorId = UUID.randomUUID();

        // Avança o tempo simulado para o momento do aceite
        Instant acceptedTime = fixedInstant.plusSeconds(600);
        instantUtilsMock.when(InstantUtils::now).thenReturn(acceptedTime);

        DonationDemand acceptedDemand = demand.accept(donorId);

        assertEquals(DemandStatus.ACCEPTED, acceptedDemand.getDemandStatus());
        assertEquals(donorId, acceptedDemand.getDonorId());
        assertEquals(acceptedTime, acceptedDemand.getAcceptedAt()); // Deve ter nova data
        assertEquals(acceptedTime, acceptedDemand.getUpdatedAt());  // UpdatedAt também atualiza
        assertEquals(fixedInstant, acceptedDemand.getCreatedAt());  // CreatedAt mantém original
    }

    @Test
    @DisplayName("Deve lançar exceção se tentar aceitar demanda que não está OPEN")
    void shouldThrowExceptionWhenAcceptingNonOpenDemand() {
        // Cria uma demanda já cancelada (simulando via restore para forçar o estado)
        DonationDemand canceledDemand = DonationDemand.restore(
                UUID.randomUUID(), beneficiaryId, null, DemandStatus.CANCELED,
                validLocation, null, validItems, fixedInstant, fixedInstant, null, null, fixedInstant
        );

        UUID donorId = UUID.randomUUID();

        DonationDemandException exception = assertThrows(DonationDemandException.class, () -> {
            canceledDemand.accept(donorId);
        });

        assertTrue(exception.getMessage().contains("Only OPEN demands can be ACCEPTED"));
    }

    @Test
    @DisplayName("Deve lançar exceção se o doador for nulo")
    void shouldThrowExceptionWhenDonorIsNull() {
        DonationDemand demand = DonationDemand.create(beneficiaryId, validLocation, validItems, "Notes");

        assertThrows(NullPointerException.class, () -> demand.accept(null));
    }

    @Test
    @DisplayName("Deve lançar exceção se o beneficiário tentar ser o próprio doador")
    void shouldThrowExceptionWhenBeneficiaryIsTheDonor() {
        DonationDemand demand = DonationDemand.create(beneficiaryId, validLocation, validItems, "Notes");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            demand.accept(beneficiaryId); // Passando o mesmo ID do beneficiário
        });

        assertEquals("Beneficiary cannot be the donor", exception.getMessage());
    }

    // =========================================================================
    // TESTES DO MÉTODO: markFulfilled
    // =========================================================================

    @Test
    @DisplayName("Deve marcar como cumprida com sucesso (Transição ACCEPTED -> FULFILLED)")
    void shouldMarkDemandAsFulfilled() {
        // Precisa estar ACCEPTED antes
        DonationDemand demand = DonationDemand.create(beneficiaryId, validLocation, validItems, "Notes")
                .accept(UUID.randomUUID());

        Instant fulfilledTime = fixedInstant.plusSeconds(1200);
        instantUtilsMock.when(InstantUtils::now).thenReturn(fulfilledTime);

        DonationDemand fulfilledDemand = demand.markFulfilled();

        assertEquals(DemandStatus.FULFILLED, fulfilledDemand.getDemandStatus());
        assertEquals(fulfilledTime, fulfilledDemand.getFulfilledAt());
        assertEquals(fulfilledTime, fulfilledDemand.getUpdatedAt());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cumprir demanda que não está ACCEPTED")
    void shouldThrowExceptionWhenFulfillingNonAcceptedDemand() {
        // Demanda ainda OPEN
        DonationDemand openDemand = DonationDemand.create(beneficiaryId, validLocation, validItems, "Notes");

        DonationDemandException exception = assertThrows(DonationDemandException.class,
                openDemand::markFulfilled);

        assertTrue(exception.getMessage().contains("Only ACCEPTED demands can be FULFILLED"));
    }

    // =========================================================================
    // TESTES DO MÉTODO: cancel
    // =========================================================================

    @Test
    @DisplayName("Deve cancelar uma demanda OPEN com sucesso")
    void shouldCancelOpenDemand() {
        DonationDemand demand = DonationDemand.create(beneficiaryId, validLocation, validItems, "Notes");

        Instant cancelTime = fixedInstant.plusSeconds(300);
        instantUtilsMock.when(InstantUtils::now).thenReturn(cancelTime);

        DonationDemand canceledDemand = demand.cancel();

        assertEquals(DemandStatus.CANCELED, canceledDemand.getDemandStatus());
        assertEquals(cancelTime, canceledDemand.getCanceledAt());
    }

    @Test
    @DisplayName("Deve cancelar uma demanda ACCEPTED com sucesso")
    void shouldCancelAcceptedDemand() {
        DonationDemand demand = DonationDemand.create(beneficiaryId, validLocation, validItems, "Notes")
                .accept(UUID.randomUUID());

        DonationDemand canceledDemand = demand.cancel();
        assertEquals(DemandStatus.CANCELED, canceledDemand.getDemandStatus());
    }

    @Test
    @DisplayName("Não deve fazer nada se cancelar uma demanda já CANCELED (Idempotência)")
    void shouldReturnSameObjectIfAlreadyCanceled() {
        DonationDemand demand = DonationDemand.create(beneficiaryId, validLocation, validItems, "Notes")
                .cancel();

        // Tenta cancelar de novo
        DonationDemand doubleCanceled = demand.cancel();

        // Verifica se é a mesma instância (return this) e o status não mudou
        assertSame(demand, doubleCanceled);
        assertEquals(DemandStatus.CANCELED, doubleCanceled.getDemandStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cancelar uma demanda FULFILLED")
    void shouldThrowExceptionWhenCancelingFulfilledDemand() {
        // Cria cenário FULFILLED
        DonationDemand fulfilledDemand = DonationDemand.create(beneficiaryId, validLocation, validItems, "Notes")
                .accept(UUID.randomUUID())
                .markFulfilled();

        DonationDemandException exception = assertThrows(DonationDemandException.class,
                fulfilledDemand::cancel);

        assertEquals("Cannot cancel a fulfilled demand", exception.getMessage());
    }

    // =========================================================================
    // TESTES DE COBERTURA (Getters, Equals, HashCode, ToString)
    // =========================================================================

    @Test
    @DisplayName("Deve garantir que todos os getters retornam os valores corretos")
    void shouldVerifyAllGetters() {
        DonationDemand demand = DonationDemand.create(beneficiaryId, validLocation, validItems, "Notes");

        // Precisamos aceitar e cancelar para ter datas preenchidas para teste
        // Nota: Usamos mocks de tempo aqui para garantir que não sejam nulos se a lógica mudar
        Instant now = Instant.now();
        instantUtilsMock.when(InstantUtils::now).thenReturn(now);

        // Criamos um objeto totalmente preenchido via restore para testar todos os campos de uma vez
        DonationDemand fullDemand = DonationDemand.restore(
                UUID.randomUUID(),
                beneficiaryId,
                UUID.randomUUID(),
                DemandStatus.CANCELED,
                validLocation,
                "Notes",
                validItems,
                now, now, now, now, now
        );

        assertNotNull(fullDemand.getId());
        assertEquals(beneficiaryId, fullDemand.getBeneficiaryId());
        assertNotNull(fullDemand.getDonorId());
        assertEquals(DemandStatus.CANCELED, fullDemand.getDemandStatus());
        assertEquals(validLocation, fullDemand.getDeliveryLocation());
        assertEquals("Notes", fullDemand.getNotes());
        assertEquals(validItems, fullDemand.getItems());
        assertEquals(now, fullDemand.getCreatedAt());
        assertEquals(now, fullDemand.getUpdatedAt());
        assertEquals(now, fullDemand.getAcceptedAt());
        assertEquals(now, fullDemand.getFulfilledAt());
        assertEquals(now, fullDemand.getCanceledAt());
    }

    @Test
    @DisplayName("Deve verificar contratos de Equals e HashCode")
    void shouldVerifyEqualsAndHashCode() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        // Cria duas demandas com o MESMO ID (devem ser iguais)
        DonationDemand demand1 = DonationDemand.restore(id1, beneficiaryId, null, DemandStatus.OPEN, validLocation, null, validItems, fixedInstant, fixedInstant, null, null, null);
        DonationDemand demand1Clone = DonationDemand.restore(id1, beneficiaryId, null, DemandStatus.OPEN, validLocation, null, validItems, fixedInstant, fixedInstant, null, null, null);

        // Cria uma demanda com ID DIFERENTE (deve ser diferente)
        DonationDemand demand2 = DonationDemand.restore(id2, beneficiaryId, null, DemandStatus.OPEN, validLocation, null, validItems, fixedInstant, fixedInstant, null, null, null);

        // Teste de Igualdade (Equals)
        assertEquals(demand1, demand1);             // Reflexivo (é igual a si mesmo)
        assertEquals(demand1, demand1Clone);        // Simétrico (tem o mesmo ID)
        assertNotEquals(demand1, demand2);          // Diferente (IDs diferentes)
        assertNotEquals(demand1, null);             // Não é igual a null
        assertNotEquals(demand1, new Object());     // Não é igual a outra classe

        // Teste de HashCode
        assertEquals(demand1.hashCode(), demand1Clone.hashCode()); // HashCode deve ser igual para objetos iguais
        assertNotEquals(demand1.hashCode(), demand2.hashCode());   // HashCode idealmente diferente para objetos diferentes
    }

    @Test
    @DisplayName("Deve verificar se o toString gera a string contendo o ID")
    void shouldVerifyToString() {
        UUID id = UUID.randomUUID();
        DonationDemand demand = DonationDemand.restore(id, beneficiaryId, null, DemandStatus.OPEN, validLocation, "Notes", validItems, fixedInstant, fixedInstant, null, null, null);

        String result = demand.toString();

        assertNotNull(result);
        assertTrue(result.contains(id.toString())); // Garante que pelo menos o ID está na representação textual
        assertTrue(result.contains("DonationDemand"));
    }


}