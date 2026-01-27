package com.astrocode.api.donation.usecases.donationdemand;

import com.astrocode.api.donation.domain.DonationDemand;
import com.astrocode.api.donation.domain.interfacerepository.DonationDemandRepository;
import com.astrocode.api.donation.usecases.exception.DonationUseCasesException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAllDonationDemandByBeneficiaryIdTest {

    @Mock
    private DonationDemandRepository donationDemandRepository;

    @InjectMocks
    private FindAllDonationDemandByBeneficiaryId useCase;

    @Test
    @DisplayName("Deve retornar lista do beneficiário")
    void shouldReturnBeneficiaryDemands() {
        UUID beneficiaryId = UUID.randomUUID();

        when(donationDemandRepository.findAll()).thenReturn(List.of(mock(DonationDemand.class)));

        List<DonationDemand> beneficiaryList = List.of(mock(DonationDemand.class));
        when(donationDemandRepository.findByBeneficiaryId(beneficiaryId)).thenReturn(beneficiaryList);

        List<DonationDemand> result = useCase.execute(beneficiaryId);

        assertEquals(beneficiaryList, result);
    }

    @Test
    @DisplayName("Deve lançar exceção se não houver NENHUMA demanda no sistema")
    void shouldThrowExceptionWhenGlobalListIsEmpty() {
        when(donationDemandRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(DonationUseCasesException.class, () -> useCase.execute(UUID.randomUUID()));
    }

}