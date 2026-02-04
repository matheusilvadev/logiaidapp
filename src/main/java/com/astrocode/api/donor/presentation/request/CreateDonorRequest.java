package com.astrocode.api.donor.presentation.request;

import com.astrocode.api.shared.vo.Location;
import com.astrocode.api.shared.vo.PhoneBR;

public record CreateDonorRequest(
        String displayName,
        String document,
        PhoneBR phone,
        Location address
) {
}
