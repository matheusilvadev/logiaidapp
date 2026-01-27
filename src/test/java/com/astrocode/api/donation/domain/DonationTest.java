package com.astrocode.api.donation.domain;

import com.astrocode.api.donation.domain.enums.DonationStatus;
import com.astrocode.api.donation.domain.exceptions.DonationException;
import com.astrocode.api.shared.utils.InstantUtils;
import com.astrocode.api.shared.vo.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DonationTest {

    private MockedStatic<InstantUtils> instantUtilsMock;
    private Instant fixedInstant;

    private UUID demandId;
    private UUID donorId;
    private Location pickupLocation;

    @BeforeEach
    void setUp() {
        // Congela o tempo
        fixedInstant = Instant.parse("2026-02-01T12:00:00Z");
        instantUtilsMock = Mockito.mockStatic(InstantUtils.class);
        instantUtilsMock.when(InstantUtils::now).thenReturn(fixedInstant);

        // Dados base
        demandId = UUID.randomUUID();
        donorId = UUID.randomUUID();
        pickupLocation = Location.of("Rua B", "456", "Bairro", "Cidade", "SP");
    }

    @AfterEach
    void tearDown() {
        instantUtilsMock.close();
    }

    // =========================================================================
    // TESTES DO MÉTODO: create
    // =========================================================================

    @Test
    @DisplayName("Deve criar uma doação com sucesso (Status PENDING_PICKUP)")
    void shouldCreateDonationSuccessfully() {
        Donation donation = Donation.create(demandId, donorId, pickupLocation);

        assertNotNull(donation.getId());
        assertEquals(demandId, donation.getDemandId());
        assertEquals(donorId, donation.getDonorId());
        assertNull(donation.getTransporterId()); // Não tem transportador no início
        assertEquals(pickupLocation, donation.getPickupLocation());
        assertEquals(DonationStatus.PENDING_PICKUP, donation.getStatus());

        assertEquals(fixedInstant, donation.getCreatedAt());
        assertEquals(fixedInstant, donation.getUpdatedAt());
        assertNull(donation.getDeliveredAt());
        assertNull(donation.getCanceledAt());
    }

    // =========================================================================
    // TESTES DO MÉTODO: restore
    // =========================================================================

    @Test
    @DisplayName("Deve restaurar uma doação com todos os atributos preservados")
    void shouldRestoreDonation() {
        UUID id = UUID.randomUUID();
        UUID transporterId = UUID.randomUUID();
        Instant past = fixedInstant.minusSeconds(3600);

        Donation restored = Donation.restore(
                id, demandId, donorId, transporterId, pickupLocation,
                DonationStatus.IN_TRANSIT, past, fixedInstant, null, null
        );

        assertEquals(id, restored.getId());
        assertEquals(transporterId, restored.getTransporterId());
        assertEquals(DonationStatus.IN_TRANSIT, restored.getStatus());
        assertEquals(past, restored.getCreatedAt());
    }

    // =========================================================================
    // TESTES DO MÉTODO: markInTransit
    // =========================================================================

    @Test
    @DisplayName("Deve marcar como em trânsito com sucesso")
    void shouldMarkDonationInTransit() {
        Donation donation = Donation.create(demandId, donorId, pickupLocation);
        UUID transporterId = UUID.randomUUID();

        // Avança tempo
        Instant updateTime = fixedInstant.plusSeconds(600);
        instantUtilsMock.when(InstantUtils::now).thenReturn(updateTime);

        Donation inTransit = donation.markInTransit(transporterId);

        assertEquals(DonationStatus.IN_TRANSIT, inTransit.getStatus());
        assertEquals(transporterId, inTransit.getTransporterId());
        assertEquals(updateTime, inTransit.getUpdatedAt());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar marcar em trânsito se status não for PENDING_PICKUP")
    void shouldThrowExceptionWhenMarkingInTransitFromInvalidStatus() {
        // Simula uma doação já ENTREGUE
        Donation deliveredDonation = Donation.restore(
                UUID.randomUUID(), demandId, donorId, UUID.randomUUID(), pickupLocation,
                DonationStatus.DELIVERED, fixedInstant, fixedInstant, fixedInstant, null
        );

        UUID transporterId = UUID.randomUUID();

        DonationException ex = assertThrows(DonationException.class,
                () -> deliveredDonation.markInTransit(transporterId));

        assertTrue(ex.getMessage().contains("Donation must be PENDING_PICKUP to go IN_TRANSIT"));
    }

    // =========================================================================
    // TESTES DO MÉTODO: markDelivered
    // =========================================================================

    @Test
    @DisplayName("Deve marcar como entregue a partir de IN_TRANSIT")
    void shouldMarkDeliveredFromInTransit() {
        UUID transporterId = UUID.randomUUID();
        Donation inTransit = Donation.restore(
                UUID.randomUUID(), demandId, donorId, transporterId, pickupLocation,
                DonationStatus.IN_TRANSIT, fixedInstant, fixedInstant, null, null
        );

        Instant deliverTime = fixedInstant.plusSeconds(1200);
        instantUtilsMock.when(InstantUtils::now).thenReturn(deliverTime);

        Donation delivered = inTransit.markDelivered();

        assertEquals(DonationStatus.DELIVERED, delivered.getStatus());
        assertEquals(deliverTime, delivered.getDeliveredAt());
        assertEquals(deliverTime, delivered.getUpdatedAt());
    }

    @Test
    @DisplayName("Deve marcar como entregue diretamente de PENDING_PICKUP (caso o doador entregue)")
    void shouldMarkDeliveredFromPendingPickup() {
        Donation pending = Donation.create(demandId, donorId, pickupLocation);

        Donation delivered = pending.markDelivered();

        assertEquals(DonationStatus.DELIVERED, delivered.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar entregar doação cancelada")
    void shouldThrowExceptionWhenDeliveringCanceledDonation() {
        Donation canceled = Donation.restore(
                UUID.randomUUID(), demandId, donorId, null, pickupLocation,
                DonationStatus.CANCELED, fixedInstant, fixedInstant, null, fixedInstant
        );

        DonationException ex = assertThrows(DonationException.class, canceled::markDelivered);

        assertEquals("Donation cannot be delivered from current status", ex.getMessage());
    }

    // =========================================================================
    // TESTES DO MÉTODO: cancel
    // =========================================================================

    @Test
    @DisplayName("Deve cancelar doação pendente")
    void shouldCancelPendingDonation() {
        Donation pending = Donation.create(demandId, donorId, pickupLocation);

        Instant cancelTime = fixedInstant.plusSeconds(100);
        instantUtilsMock.when(InstantUtils::now).thenReturn(cancelTime);

        Donation canceled = pending.cancel();

        assertEquals(DonationStatus.CANCELED, canceled.getStatus());
        assertEquals(cancelTime, canceled.getCanceledAt());
    }

    @Test
    @DisplayName("Deve retornar o mesmo objeto se já estiver cancelada (Idempotência)")
    void shouldReturnSameObjectIfAlreadyCanceled() {
        Donation alreadyCanceled = Donation.restore(
                UUID.randomUUID(), demandId, donorId, null, pickupLocation,
                DonationStatus.CANCELED, fixedInstant, fixedInstant, null, fixedInstant
        );

        Donation result = alreadyCanceled.cancel();

        assertSame(alreadyCanceled, result);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cancelar doação entregue")
    void shouldThrowExceptionWhenCancelingDeliveredDonation() {
        Donation delivered = Donation.restore(
                UUID.randomUUID(), demandId, donorId, UUID.randomUUID(), pickupLocation,
                DonationStatus.DELIVERED, fixedInstant, fixedInstant, fixedInstant, null
        );

        DonationException ex = assertThrows(DonationException.class, delivered::cancel);

        assertEquals("Cannot cancel a DELIVERED donation", ex.getMessage());
    }

    // =========================================================================
    // TESTES DE COBERTURA (Getters, Equals, HashCode, ToString)
    // =========================================================================

    @Test
    @DisplayName("Deve garantir cobertura de todos os Getters")
    void shouldVerifyAllGetters() {
        UUID id = UUID.randomUUID();
        UUID transporterId = UUID.randomUUID();
        Instant now = Instant.now();

        Donation fullDonation = Donation.restore(
                id, demandId, donorId, transporterId, pickupLocation,
                DonationStatus.DELIVERED, now, now, now, now
        );

        assertEquals(id, fullDonation.getId());
        assertEquals(demandId, fullDonation.getDemandId());
        assertEquals(donorId, fullDonation.getDonorId());
        assertEquals(transporterId, fullDonation.getTransporterId());
        assertEquals(pickupLocation, fullDonation.getPickupLocation());
        assertEquals(DonationStatus.DELIVERED, fullDonation.getStatus());
        assertEquals(now, fullDonation.getCreatedAt());
        assertEquals(now, fullDonation.getUpdatedAt());
        assertEquals(now, fullDonation.getDeliveredAt());
        assertEquals(now, fullDonation.getCanceledAt());
    }

    @Test
    @DisplayName("Deve verificar Equals e HashCode")
    void shouldVerifyEqualsAndHashCode() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        // Mesmos IDs
        Donation d1 = Donation.restore(id1, demandId, donorId, null, pickupLocation, DonationStatus.PENDING_PICKUP, fixedInstant, fixedInstant, null, null);
        Donation d1Clone = Donation.restore(id1, demandId, donorId, null, pickupLocation, DonationStatus.PENDING_PICKUP, fixedInstant, fixedInstant, null, null);

        // ID Diferente
        Donation d2 = Donation.restore(id2, demandId, donorId, null, pickupLocation, DonationStatus.PENDING_PICKUP, fixedInstant, fixedInstant, null, null);

        assertEquals(d1, d1Clone);
        assertEquals(d1.hashCode(), d1Clone.hashCode());

        assertNotEquals(d1, d2);
        assertNotEquals(d1, null);
        assertNotEquals(d1, new Object());
    }

    @Test
    @DisplayName("Deve verificar se toString contém informações essenciais")
    void shouldVerifyToString() {
        UUID id = UUID.randomUUID();
        Donation donation = Donation.restore(
                id, demandId, donorId, null, pickupLocation,
                DonationStatus.PENDING_PICKUP, fixedInstant, fixedInstant, null, null
        );

        String str = donation.toString();

        assertNotNull(str);
        assertTrue(str.contains(id.toString()));
        assertTrue(str.contains("Donation"));
        assertTrue(str.contains(DonationStatus.PENDING_PICKUP.name()));
    }

}