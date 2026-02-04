package com.astrocode.api.transporter.presentation.request;

import com.astrocode.api.shared.vo.Location;
import com.astrocode.api.shared.vo.PhoneBR;

public record CreateTransporterRequest(
        String displayName,
        String document,
        PhoneBR phone,
        Location address
) {
}
