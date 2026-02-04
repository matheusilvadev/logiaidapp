package com.astrocode.api.beneficiary.presentation.request;

import com.astrocode.api.shared.vo.Location;
import com.astrocode.api.shared.vo.PhoneBR;

public record CreateBeneficiaryRequest(
        String displayName,
        String document,
        PhoneBR phone,
        Location address
) {
}
